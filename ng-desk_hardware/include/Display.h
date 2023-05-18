#pragma once
#ifndef DISPLAY_H

#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include "List.h"

#define DISPLAY_H

/**
 * @file
 * @brief Wrapper for Arduino I2C display
 *
 * Contains and control Arduino 1602 I2c display. Store
 * current state of display and change it only if it is
 * different from current to prevent it from blinking
 */
class Display
{
private:
    static Display *_display;   /**<static field to store himself for singleton*/
    LiquidCrystal_I2C lcd;      /**<LiquidCrystal_I2C object*/
    List *list;                 /**<reference to list*/
    StateManager *stateManager; /**<reference to stateManager*/
    String firstRow;            /**<first row of display which is now on the display*/
    String secondRow;           /**<second row of display which is now on the display*/

    /**
     * @brief Construct a new Display object
     *
     */
    Display();

    /**
     * @brief print text on display
     *
     * Print String on display on current position
     * @param text text to print
     * @param column position where to print by horizontal
     * @param row position where to print by vertical
     */
    void print(String text, uint8_t column, uint8_t row);

    /**
     * @brief print text on first row of display
     *
     * Print text on first row of display. Compare it with
     * cuurent and print only if it is different to prevent
     * redundant blinking
     * @param text text to print on display
     */
    void printFirstRow(String text);

    /**
     * @brief print text on second row of display
     *
     * Print text on first second of display. Compare it with
     * cuurent and print only if it is different to prevent
     * redundant blinking
     * @param text text to print on display
     */
    void printSecondRow(String text);

public:
    /**
     * @brief Get the Instance object
     *
     * Singleton pattern method to get instance of class
     * @return Display* single instance of class Display
     */
    static Display *getInstance();

    /**
     * @brief Initialize the display
     *
     * Set the address of display on i2c bus and turn on
     * backlight
     */
    void init();

    /**
     * @brief Print logo on display
     *
     * Display hello logo on display while loading and
     * establishing connection
     */
    void showLogo();

    /**
     * @brief print list of projects on display
     *
     */
    void showList();

    /**
     * @brief print current active project
     *
     */
    void showActive();
};

#endif