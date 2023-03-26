#ifndef BUTTON_H

#include <Arduino.h>

#define BUTTON_H

class Button
{
private:
    int pin;
    int previousState;
    int currentValue;
    int previousValue;
    int state;

public:
    Button();
    void init(int pin);
    bool getState();
    bool isChanged();
};

#endif