/**
 * 
 */
package io.schultz.dustin.elevator.core.operations;

import io.schultz.dustin.elevator.core.state.ElevatorState;

/**
 * @author Dustin Schultz
 *
 */
public interface ElevatorOperations {
	
	ElevatorState sendFromFloorToFloor(int fromFloor, int toFloor);
	
	ElevatorState arrived();
	
	ElevatorState openDoor();
	
	ElevatorState closeDoor();
}
