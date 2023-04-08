#pragma once
#ifndef DISPLAY_H

#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include "List.h"

#define DISPLAY_H

class Display
{
private:
    static Display *_display;

    LiquidCrystal_I2C lcd;
    List *list;
    StateManager *stateManager;

    String firstRow;
    String secondRow;

    Display();

    void print(String text, uint8_t column, uint8_t row);
    void printFirstRow(String text);
    void printSecondRow(String text);

public:
    static Display *getInstance();

    void init();
    void showLogo();
    void showList();
    void showActive();
};

#endif