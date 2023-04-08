#include "Communicator.h"

Communicator *Communicator::_communicator = nullptr;

Communicator *Communicator::getInstance()
{
    if (_communicator == nullptr)
    {
        _communicator = new Communicator();
    }

    return _communicator;
}

Communicator::Communicator()
{
    list = List::getInstance();
    stateManager = StateManager::getInstance();
}

void Communicator::initSerial(unsigned long baud)
{
    Serial.begin(baud);
    Serial.setTimeout(2000);
}

void Communicator::establishConnection()
{
    if (stateManager->getConnectionState() == State::DISCONNECTED)
    {
        Serial.flush();
        Serial.print(static_cast<int>(Commands::CHECK_CONNECTION));
        String code = Serial.readString();

        if (code.length() > 0)
        {
            stateManager->setConnectionState(State::CONNECTED);
        }
    }
}

void Communicator::receiveList()
{
    Serial.flush();
    Serial.print(static_cast<int>(Commands::GET_LIST));
    String sizeStr = Serial.readStringUntil(',');

    if (sizeStr.length() > 0)
    {
        int size = sizeStr.toInt();

        for (int i = 0; i < size; i++)
        {
            arr[i] = Serial.readStringUntil(',');
        }

        list->setProjects(arr, size);
        stateManager->setListState(State::LIST_CHANGED);
    }
    else
    {
        stateManager->setConnectionState(State::DISCONNECTED);
    }
}

int Communicator::launchProject()
{
    Serial.flush();
    Serial.print(static_cast<int>(Commands::LAUNCH_RPOJECT) + stateManager->getActiveProject());
    this->stateManager->setProjectState(State::PROJECT_ACTIVE);
}

int Communicator::rebootProject()
{
    Serial.flush();
    Serial.print(static_cast<int>(Commands::REBOOT_PROJECT));
}

int Communicator::stopProject()
{
    Serial.flush();
    Serial.print(static_cast<int>(Commands::STOP_PROJECT));
    this->stateManager->setProjectState(State::PROJECT_SELECTED);
}

void Communicator::processResponse()
{
    int response = Serial.read();
    if (response == static_cast<int>(Codes::OK))
    {
        Serial.println("OK");
    }
    else
    {
        stateManager->setConnectionState(State::DISCONNECTED);
    }
}