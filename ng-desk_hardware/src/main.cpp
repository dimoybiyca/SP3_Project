#include <Arduino.h>
#include "Display.h"
#include "StateManager.h"
#include "Communicator.h"
#include "Button.h"

#define buttonJoyPin 5
#define buttonStartPin 6
#define buttonStopPin 7

Display *display;
StateManager *stateManager;

Communicator communicator;
Button buttonStart;
Button buttonStop;
Button buttonJoy;

unsigned long ButtonCnt = millis();
unsigned long ListenerCnt = millis();
int joy = 0;
int size = 0;
int offset = 0;

bool first = true;
int active = -1;

void setup()
{
  communicator = Communicator();
  communicator.initSerial(9600);

  stateManager = StateManager::getInstance();

  display = Display::getInstance();
  display->init();

  buttonStart.init(buttonStartPin);
  buttonStop.init(buttonStopPin);
  buttonJoy.init(buttonJoyPin);
}

void loop()
{

  while (stateManager->getConnectionState() == State::DISCONNECTED)
  {
    communicator.establishConnection();
  }

  if (millis() - ButtonCnt > 100)
  {
    if (buttonStop.isChanged())
    {
      bool state = buttonStop.getState();
      if (state && active != -1)
      {
        Serial.print("lnch" + String(active));
      }
      else
      {
        if (active != -1)
        {
          Serial.print("rbt" + String(active));
          active = -1;
        }
      }
    }

    int current = analogRead(A1);
    if (current > 900 && joy != 1)
    {
      joy = 1;
      if (offset < size - 1)
      {
        offset = offset + 1;
      }
    }
    else if (current < 100 && joy != -1)
    {
      joy = -1;
      if (offset > 0)
      {
        offset = offset - 1;
      }
    }
    else if (current > 400 && current < 600)
    {
      joy = 0;
    }

    if (joy != 0 || stateManager->getListState() == State::LIST_CHANGED)
    {
      display->showList();
    }

    if (buttonJoy.isChanged())
    {
      active = offset;
    }

    ButtonCnt = millis();
  }

  if (millis() - ListenerCnt > 60000 || stateManager->getListState() == State::LIST_EMPTHY)
  {
    communicator.receiveList();

    ListenerCnt = millis();
    first = false;
  }
}