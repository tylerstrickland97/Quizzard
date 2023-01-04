package quizzard.app.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import quizzard.app.models.Flashcard;
import quizzard.app.models.StudySet;
import quizzard.app.repositories.StudySetRepository;

/**
 * Class that allows us to save StudySet objects to the database
 *
 * @author Tyler Strickland
 *
 */
@Component
@Transactional
public class StudySetService extends Service<StudySet, String> {

    /**
     * Repository that allows us to perform CRUD operations
     */
    @Autowired
    private StudySetRepository repository;

    /**
     * Returns our StudySetRepository for CRUD operations
     */
    @Override
    protected JpaRepository<StudySet, String> getRepository () {
        return repository;
    }

    /**
     * Returns the StudySet with the given name
     *
     * @param name
     *            the name of the StudySet to retrieve
     * @return the StudySet with the given name
     */
    public StudySet findByName ( String name ) {
        return repository.findByName( name );
    }

    /**
     * Returns the list of flashcards associated with the StudySet of the given
     * name
     *
     * @param name
     *            the name of the StudySet to retrieve flashcards from
     * @return list of flashcards associated with the StudySet of the given name
     */
    public List<Flashcard> getFlashcardsByName ( String name ) {
        return repository.findByName( name ).getFlashcards();
    }

}
