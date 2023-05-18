#include "Controls/ButtonsProcessor.h"

ButtonsProcessor::ButtonsProcessor() : startButton(Button()),
                                       stopButton(Button())
{
    stateManager = StateManager::getInstance();
    communicator = Communicator::getInstance();
    list = List::getInstance();
}

void ButtonsProcessor::initButtons()
{
    startButton.init(buttonStartPin);
    stopButton.init(buttonStopPin);
}

void ButtonsProcessor::process()
{
    if (startButton.isChanged())
    {
        this->processStartButton();
    }
    else if (stopButton.isChanged())
    {
        this->processStopButton();
    }
}

void ButtonsProcessor::processStartButton()
{
    if (stateManager->getProjectState() == State::PROJECT_SELECTED)
    {
        this->communicator->launchProject();
    }
    else if (stateManager->getProjectState() == State::PROJECT_ACTIVE)
    {
        this->communicator->rebootProject();
    }
}

void ButtonsProcessor::processStopButton()
{
    if (stateManager->getProjectState() == State::PROJECT_ACTIVE)
    {
        this->communicator->stopProject();
        this->stateManager->setListState(State::LIST_CHANGED);
    }
    else if (this->stateManager->getProjectState() != State::PROJECT_ACTIVE)
    {
        this->stateManager->setActiveProject(this->list->getFirstProject());
        this->stateManager->setProjectState(State::PROJECT_SELECTED);
    }
}
