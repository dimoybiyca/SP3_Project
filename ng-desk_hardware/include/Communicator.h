#pragma once
#ifndef COMMUNICATOR_H

#include <Arduino.h>
#include "StateManager.h"
#include "List.h"

#define COMMUNICATOR_H

class Communicator
{
private:
    static Communicator *_communicator;

    StateManager *stateManager;
    List *list;
    String arr[20];

    Communicator();

    void processResponse();

public:
    static Communicator *getInstance();

    void initSerial(unsigned long baud);
    void establishConnection();
    void receiveList();
    int launchProject();
    int rebootProject();
    int stopProject();
};

enum class Commands
{
    CHECK_CONNECTION = 1111,
    LAUNCH_RPOJECT = 2222,
    REBOOT_PROJECT = 3333,
    STOP_PROJECT = 4444,
    GET_LIST = 9999
};

enum class Codes
{
    OK = 200
};

#endif