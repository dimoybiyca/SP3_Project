#pragma once
#ifndef COMMUNICATOR_H

#include <Arduino.h>
#include "StateManager.h"
#include "List.h"

#define COMMUNICATOR_H

/**
 * @file
 * @brief communicate with PC via Serial
 *
 * Class to communicate with service on PC
 */
class Communicator
{
private:
    static Communicator *_communicator; /**<static field to store himself for singleton*/
    StateManager *stateManager;         /**<StateManager reference*/
    List *list;                         /**<List reference*/
    String arr[20];                     /**array of strings which receive from service*/

    /**
     * @brief Construct a new Communicator object
     *
     */
    Communicator();

    /**
     * @brief process the response of service
     *
     */
    void processResponse();

public:
    /**
     * @brief Get the Instance object
     *
     * Singleton pattern method to get instance of class
     * @return Communicator* single instance of class Communicator
     */
    static Communicator *getInstance();

    /**
     * @brief Initialize serialPort
     *
     * @param baud speed of port
     */
    void initSerial(unsigned long baud);

    /**
     * @brief Establish connection with PC
     *
     */
    void establishConnection();

    /**
     * @brief Check if the state of Arduino is actual
     *
     * Send command to arduino to get know if the state of list or project
     * was changed from external
     */
    void checkState();

    /**
     * @brief Receive list from PC
     *
     * Send command to PC to get response with list of projects
     * or code that notying changed
     */
    void receiveList();

    /**
     * @brief launch active project
     *
     * Send command to PC to launch current active project
     * @return int code of execution
     */
    int launchProject();

    /**
     * @brief reboot active project
     *
     * Send command to PC to reboot current active project
     * @return int code of execution
     */
    int rebootProject();

    /**
     * @brief stop active project
     *
     * Send command to PC to stop current active project
     * @return int code of execution
     */
    int stopProject();
};

/**
 * @brief Enumeration of commands
 *
 * Needs to unify on Arduino and Service
 */
enum class Commands
{
    CHECK_CONNECTION = 1111, /**<command to check connection with PC*/
    LAUNCH_RPOJECT = 2222,   /**<command to launch project*/
    REBOOT_PROJECT = 3333,   /**<command to reboot active project*/
    STOP_PROJECT = 4444,     /**<command to stop active project*/
    CHECK_STATE = 5555,      /**<command to check the state*/
    GET_LIST = 9999          /**<command to get list*/
};

/**
 * @brief Enumeration of codes
 *
 * Needs to unify on Arduino and Service
 */
enum class Codes
{
    OK = 200,        /**<Everything is OK*/
    WAS_CHANGE = 300 /**<Something changed*/
};

#endif