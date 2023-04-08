#pragma once
#ifndef BUTTONS_PROCESSOR_H

#define buttonStopPin 6
#define buttonStartPin 7

#include <Arduino.h>
#include "Button.h"
#include "StateManager.h"
#include "Communicator.h"

#define BUTTONS_PROCESSOR_H

class ButtonsProcessor
{
private:
    StateManager *stateManager;
    Communicator *communicator;
    List *list;

    Button startButton;
    Button stopButton;

    void processStartButton();
    void processStopButton();

public:
    ButtonsProcessor();
    void initButtons();
    void process();
};

#endif