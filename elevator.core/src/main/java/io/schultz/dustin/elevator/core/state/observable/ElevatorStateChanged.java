/**
 * 
 */
package io.schultz.dustin.elevator.core.state.observable;

import io.schultz.dustin.elevator.core.state.ElevatorState;

/**
 * Simple POJO to track the state of the elevator as it changes
 * 
 * @author Dustin Schultz
 *
 */
public class ElevatorStateChanged {

	private String state;

	private String direction;

	private int currentFloor;

	private int previousFloor;

	private int requestedFloor;

	/**
	 * @param state
	 * @param direction
	 * @param currentFloor
	 * @param previousFloor
	 */
	public ElevatorStateChanged(final ElevatorState state,
			final int currentFloor, final int previousFloor,
			final int requestedFloor) {
		this.state = state.toString();
		this.direction = state.getDirection().toString();
		this.currentFloor = currentFloor;
		this.previousFloor = previousFloor;
		this.requestedFloor = requestedFloor;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(final String state) {
		this.state = state;
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
	 * @return the currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}

	/**
	 * @param currentFloor
	 *            the currentFloor to set
	 */
	public void setCurrentFloor(final int currentFloor) {
		this.currentFloor = currentFloor;
	}

	/**
	 * @return the previousFloor
	 */
	public int getPreviousFloor() {
		return previousFloor;
	}

	/**
	 * @param previousFloor
	 *            the previousFloor to set
	 */
	public void setPreviousFloor(final int previousFloor) {
		this.previousFloor = previousFloor;
	}

	/**
	 * @return the requestedFloor
	 */
	public int getRequestedFloor() {
		return requestedFloor;
	}

	/**
	 * @param requestedFloor
	 *            the requestedFloor to set
	 */
	public void setRequestedFloor(final int requestedFloor) {
		this.requestedFloor = requestedFloor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ElevatorStateChanged [state=" + state + ", direction="
				+ direction + ", currentFloor=" + currentFloor
				+ ", previousFloor=" + previousFloor + ", requestedFloor="
				+ requestedFloor + "]";
	}

}
