/**
 * 
 */
package io.schultz.dustin.elevator.core;

import io.schultz.dustin.elevator.core.operations.ElevatorAction;
import io.schultz.dustin.elevator.core.state.ElevatorState;
import io.schultz.dustin.elevator.core.state.observable.ElevatorStateChanged;

import java.lang.invoke.MethodHandles;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The "brains" of the elevator.
 * 
 * Responsible for handling all elevator requests from the queue as well as
 * adding new requests to the queue.
 * 
 * Algorithm is as follows: take the first item in the queue, process item, move
 * floor by floor. If the elevator direction and elevator floor match the
 * current floor, process request regardless of position in queue (e.g. pick up
 * a person on the 4th floor requesting to go up while on your way up to the
 * 5th).
 * 
 * @author Dustin Schultz
 *
 */
public class ElevatorController extends Observable implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles
			.lookup().lookupClass());

	/**
	 * The queue of requests
	 */
	private final LinkedBlockingQueue<ElevatorAction> queue = new LinkedBlockingQueue<>();

	/**
	 * The elevator state machine which tracks the current state and how to
	 * transition from one to the next.
	 */
	private ElevatorState state = ElevatorState.IDLE;

	/**
	 * Current floor elevator is on
	 */
	private int currentFloor = 1;

	/**
	 * Currently requested floor
	 */
	private int currentRequestedFloor;

	/**
	 * Previously floor elevator was on
	 */
	private int previousFloor = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		log.debug("Starting elevator");
		setState(ElevatorState.IDLE);

		while (!Thread.currentThread().isInterrupted()) {
			try {

				log.debug("Elevator is {}, waiting for request", getState());

				final ElevatorAction action = queue.take();
				setCurrentRequestedFloor(action.getFloor());

				log.debug("On floor {}, request for floor {}", currentFloor,
						currentRequestedFloor);

				setPreviousFloor(currentFloor);

				while (currentFloor != currentRequestedFloor) {
					int toFloor = -1;

					if (currentFloor < currentRequestedFloor) {
						toFloor = currentFloor + 1;
					} else {
						toFloor = currentFloor - 1;
					}

					setState(state.sendFromFloorToFloor(currentFloor, toFloor));

					if (state == ElevatorState.MOVING) {
						// Simulate moving between floors
						Thread.sleep(5000);
					}

					log.debug("Elevator is {} from floor {} to floor {}",
							getState(), currentFloor, toFloor);

					// Check if there's anyone to pickup on this floor
					final ElevatorAction possiblePickup = new ElevatorAction(
							toFloor, state.getDirection());

					if (queue.contains(possiblePickup)) {
						log.debug("Found pickup at floor {}, picking up",
								possiblePickup.getFloor());
						setState(state.arrived());
						setState(state.openDoor());
						// Wait for person to get on
						Thread.sleep(1000);
						setState(state.closeDoor());
						queue.remove(possiblePickup);
					}

					setCurrentFloor(toFloor);
				}

				setState(state.arrived());

				if (previousFloor == currentFloor) {
					log.debug("Already at floor {}", action.getFloor());
				} else {
					log.debug("Arrived at floor {}", action.getFloor());
				}

				setState(state.openDoor());
				// Wait for person to get on
				Thread.sleep(1000);
				setState(state.closeDoor());
				// Reset requested floor
				setCurrentRequestedFloor(0);
			} catch (final InterruptedException e) {
				log.error("Interrupted", e);
			}
		}
	}

	/**
	 * Request the elevator with the given action (e.g. 3rd floor, requesting
	 * up)
	 * 
	 * @param action
	 */
	public void request(final ElevatorAction action) {
		// ignore repeated requests
		if (queue.contains(action)) {
			return;
		}
		
		queue.add(action);
	}

	/**
	 * Returns the current floor
	 * 
	 * @return the currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}

	/**
	 * Returns the current state
	 * 
	 * @return the state
	 */
	public ElevatorState getState() {
		return state;
	}

	/**
	 * Sets the current state and notifies observers
	 * 
	 * @param state
	 *            the state to set
	 */
	protected void setState(final ElevatorState state) {
		if ((this.state != state)
				|| (this.state == state && this.state.getDirection() != state
						.getDirection())) {
			this.state = state;
			setChanged();
			notifyObservers(new ElevatorStateChanged(state, currentFloor,
					previousFloor, currentRequestedFloor));
		}
	}

	/**
	 * Sets the current floor and notifies observers
	 * 
	 * @param currentFloor
	 *            the currentFloor to set
	 */
	protected void setCurrentFloor(final int currentFloor) {
		if (this.currentFloor != currentFloor) {
			this.currentFloor = currentFloor;
			setChanged();
			notifyObservers(new ElevatorStateChanged(state, currentFloor,
					previousFloor, currentRequestedFloor));
		}
	}

	/**
	 * Sets the currently requested floor and notifies observers
	 * 
	 * @param currentRequestedFloor
	 *            the currentRequestedFloor to set
	 */
	public void setCurrentRequestedFloor(final int currentRequestedFloor) {
		if (this.currentRequestedFloor != currentRequestedFloor) {
			this.currentRequestedFloor = currentRequestedFloor;
			setChanged();
			notifyObservers(new ElevatorStateChanged(state, currentFloor,
					previousFloor, currentRequestedFloor));
		}
	}

	/**
	 * Sets the previous status and notifies observers
	 * 
	 * @param previousFloor
	 *            the previousFloor to set
	 */
	protected void setPreviousFloor(final int previousFloor) {
		if (this.previousFloor != previousFloor) {
			this.previousFloor = previousFloor;
			setChanged();
			notifyObservers(new ElevatorStateChanged(state, currentFloor,
					previousFloor, currentRequestedFloor));
		}
	}

	/**
	 * Notify observers of the current status
	 */
	public void broadcastStatusToObservers() {
		setChanged();
		notifyObservers(new ElevatorStateChanged(state, currentFloor,
				previousFloor, currentRequestedFloor));
	}

}
