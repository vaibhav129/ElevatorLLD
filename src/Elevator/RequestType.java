// RequestType enum distinguishes between external and internal requests
package Elevator;

public enum RequestType {
    EXTERNAL, // Request from a floor (someone waiting for elevator)
    INTERNAL  // Request from inside the elevator (button press)
}
