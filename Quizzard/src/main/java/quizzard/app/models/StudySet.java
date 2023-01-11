package quizzard.app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.lang.NonNull;

/**
 * Model class that represents a complete set of flash cards
 *
 * @author Tyler Strickland
 *
 */
@Entity
public class StudySet extends DomainObject {

    /**
     * The set of flash cards associated with this study set
     */
    @OneToMany ( cascade = CascadeType.ALL )
    private List<Flashcard> flashcards;

    /**
     * The name of this study set
     */
    @Id
    @NonNull
    private String          name;

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
     * @param list
     *            the set of flash cards associated with the new study set
     */
    public StudySet ( String name, List<Flashcard> list ) {
        setName( name );
        if ( list != null ) {
            setFlashcards( list );
        }
        else {
            list = new ArrayList<Flashcard>();
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
    public void setFlashcards ( List<Flashcard> flashcards ) {
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
    public List<Flashcard> getFlashcards () {
        return flashcards;
    }

    /**
     * Returns the id of the StudySet, which here is the name
     *
     * @return id of the StudySet, which here is the name
     */
    @Override
    public Serializable getId () {
        return name;
    }

}
