package quizzard.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import quizzard.app.models.Flashcard;
import quizzard.app.models.StudySet;
import quizzard.app.services.StudySetService;

/**
 * Test class that makes sure that StudySet objects are correctly saved and
 * retrieved from the database
 *
 * @author Tyler Strickland
 *
 */
@RunWith ( SpringRunner.class )
@ContextConfiguration ( classes = { TestConfig.class } )
@SpringBootTest ( classes = TestConfig.class )
public class TestStudySetDatabaseInteraction {

    @Autowired
    private StudySetService studySetService;

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

}
