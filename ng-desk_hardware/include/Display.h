#ifndef DISPLAY_H

#include <Wire.h>
#include <LiquidCrystal_I2C.h>

#define DISPLAY_H

class Display
{
private:
    LiquidCrystal_I2C lcd;

public:
    Display();
    void init();
    LiquidCrystal_I2C getLCD();
    void print(const char *text);
    void print(String text);
};

#endif