#include <Arduino.h>
#include "Display.h"
#include "StateManager.h"
#include "Communicator.h"
#include "Controls/JoyProcessor.h"
#include "Controls/ButtonsProcessor.h"

Display *display;
StateManager *stateManager;
Communicator *communicator;

JoyProcessor joyProcessor;
ButtonsProcessor buttonsProcessor;

unsigned long ButtonCnt = millis();
unsigned long ListenerCnt = millis();

void setup()
{
  communicator = Communicator::getInstance();
  stateManager = StateManager::getInstance();
  display = Display::getInstance();
  joyProcessor = JoyProcessor();
  buttonsProcessor = ButtonsProcessor();

  communicator->initSerial(9600);
  display->init();
  buttonsProcessor.initButtons();
}

void loop()
{

  // If there is no connection to PC, execute untill establish
  while (stateManager->getConnectionState() == State::DISCONNECTED)
  {
    communicator->establishConnection();
  }

  if (millis() - ListenerCnt > 60000 || stateManager->getListState() == State::LIST_EMPTHY)
  {
    communicator->receiveList();

    ListenerCnt = millis();
  }

  if (millis() - ButtonCnt > 100)
  {
    if (stateManager->getListState() != State::LIST_EMPTHY)
    {
      if (stateManager->getProjectState() != State::PROJECT_ACTIVE)
      {
        joyProcessor.process();
      }

      if (stateManager->getProjectState() == State::PROJECT_ACTIVE)
      {
        display->showActive();
      }

      if (stateManager->getListState() == State::LIST_CHANGED)
      {
        display->showList();
      }

      buttonsProcessor.process();
    }

    ButtonCnt = millis();
  }
}