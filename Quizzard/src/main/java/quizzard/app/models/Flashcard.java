package quizzard.app.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Model class that represents a single flash card in a study set
 *
 * @author Tyler Strickland
 *
 */
@Entity
public class Flashcard {

    /**
     * The term or main word associated with this flash card
     */
    @Id
    @NotNull
    @Length ( min = 1 )
    private String term;

    /**
     * The definition of the term associated with this flash card
     */
    @NotNull
    @Length ( min = 1 )
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
        this.term = term;
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
        this.definition = definition;
    }
}
