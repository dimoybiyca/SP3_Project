#pragma once
#ifndef LIST_H

#include <Arduino.h>
#include "StateManager.h"

#define LIST_H

/**
 *@file
 *@brief Contains and manipulate list of projects
 * This class contains an array of Strigns which is
 * an projects received from Service and allows to
 * get projects to output on display
 */
class List
{
private:
    static List *_list;         /**<static field to store himself for singleton*/
    StateManager *stateManager; /**<stateManager reference*/
    int size;                   /**<amount of projects in array*/
    int offset;                 /**<how many projects skip from start to output*/
    String list[20];            /**<array of projects*/

    /**
     * @brief Construct a new List object
     *
     */
    List();

    /**
     * @brief Get the Project object
     *
     * Return the project from list by index
     * @param index index of project in array
     * @return String project name
     */
    String getProject(int index);

public:
    /**
     * @brief Get the Instance object
     *
     * Singleton pattern method to get instance of class
     * @return List* single instance of class List
     */
    static List *getInstance();

    /**
     * @brief Set the Projects Array
     *
     * @param list array of strings with projects
     * @param size size of given array
     */
    void setProjects(String list[], int size);

    /**
     * @brief Downcrease offset
     *
     * Downcrease offset to move list up.Needed to
     * simulate movement through the menu
     */
    void moveUp();

    /**
     * @brief Increase offset
     *
     * Increase offset to move list down. Needed to
     * simulate movement through the menu
     */
    void moveDown();

    /**
     * @brief Get the First Project
     *
     * Return the first project to output on display by index offset
     * @return String project name
     */
    String getFirstProject();

    /**
     * @brief Get the Second Project
     *
     * Return the first project to output on display by index offset + 1
     * or String '==== END ====' if end of list
     * @return String project name
     */
    String getSecondProject();
};

#endif