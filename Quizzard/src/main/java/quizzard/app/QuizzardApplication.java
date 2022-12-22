package quizzard.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class that starts the program
 *
 * @author Tyler Strickland
 *
 */
@SpringBootApplication
public class QuizzardApplication {

    /**
     * Main method for the Quizzard application
     *
     * @param args
     *            command line arguments
     */
    public static void main ( String[] args ) {
        SpringApplication.run( QuizzardApplication.class, args );

    }

}
