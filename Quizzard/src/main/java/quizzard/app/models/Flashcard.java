package quizzard.app.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.lang.NonNull;

/**
 * Model class that represents a single flash card in a study set
 *
 * @author Tyler Strickland
 *
 */
@Entity
public class Flashcard extends DomainObject {

    /**
     * The term or main word associated with this flash card
     */
    @NonNull
    @Id
    private String term;

    /**
     * The definition of the term associated with this flash card
     */
    @NonNull
    private String definition;

    /**
     * Empty constructor for Hibernate
     */
    public Flashcard () {

    }

    /**
     * Constructor that takes both a term and definition argument.
     *
     * @param term
     *            the term associated with this flash card
     * @param definition
     *            the definition associated with this flash card
     */
    public Flashcard ( String term, String definition ) {
        setTerm( term );
        setDefinition( definition );
    }

    /**
     * Returns the term associated with the flash card
     *
     * @return the term associated with the flash card
     */
    public String getTerm () {
        return term;
    }

    /**
     * Sets the term for this flash card
     *
     * @param term
     *            the term for this flash card to set
     */
    public void setTerm ( String term ) {
        if ( term == null ) {
            throw new IllegalArgumentException( "Term cannot be null" );
        }
        else {
            this.term = term;
        }
    }

    /**
     * Returns the definition associated with the flash card
     *
     * @return the definition associated with the flash card
     */
    public String getDefinition () {
        return definition;
    }

    /**
     * Sets the definition for this flash card
     *
     * @param definition
     *            the definition for this flash card to set
     */
    public void setDefinition ( String definition ) {
        if ( definition == null ) {
            throw new IllegalArgumentException( "Definition cannot be null" );
        }
        else {
            this.definition = definition;
        }

    }

    /**
     * Returns the id of the Flashcard, which here is the term
     *
     * @return id of the flashcard, which is the term
     */
    @Override
    public Serializable getId () {
        return term;
    }
}
