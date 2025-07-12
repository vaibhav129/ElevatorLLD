// Main entry point for the Elevator simulation
import Building.Building;
import Building.Floor;
import Elevator.Direction;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a list of 5 floors (0 to 4)
        List<Floor> floorList = new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            floorList.add(new Floor(i)); // Add each floor to the list
        }
        // Create a building with the list of floors and 2 elevators
        Building b = new Building(floorList,2);
        // Simulate external requests (someone on a floor calls the elevator)
        b.ExternalRequest(0,Direction.UP); // Floor 0 requests UP
        b.ExternalRequest(2,Direction.UP); // Floor 2 requests UP
        // Simulate internal requests (someone inside elevator presses a button)
        b.addInternalRequest(1,3); // Elevator 1: go to floor 3
        b.addInternalRequest(1,4); // Elevator 1: go to floor 4
        b.addInternalRequest(0,5); // Elevator 0: go to floor 5
        // Start the automatic elevator processing loop
        b.startAutomaticProcessing();
    }
}