#pragma once
#ifndef JOY_PROCESSOR_H

#define JoyPin A1

#include <Arduino.h>
#include "List.h"

#define JOY_PROCESSOR_H

class JoyProcessor
{
private:
    List *list;

    int joy;
    int previousJoy;
    int checkState();
    bool isChanged();

public:
    JoyProcessor();
    void process();
};

#endif