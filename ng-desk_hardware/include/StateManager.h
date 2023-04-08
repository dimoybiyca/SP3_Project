#pragma once
#ifndef STATE_MANAGER_H

#include <Arduino.h>
#include "State_Enum.h"

#define STATE_MANAGER_H

class StateManager
{
private:
    static StateManager *_stateManager;

    State connectionState;
    State listState;
    State projectState;

    StateManager();

public:
    static StateManager *getInstance();

    State getConnectionState();
    void setConnectionState(State connectionState);

    State getProjectState();
    void setProjectState(State projectState);

    State getListState();
    void setListState(State listState);
};

#endif