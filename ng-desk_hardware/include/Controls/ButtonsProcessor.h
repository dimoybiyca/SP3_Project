#pragma once
#ifndef BUTTONS_PROCESSOR_H

#define buttonStopPin 6
#define buttonStartPin 7

#include <Arduino.h>
#include "Button.h"
#include "StateManager.h"
#include "Communicator.h"

#define BUTTONS_PROCESSOR_H

/**
 * @file
 * @brief Handle buttons
 *
 */
class ButtonsProcessor
{
private:
    StateManager *stateManager; /**<StateManager reference*/
    Communicator *communicator; /**<Communicator reference*/
    List *list;                 /**<List reference*/
    Button startButton;         /**<First button*/
    Button stopButton;          /**<Second button*/

    /**
     * @brief Handle start button
     *
     * private method to reduce amount of code in process method
     * Launch project if there is no active project or reboot
     * active project
     * @see process()
     */
    void processStartButton();

    /**
     * @brief Handle stop button
     *
     * private class to reduce amount of code in process method
     * Stop active project or set project which is on display as
     * active when there is no active project
     * @see process()
     */
    void processStopButton();

public:
    /**
     * @brief Construct a new Buttons Processor object
     *
     */
    ButtonsProcessor();

    /**
     * @brief Init buttons
     *
     */
    void initButtons();

    /**
     * @brief Handles buttons clicks
     *
     */
    void process();
};

#endif