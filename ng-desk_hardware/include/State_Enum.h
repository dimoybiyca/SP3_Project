#pragma once

/**
 * @file
 * @brief Enum of program states
 *
 */
enum class State
{
    CONNECTED,    /**<arduino is connected to PC*/
    DISCONNECTED, /**<arduino isn't connected to PC*/

    LIST_EMPTHY,         /**<list of projects is empty*/
    LIST_CHANGED,        /**<list was changed and should be printed*/
    LIST_REQUIRE_UPDATE, /**<list of projects was modified on PC*/
    LIST_ACTUAL,         /**<list on display is actual*/

    PROJECT_NONE,     /**<there is no projects selected*/
    PROJECT_SELECTED, /**<project selected*/
    PROJECT_ACTIVE    /**project is active*/
};