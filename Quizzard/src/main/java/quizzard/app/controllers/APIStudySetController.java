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
import quizzard.app.services.StudySetService;
import quizzard.app.services.UserService;

/**
 * API controller class that handles frontend to backend communication in
 * creating, getting, editing, and deleting study set objects
 *
 * @author Tyler Strickland
 *
 */
@RestController
@SuppressWarnings ( { "rawtypes", "unchecked" } )
public class APIStudySetController extends APIController {

    /**
     * Service class that handles the retrieval, deleting, and saving of
     * StudySet objects
     */
    @Autowired
    private StudySetService studySetService;

    /**
     * Service that handles the retrieval and saving of User objects
     */
    @Autowired
    private UserService     userService;

    /**
     * Returns all study sets that are in the Quizzard system
     *
     * @return list of all StudySets in the Quizzard system
     */
    @GetMapping ( BASE_PATH + "/studysets" )
    public List<StudySet> getStudySets () {
        return studySetService.findAll();
    }

    /**
     * Returns a specific StudySet that a User has in their list of StudySetss
     *
     * @param username
     *            the username of the User to retrieve the StudySet from
     * @param name
     *            the name of the StudySet to retrieve
     * @return a ResponseEntity indicating whether or not the retrieval of the
     *         StudySet was successful or not
     */
    @GetMapping ( BASE_PATH + "/users/{username}/studysets/{name}" )
    public ResponseEntity getStudySet ( @PathVariable final String username, @PathVariable final String name ) {
        User foundUser = userService.findByUsername( username );
        StudySet foundStudySet = studySetService.findByName( name );

        if ( foundUser == null ) {
            return new ResponseEntity( toJson( "The user does not exist" ), HttpStatus.BAD_REQUEST );
        }
        else if ( foundStudySet == null ) {
            return new ResponseEntity( toJson( "The study set with the given name does not exist" ),
                    HttpStatus.BAD_REQUEST );
        }

        List<StudySet> foundUserStudySets = foundUser.getStudySets();
        for ( int i = 0; i < foundUserStudySets.size(); i++ ) {
            StudySet s = foundUserStudySets.get( i );
            if ( s.getName().equals( name ) ) {
                return new ResponseEntity( s, HttpStatus.OK );
            }
        }

        return new ResponseEntity( toJson( "The study set with the given name does not exist" ),
                HttpStatus.BAD_REQUEST );
    }

    /**
     * Creates a new Study Set for the User with the given username
     *
     * @param username
     *            the username of the User to create a new Study Set for
     * @param newStudySet
     *            the StudySet object that will be used to create and save a new
     *            StudySet to the database
     * @return ResponseEntity indicating whether the creation of the StudySet
     *         was successful or not
     */
    @PostMapping ( BASE_PATH + "/users/{username}/studysets" )
    public ResponseEntity createStudySet ( @PathVariable final String username,
            @RequestBody final StudySet newStudySet ) {
        User foundUser = userService.findByUsername( username );
        StudySet foundStudySet = studySetService.findByName( newStudySet.getName() );
        if ( foundStudySet != null ) {
            return new ResponseEntity( toJson( "The study set with the given name already exists" ),
                    HttpStatus.BAD_REQUEST );
        }
        else if ( foundUser == null ) {
            return new ResponseEntity( toJson( "The user does not exist" ), HttpStatus.BAD_REQUEST );
        }
        try {
            StudySet s = new StudySet( newStudySet.getName(), newStudySet.getFlashcards() );
            studySetService.save( s );
            foundUser.getStudySets().add( s );
            userService.save( foundUser );
            return new ResponseEntity( toJson( "Successfully created the Study Set" ), HttpStatus.OK );
        }
        catch ( Exception e ) {
            return new ResponseEntity( toJson( "There was an error creating a new Study Set" ),
                    HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Edits the StudySet with the given name. The editing could involve a
     * simple name change, or the adding or deleting of flashcards in the
     * StudySet
     *
     * @param username
     *            the username of the User who is editing their StudySet
     * @param name
     *            the name of the StudySet to edit
     * @param newStudySet
     *            the StudySet whose fields will be used to edit the already
     *            existing StudySet
     * @return a ResponseEntity indicating whether or not editing of the
     *         StudySet was successful or not
     */
    @PutMapping ( BASE_PATH + "/users/{username}/studysets/{name}" )
    public ResponseEntity editStudySet ( @PathVariable final String username, @PathVariable final String name,
            @RequestBody final StudySet newStudySet ) {
        User foundUser = userService.findByUsername( username );
        StudySet foundStudySet = studySetService.findByName( name );
        if ( foundUser == null ) {
            return new ResponseEntity( toJson( "The user does not exist" ), HttpStatus.BAD_REQUEST );
        }
        else if ( foundStudySet == null ) {
            System.out.println( "%%%%% 149" );
            return new ResponseEntity( toJson( "The study set with the given name does not exist" ),
                    HttpStatus.BAD_REQUEST );
        }

        try {
            foundStudySet.setFlashcards( newStudySet.getFlashcards() );
            userService.save( foundUser );
            studySetService.save( foundStudySet );
            return new ResponseEntity( toJson( "Successfully updated the Study Set" ), HttpStatus.OK );
        }
        catch ( Exception e ) {
            System.out.println( "%%%%% " + e.getMessage() );
            return new ResponseEntity( toJson( "There was an error editing the Study Set" ), HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Deletes a StudySet with the given name from the User with the given
     * username
     *
     * @param username
     *            the username of the User who is deleting a StudySet
     * @param name
     *            the name of the StudySet to delete
     * @return a ResponseEntity indicating whether the deletion of the StudySet
     *         was successful or not
     */
    @DeleteMapping ( BASE_PATH + "/users/{username}/studysets/{name}" )
    public ResponseEntity deleteStudySet ( @PathVariable final String username, @PathVariable final String name ) {
        User foundUser = userService.findByUsername( username );
        StudySet foundStudySet = studySetService.findByName( name );

        if ( foundUser == null ) {
            return new ResponseEntity( toJson( "The user does not exist" ), HttpStatus.BAD_REQUEST );
        }
        else if ( foundStudySet == null ) {
            return new ResponseEntity( toJson( "The study set with the given name does not exist" ),
                    HttpStatus.BAD_REQUEST );
        }

        List<StudySet> foundUserStudySets = foundUser.getStudySets();
        for ( int i = 0; i < foundUserStudySets.size(); i++ ) {
            StudySet s = foundUserStudySets.get( i );
            if ( s.getName().equals( name ) ) {
                foundUser.getStudySets().remove( i );
                studySetService.delete( s );
                userService.save( foundUser );
                return new ResponseEntity( toJson( "Successfully deleted the study set" ), HttpStatus.OK );
            }
        }

        return new ResponseEntity( toJson( "The study set with the given name does not exist" ),
                HttpStatus.BAD_REQUEST );
    }

}
