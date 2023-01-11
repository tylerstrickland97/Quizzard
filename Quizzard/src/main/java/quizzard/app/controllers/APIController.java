package quizzard.app.controllers;

/**
 * Abstract class that all REST API controllers will extend. This will give each
 * controller class access to the base path string which will be used as the
 * beginning of the path to each mapping in the Quizzard system.
 *
 * @author Tyler Strickland
 *
 */
public abstract class APIController {

    /**
     * Base path to all mappings in the Quizzard system.
     */
    protected final String BASE_PATH = "/api/v1";

}
