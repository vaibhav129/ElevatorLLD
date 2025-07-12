// ElevatorRequest represents a request for elevator service (internal or external)
package Elevator;

public class ElevatorRequest {
    int source; // Source floor (where request originated)
    int destination; // Destination floor (for internal requests)
    Elevator elevator; // (Unused) Elevator reference
    RequestType type; // Type of request (EXTERNAL or INTERNAL)
    Direction direction; // Direction of request (UP, DOWN, IDLE)
    Status status; // (Unused) Status of the request

    // Constructor: create a new elevator request
    public ElevatorRequest(int source, int destination, Direction direction, RequestType type) {
        this.source = source;
        this.destination = destination;
        this.type = type;
        this.direction = direction;
    }
}
