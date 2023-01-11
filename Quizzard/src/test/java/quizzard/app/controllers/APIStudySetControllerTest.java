package quizzard.app.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import quizzard.app.models.Flashcard;
import quizzard.app.models.StudySet;
import quizzard.app.models.User;
import quizzard.app.services.StudySetService;
import quizzard.app.services.UserService;
import quizzard.application.TestConfig;

/**
 * Tests the API Study Set class which handles REST API endpoints associated
 * with Study Sets
 *
 * @author Tyler Strickland
 *
 */
@RunWith ( SpringRunner.class )
@ContextConfiguration ( classes = { TestConfig.class } )
@SpringBootTest ( classes = TestConfig.class )
public class APIStudySetControllerTest {

    /**
     * MVC that is used to use each API endpoint
     */
    private MockMvc               mvc;

    /**
     * Used to create the MVC
     */
    @Autowired
    private WebApplicationContext context;

    /**
     * Service class that retrieves StudySet objects
     */
    @Autowired
    private StudySetService       studySetService;

    /**
     * Service class that retrieves User objects
     */
    @Autowired
    private UserService           userService;

    /**
     * Allows us to create Json Strings
     */
    private static Gson           gson = new Gson();

    /**
     * Sets up the tests.
     */
    @Before
    public void setup () {
        userService.deleteAll();
        studySetService.deleteAll();
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
    }

    /**
     * Tests that returning all study sets works as expected
     *
     * @throws Exception
     */
    @Transactional
    @Test
    public void getStudySets () throws Exception {
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        ArrayList<StudySet> sets = new ArrayList<StudySet>();
        sets.add( s );
        studySetService.save( s );

        Flashcard f3 = new Flashcard( "Respite", "A pause from doing something" );
        Flashcard f4 = new Flashcard( "Pensive", "Deeply or seriously thoughtful" );
        ArrayList<Flashcard> cards2 = new ArrayList<Flashcard>();
        cards2.add( f3 );
        cards2.add( f4 );
        StudySet newSet = new StudySet( "My Study Set", cards2 );
        studySetService.save( newSet );
        assertEquals( 2, studySetService.count() );

        mvc.perform( get( "/api/v1/studysets" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
    }

    /**
     * Tests that retrieving a single Study Set from a User's list of Study Sets
     * works as expected
     *
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetStudySet () throws Exception {
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        ArrayList<StudySet> sets = new ArrayList<StudySet>();
        sets.add( s );
        studySetService.save( s );

        User user = new User( "tyler_strickland14", "password", "Tyler", "Strickland", "tylerstrickland@mail.com",
                sets );

        // Try to retrieve this study set before saving the user to the
        // database. This shouldn't work
        mvc.perform( get( "/api/v1/users/tyler_strickland14/studysets/Study Set For Me" )
                .contentType( MediaType.APPLICATION_JSON ) ).andExpect( status().isBadRequest() );

        // Now save the user and confirm that it works
        userService.save( user );
        mvc.perform( get( "/api/v1/users/tyler_strickland14/studysets/Study Set For Me" )
                .contentType( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );

        // Try to retrieve a Study Set that exists in the system, but is not
        // associated with the specific User
        Flashcard f3 = new Flashcard( "Respite", "A pause from doing something" );
        Flashcard f4 = new Flashcard( "Pensive", "Deeply or seriously thoughtful" );
        ArrayList<Flashcard> cards2 = new ArrayList<Flashcard>();
        cards2.add( f3 );
        cards2.add( f4 );
        StudySet newSet = new StudySet( "My Study Set", cards2 );
        studySetService.save( newSet );
        mvc.perform( get( "/api/v1/users/tyler_strickland14/studysets/My Study Set" )
                .contentType( MediaType.APPLICATION_JSON ) ).andExpect( status().isBadRequest() );

        // Now try to retrieve a StudySet that is associated with the User, but
        // not saved to the database (shouldn't happen, but still testing)
        studySetService.delete( newSet );
        user.getStudySets().add( newSet );
        assertEquals( 1, userService.count() );
        mvc.perform( get( "/api/v1/users/tyler_strickland14/studysets/My Study Set" )
                .contentType( MediaType.APPLICATION_JSON ) ).andExpect( status().isBadRequest() );

    }

    /**
     * Tests that using the API to create a new StudySet works as expected
     */
    @Transactional
    @Test
    public void testCreateStudySet () throws Exception {
        // First, try to create a study set from a user that doesn't exist in
        // the system
        User user = new User( "tyler_strickland14", "password", "Tyler", "Strickland", "tylerstrickland@mail.com",
                null );
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        mvc.perform( post( "/api/v1/users/tyler_strickland14/studysets" ).contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( s ) ) ).andExpect( status().isBadRequest() );
        assertEquals( 0, studySetService.count() );
        assertNull( studySetService.findByName( "Study Set For Me" ) );

        // Now, try to add the study set to the User's list of study sets
        userService.save( user );
        mvc.perform( post( "/api/v1/users/tyler_strickland14/studysets" ).contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( s ) ) ).andExpect( status().isOk() );
        User foundUser = userService.findByUsername( "tyler_strickland14" );
        assertEquals( 1, foundUser.getStudySets().size() );
        assertEquals( 1, studySetService.count() );

        // Now, try to create the same study set again, which shouldn't work
        // since the study set already exists in the system
        mvc.perform( post( "/api/v1/users/tyler_strickland14/studysets" ).contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( s ) ) ).andExpect( status().isBadRequest() );
        assertEquals( 1, foundUser.getStudySets().size() );
        assertEquals( 1, studySetService.count() );

        // Now, try to save a Study Set that has invalid fields, which should
        // not be saved to the database
        Flashcard f3 = new Flashcard( "Respite", "A pause from doing something" );
        Flashcard f4 = new Flashcard( "Pensive", "Deeply or seriously thoughtful" );
        ArrayList<Flashcard> cards2 = new ArrayList<Flashcard>();
        cards2.add( f3 );
        cards2.add( f4 );
        StudySet newSet = new StudySet( null, cards2 );
        mvc.perform( post( "/api/v1/users/tyler_strickland14/studysets" ).contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( newSet ) ) ).andExpect( status().isBadRequest() );
        assertEquals( 1, foundUser.getStudySets().size() );
        assertEquals( 1, studySetService.count() );

    }

    /**
     *
     * @throws Exception
     */
    @Transactional
    @Test
    public void testEditStudySet () throws Exception {
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        ArrayList<StudySet> sets = new ArrayList<StudySet>();
        sets.add( s );
        User user = new User( "tyler_strickland14", "password", "Tyler", "Strickland", "tylerstrickland@mail.com",
                null );

        // First, try to edit a study set as a user that doesn't exist
        mvc.perform( put( "/api/v1/users/tyler_strickland14/studysets/Study Set For Me" )
                .contentType( MediaType.APPLICATION_JSON ).content( asJsonString( s ) ) )
                .andExpect( status().isBadRequest() );

        // Now, save the study set to the database and then try to edit
        userService.save( user );
        mvc.perform( post( "/api/v1/users/tyler_strickland14/studysets" ).contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( s ) ) ).andExpect( status().isOk() );
        StudySet foundStudySet = studySetService.findByName( "Study Set For Me" );
        assertEquals( "Biology", foundStudySet.getFlashcards().get( 0 ).getTerm() );
        assertEquals( "Computer", foundStudySet.getFlashcards().get( 1 ).getTerm() );
        Flashcard f3 = new Flashcard( "Respite", "A pause from doing something" );
        Flashcard f4 = new Flashcard( "Pensive", "Deeply or seriously thoughtful" );
        ArrayList<Flashcard> cards2 = new ArrayList<Flashcard>();
        cards2.add( f3 );
        cards2.add( f4 );
        StudySet editSet = new StudySet( "Study Set For Me", cards2 );
        mvc.perform( put( "/api/v1/users/tyler_strickland14/studysets/Study Set For Me" )
                .contentType( MediaType.APPLICATION_JSON ).content( asJsonString( editSet ) ) )
                .andExpect( status().isOk() );
        // User foundUser = userService.findByUsername( "tyler_strickland14" );
        foundStudySet = studySetService.findByName( "Study Set For Me" );
        assertEquals( "Respite", foundStudySet.getFlashcards().get( 0 ).getTerm() );
        assertEquals( "Pensive", foundStudySet.getFlashcards().get( 1 ).getTerm() );

        // Now try to edit a Study Set that exists in the database, but is not
        // associated with that User which isn't allowed
        Flashcard f5 = new Flashcard( "Teleological",
                "exhibiting or relating to purpose or design especially in nature" );
        Flashcard f6 = new Flashcard( "Charlatan",
                "a person who pretends to know or be something in order to deceive people" );
        ArrayList<Flashcard> cards3 = new ArrayList<Flashcard>();
        cards3.add( f5 );
        cards3.add( f6 );
        StudySet newStudySet = new StudySet( "New Set", cards3 );
        studySetService.save( newStudySet );
        mvc.perform( put( "/api/v1/users/tyler_strickland14/studysets/New Set" )
                .contentType( MediaType.APPLICATION_JSON ).content( asJsonString( editSet ) ) )
                .andExpect( status().isBadRequest() );

    }

    /**
     * Tests that using the delete endpoint works as expected
     */
    @Transactional
    @Test
    public void testDeleteStudySet () throws Exception {
        // First, try to delete a study set as a user that does not exist in the
        // system
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set 1", cards );
        ArrayList<StudySet> sets = new ArrayList<StudySet>();
        sets.add( s );
        Flashcard f3 = new Flashcard( "Respite", "A pause from doing something" );
        Flashcard f4 = new Flashcard( "Pensive", "Deeply or seriously thoughtful" );
        ArrayList<Flashcard> cards2 = new ArrayList<Flashcard>();
        cards2.add( f3 );
        cards2.add( f4 );
        StudySet s2 = new StudySet( "Study Set 2", cards2 );
        sets.add( s2 );

        User user = new User( "tyler_strickland14", "password", "Tyler", "Strickland", "tylerstrickland@mail.com",
                sets );
        mvc.perform( delete( "/api/v1/users/tyler_strickland14/studysets/Study Set 1" )
                .contentType( MediaType.APPLICATION_JSON ) ).andExpect( status().isBadRequest() );

        // Now, save the study sets and the user so they exist, but try to
        // delete a study set that doesn't exist.
        userService.save( user );
        assertEquals( 1, userService.count() );
        assertEquals( 2, studySetService.count() );
        mvc.perform(
                delete( "/api/v1/users/tyler_strickland14/studysets/nope" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isBadRequest() );

        // Now, try to delete a study set that exists, but does not belong to
        // the user trying to delete it.
        Flashcard f5 = new Flashcard( "Teleological",
                "exhibiting or relating to purpose or design especially in nature" );
        Flashcard f6 = new Flashcard( "Charlatan",
                "a person who pretends to know or be something in order to deceive people" );
        ArrayList<Flashcard> cards3 = new ArrayList<Flashcard>();
        cards3.add( f5 );
        cards3.add( f6 );
        StudySet s3 = new StudySet( "Study Set 3", cards3 );
        ArrayList<StudySet> sets2 = new ArrayList<StudySet>();
        sets2.add( s3 );
        User user2 = new User( "quizzard_lover1", "password", "John", "Johnson", "john.johnson@mail.com", sets2 );
        userService.save( user2 );
        assertEquals( 2, userService.count() );
        assertEquals( 3, studySetService.count() );
        mvc.perform( delete( "/api/v1/users/tyler_strickland14/studysets/Study Set 3" )
                .contentType( MediaType.APPLICATION_JSON ) ).andExpect( status().isBadRequest() );

        // Now, delete Study Sets in a valid manner, and ensure that the
        // database is updated correctly
        mvc.perform( delete( "/api/v1/users/tyler_strickland14/studysets/Study Set 1" )
                .contentType( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );
        assertEquals( 2, userService.count() );
        assertEquals( 2, studySetService.count() );
        assertEquals( 2, studySetService.findAll().size() );
        assertNull( studySetService.findByName( "Study Set 1" ) );
        User foundUser = userService.findByUsername( "tyler_strickland14" );
        assertEquals( 1, foundUser.getStudySets().size() );

        mvc.perform( delete( "/api/v1/users/tyler_strickland14/studysets/Study Set 2" )
                .contentType( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );
        assertEquals( 2, userService.count() );
        assertEquals( 1, studySetService.count() );
        assertEquals( 1, studySetService.findAll().size() );
        assertNull( studySetService.findByName( "Study Set 2" ) );
        foundUser = userService.findByUsername( "tyler_strickland14" );
        assertEquals( 0, foundUser.getStudySets().size() );

        mvc.perform( delete( "/api/v1/users/quizzard_lover1/studysets/Study Set 3" )
                .contentType( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );
        assertEquals( 2, userService.count() );
        assertEquals( 0, studySetService.count() );
        assertEquals( 0, studySetService.findAll().size() );
        assertNull( studySetService.findByName( "Study Set 3" ) );
        User foundUser2 = userService.findByUsername( "quizzard_lover1" );
        assertEquals( 0, foundUser2.getStudySets().size() );

    }

    /**
     * Uses Google's GSON parser to serialize a Java object to JSON. Useful for
     * creating JSON representations of our objects when calling API methods.
     *
     * @param obj
     *            to serialize to JSON
     * @return JSON string associated with object
     */
    private static String asJsonString ( final Object obj ) {
        return gson.toJson( obj );
    }

}
