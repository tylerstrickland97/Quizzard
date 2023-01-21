package quizzard.app.controllers;

import com.google.gson.Gson;

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
    protected final String    BASE_PATH = "/api/v1";

    /**
     * Used to serialize data and messages to JSON for transmitting through the
     * REST API
     */
    static final private Gson GSON      = new Gson();

    /**
     * Turns the provided object into JSON
     *
     * @param obj
     *            The object to serialize
     * @return The resulting JSON String
     */
    static final protected String toJson ( final Object obj ) {
        return GSON.toJson( obj );
    }

}
