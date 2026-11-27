# Smart Parking Assistant

## Project Overview
The Smart Parking Assistant is an Arduino-based parking distance monitoring system with a Java Swing graphical user interface.

It uses an ultrasonic sensor to measure the distance between a vehicle and an obstacle. Three LEDs indicate the parking safety level.

## Objectives
- Measure distance using an HC-SR04 ultrasonic sensor.
- Display parking distance and status.
- Indicate parking conditions using three LEDs.
- Develop a Java Swing user interface.
- Establish serial communication between Arduino and Java.

## Hardware Components
- Arduino Uno
- HC-SR04 Ultrasonic Sensor
- Red LED
- Yellow LED
- Green LED
- 220-ohm resistors
- Jumper wires

## Pin Connections

| Component | Arduino Pin |
|---|---|
| Red LED | D2 |
| Yellow LED | D3 |
| Green LED | D4 |
| Sensor TRIG | D9 |
| Sensor ECHO | D10 |
| Sensor VCC | 5V |
| Sensor GND | GND |

## Parking Status

| Distance | Status | LED |
|---|---|---|
| Above 50 cm | SAFE | Green |
| 20–50 cm | WARNING | Yellow |
| Below 20 cm | STOP | Red |

## Software Used
- Arduino IDE
- Wokwi Simulator
- Java
- Java Swing
- IntelliJ IDEA
- GitHub

## Project Structure
- Arduino/ – Arduino source code
- Java/ – Java application source code
- Images/ – Project screenshots and evidence
- README.md – Project documentation

## Current Progress
- Arduino prototype created in Wokwi.
- Ultrasonic sensor and LED indicators configured.
- Java Swing application created.
- Project screenshots uploaded to GitHub.

## Future Work
- Complete Java and Arduino serial communication.
- Test the system using physical hardware.
- Improve the graphical user interface.
- Complete system testing and documentation.
- ## Wokwi Simulation
- https://wokwi.com/projects/481773791471033345
