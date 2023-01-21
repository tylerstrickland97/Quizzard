package quizzard.app.controllers;

import static org.junit.Assert.assertEquals;
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
 * Tests that the APIUserController works correctly and allows frontend to
 * backend communication to retrieve, create, update, and delete User entities
 *
 * @author Tyler Strickland
 *
 */
@RunWith ( SpringRunner.class )
@ContextConfiguration ( classes = { TestConfig.class } )
@SpringBootTest ( classes = TestConfig.class )
public class APIUserControllerTest {

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

    @Transactional
    @Test
    /**
     * Ensures that retrieving the list of users works as expected
     *
     * @throws Exception
     */
    public void testGetUsers () throws Exception {
        User user1 = new User( "tyler_strickland14", "password", "Tyler", "Strickland", "tylerstrickland@mail.com",
                null );
        User user2 = new User( "quizzard_lover1", "mypassword", "John", "Johnson", "john.johnson@mail.com", null );
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        ArrayList<StudySet> sets = new ArrayList<StudySet>();
        sets.add( s );
        User user3 = new User( "academic_weapon", "weapon23", "Max", "James", "jamesmax@mail.com", sets );
        userService.save( user1 );
        userService.save( user2 );
        userService.save( user3 );
        assertEquals( 3, userService.count() );
        assertEquals( 1, studySetService.count() );
        mvc.perform( get( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );

        // Try to retrieve users using the wrong url
        mvc.perform( get( "/api/v1/user" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNotFound() );
    }

    @Transactional
    @Test
    /**
     * Ensures that retrieving single users works as expected
     *
     * @throws Exception
     */
    public void testGetUser () throws Exception {
        User user1 = new User( "tyler_strickland14", "password", "Tyler", "Strickland", "tylerstrickland@mail.com",
                null );
        User user2 = new User( "quizzard_lover1", "mypassword", "John", "Johnson", "john.johnson@mail.com", null );
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        ArrayList<StudySet> sets = new ArrayList<StudySet>();
        sets.add( s );
        User user3 = new User( "academic_weapon", "weapon23", "Max", "James", "jamesmax@mail.com", sets );
        userService.save( user1 );
        userService.save( user2 );
        userService.save( user3 );
        assertEquals( 3, userService.count() );
        assertEquals( 1, studySetService.count() );

        // First, try to retrieve users that don't exist
        mvc.perform( get( "/api/v1/users/nope" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isBadRequest() );
        mvc.perform( get( "/api/v1/users/othernope" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isBadRequest() );

        // Then, try to retrieve users using the wrong url
        mvc.perform( get( "/api/v1/wrongurl/users/tyler_strickland14" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNotFound() );

        // Now, try to retrieve users correctly
        mvc.perform( get( "/api/v1/users/tyler_strickland14" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
        mvc.perform( get( "/api/v1/users/quizzard_lover1" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
        mvc.perform( get( "/api/v1/users/academic_weapon" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
    }

    @Transactional
    @Test
    /**
     * Ensures that creating new users works as expected
     *
     * @throws Exception
     */
    public void testCreateUser () throws Exception {
        User user1 = new User( "tyler_strickland14", "password", "Tyler", "Strickland", "tylerstrickland@mail.com",
                null );
        User user2 = new User( "quizzard_lover1", "mypassword", "John", "Johnson", "john.johnson@mail.com", null );
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        ArrayList<StudySet> sets = new ArrayList<StudySet>();
        sets.add( s );
        User user3 = new User( "academic_weapon", "weapon23", "Max", "James", "jamesmax@mail.com", sets );

        // First, add the users validly
        assertEquals( 0, userService.count() );
        assertEquals( 0, userService.findAll().size() );
        mvc.perform(
                post( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( user1 ) ) )
                .andExpect( status().isOk() );

        assertEquals( 1, userService.count() );
        assertEquals( 1, userService.findAll().size() );
        mvc.perform(
                post( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( user2 ) ) )
                .andExpect( status().isOk() );

        assertEquals( 2, userService.count() );
        assertEquals( 2, userService.findAll().size() );
        mvc.perform(
                post( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( user3 ) ) )
                .andExpect( status().isOk() );
        assertEquals( 3, userService.count() );
        assertEquals( 3, userService.findAll().size() );

        User foundUser1 = userService.findByUsername( "tyler_strickland14" );
        assertEquals( 0, foundUser1.getStudySets().size() );
        assertEquals( "tylerstrickland@mail.com", foundUser1.getEmail() );

        User foundUser2 = userService.findByUsername( "quizzard_lover1" );
        assertEquals( 0, foundUser2.getStudySets().size() );
        assertEquals( "john.johnson@mail.com", foundUser2.getEmail() );

        User foundUser3 = userService.findByUsername( "academic_weapon" );
        assertEquals( 1, foundUser3.getStudySets().size() );
        assertEquals( "jamesmax@mail.com", foundUser3.getEmail() );

        // Now, try to create users that already exist, which shouldn't work
        mvc.perform(
                post( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( user1 ) ) )
                .andExpect( status().isBadRequest() );

    }

    @Transactional
    @Test
    /**
     * Ensures that editing a user works as expected
     *
     * @throws Exception
     */
    public void testEditUser () throws Exception {
        User user1 = new User( "tyler_strickland14", "password", "Tyler", "Strickland", "tylerstrickland@mail.com",
                null );
        User user2 = new User( "quizzard_lover1", "mypassword", "John", "Johnson", "john.johnson@mail.com", null );
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        ArrayList<StudySet> sets = new ArrayList<StudySet>();
        sets.add( s );
        User user3 = new User( "academic_weapon", "weapon23", "Max", "James", "jamesmax@mail.com", sets );

        userService.save( user1 );
        userService.save( user2 );
        userService.save( user3 );

        User userToEdit1 = new User( "tyler_strickland14", "NewPassword", "tyler", "strickland",
                "strickland.tyler@mail.com", null );
        User userToEdit2 = new User( "quizzard_lover1", "loverofquizzard", "Johnny", "Johnson",
                "johnnyjohnson1@mail.com", null );
        User userToEdit3 = new User( "academic_weapon", "weapon24", "Maximus", "James-Smith", "maxjamessmith@mail.com",
                sets );

        // First, try to edit a user that isn't in the database
        mvc.perform( put( "/api/v1/users/nope" ).contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( userToEdit1 ) ) ).andExpect( status().isBadRequest() );

        // Now, try to validly edit users
        User foundUser1 = userService.findByUsername( "tyler_strickland14" );
        assertEquals( "password", foundUser1.getPassword() );
        assertEquals( "Tyler", foundUser1.getFirstName() );
        assertEquals( "Strickland", foundUser1.getLastName() );
        assertEquals( "tylerstrickland@mail.com", foundUser1.getEmail() );
        assertEquals( 0, foundUser1.getStudySets().size() );
        mvc.perform( put( "/api/v1/users/tyler_strickland14" ).contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( userToEdit1 ) ) ).andExpect( status().isOk() );
        foundUser1 = userService.findByUsername( "tyler_strickland14" );
        assertEquals( "NewPassword", foundUser1.getPassword() );
        assertEquals( "tyler", foundUser1.getFirstName() );
        assertEquals( "strickland", foundUser1.getLastName() );
        assertEquals( "strickland.tyler@mail.com", foundUser1.getEmail() );
        assertEquals( 0, foundUser1.getStudySets().size() );

        User foundUser2 = userService.findByUsername( "quizzard_lover1" );
        assertEquals( "mypassword", foundUser2.getPassword() );
        assertEquals( "John", foundUser2.getFirstName() );
        assertEquals( "Johnson", foundUser2.getLastName() );
        assertEquals( "john.johnson@mail.com", foundUser2.getEmail() );
        assertEquals( 0, foundUser2.getStudySets().size() );
        mvc.perform( put( "/api/v1/users/tyler_strickland14" ).contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( userToEdit2 ) ) ).andExpect( status().isOk() );
        foundUser2 = userService.findByUsername( "tyler_strickland14" );
        assertEquals( "loverofquizzard", foundUser2.getPassword() );
        assertEquals( "Johnny", foundUser2.getFirstName() );
        assertEquals( "Johnson", foundUser2.getLastName() );
        assertEquals( "johnnyjohnson1@mail.com", foundUser2.getEmail() );
        assertEquals( 0, foundUser2.getStudySets().size() );

        User foundUser3 = userService.findByUsername( "academic_weapon" );
        assertEquals( "weapon23", foundUser3.getPassword() );
        assertEquals( "Max", foundUser3.getFirstName() );
        assertEquals( "James", foundUser3.getLastName() );
        assertEquals( "jamesmax@mail.com", foundUser3.getEmail() );
        assertEquals( 1, foundUser3.getStudySets().size() );
        mvc.perform( put( "/api/v1/users/academic_weapon" ).contentType( MediaType.APPLICATION_JSON )
                .content( asJsonString( userToEdit3 ) ) ).andExpect( status().isOk() );
        foundUser3 = userService.findByUsername( "academic_weapon" );
        assertEquals( "weapon24", foundUser3.getPassword() );
        assertEquals( "Maximus", foundUser3.getFirstName() );
        assertEquals( "James-Smith", foundUser3.getLastName() );
        assertEquals( "maxjamessmith@mail.com", foundUser3.getEmail() );
        assertEquals( 1, foundUser3.getStudySets().size() );

    }

    @Transactional
    @Test
    /**
     * Ensures that deleting users from the endpoint works as expected
     *
     * @throws Exception
     */
    public void testDeleteUser () throws Exception {
        User user1 = new User( "tyler_strickland14", "password", "Tyler", "Strickland", "tylerstrickland@mail.com",
                null );
        User user2 = new User( "quizzard_lover1", "mypassword", "John", "Johnson", "john.johnson@mail.com", null );
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        ArrayList<StudySet> sets = new ArrayList<StudySet>();
        sets.add( s );
        User user3 = new User( "academic_weapon", "weapon23", "Max", "James", "jamesmax@mail.com", sets );

        userService.save( user1 );
        userService.save( user2 );
        userService.save( user3 );

        assertEquals( 3, userService.count() );

        // First, try to delete as a user that doesn't exist
        mvc.perform( delete( "/api/v1/users/nope" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isBadRequest() );

        // Now, delete users as expected
        mvc.perform( delete( "/api/v1/users/tyler_strickland14" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
        assertEquals( 2, userService.count() );
        assertEquals( 1, studySetService.count() );
        mvc.perform( delete( "/api/v1/users/quizzard_lover1" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
        assertEquals( 1, userService.count() );
        assertEquals( 1, studySetService.count() );
        mvc.perform( delete( "/api/v1/users/academic_weapon" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
        assertEquals( 0, userService.count() );
        assertEquals( 0, studySetService.count() );
    }

    @Transactional
    @Test
    /**
     * Ensures that retrieving a user's list of studysets works as expected
     */
    public void testGetUserStudySets () throws Exception {
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        ArrayList<StudySet> sets = new ArrayList<StudySet>();
        sets.add( s );
        Flashcard f3 = new Flashcard( "Respite", "A pause from doing something" );
        Flashcard f4 = new Flashcard( "Pensive", "Deeply or seriously thoughtful" );
        ArrayList<Flashcard> cards2 = new ArrayList<Flashcard>();
        cards2.add( f3 );
        cards2.add( f4 );
        StudySet s2 = new StudySet( "Study Set 2", cards2 );
        sets.add( s2 );
        User user = new User( "academic_weapon", "weapon23", "Max", "James", "jamesmax@mail.com", sets );
        User user2 = new User( "tyler_strickland14", "password", "Tyler", "Strickland", "tylerstrickland14@mail.com",
                null );
        userService.save( user );

        // First, try to retrieve the list from the wrong url
        mvc.perform(
                get( "/api/v1/wrongurl/users/academic_weapon/studysets" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNotFound() );

        // Now, try to retrieve from a user validly
        userService.save( user2 );
        mvc.perform( get( "/api/v1/users/academic_weapon/studysets" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
        mvc.perform( get( "/api/v1/users/tyler_strickland14/studysets" ).contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );

    }

    /**
     * Tests that logging in and out as well as retrieving the current user
     * works as expected
     *
     * @throws Exception
     */
    @Transactional
    @Test
    public void testLoginAndLogout () throws Exception {
        // Create two users, but only save user1 to the system
        User user1 = new User( "tyler_strickland14", "password", "Tyler", "Strickland", "tylerstrickland@mail.com",
                null );
        User user2 = new User( "quizzard_lover1", "mypassword", "John", "Johnson", "john.johnson@mail.com", null );
        userService.save( user1 );

        // Try to login as both users. User1 should be able to, but user2 should
        // not be able to.
        mvc.perform(
                post( "/api/v1/login" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( user1 ) ) )
                .andExpect( status().isOk() );
        mvc.perform(
                post( "/api/v1/login" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( user2 ) ) )
                .andExpect( status().isBadRequest() );

        // Make sure we can logout as user1
        mvc.perform(
                post( "/api/v1/logout" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( user1 ) ) )
                .andExpect( status().isOk() );

        // Make sure that once we logout we can log back in if we wanted to
        mvc.perform(
                post( "/api/v1/login" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( user1 ) ) )
                .andExpect( status().isOk() );
        // Make sure we still can't sign in as user2 since they are not saved to
        // the system yet
        mvc.perform(
                post( "/api/v1/login" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( user2 ) ) )
                .andExpect( status().isBadRequest() );

        // Save user2 to the system
        userService.save( user2 );
        // Log out as user1
        mvc.perform(
                post( "/api/v1/logout" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( user1 ) ) )
                .andExpect( status().isOk() );
        // Login as user2 which should work now
        mvc.perform(
                post( "/api/v1/login" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( user2 ) ) )
                .andExpect( status().isOk() );
        // Log out as user2
        mvc.perform(
                post( "/api/v1/logout" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( user2 ) ) )
                .andExpect( status().isOk() );

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
