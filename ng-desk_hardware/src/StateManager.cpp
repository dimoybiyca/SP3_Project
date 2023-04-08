#include "StateManager.h"

StateManager *StateManager::_stateManager = nullptr;

StateManager *StateManager::getInstance()
{
    if (_stateManager == nullptr)
    {
        _stateManager = new StateManager();
    }

    return _stateManager;
}

StateManager::StateManager() : connectionState(State::DISCONNECTED),
                               listState(State::LIST_EMPTHY),
                               projectState(State::PROJECT_NONE) {}

State StateManager::getConnectionState()
{
    return connectionState;
}
void StateManager::setConnectionState(State connectionState)
{
    this->connectionState = connectionState;
}

State StateManager::getProjectState()
{
    return projectState;
}
void StateManager::setProjectState(State projectState)
{
    this->projectState = projectState;
}

State StateManager::getListState()
{
    return listState;
}
void StateManager::setListState(State listState)
{
    this->listState = listState;
}
