package quizzard.app.models;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import quizzard.application.TestConfig;

/**
 * Tests that the User model class stores its fields as expected
 *
 * @author Tyler Strickland
 *
 */
@RunWith ( SpringRunner.class )
@ContextConfiguration ( classes = { TestConfig.class } )
@SpringBootTest ( classes = TestConfig.class )
public class UserTest {

    /**
     * Tests that creating a new User using the constructor works as expected
     */
    @Test
    public void testConstructor () {
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        ArrayList<StudySet> sets = new ArrayList<StudySet>();
        sets.add( s );
        User user = new User( "quizzard_lover", "quizzardlover2", "Tyler", "Strickland", "tylerstrickland@mail.com",
                sets );
        assertEquals( "quizzard_lover", user.getUsername() );
        assertEquals( "quizzardlover2", user.getPassword() );
        assertEquals( "Tyler", user.getFirstName() );
        assertEquals( "Strickland", user.getLastName() );
        assertEquals( "tylerstrickland@mail.com", user.getEmail() );
        assertEquals( 1, user.getStudySets().size() );

    }

    /**
     * Tests that using the edit user method to set new fields for the User
     * correctly updates each field
     */
    @Test
    public void testEditUser () {
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet s = new StudySet( "Study Set For Me", cards );
        ArrayList<StudySet> sets = new ArrayList<StudySet>();
        sets.add( s );

        Flashcard f3 = new Flashcard( "Garner", "To collect or gather something" );
        Flashcard f4 = new Flashcard( "Fortuitous", "Happening by chance" );
        ArrayList<Flashcard> cards2 = new ArrayList<Flashcard>();
        cards2.add( f3 );
        cards2.add( f4 );
        StudySet s2 = new StudySet( "New Study Set", cards2 );

        User user = new User( "quizzard_lover", "quizzardlover2", "Tyler", "Strickland", "tylerstrickland@mail.com",
                sets );
        assertEquals( "quizzard_lover", user.getUsername() );
        assertEquals( "quizzardlover2", user.getPassword() );
        assertEquals( "Tyler", user.getFirstName() );
        assertEquals( "Strickland", user.getLastName() );
        assertEquals( "tylerstrickland@mail.com", user.getEmail() );
        assertEquals( 1, user.getStudySets().size() );

        sets.add( s2 );
        user.editUser( "quizzard_lover1", "newPassword", "tyler", "strickland", "tylerstrickland@newmail.com" );
        assertEquals( "quizzard_lover1", user.getUsername() );
        assertEquals( "newPassword", user.getPassword() );
        assertEquals( "tyler", user.getFirstName() );
        assertEquals( "strickland", user.getLastName() );
        assertEquals( "tylerstrickland@newmail.com", user.getEmail() );
        assertEquals( 2, user.getStudySets().size() );
    }

}
