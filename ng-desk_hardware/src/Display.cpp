

#include "Display.h"

Display::Display(): lcd(LiquidCrystal_I2C(0x27, 16,2)) {}

void Display::init() {
    lcd.init();
    lcd.backlight();
}

void Display::print(const char *text) {
    lcd.clear();
    this->lcd.print(text);
}
