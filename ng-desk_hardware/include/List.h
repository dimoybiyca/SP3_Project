#pragma once
#ifndef LIST_H

#include <Arduino.h>

#define LIST_H

class List
{
private:
    static List *_list;

    List();

    String getProject(int index);

    int size;
    int offset;
    String list[20];

public:
    static List *getInstance();

    void setProjects(String list[], int size);

    void incOffset();
    void decOffset();
    String getFirstProject();
    String getSecondProject();
};

#endif