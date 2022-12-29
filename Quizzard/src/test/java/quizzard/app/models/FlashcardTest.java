package quizzard.app.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test class for the Flashcard model class
 *
 * @author Tyler Strickland
 *
 */
public class FlashcardTest {

    /**
     * Tests creating a new flash card object using the constructors
     */
    @Test
    public void testConstructors () {
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );

        assertEquals( "Biology", f1.getTerm() );
        assertEquals( "The study of life", f1.getDefinition() );

        Flashcard f2 = new Flashcard( "Computer",
                "a programmable usually electronic device that can store, retrieve, and process data" );
        assertEquals( "Computer", f2.getTerm() );
        assertEquals( "a programmable usually electronic device that can store, retrieve, and process data",
                f2.getDefinition() );

    }

    /**
     * Tests setting and getting the fields of the flash card
     */
    @Test
    public void testSettersAndGetters () {
        Flashcard f1 = new Flashcard( "Biology", "The study of life" );
        assertEquals( "Biology", f1.getTerm() );
        f1.setTerm( "biology" );
        assertEquals( "biology", f1.getTerm() );

        assertEquals( "The study of life", f1.getDefinition() );
        f1.setDefinition( "a branch of knowledge that deals with living organisms and vital processes" );
        assertEquals( "a branch of knowledge that deals with living organisms and vital processes",
                f1.getDefinition() );
    }

}
