#ifndef BUTTON_H

#include <Arduino.h>

#define BUTTON_H

class Button {
private:
    int pin;
    int previousState;

public:
    Button();
    void init(int pin);
    bool getState();
    bool isChanged();
    int currentValue;
    int previousValue;
    int state;
};

#endif