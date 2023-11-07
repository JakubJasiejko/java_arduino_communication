# SerialPortReader - Reading and Saving Data from Serial Port

![Arduino](https://img.shields.io/badge/Arduino-Compatible-brightgreen)
![Java](https://img.shields.io/badge/Java-Compatible-blue)

This project contains code for two applications - one for the Arduino platform and another written in Java. It allows you to read data from the serial port transmitted by an Arduino and save it to a CSV file.

## Arduino

The Arduino code is written in C/C++ and is responsible for generating numerical data and transmitting it over the serial port (UART) in decimal format. In this code, we use the `Serial.print` function instead of `Serial.println` to send data as strings without newlines.

The Arduino code, written in C/C++, serves as a universal method for communication and data display. It is designed to work with a wide range of sensors that can be attached to the microcontroller. The code generates numerical data, which can be sourced from any sensor, and transmits it over the serial port (UART) in decimal format. In this code, we use the `Serial.print` function instead of `Serial.println` to send data as strings without newlines.

### Author
* [Jakub Jasiejko](https://github.com/JakubJasiejko)

## Java

The Java code is written in the Java programming language and uses the [jSerialComm](https://github.com/Fazecast/jSerialComm) library, which facilitates serial communication. The Java code is responsible for reading data from the serial port, converting it to integers, and saving it to a CSV file.

### Author
* [Jakub Jasiejko](https://github.com/JakubJasiejko)

## Usage

1. Connect the Arduino to your computer via the serial port.
2. Upload the Arduino code to the Arduino.
3. Run the Java code on your computer.
4. Configure the COM port and baud rate settings in the Java code.
5. Start the Java code, and data sent by the Arduino will be read and saved to a CSV file.

## Dependencies

- [jSerialComm](https://github.com/Fazecast/jSerialComm)

## License

This project is released under the MIT License - see the [LICENSE](LICENSE) file for more information.
