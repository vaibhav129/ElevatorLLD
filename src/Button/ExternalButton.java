// ExternalButton represents buttons on floors to call elevators (UP/DOWN)
package Button;

import Elevator.Direction;

public class ExternalButton implements Button {
    Direction direction; // Direction the button calls (UP or DOWN)
    int floorNumber; // Floor number where this button is located

    // Constructor: initialize button with direction and floor number
    public ExternalButton(Direction direction, int floorNumber) {
        this.direction = direction;
        this.floorNumber = floorNumber;
    }

    // Handle button press event
    @Override
    public void press() {
        System.out.println("External button pressed for " + direction + " at floor " + floorNumber);
    }
}