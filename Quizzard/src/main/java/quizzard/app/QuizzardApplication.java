package quizzard.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Class that starts the program
 *
 * @author Tyler Strickland
 *
 */
@SpringBootApplication ( scanBasePackages = { "quizzard.app" } )
@EnableJpaRepositories ( basePackages = { "quizzard.app.repositories" } )
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
