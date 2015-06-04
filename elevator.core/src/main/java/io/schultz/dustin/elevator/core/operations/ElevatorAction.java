/**
 * 
 */
package io.schultz.dustin.elevator.core.operations;

import io.schultz.dustin.elevator.core.state.ElevatorDirection;

/**
 * Represents a request for the elevator to a floor with a direction (optional)
 * 
 * @author Dustin Schultz
 */
public class ElevatorAction {
	private final int floor;

	private final ElevatorDirection direction;

	/**
	 * Default constructor
	 * 
	 * @param floor
	 * @param direction
	 */
	public ElevatorAction(final int floor, final ElevatorDirection direction) {
		super();
		this.floor = floor;
		this.direction = direction;
	}

	/**
	 * @return the floor
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * @return the direction
	 */
	public ElevatorDirection getDirection() {
		return direction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + floor;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ElevatorAction other = (ElevatorAction) obj;
		if (direction != other.direction)
			return false;
		if (floor != other.floor)
			return false;

		return true;
	}

}
