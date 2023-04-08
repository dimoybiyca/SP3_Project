#include "Display.h"

Display *Display::_display = nullptr;

Display *Display::getInstance()
{
    if (_display == nullptr)
    {
        Display::_display = new Display();
    }

    return Display::_display;
}

Display::Display() : lcd(LiquidCrystal_I2C(0x27, 16, 2))
{
    this->list = List::getInstance();
    this->stateManager = StateManager::getInstance();
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
    delay(200);
    this->printFirstRow(">" + this->list->getFirstProject());
    this->printSecondRow(this->list->getSecondProject());

    this->stateManager->setListState(State::LIST_ACTUAL);
}

void Display::showActive()
{
    this->printFirstRow("A:" + this->stateManager->getActiveProject());
    this->secondRow = "";
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