#include "Init.h"

void initSerial(unsigned long baud)
{
    Serial.begin(baud);
    Serial.setTimeout(1000);
}