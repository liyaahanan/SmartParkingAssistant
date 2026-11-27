# Smart Parking Assistant Using Arduino Uno and Java Swing

## 1. Introduction

The Smart Parking Assistant is a parking distance monitoring system developed using an Arduino Uno, an ultrasonic sensor and a Java Swing application.

The system measures the distance between a vehicle and an obstacle and indicates the parking condition through three LED indicators: green, yellow and red.

The project combines hardware development and software programming to demonstrate a simple smart parking solution.

## 2. Project Objectives

- Measure the distance between a vehicle and an obstacle.
- Use an ultrasonic sensor to detect nearby objects.
- Display parking conditions using LED indicators.
- Develop a graphical user interface using Java Swing.
- Establish serial communication between Arduino and Java.

## 3. Hardware Components

The following components are used:

- Arduino Uno
- HC-SR04 ultrasonic distance sensor
- Red, yellow and green LED traffic-light module
- Breadboard
- Jumper wires
- USB cable

## 4. Software and Development Tools

- Arduino IDE
- Wokwi online simulator
- Java Development Kit (JDK 21)
- IntelliJ IDEA
- Java Swing
- jSerialComm library
- GitHub

## 5. Hardware Connections

The project uses the following Arduino pin configuration.

| Component | Arduino Pin |
|---|---|
| Ultrasonic sensor TRIG | D9 |
| Ultrasonic sensor ECHO | D10 |
| Ultrasonic sensor VCC | 5V |
| Ultrasonic sensor GND | GND |
| Red LED control | D2 |
| Yellow LED control | D3 |
| Green LED control | D4 |
| LED module V | 5V |
| LED module GND | GND |

## 6. Parking Detection Logic

The system uses three distance ranges:

| Distance | Parking Status | LED |
|---|---|---|
| Above 50 cm | SAFE | Green |
| 20–50 cm | WARNING | Yellow |
| Below 20 cm | STOP | Red |

## 7. Arduino Programming

The Arduino program performs the following operations:

1. Initializes the ultrasonic sensor and LED pins.
2. Sends an ultrasonic pulse using the TRIG pin.
3. Measures the returning signal using the ECHO pin.
4. Calculates the distance in centimetres.
5. Determines the parking status.
6. Controls the corresponding LED.
7. Sends distance and status information through serial communication.

The serial output format is:

- DIST:75.00
- STATUS:SAFE

## 8. Wokwi Simulation

The initial circuit was developed and tested using the Wokwi online simulator.

The simulation helped with understanding the circuit connections, sensor readings and parking detection logic before working with physical hardware.

**Wokwi Project:** [Insert simulation link]

## 9. Java Swing Application

The graphical user interface was developed using Java Swing in IntelliJ IDEA.

The application includes:

- Application title
- Distance display
- Parking status display
- Colour indicator
- Arduino connection controls
- COM port selection

The jSerialComm library was added to the Java project to support serial communication.

## 10. Testing and Implementation

The project was developed in stages:

1. Created the Arduino circuit in
