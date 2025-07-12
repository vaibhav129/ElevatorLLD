// Building class manages floors, elevators, and request processing
package Building;

import Elevator.Elevator;
import Elevator.ElevatorController;
import Elevator.ElevatorManager;
import Elevator.*;
import java.util.ArrayList;
import java.util.List;

public class Building {
    ElevatorManager elevatorManager; // Manages all elevator controllers
    Elevator elevator; // (Unused) Single elevator reference
    List<Floor> floors; // List of all floors in the building
    List<Elevator> elevators; // List of all elevators
    int totalFloors; // Total number of floors

    // Constructor: initializes floors and elevators
    public Building(List<Floor> floors, int numberOfElevators) {
        this.floors = floors;
        this.elevators = new ArrayList<>();
        this.totalFloors = floors.size();
        // Create specified number of elevators at floor 0
        for (int i = 0; i < numberOfElevators; i++) {
            elevators.add(new Elevator(i, 0, totalFloors));
        }
        elevatorManager = new ElevatorManager();
        for (Elevator elevator : elevators) {
            elevatorManager.addController(new ElevatorController(elevator));
        }
    }

    // Get a specific floor by its number
    public Floor getFloor(int floorNumber) {
        return floors.get(floorNumber);
    }

    // Get the list of elevators
    public List<Elevator> getElevators() {
        return elevators;
    }

    // Get the elevator manager
    public ElevatorManager getElevatorManager() {
        return elevatorManager;
    }

    // Handle an external request (from a floor)
    public void ExternalRequest(int sourceFloor, Direction direction) {
        ElevatorRequest request = new ElevatorRequest(sourceFloor, sourceFloor, direction, RequestType.EXTERNAL);
        elevatorManager.assignRequest(request);
    }

    // Handle an internal request (from inside an elevator)
    public void addInternalRequest(int elevatorId, int destinationFloor) {
        if (elevatorId >= 0 && elevatorId < elevatorManager.controllers.size()) {
            elevatorManager.controllers.get(elevatorId).addRequest(destinationFloor);
        }
    }

    // Process all requests for all elevators (one simulation step)
    public void processAllRequests() {
        for (ElevatorController controller : elevatorManager.controllers) {
            controller.ProcessRequest();
        }
    }

    // Check if there are any pending requests
    public boolean hasRequests() {
        for (ElevatorController controller : elevatorManager.controllers) {
            if (controller.hasRequests()) {
                return true;
            }
        }
        return false;
    }

    // Start the automatic elevator processing loop (simulation)
    public void startAutomaticProcessing() {
        System.out.println("\n=== STARTING AUTOMATIC ELEVATOR PROCESSING ===");
        int step = 1;
        while (hasRequests()) {
            System.out.println("\n--- Step " + step + " ---");
            processAllRequests();
            step++;

            // Add a small delay to make the simulation more realistic
            try {
                Thread.sleep(1000); // 1 second delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("\n=== ALL REQUESTS COMPLETED ===");
    }
}
