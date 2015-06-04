/**
 * 
 */
package io.schultz.dustin.elevator;

import io.schultz.dustin.elevator.core.Elevator;
import io.schultz.dustin.elevator.core.operations.ElevatorAction;
import io.schultz.dustin.elevator.core.state.ElevatorDirection;

import java.lang.invoke.MethodHandles;
import java.util.Observable;
import java.util.Observer;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests the {@link Elevator}
 * 
 * TODO: Add asserts
 * 
 * @author Dustin Schultz
 */
@Ignore
public class ElevatorTest {
	
	private static final Logger log = LoggerFactory.getLogger(MethodHandles
			.lookup().lookupClass());

	@Test
	public void callElevatorOnce() {
		final Elevator elevator = new Elevator(new Observer() {
			@Override
			public void update(final Observable o, final Object arg) {
				log.debug(arg.toString());
			}
		});
		
		elevator.powerOn();
		// Start on first floor, press the button to go up
		elevator.call(new ElevatorAction(1, ElevatorDirection.UP));
		// Step in, press button
		elevator.pressButtonForFloor(3);
		// Get off elevator
		elevator.shutdown(6000);
	}

	@Test
	public void callElevatorMultipleTimes() {
		final Elevator elevator = new Elevator();
		elevator.powerOn();
		// Start on the 3rd floor, press to go up
		elevator.call(new ElevatorAction(3, ElevatorDirection.UP));
		// Step in, press button
		elevator.pressButtonForFloor(5);

		// Some other person, requesting to go up on floor 4, will pick up
		elevator.call(new ElevatorAction(4, ElevatorDirection.UP));
		elevator.shutdown(25);
	}

	@Test
	public void callElevatorMultipleTimesWrongDirection() {
		final Elevator elevator = new Elevator();
		elevator.powerOn();
		// Start on the 3rd floor, press to go up
		elevator.call(new ElevatorAction(3, ElevatorDirection.UP));
		// Step in, press button
		elevator.pressButtonForFloor(5);

		// Some other person, requesting to go down on floor 4, will skip
		elevator.call(new ElevatorAction(4, ElevatorDirection.DOWN));
		elevator.shutdown(25);
	}
}
