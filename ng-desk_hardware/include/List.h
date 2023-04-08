#pragma once
#ifndef LIST_H

#include <Arduino.h>
#include "StateManager.h"

#define LIST_H

class List
{
private:
    static List *_list;

    StateManager *stateManager;

    int size;
    int offset;
    String list[20];

    List();

    String getProject(int index);

public:
    static List *getInstance();

    void setProjects(String list[], int size);

    void moveUp();
    void moveDown();
    String getFirstProject();
    String getSecondProject();
};

#endif