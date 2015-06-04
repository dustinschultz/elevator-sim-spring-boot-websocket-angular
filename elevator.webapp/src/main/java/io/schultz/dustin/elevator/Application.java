/**
 * 
 */
package io.schultz.dustin.elevator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Main Application class to bootstrap and run the app.
 * 
 * @author Dustin Schultz
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer{

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
}