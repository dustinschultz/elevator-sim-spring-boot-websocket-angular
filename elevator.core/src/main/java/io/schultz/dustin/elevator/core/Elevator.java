/**
 * 
 */
package io.schultz.dustin.elevator.core;

import io.schultz.dustin.elevator.core.operations.ElevatorAction;
import io.schultz.dustin.elevator.core.state.ElevatorDirection;

import java.lang.invoke.MethodHandles;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The elevator class which is used to call or travel to a given floor.
 * 
 * This class is mainly a facade/interface and is largely delegated to the
 * {@link ElevatorController}
 * 
 * @author Dustin Schultz
 *
 */
public class Elevator {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles
			.lookup().lookupClass());

	private final ExecutorService executor = Executors.newFixedThreadPool(1);

	/**
	 * The brains of the elevator
	 */
	private final ElevatorController controller = new ElevatorController();

	/**
	 * Default constructor
	 */
	public Elevator() {
	}

	/**
	 * Constructor with observer to be notified about elevator state changes
	 * 
	 * @param observer
	 */
	public Elevator(final Observer observer) {
		controller.addObserver(observer);
	}

	/**
	 * Power on the elevator, start queuing requests
	 */
	public void powerOn() {
		executor.submit(controller);
	}

	/**
	 * Request a floor by pressing the button
	 * 
	 * @param floor
	 */
	public void pressButtonForFloor(final int floor) {
		final int currentFloor = controller.getCurrentFloor();

		if (currentFloor != floor) {
			call(new ElevatorAction(floor, ElevatorDirection.NONE));
		}
	}

	/**
	 * Call the elevator from a given floor with a given direction
	 * (ElevatorAction)
	 * 
	 * @param action
	 */
	public void call(final ElevatorAction action) {
		controller.request(action);
	}

	/**
	 * Shutsdown the elevator, waiting if necessary
	 * 
	 * @param waitSeconds
	 */
	public void shutdown(final int waitSeconds) {
		try {
			executor.awaitTermination(waitSeconds, TimeUnit.SECONDS);
		} catch (final InterruptedException e) {
			// ignore
		}

		log.debug("Shutting down elevator");
	}

	/**
	 * Broadcasts the current status of the elevator
	 */
	public void broadcastStatus() {
		controller.broadcastStatusToObservers();
	}

}
