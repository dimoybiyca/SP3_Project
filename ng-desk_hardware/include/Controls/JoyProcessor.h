#pragma once
#ifndef JOY_PROCESSOR_H

#define JoyPin A1

#include <Arduino.h>
#include "List.h"

#define JOY_PROCESSOR_H

/**
 * @file
 * @brief Handle joystick
 *
 */
class JoyProcessor
{
private:
    List *list;      /**<List reference*/
    int joy;         /**<current state of joystick 1 - up 0 - center -1 - down*/
    int previousJoy; /**<previous state of joystick*/

    /**
     * @brief read and return actual state of joy
     *
     * @return 1 if joystick was directed upwards
     * @return 0 if joystick is in the center
     * @return -1 if joystick was directed downwards
     */
    int checkState();

    /**
     * @brief return whether joystic state has changed
     *
     * @return true if it was changed
     * @return false if it wasnt changer
     */
    bool isChanged();

public:
    /**
     * @brief Construct a new Joy Processor object
     *
     */
    JoyProcessor();

    /**
     * @brief Handle direction of joystick
     *
     */
    void process();
};

#endif