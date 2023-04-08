#pragma once
#ifndef COMMUNICATOR_H

#include <Arduino.h>
#include "List.h"
#include "StateManager.h"

#define COMMUNICATOR_H

class Communicator
{
private:
    StateManager *stateManager;
    List *list;
    String arr[20];

public:
    Communicator();
    void initSerial(unsigned long baud);
    void establishConnection();
    void receiveList();
};

enum class Commands
{
    CHECK_CONNECTION = 1111,
    GET_LIST = 9999
};

enum class Codes
{
    OK = 200
};

#endif