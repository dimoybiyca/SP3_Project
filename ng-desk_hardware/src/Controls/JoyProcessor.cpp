#include "Controls/JoyProcessor.h"

int JoyProcessor::checkState()
{
    int current = analogRead(A1);
    if (current > 900 && joy != 1)
    {
        joy = 1;
    }
    else if (current < 100 && joy != -1)
    {
        joy = -1;
    }
    else if (current > 400 && current < 600)
    {
        joy = 0;
    }
}

bool JoyProcessor::isChanged()
{
    this->checkState();
    bool changed = joy != previousJoy;
    previousJoy = joy;
    return changed;
}

JoyProcessor::JoyProcessor() : joy(0), previousJoy(0)
{
    list = List::getInstance();
}

void JoyProcessor::process()
{
    if (this->isChanged())
    {
        if (joy == 1)
        {
            this->list->moveUp();
        }
        else if (joy == -1)
        {
            this->list->moveDown();
        }
    }
}
