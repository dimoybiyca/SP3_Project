#include <Arduino.h>
#include "Init.h"
#include "Display.h"
#include "Button.h"

int buttonStartPin = 6;
int buttonStopPin = 7;
Display display;
Button buttonStart;
Button buttonStop;

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
    delay(100);

    if(buttonStop.isChanged()) 
    {
      bool state  = buttonStop.getState();
        if(state) {
          display.print("Active");
        } 
        else 
        {
          display.print("Disabled");
        }
    }
}