#pragma once
#ifndef STATE_MANAGER_H

#include <Arduino.h>
#include "State_Enum.h"

#define STATE_MANAGER_H

/**
 * @file
 * @brief program state manager
 *
 * Global class to manage state of program
 */
class StateManager
{
private:
    static StateManager *_stateManager; /**<static field to store himself for singleton*/
    State connectionState;              /**<state of connection to PC*/
    State listState;                    /**<state of list*/
    State projectState;                 /**<state of project*/
    String activeProject;               /**<current active project*/

    /**
     * @brief Construct a new State Manager object
     *
     */
    StateManager();

public:
    /**
     * @brief Get the Instance object
     *
     * Singleton pattern method to get instance of class
     * @return StateManager* single instance of class StateManager
     */
    static StateManager *getInstance();

    /**
     * @brief Get the Connection State object
     *
     * @return State current state of connection
     */
    State getConnectionState();

    /**
     * @brief Set the Connection State object
     *
     * @param connectionState state of connection with PC
     */
    void setConnectionState(State connectionState);

    /**
     * @brief Get the Project State object
     *
     * @return State current state of project
     */
    State getProjectState();

    /**
     * @brief Set the Project State object
     *
     * @param projectState state of project
     */
    void setProjectState(State projectState);

    /**
     * @brief Get the List State object
     *
     * @return State current state of list on display
     */
    State getListState();

    /**
     * @brief Set the List State object
     *
     * @param listState state of list
     */
    void setListState(State listState);

    /**
     * @brief Get the Active Project object
     *
     * @return String current active project
     */
    String getActiveProject();

    /**
     * @brief Set the Active Project object
     *
     * @param project name of project to set as active
     */
    void setActiveProject(String project);
};

#endif