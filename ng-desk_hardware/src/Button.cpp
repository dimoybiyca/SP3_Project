#include "Button.h"

Button::Button() : currentValue(LOW),
                   previousValue(LOW),
                   previousState(HIGH),
                   state(LOW) {}

void Button::init(int pin)
{
    this->pin = pin;
    pinMode(pin, INPUT);
}

bool Button::getState()
{
    currentValue = digitalRead(pin);

    if (currentValue != previousValue)
    {
        previousValue = currentValue;

        if (currentValue == LOW)
        {
            state = !state;
        }
    }

    return state;
}

bool Button::isChanged()
{
    this->getState();

    if (state != previousState)
    {
        previousState = state;
        return true;
    }

    return state != previousState;
}