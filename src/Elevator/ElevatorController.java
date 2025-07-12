// ElevatorController manages requests and movement for a single elevator
package Elevator;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

public class ElevatorController {
    public Elevator elevator; // The elevator this controller manages
    Queue<Integer> requestQueue; // Queue of floor requests for this elevator

    // Constructor: initializes the controller for a given elevator
    public ElevatorController(Elevator elevator) {
        this.elevator = elevator;
        this.requestQueue = new LinkedList<>();
    }

    // Add an internal request (from inside the elevator)
    public void addRequest(int destinationFloor) {
        if (destinationFloor > 0 && !requestQueue.contains(destinationFloor)) {
            requestQueue.add(destinationFloor);
        }
    }

    // Handle a new request (external or internal)
    public void handleRequest(ElevatorRequest request) {
        if (request.type == RequestType.EXTERNAL) {
            requestQueue.add(request.source); // Only add the source floor for external requests
        } else {
            requestQueue.add(request.destination); // For internal requests, add the destination floor
        }
        System.out.println("Request added: from floor " + request.source + " to floor " + request.destination + " (" + request.type + ")");
    }

    // Process the next request in the queue (one simulation step)
    public void ProcessRequest() {
        if (!requestQueue.isEmpty()) {
            int nextFloor = requestQueue.peek();

            if (nextFloor == elevator.currentFloor) {
                requestQueue.poll(); // Remove the request from the queue
                elevator.openDoors();
                elevator.closeDoors();
                System.out.println("Elevator " + elevator.elevatorId + " completed request for floor " + nextFloor);
            } else {
                // Move elevator one floor towards the next request
                if (nextFloor > elevator.currentFloor) {
                    elevator.currentFloor++;
                    elevator.direction = Direction.DOWN;
                    elevator.status = Status.MOVING_DOWN;
                } else {
                    elevator.currentFloor--;
                    elevator.direction = Direction.UP;
                    elevator.status = Status.MOVING_UP;
                }
                System.out.println("Elevator " + elevator.elevatorId + " moving to floor " + elevator.currentFloor);
            }
        } else {
            // No requests: set elevator to idle
            elevator.direction = Direction.IDLE;
            elevator.status = Status.IDLE;
        }
    }

    // Check if there are any pending requests
    public boolean hasRequests() {
        return !requestQueue.isEmpty();
    }

    // Get the managed elevator
    public Elevator getElevator() {
        return elevator;
    }
}
