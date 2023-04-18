#include "List.h"

List *List::_list = nullptr;

List::List() : offset(0)
{
    stateManager = StateManager::getInstance();
}

List *List::getInstance()
{
    if (_list == nullptr)
    {
        _list = new List();
    }

    return _list;
}

String List::getProject(int index)
{
    if (size > 0)
    {
        return list[index];
    }

    return "";
}

void List::setProjects(String list[], int size)
{
    this->size = size;
    for (int i = 0; i < size; i++)
    {
        this->list[i] = list[i];
    }
}

void List::moveUp()
{
    if (offset > 0)
    {
        offset = offset - 1;
        stateManager->setListState(State::LIST_CHANGED);
    }
}
void List::moveDown()
{
    if (offset < size - 1)
    {
        offset = offset + 1;
        stateManager->setListState(State::LIST_CHANGED);
    }
}

String List::getFirstProject()
{
    return this->getProject(offset);
}
String List::getSecondProject()
{
    if (offset == size - 1)
    {
        return "===== END =====";
    }

    return this->getProject(offset + 1);
}
