package quizzard.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quizzard.app.models.StudySet;

/**
 * Repository class for StudySet objects. This class handles CRUD operations
 * that will be used by the StudySetService class
 *
 * @author Tyler Strickland
 *
 */
@Repository
public interface StudySetRepository extends JpaRepository<StudySet, String> {

    /**
     * Will return a StudySet with the given name
     *
     * @param name
     *            the name of the StudySet to return
     * @return the StudySet with the given name
     */
    StudySet findByName ( String name );
}
