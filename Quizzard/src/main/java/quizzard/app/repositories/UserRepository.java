package quizzard.app.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quizzard.app.models.StudySet;
import quizzard.app.models.User;

/**
 * Repository for User objects that allows us to perform CRUD operations
 *
 * @author Tyler Strickland
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Returns a user by the username
     *
     * @param username
     *            the username of the User to retrieve
     * @return the User with the given username
     */
    User findByUsername ( String username );

    /**
     * Returns the list of StudySets associated with the User with the given
     * username
     *
     * @param username
     *            the username of the User with the StudySets to retrieve
     * @return the list of StudySets associated with the User
     */
    ArrayList<StudySet> getStudySetsByUsername ( String username );
}
