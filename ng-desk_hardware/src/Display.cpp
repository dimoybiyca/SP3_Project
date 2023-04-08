#include "Display.h"

Display *Display::display_ = nullptr;

Display::Display() : lcd(LiquidCrystal_I2C(0x27, 16, 2))
{
    this->list = List::getInstance();
}

void Display::init()
{
    lcd.init();
    lcd.backlight();
    this->showLogo();
}

void Display::showLogo()
{
    lcd.clear();
    this->printFirstRow("=== ng-deck ===");
    this->printSecondRow("Connecting...");
}

void Display::showList()
{
    this->printFirstRow(">" + this->list->getFirstProject());
    this->printSecondRow(this->list->getSecondProject());
}

void Display::print(String text, uint8_t column = 0, uint8_t row = 0)
{
    this->lcd.setCursor(column, row);
    this->lcd.print(text);
}

void Display::printFirstRow(String text)
{
    if (!text.equals(firstRow))
    {
        this->lcd.clear();
        this->print(text, 0, 0);
        firstRow = text;
    }
}

void Display::printSecondRow(String text)
{
    if (!text.equals(secondRow))
    {
        this->print(text, 0, 1);
        secondRow = text;
    }
}

LiquidCrystal_I2C Display::getLCD()
{
    return lcd;
}

Display *Display::getInstance()
{
    if (display_ == nullptr)
    {
        Display::display_ = new Display();
    }

    return Display::display_;
}