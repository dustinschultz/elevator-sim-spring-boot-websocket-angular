/**
 * 
 */
package io.schultz.dustin.elevator.core.operations;

/**
 * Represents an invalid elevator state
 * 
 * @author Dustin Schultz
 *
 */
public class InvalidElevatorState extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public InvalidElevatorState() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidElevatorState(final String message, final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidElevatorState(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public InvalidElevatorState(final String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidElevatorState(final Throwable cause) {
		super(cause);
	}

}
