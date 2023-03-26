

#include "Display.h"

Display::Display() : lcd(LiquidCrystal_I2C(0x27, 16, 2)) {}

void Display::init()
{
    lcd.init();
    lcd.backlight();
}

void Display::print(const char *text)
{
    this->lcd.print(text);
}

void Display::print(String text)
{
    this->lcd.print(text);
}

LiquidCrystal_I2C Display::getLCD()
{
    return lcd;
}
