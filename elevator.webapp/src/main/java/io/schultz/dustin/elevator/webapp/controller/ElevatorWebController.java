/**
 * 
 */
package io.schultz.dustin.elevator.webapp.controller;

import io.schultz.dustin.elevator.core.service.ElevatorService;
import io.schultz.dustin.elevator.webapp.observer.ElevatorStatusProducer;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * Web socket elevator controller which takes requests and sends them off to the
 * elevator.
 * 
 * @author Dustin Schultz
 */
@Controller
public class ElevatorWebController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles
			.lookup().lookupClass());

	@Autowired
	private ElevatorService elevatorService;

	@Autowired
	private ElevatorStatusProducer statusProducer;

	@PostConstruct
	public void init() {
		elevatorService.powerOnElevator(statusProducer);
	}

	@MessageMapping("/call")
	public void call(final ElevatorActionMessage action) {
		log.debug("Elevator called");
		elevatorService.callElevator(action.getElevatorAction());
	}

	@MessageMapping("/request")
	public void request(final ElevatorActionMessage action) {
		log.debug("Elevator requested");
		elevatorService.pressButtonForFloor(action.getFloor());
	}

	@MessageMapping("/status")
	public void status() {
		log.debug("Elevator status requested");
		elevatorService.broadcastStatus();
	}
}
