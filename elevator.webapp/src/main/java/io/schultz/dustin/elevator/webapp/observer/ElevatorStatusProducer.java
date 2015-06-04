/**
 * 
 */
package io.schultz.dustin.elevator.webapp.observer;

import java.util.Observable;
import java.util.Observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Dustin Schultz
 *
 */
@Component
public class ElevatorStatusProducer implements Observer {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(final Observable o, final Object state) {
		messagingTemplate.convertAndSend("/topic/elevator", state);
	}

}
