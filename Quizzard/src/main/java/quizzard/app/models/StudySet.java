package quizzard.app.models;

import java.util.ArrayList;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Model class that represents a complete set of flash cards
 *
 * @author Tyler Strickland
 *
 */
public class StudySet {

    /**
     * The set of flash cards associated with this study set
     */
    @OneToMany ( mappedBy = "studyset" )
    private ArrayList<Flashcard> flashcards;

    /**
     * The name of this study set
     */
    @Id
    @NotNull
    @Length ( min = 1 )
    private String               name;

    /**
     * Empty constructor for Hibernate
     */
    public StudySet () {

    }

    /**
     * Constructs a new study set object using a name and flashcard list as
     * parameters
     *
     * @param name
     *            the name of the new study set
     * @param flashcards
     *            the set of flash cards associated with the new study set
     */
    public StudySet ( String name, ArrayList<Flashcard> flashcards ) {
        setName( name );
        if ( flashcards != null ) {
            setFlashcards( flashcards );
        }
        else {
            flashcards = new ArrayList<Flashcard>();
        }
    }

    /**
     * Sets the name of the study set
     *
     * @param name
     *            the name of the study set
     */
    public void setName ( String name ) {
        this.name = name;
    }

    /**
     * Sets the list of flashcards of the study set
     *
     * @param flashcards
     *            the flashcards associated with the study set
     */
    public void setFlashcards ( ArrayList<Flashcard> flashcards ) {
        this.flashcards = flashcards;
    }

    /**
     * Returns the name of the study set
     *
     * @return name of the study set
     */
    public String getName () {
        return name;
    }

    /**
     * Returns the flash cards of the study set
     *
     * @return flash cards associated with the study set
     */
    public ArrayList<Flashcard> getFlashcards () {
        return flashcards;
    }

}
