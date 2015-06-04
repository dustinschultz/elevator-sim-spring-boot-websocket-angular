/**
 * 
 */
package io.schultz.dustin.elevator.webapp.controller;

import io.schultz.dustin.elevator.core.operations.ElevatorAction;
import io.schultz.dustin.elevator.core.state.ElevatorDirection;

/**
 * An {@link ElevatorAction} transfer object.
 * 
 * TODO: Remove if determined how to get Jackson to serialize/deserialze enum
 * based on string value.
 * 
 * @author Dustin Schultz
 *
 */
public class ElevatorActionMessage {
	private int floor;

	private String direction;

	/**
	 * @return the floor
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * @param floor
	 *            the floor to set
	 */
	public void setFloor(final int floor) {
		this.floor = floor;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(final String direction) {
		this.direction = direction;
	}

	/**
	 * Convert it to an {@link ElevatorAction} object
	 * 
	 * @return
	 */
	public ElevatorAction getElevatorAction() {
		return new ElevatorAction(floor, ElevatorDirection.valueOf(direction
				.trim().toUpperCase()));
	}

}
