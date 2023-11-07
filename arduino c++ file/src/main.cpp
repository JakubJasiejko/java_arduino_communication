#include <Arduino.h>

void setup() {
  Serial.begin(9600);
}

void loop() {
  static unsigned long previousMillis = 0;
  const unsigned long interval = 500;

  unsigned long currentMillis = millis();

  if (currentMillis - previousMillis >= interval) {
    previousMillis = currentMillis;

    static int counter = 0;
    Serial.println(counter, DEC);
    counter++;
    if (counter < 0) {
      counter = 0;
    }
  }
}