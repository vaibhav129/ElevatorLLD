// Status enum represents the current state of an elevator
package Elevator;

public enum Status {
    IDLE,         // Not moving, waiting for requests
    MOVING_UP,    // Moving up
    MOVING_DOWN,  // Moving down
    LOADING,      // Doors open, loading/unloading passengers
    MAINTENANCE   // Out of service
}
