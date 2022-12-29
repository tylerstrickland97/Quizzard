package quizzard.app.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Test class for the StudySet model class
 *
 * @author Tyler Strickland
 *
 */
public class StudySetTest {

    /**
     * Tests that creating a study set correctly sets the fields
     */
    @Test
    public void testConstructor () {
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet set = new StudySet( "My Study Set", cards );
        assertEquals( "My Study Set", set.getName() );
        assertEquals( 2, set.getFlashcards().size() );
        assertTrue( set.getFlashcards().contains( f1 ) );
        assertTrue( set.getFlashcards().contains( f2 ) );

    }

    /**
     * Tests that using the setter methods correctly updates fields for the
     * StudySet
     */
    @Test
    public void testSettersAndGetters () {
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        Flashcard f2 = new Flashcard( "Computer", "One who computes" );
        ArrayList<Flashcard> cards = new ArrayList<Flashcard>();
        cards.add( f1 );
        cards.add( f2 );

        StudySet set = new StudySet( "My Study Set", cards );
        assertEquals( "My Study Set", set.getName() );
        assertEquals( 2, set.getFlashcards().size() );
        assertTrue( set.getFlashcards().contains( f1 ) );
        assertTrue( set.getFlashcards().contains( f2 ) );

        set.setName( "Study Set For Me" );
        assertEquals( "Study Set For Me", set.getName() );

        Flashcard f3 = new Flashcard( "Computer Science", "The study of the principles and uses of computers" );
        Flashcard f4 = new Flashcard( "Mathematics", "The abstract science of number, quantity, and space" );
        cards.add( f3 );
        cards.add( f4 );
        set.setFlashcards( cards );
        assertEquals( 4, set.getFlashcards().size() );
        assertTrue( set.getFlashcards().contains( f1 ) );
        assertTrue( set.getFlashcards().contains( f2 ) );
        assertTrue( set.getFlashcards().contains( f3 ) );
        assertTrue( set.getFlashcards().contains( f4 ) );

    }

}
