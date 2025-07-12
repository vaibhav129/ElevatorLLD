// InternalButton represents buttons inside elevators for floor selection
package Button;

public class InternalButton implements Button {
    int destinationFloor; // Target floor number for this button
    boolean isLit = false; // Indicates if button is currently pressed/active

    // Constructor: initialize button with destination floor
    public InternalButton(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    // Handle button press event
    @Override
    public void press() {
        isLit = true; // Mark button as pressed
        System.out.println("Internal button pressed for floor " + destinationFloor);
    }
}
