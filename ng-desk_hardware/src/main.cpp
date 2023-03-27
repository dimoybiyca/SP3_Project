#include <Arduino.h>
#include "Init.h"
#include "Display.h"
#include "Button.h"

int buttonStartPin = 6;
int buttonStopPin = 7;
Display display;
Button buttonStart;
Button buttonStop;
unsigned long ButtonCnt = millis();
unsigned long ListenerCnt = millis();
int joy = 0;
String list[20];
int size = 0;
int offset = 0;
bool first = true;

void setup()
{
  initSerial(9600);
  display = Display();
  display.init();

  buttonStart.init(buttonStartPin);
  buttonStop.init(buttonStopPin);
}

void loop()
{

  if (millis() - ButtonCnt > 100)
  {
    if (buttonStop.isChanged())
    {
      bool state = buttonStop.getState();
      if (state)
      {
        display.getLCD().clear();
        display.print("Active");
        Serial.print("1");
      }
      else
      {
        display.getLCD().clear();

        Serial.print("0");
      }
    }

    int current = analogRead(A1);
    if (current > 900 && joy != 1)
    {
      joy = 1;
      if (offset < size - 2)
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

    if (joy != 0)
    {
      display.getLCD().clear();
      for (int i = offset; i < offset + 2; i++)
      {
        display.getLCD().setCursor(0, i - offset);
        display.print(list[i]);
      }
    }

    ButtonCnt = millis();
  }

  if (millis() - ListenerCnt > 10000 || first == true)
  {
    Serial.print("900");
    String sizeStr = Serial.readStringUntil(',');

    if (sizeStr.length() > 0)
    {
      size = sizeStr.toInt();

      for (int i = 0; i < size; i++)
      {
        list[i] = Serial.readStringUntil(',');
      }
    }
    ListenerCnt = millis();

    first = false;
  }
}