#include "Communicator.h"

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
    if (stateManager->getConnectionState() == State::CONNECTED)
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
}