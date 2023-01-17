package quizzard.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import quizzard.app.models.StudySet;
import quizzard.app.models.User;
import quizzard.app.services.UserService;

/**
 * REST API controller that handles interaction between the frontend and backend
 * dealing with User objects.
 *
 * @author Tyler Strickland
 *
 */
@RestController
@SuppressWarnings ( { "rawtypes", "unchecked" } )
public class APIUserController extends APIController {

    @Autowired
    private UserService userService;

    private User        currentUser;

    /**
     * Allows a user to "login" to Quizzard, making sure they are a valid user
     * in the system and also keeping up with who the current user is
     *
     * @param username
     *            the username the user tries to login with
     * @param password
     *            the password the user tries to login with
     * @return ResponseEntity indicating whether the login was successful or not
     */
    @PostMapping ( BASE_PATH + "/login" )
    public ResponseEntity login ( @RequestBody User user ) {
        User foundUser = userService.findByUsername( user.getUsername() );
        if ( foundUser == null ) {
            return new ResponseEntity( "Invalid username or password", HttpStatus.BAD_REQUEST );
        }
        else if ( !foundUser.getPassword().equals( user.getPassword() ) ) {
            return new ResponseEntity( "Invalid username or password", HttpStatus.BAD_REQUEST );
        }

        currentUser = user;
        return new ResponseEntity( "Success", HttpStatus.OK );

    }

    /**
     * Logs the current user out and updates the current user
     *
     * @param username
     *            the username of the User logging out
     * @return ResponseEntity indicating whether the logout was successful or
     *         not
     */
    @PostMapping ( BASE_PATH + "/logout" )
    public ResponseEntity logout ( @RequestBody User user ) {
        User foundUser = userService.findByUsername( user.getUsername() );

        if ( foundUser == null || !currentUser.getUsername().equals( user.getUsername() ) ) {
            return new ResponseEntity( "Couldn't log out", HttpStatus.BAD_REQUEST );
        }
        currentUser = null;
        return new ResponseEntity( "Success", HttpStatus.OK );
    }

    /**
     * Returns the user currently logged into Quizzard
     *
     * @return the user currently logged into Quizzard
     */
    @GetMapping ( BASE_PATH + "/current_user" )
    public ResponseEntity getCurrentUser () {
        return new ResponseEntity( currentUser, HttpStatus.OK );
    }

    /**
     * Returns all users that are in the Quizzard system
     *
     * @return a list of all users in the Quizzard system
     */
    @GetMapping ( BASE_PATH + "/users" )
    public List<User> getUsers () {
        return userService.findAll();
    }

    /**
     * Returns the user with the specific username
     *
     * @param username
     *            the username of the User to retrieve
     * @return the User with the given username
     */
    @GetMapping ( BASE_PATH + "/users/{username}" )
    public ResponseEntity getUser ( @PathVariable final String username ) {
        User foundUser = userService.findByUsername( username );
        if ( foundUser == null ) {
            return new ResponseEntity( "The user does not exist", HttpStatus.BAD_REQUEST );
        }
        else {
            return new ResponseEntity( foundUser, HttpStatus.OK );
        }
    }

    /**
     * Creates a new User
     *
     * @param newUser
     *            the User object that will be used to create a new User and
     *            save it to the database
     * @return a ResponseEntity that indicates whether the creation of the new
     *         User was successful or not
     */
    @PostMapping ( BASE_PATH + "/users" )
    public ResponseEntity createUser ( @RequestBody User newUser ) {
        User foundUser = userService.findByUsername( newUser.getUsername() );
        if ( foundUser != null ) {
            return new ResponseEntity( "The user already exists", HttpStatus.BAD_REQUEST );
        }
        try {
            User user = new User( newUser.getUsername(), newUser.getPassword(), newUser.getFirstName(),
                    newUser.getLastName(), newUser.getEmail(), newUser.getStudySets() );
            userService.save( user );
            return new ResponseEntity( "Successfully created new User", HttpStatus.OK );
        }
        catch ( Exception e ) {
            return new ResponseEntity( "There was an error creating the new User", HttpStatus.BAD_REQUEST );
        }

    }

    /**
     * Edits the information of the User with the given username
     *
     * @param username
     *            the username of the User to edit
     * @param newUser
     *            the User object whose fields will be used to update the
     *            existing User
     * @return ResponseEntity indicating whether the editing of the User was
     *         successful or not
     */
    @PutMapping ( BASE_PATH + "/users/{username}" )
    public ResponseEntity editUser ( @PathVariable final String username, @RequestBody final User newUser ) {
        User foundUser = userService.findByUsername( username );
        if ( foundUser == null ) {
            return new ResponseEntity( "The user does not exist", HttpStatus.BAD_REQUEST );
        }
        try {
            foundUser.editUser( username, newUser.getPassword(), newUser.getFirstName(), newUser.getLastName(),
                    newUser.getEmail() );
            userService.save( foundUser );
            return new ResponseEntity( "Successfully edited the User", HttpStatus.OK );
        }
        catch ( Exception e ) {
            return new ResponseEntity( "There was an error editing the User", HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Deletes the User with the given username
     *
     * @param username
     *            the username of the User to delete
     * @return ResponseEntity indicating whether the deletion of the User was
     *         successful or not
     */
    @DeleteMapping ( BASE_PATH + "/users/{username}" )
    public ResponseEntity deleteUser ( @PathVariable final String username ) {
        User foundUser = userService.findByUsername( username );
        if ( foundUser == null ) {
            return new ResponseEntity( "The user does not exist", HttpStatus.BAD_REQUEST );
        }
        userService.delete( foundUser );
        return new ResponseEntity( "The user was successfully deleted", HttpStatus.OK );
    }

    /**
     * Returns the StudySets associated with the User with the given username
     *
     * @param username
     *            the username of the User to retrieve StudySets from
     * @return the list of StudySets associated with the User with the given
     *         username
     */
    @GetMapping ( BASE_PATH + "/users/{username}/studysets" )
    public List<StudySet> getUserStudySets ( @PathVariable final String username ) {

        User foundUser = userService.findByUsername( username );
        if ( foundUser == null ) {
            return null;
        }
        return foundUser.getStudySets();
    }

}
