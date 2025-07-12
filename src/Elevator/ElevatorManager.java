// ElevatorManager manages all elevator controllers and assigns requests
package Elevator;

import java.util.ArrayList;
import java.util.List;

public class ElevatorManager {
    Elevator elevator; // (Unused) Single elevator reference
    ElevatorController controller; // (Unused) Single controller reference
    public List<ElevatorController> controllers; // List of all elevator controllers

    // Constructor: initializes the list of controllers
    public ElevatorManager() {
        controllers = new ArrayList<>();
    }

    // Assign a request to the best elevator
    public void assignRequest(ElevatorRequest request) {
        ElevatorController bestController = chooseElevator(request);
        if (bestController != null) {
            bestController.handleRequest(request);
        } else {
            System.out.println("No available elevator for the request.");
        }
    }

    // Choose the best elevator for a request (closest, idle preferred)
    public ElevatorController chooseElevator(ElevatorRequest request) {
        ElevatorController bestController = null;
        int minDistance = Integer.MAX_VALUE;
        for (ElevatorController controller : controllers) {
            int distance = Math.abs(controller.elevator.currentFloor - request.source);
            if (controller.elevator.status == Status.IDLE) {
                distance -= 1000; // Give idle elevators priority
            }
            if (distance < minDistance) {
                minDistance = distance;
                bestController = controller;
            }
        }
        return bestController;
    }

    // Add a new elevator controller to the manager
    public void addController(ElevatorController controller) {
        controllers.add(controller);
    }
}
