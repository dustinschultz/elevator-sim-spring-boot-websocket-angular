/**
 * 
 */
package io.schultz.dustin.elevator.core.state;

import io.schultz.dustin.elevator.core.operations.ElevatorOperations;
import io.schultz.dustin.elevator.core.operations.InvalidElevatorState;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A state machine representing an elevator.
 * 
 * An elevator is in the IDLE state and can go to the moving state, where it can
 * stay until it reaches its destination, at which point it moves to the IDLE
 * state, as only the IDLE state can open or close the door.
 * 
 * Invalid state transitions throw {@link InvalidElevatorState}
 * 
 * @author Dustin Schultz
 *
 */
public enum ElevatorState implements ElevatorOperations {

	/**
	 * The elevator is doing nothing, just waiting
	 */
	IDLE {

		@Override
		public ElevatorState sendFromFloorToFloor(final int fromFloor,
				final int toFloor) {
			return moveFloors(fromFloor, toFloor);
		}

		@Override
		public ElevatorState arrived() {
			return IDLE;
		}

		@Override
		public ElevatorState openDoor() {
			log.debug("Door is opening");
			// Sleep 1 second to simulate opening door
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				log.error("Interrupted", e);
			}
			log.debug("Door is open");

			return IDLE;
		}

		@Override
		public ElevatorState closeDoor() {
			log.debug("Door is closing");
			// Sleep 1 second to simulate closing door
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				log.error("Interrupted", e);
			}
			log.debug("Door is closed");

			return IDLE;
		}

	},

	/**
	 * We're moving in some direction (see ElevatorDirection)
	 */
	MOVING {

		@Override
		public ElevatorState sendFromFloorToFloor(final int fromFloor,
				final int toFloor) {
			return moveFloors(fromFloor, toFloor);
		}

		@Override
		public ElevatorState arrived() {
			return IDLE;
		}

	};

	private static final Logger log = LoggerFactory.getLogger(MethodHandles
			.lookup().lookupClass());

	/**
	 * The direction of the given state
	 */
	private ElevatorDirection direction = ElevatorDirection.NONE;

	/**
	 * 5 stories
	 */
	private static final int MAX_FLOORS = 5;

	/**
	 * Get the elevator direction
	 * 
	 * @return the direction
	 */
	public ElevatorDirection getDirection() {
		return direction;
	}

	/**
	 * Set the elevator direction
	 * 
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(final ElevatorDirection direction) {
		this.direction = direction;
	}

	@Override
	public ElevatorState sendFromFloorToFloor(final int fromFloor,
			final int floor) {
		throw new InvalidElevatorState("sendToFloor is an invalid state");
	}

	@Override
	public ElevatorState openDoor() {
		throw new InvalidElevatorState("openDoor is an invalid state");
	}

	@Override
	public ElevatorState closeDoor() {
		throw new InvalidElevatorState("closeDoor is an invalid state");
	}

	@Override
	public ElevatorState arrived() {
		throw new InvalidElevatorState("arrived is an invalid state");
	}

	/**
	 * Move between floors determining which direction to move based on the
	 * current floor and destination floor.
	 * 
	 * @param fromFloor
	 * @param toFloor
	 * @return
	 */
	protected ElevatorState moveFloors(final int fromFloor, final int toFloor) {
		ElevatorState state = ElevatorState.IDLE;

		if (toFloor <= MAX_FLOORS) {
			if (fromFloor < toFloor) {
				state = MOVING;
				state.direction = ElevatorDirection.UP;
			} else if (fromFloor > toFloor) {
				state = MOVING;
				state.direction = ElevatorDirection.DOWN;
			}
		}

		return state;
	}
}
