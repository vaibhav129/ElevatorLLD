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
        -List~Floor~ floors
        -List~Elevator~ elevators
        -int totalFloors
        +Building(List~Floor~, int)
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
        -List~ElevatorController~ controllers
        +ElevatorManager()
        +assignRequest(ElevatorRequest)
        +chooseElevator(ElevatorRequest)
        +addController(ElevatorController)
    }
    
    class ElevatorController {
        -Elevator elevator
        -Queue~Integer~ requestQueue
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
        -List~InternalButton~ buttonList
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
    
    enum Direction {
        UP
        DOWN
        IDLE
    }
    
    enum Status {
        IDLE
        MOVING_UP
        MOVING_DOWN
        LOADING
        MAINTENANCE
    }
    
    enum RequestType {
        EXTERNAL
        INTERNAL
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
    ElevatorRequest --> Direction
    ElevatorRequest --> RequestType
    Elevator --> Status
    Elevator --> Direction
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

## 📦 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/vaibhav129/ElevatorLLD.git
   cd ElevatorLLD
   ```

2. **Compile the Java files**
   ```bash
   javac -d bin src/*.java src/*/*.java
   ```

## 🏃‍♂️ Running the Application

1. **Run the main class**
   ```bash
   java -cp bin Main
   ```

2. **Expected Output**
   ```
   === STARTING AUTOMATIC ELEVATOR PROCESSING ===
   
   --- Step 1 ---
   Request added: from floor 0 to floor 0 (EXTERNAL)
   Request added: from floor 2 to floor 2 (EXTERNAL)
   Elevator 0 doors opening at floor 0
   Elevator 0 doors closing at floor 0
   Elevator 0 completed request for floor 0
   Elevator 1 moving to floor 1
   
   --- Step 2 ---
   Elevator 0 moving to floor 1
   Elevator 1 moving to floor 2
   
   ... (continues until all requests are processed)
   
   === ALL REQUESTS COMPLETED ===
   ```

## 🔧 Customization

### Adding More Floors
```java
// Create building with more floors
List<Floor> floors = new ArrayList<>();
for(int i = 0; i < 10; i++) {  // 10 floors
    floors.add(new Floor(i));
}
Building building = new Building(floors, 3);  // 3 elevators
```

### Adding More Elevators
```java
// Create building with more elevators
Building building = new Building(floors, 5);  // 5 elevators
```

### Custom Request Patterns
```java
// Add complex request patterns
building.ExternalRequest(0, Direction.UP);
building.ExternalRequest(5, Direction.DOWN);
building.addInternalRequest(0, 3);
building.addInternalRequest(1, 7);
// ... add more requests as needed
```

## 🧪 Testing Scenarios

### Scenario 1: Basic Operation
- Single elevator, multiple floors
- Sequential external requests
- Verify proper elevator movement

### Scenario 2: Multi-Elevator Coordination
- Multiple elevators
- Concurrent requests
- Verify intelligent elevator selection

### Scenario 3: Complex Request Patterns
- Mixed external and internal requests
- Requests in different directions
- Verify efficient processing

## 📈 Performance Considerations

- **Time Complexity**: O(n) for elevator selection, where n is number of elevators
- **Space Complexity**: O(m) for request queues, where m is number of pending requests
- **Scalability**: System can handle multiple elevators and floors efficiently

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

**Vaibhav** - [GitHub Profile](https://github.com/vaibhav129)

---

**Note**: This is a Low Level Design (LLD) implementation focusing on object-oriented design principles, design patterns, and system architecture. The code is well-commented and structured for easy understanding and modification. 