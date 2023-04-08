#pragma once
#ifndef BUTTON_H

#include <Arduino.h>

#define BUTTON_H

class Button
{
private:
    int pin;
    int currentValue;
    int previousValue;
    int previousState;
    int state;

public:
    Button();
    void init(int pin);
    bool getState();
    bool isChanged();
};

#endif