// Floor class represents a single floor in the building
package Building;

import Button.ExternalButton;
import Elevator.Direction;

public class Floor {
    int floorNumber; // The floor's number (0-based)
    ExternalButton upButton; // Button to call elevator UP
    ExternalButton downButton; // Button to call elevator DOWN

    // Constructor: initializes the floor and its buttons
    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.upButton = new ExternalButton(Direction.UP, floorNumber);
        this.downButton = new ExternalButton(Direction.DOWN, floorNumber);
    }

    // Simulate pressing the UP button on this floor
    public void pressUpButton() {
        upButton.press();
    }

    // Simulate pressing the DOWN button on this floor
    public void pressDownButton() {
        downButton.press();
    }
}
