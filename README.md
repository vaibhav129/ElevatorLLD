# Elevator System - Low Level Design

A comprehensive Java implementation of an elevator system with multiple elevators, floors, and intelligent request handling.

## 🏗️ System Overview

This elevator system simulates a multi-floor building with multiple elevators that can handle both external requests (from floors) and internal requests (from inside elevators). The system uses intelligent elevator selection and efficient request processing.

## 📋 Table of Contents

- [System Architecture](#system-architecture)
- [Class Diagram](#class-diagram)
- [Class Descriptions](#class-descriptions)
- [System Flow](#system-flow)
- [Key Features](#key-features)
- [Usage](#usage)
- [Installation](#installation)
- [Running the Application](#running-the-application)

## 🏛️ System Architecture

The system follows a layered architecture with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                        Main.java                            │
│                    (Entry Point)                            │
└─────────────────────┬───────────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────────┐
│                    Building.java                            │
│              (System Coordinator)                           │
└─────────────────────┬───────────────────────────────────────┘
                      │
        ┌─────────────┼─────────────┐
        │             │             │
┌───────▼──────┐ ┌────▼────┐ ┌─────▼─────┐
│ Floor.java   │ │Elevator │ │Elevator   │
│              │ │Manager  │ │Controller │
└──────────────┘ └─────────┘ └───────────┘
                          │         │
                          │    ┌────▼────┐
                          │    │Elevator │
                          │    │         │
                          │    └─────────┘
                          │
                    ┌─────▼────┐
                    │Elevator  │
                    │Request   │
                    └──────────┘
```

## 📊 Class Diagram

```mermaid
classDiagram
    class Main {
        +main(String[] args)
    }
    
    class Building {
        -ElevatorManager elevatorManager
        -List floors
        -List elevators
        -int totalFloors
        +Building(List, int)
        +ExternalRequest(int, Direction)
        +addInternalRequest(int, int)
        +startAutomaticProcessing()
        +processAllRequests()
        +hasRequests()
    }
    
    class Floor {
        -int floorNumber
        -ExternalButton upButton
        -ExternalButton downButton
        +Floor(int)
        +pressUpButton()
        +pressDownButton()
    }
    
    class ElevatorManager {
        -List controllers
        +ElevatorManager()
        +assignRequest(ElevatorRequest)
        +chooseElevator(ElevatorRequest)
        +addController(ElevatorController)
    }
    
    class ElevatorController {
        -Elevator elevator
        -Queue requestQueue
        +ElevatorController(Elevator)
        +addRequest(int)
        +handleRequest(ElevatorRequest)
        +ProcessRequest()
        +hasRequests()
    }
    
    class Elevator {
        -int elevatorId
        -Status status
        -Direction direction
        -List buttonList
        -int currentFloor
        +Elevator(int, int, int)
        +openDoors()
        +closeDoors()
        +moveToFloor(int)
        +getStatus()
        +setStatus(Status)
    }
    
    class ElevatorRequest {
        -int source
        -int destination
        -RequestType type
        -Direction direction
        +ElevatorRequest(int, int, Direction, RequestType)
    }
    
    class ExternalButton {
        -Direction direction
        -int floorNumber
        +ExternalButton(Direction, int)
        +press()
    }
    
    class InternalButton {
        -int floorNumber
        +InternalButton(int)
        +press()
    }
    
    Main --> Building
    Building --> Floor
    Building --> ElevatorManager
    Building --> Elevator
    ElevatorManager --> ElevatorController
    ElevatorController --> Elevator
    ElevatorController --> ElevatorRequest
    Floor --> ExternalButton
    Elevator --> InternalButton
```

## 🏢 Class Descriptions

### Core Classes

#### 1. **Main.java**
- **Purpose**: Entry point of the application
- **Responsibilities**: 
  - Initialize the building with floors and elevators
  - Create sample requests (external and internal)
  - Start the automatic processing simulation

#### 2. **Building.java**
- **Purpose**: Central coordinator for the entire elevator system
- **Responsibilities**:
  - Manage all floors and elevators
  - Handle external requests from floors
  - Handle internal requests from elevators
  - Coordinate the automatic processing loop
  - Provide system-wide status information

#### 3. **Floor.java**
- **Purpose**: Represents a single floor in the building
- **Responsibilities**:
  - Maintain floor number
  - Manage UP and DOWN external buttons
  - Handle button press events

#### 4. **Elevator.java**
- **Purpose**: Represents a single elevator car
- **Responsibilities**:
  - Track current floor, status, and direction
  - Manage internal buttons for each floor
  - Handle door operations (open/close)
  - Provide elevator state information

### Management Classes

#### 5. **ElevatorManager.java**
- **Purpose**: Manages all elevator controllers and request assignment
- **Responsibilities**:
  - Maintain list of all elevator controllers
  - Assign incoming requests to the best elevator
  - Implement elevator selection algorithm (closest + idle priority)
  - Coordinate between multiple elevators

#### 6. **ElevatorController.java**
- **Purpose**: Controls a single elevator's behavior and request processing
- **Responsibilities**:
  - Manage request queue for one elevator
  - Process requests one by one
  - Control elevator movement (up/down/idle)
  - Handle door operations when reaching destination
  - Remove completed requests from queue

#### 7. **ElevatorRequest.java**
- **Purpose**: Represents a request for elevator service
- **Responsibilities**:
  - Store request details (source, destination, type, direction)
  - Distinguish between external and internal requests
  - Provide request information for processing

### Supporting Classes

#### 8. **ExternalButton.java**
- **Purpose**: Represents buttons on floors to call elevators
- **Responsibilities**:
  - Handle UP/DOWN button presses
  - Store floor number and direction

#### 9. **InternalButton.java**
- **Purpose**: Represents buttons inside elevators for floor selection
- **Responsibilities**:
  - Handle floor selection button presses
  - Store target floor number

### Enums

#### 10. **Direction.java**
- **Values**: UP, DOWN, IDLE
- **Purpose**: Represents elevator movement direction or request direction

#### 11. **Status.java**
- **Values**: IDLE, MOVING_UP, MOVING_DOWN, LOADING, MAINTENANCE
- **Purpose**: Represents current elevator state

#### 12. **RequestType.java**
- **Values**: EXTERNAL, INTERNAL
- **Purpose**: Distinguishes between floor requests and elevator button requests

## 🔄 System Flow

### 1. **Initialization Phase**
```
Main.java → Building.java → Creates Floors & Elevators → ElevatorManager → ElevatorControllers
```

### 2. **Request Handling Flow**
```
External Request: Floor → Building → ElevatorManager → Choose Best Elevator → ElevatorController → Add to Queue
Internal Request: Elevator → Building → Direct to Specific ElevatorController → Add to Queue
```

### 3. **Processing Flow**
```
ElevatorController → Check Queue → Process Next Request → Move Elevator → Open/Close Doors → Remove Completed Request
```

### 4. **Elevator Selection Algorithm**
1. Calculate distance from each elevator to request source
2. Give priority to idle elevators (subtract 1000 from distance)
3. Select elevator with minimum calculated distance
4. Assign request to selected elevator

### 5. **Request Processing Algorithm**
1. Check if elevator is at requested floor
2. If yes: Open doors → Close doors → Remove request from queue
3. If no: Move one floor towards destination
4. Update elevator status and direction
5. Repeat until all requests are processed

## ✨ Key Features

### 🎯 **Intelligent Elevator Selection**
- Closest elevator prioritization
- Idle elevator preference
- Efficient request distribution

### 🔄 **Automatic Processing**
- Continuous request processing loop
- Real-time elevator movement simulation
- Automatic request completion

### 🏢 **Multi-Elevator Support**
- Support for multiple elevators
- Independent elevator controllers
- Coordinated request management

### 📊 **Comprehensive Logging**
- Step-by-step simulation output
- Elevator movement tracking
- Request completion notifications

### 🎮 **Flexible Request System**
- External requests (from floors)
- Internal requests (from elevators)
- Direction-aware processing

## 🚀 Usage

### Basic Usage Example
```java
// Create building with 5 floors and 2 elevators
List<Floor> floors = new ArrayList<>();
for(int i = 0; i < 5; i++) {
    floors.add(new Floor(i));
}
Building building = new Building(floors, 2);

// Add external requests (from floors)
building.ExternalRequest(0, Direction.UP);  // Floor 0 wants to go UP
building.ExternalRequest(2, Direction.UP);  // Floor 2 wants to go UP

// Add internal requests (from inside elevators)
building.addInternalRequest(1, 3);  // Elevator 1: go to floor 3
building.addInternalRequest(1, 4);  // Elevator 1: go to floor 4
building.addInternalRequest(0, 5);  // Elevator 0: go to floor 5

// Start automatic processing
building.startAutomaticProcessing();
```
---
