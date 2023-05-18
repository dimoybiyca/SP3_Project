#pragma once
#ifndef BUTTON_H

#include <Arduino.h>

#define BUTTON_H

/**
 * @file
 * @brief Wrapper for button
 *
 * facilitates the handling of button presses
 */
class Button
{
private:
    int pin;           /**<pin where the button is connected*/
    int currentValue;  /**<current value of button*/
    int previousValue; /**<previous value of button*/
    int previousState; /**<previous state of button*/
    int state;         /**current state of button*/

public:
    /**
     * @brief Construct a new Button object
     *
     */
    Button();

    /**
     * @brief Initialize button
     *
     * Set pin where button is connected to read
     * @param pin pin of button
     */
    void init(int pin);

    /**
     * @brief Get the State object
     *
     * @return true if button is activeted
     * @return false if button is deactivated
     */
    bool getState();

    /**
     * @brief returns whether the state of the button has changed
     *
     * @return true if button state changed
     * @return false if button state doesn't changed
     */
    bool isChanged();
};

#endif