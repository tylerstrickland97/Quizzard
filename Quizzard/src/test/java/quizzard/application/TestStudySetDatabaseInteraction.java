package quizzard.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import quizzard.app.models.Flashcard;
import quizzard.app.models.StudySet;
import quizzard.app.models.User;
import quizzard.app.services.StudySetService;
import quizzard.app.services.UserService;

/**
 * Test class that makes sure that StudySet and User objects are correctly saved
 * and retrieved from the database
 *
 * @author Tyler Strickland
 *
 */
@RunWith ( SpringRunner.class )
@ContextConfiguration ( classes = { TestConfig.class } )
@SpringBootTest ( classes = TestConfig.class )
public class TestStudySetDatabaseInteraction {

    /**
     * Service that will allow us to save, retrieve, and delete study sets
     */
    @Autowired
    private StudySetService studySetService;

    @Autowired
    private UserService     userService;

    @Before
    public void setup () {
        studySetService.deleteAll();
        userService.deleteAll();
    }

    /**
     * Tests that saving a study set to the database works as expected
     */
    @Transactional
    @Test
    public void testSaveStudySet () {
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        studySetService.save( s );
        assertEquals( 1, studySetService.count() );

        List<StudySet> foundList = studySetService.findAll();
        assertEquals( 1, foundList.size() );
        assertEquals( "Study Set For Me", foundList.get( 0 ).getName() );
        assertEquals( 2, foundList.get( 0 ).getFlashcards().size() );

        StudySet foundSet = studySetService.findByName( "Study Set For Me" );
        assertNotNull( foundSet );
        assertEquals( 2, foundSet.getFlashcards().size() );

        assertEquals( 2, studySetService.getFlashcardsByName( "Study Set For Me" ).size() );

        assertNull( studySetService.findByName( "My Study Set" ) );
        Flashcard f3 = new Flashcard( "Respite", "A pause from doing something" );
        Flashcard f4 = new Flashcard( "Pensive", "Deeply or seriously thoughtful" );
        ArrayList<Flashcard> cards2 = new ArrayList<Flashcard>();
        cards2.add( f3 );
        cards2.add( f4 );
        StudySet newSet = new StudySet( "My Study Set", cards2 );
        studySetService.save( newSet );
        assertEquals( 2, studySetService.count() );
        assertNotNull( studySetService.findByName( "My Study Set" ) );
        foundList = studySetService.findAll();
        assertEquals( 2, foundList.size() );
        assertEquals( "Study Set For Me", foundList.get( 1 ).getName() );
        assertEquals( "My Study Set", foundList.get( 0 ).getName() );
        assertEquals( 2, foundList.get( 1 ).getFlashcards().size() );
    }

    /**
     * Tests that saving and deleting study sets works as expected
     */
    @Transactional
    @Test
    public void testSaveAndDeleteStudySet () {
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        studySetService.save( s );
        assertEquals( 1, studySetService.count() );

        Flashcard f3 = new Flashcard( "Respite", "A pause from doing something" );
        Flashcard f4 = new Flashcard( "Pensive", "Deeply or seriously thoughtful" );
        ArrayList<Flashcard> cards2 = new ArrayList<Flashcard>();
        cards2.add( f3 );
        cards2.add( f4 );
        StudySet newSet = new StudySet( "My Study Set", cards2 );
        studySetService.save( newSet );
        assertEquals( 2, studySetService.count() );

        studySetService.delete( s );
        assertEquals( 1, studySetService.count() );
        assertNull( studySetService.findByName( "Study Set For Me" ) );
        assertNotNull( studySetService.findByName( "My Study Set" ) );

        studySetService.delete( newSet );
        assertEquals( 0, studySetService.count() );
        assertNull( studySetService.findByName( "Study Set For Me" ) );
        assertNull( studySetService.findByName( "My Study Set" ) );
    }

    /**
     * Tests that deleting all study sets from the database works as expected
     */
    @Test
    @Transactional
    public void testDeleteAllStudySet () {
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        studySetService.save( s );
        Flashcard f3 = new Flashcard( "Respite", "A pause from doing something" );
        Flashcard f4 = new Flashcard( "Pensive", "Deeply or seriously thoughtful" );
        ArrayList<Flashcard> cards2 = new ArrayList<Flashcard>();
        cards2.add( f3 );
        cards2.add( f4 );
        StudySet newSet = new StudySet( "My Study Set", cards2 );
        studySetService.save( newSet );
        assertEquals( 2, studySetService.count() );
        studySetService.deleteAll();
        assertEquals( 0, studySetService.count() );
        List<StudySet> found = studySetService.findAll();
        assertEquals( 0, found.size() );
    }

    /**
     * Tests that saving users with and without studysets works as expected
     */
    @Test
    @Transactional
    public void testSaveUsers () {
        User user = new User( "quizzard_lover1", "password", "Tyler", "Strickland", "tylerstrickland@mail.com", null );
        userService.save( user );

        assertEquals( 1, userService.count() );
        assertEquals( 1, userService.findAll().size() );

        User foundUser = userService.findById( "quizzard_lover1" );
        assertNotNull( foundUser );
        assertEquals( "password", foundUser.getPassword() );
        assertEquals( "Tyler", foundUser.getFirstName() );
        assertEquals( 0, foundUser.getStudySets().size() );

        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        ArrayList<StudySet> sets = new ArrayList<StudySet>();
        sets.add( s );
        User user2 = new User( "big_study_boy", "quizzarduser2", "John", "Johnson", "john.johnson@mail.com", sets );
        userService.save( user2 );
        assertEquals( 2, userService.count() );
        assertEquals( 2, userService.findAll().size() );

        User foundUser2 = userService.findByUsername( "big_study_boy" );
        assertNotNull( foundUser2 );
        assertEquals( "John", foundUser2.getFirstName() );
        assertEquals( 1, foundUser2.getStudySets().size() );
        List<StudySet> foundSets = userService.findStudySetsByUsername( "big_study_boy" );
        assertEquals( 1, foundSets.size() );
        assertEquals( "Biology", foundSets.get( 0 ).getFlashcards().get( 0 ).getTerm() );
        assertEquals( "Computer", foundSets.get( 0 ).getFlashcards().get( 1 ).getTerm() );
    }

    /**
     * Tests that saving multiple Users with StudySets and then deleting them
     * one by one deletes the correct study sets and users
     */
    @Transactional
    @Test
    public void testSaveAndDeleteStudySets () {
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
        userService.save( user );
        assertEquals( 1, userService.count() );
        assertEquals( 1, studySetService.count() );

        userService.delete( user );
        assertEquals( 0, studySetService.count() );
        assertEquals( 0, userService.count() );

        userService.save( user );
        Flashcard f3 = new Flashcard( "Respite", "A pause from doing something" );
        Flashcard f4 = new Flashcard( "Pensive", "Deeply or seriously thoughtful" );
        ArrayList<Flashcard> cards2 = new ArrayList<Flashcard>();
        cards2.add( f3 );
        cards2.add( f4 );
        StudySet newSet = new StudySet( "My Study Set", cards2 );
        ArrayList<StudySet> otherSets = new ArrayList<StudySet>();
        otherSets.add( newSet );

        User newUser = new User( "quizzard_boy", "passWord", "Sammy", "Smart", "smartsam@mail.com", otherSets );
        userService.save( newUser );
        assertEquals( 2, userService.count() );
        assertEquals( 2, studySetService.count() );

        userService.delete( newUser );
        assertEquals( 1, userService.count() );
        assertEquals( 1, studySetService.count() );
        assertNotNull( studySetService.findByName( "Study Set For Me" ) );
        assertNull( studySetService.findByName( "My Study Set" ) );
        assertNotNull( userService.findByUsername( "tyler_strickland14" ) );
        assertNull( userService.findByUsername( "quizzard_boy" ) );
    }

    /**
     * Tests that deleting all users at once deletes all study sets and users
     * from the database
     */
    @Transactional
    @Test
    public void testDeleteAllUsers () {
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
        userService.save( user );

        Flashcard f3 = new Flashcard( "Respite", "A pause from doing something" );
        Flashcard f4 = new Flashcard( "Pensive", "Deeply or seriously thoughtful" );
        ArrayList<Flashcard> cards2 = new ArrayList<Flashcard>();
        cards2.add( f3 );
        cards2.add( f4 );
        StudySet newSet = new StudySet( "My Study Set", cards2 );
        ArrayList<StudySet> otherSets = new ArrayList<StudySet>();
        otherSets.add( newSet );

        User newUser = new User( "quizzard_boy", "passWord", "Sammy", "Smart", "smartsam@mail.com", otherSets );
        userService.save( newUser );
        assertEquals( 2, userService.count() );
        assertEquals( 2, studySetService.count() );

        userService.deleteAll();
        assertEquals( 0, userService.count() );
        assertEquals( 0, studySetService.count() );

    }

}
