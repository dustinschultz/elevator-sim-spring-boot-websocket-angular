/**
 * 
 */
package io.schultz.dustin.elevator.core.service;

import io.schultz.dustin.elevator.core.Elevator;
import io.schultz.dustin.elevator.core.operations.ElevatorAction;

import java.util.Observable;
import java.util.Observer;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

/**
 * @author Dustin Schultz
 *
 */
@Service
public class ElevatorService extends Observable {
	private Elevator elevator;

	public void powerOnElevator(final Observer observer) {
		elevator = new Elevator(observer);
		elevator.powerOn();
	}

	public void pressButtonForFloor(final int floor) {
		elevator.pressButtonForFloor(floor);
	}

	public void callElevator(final ElevatorAction action) {
		elevator.call(action);
	}
	
	public void broadcastStatus() {
		elevator.broadcastStatus();
	}

	@PreDestroy
	public void shutdownElevator() {
		elevator.shutdown(0);
	}
}
