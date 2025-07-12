// Elevator class represents a single elevator in the building
package Elevator;

import Button.InternalButton;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    int elevatorId; // Elevator ID
    Status status; // Current status (IDLE, MOVING, etc.)
    Direction direction; // Current direction (UP, DOWN, IDLE)
    List<InternalButton> buttonList; // List of internal buttons for each floor
    int currentFloor; // Current floor of the elevator

    // Constructor: initializes elevator at a given floor with buttons for all floors
    public Elevator(int elevatorId, int currentFloor, int totalFloors) {
        this.elevatorId = elevatorId;
        this.status = Status.IDLE;
        this.direction = Direction.IDLE;
        this.buttonList = new ArrayList<>();
        for (int i = 0; i < totalFloors; i++) {
            buttonList.add(new InternalButton(i));
        }
    }

    // Get the current status of the elevator
    public Status getStatus() {
        return status;
    }

    // Set the status of the elevator
    public void setStatus(Status status) {
        this.status = status;
    }

    // Move the elevator directly to a specific floor
    public void moveToFloor(int floor) {
        this.currentFloor = floor;
    }

    // Simulate opening the elevator doors
    public void openDoors() {
        System.out.println("Elevator " + elevatorId + " doors opening at floor " + currentFloor);
        this.status = Status.LOADING;
    }

    // Simulate closing the elevator doors
    public void closeDoors() {
        System.out.println("Elevator " + elevatorId + " doors closing at floor " + currentFloor);
        this.status = Status.IDLE;
    }
}
