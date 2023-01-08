package quizzard.app.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import quizzard.app.models.StudySet;
import quizzard.app.models.User;
import quizzard.app.repositories.UserRepository;

/**
 * Service class that handles saving, deleting, and retrieving User objects
 * using the UserRepository
 *
 * @author Tyler Strickland
 *
 */
@Component
@Transactional
public class UserService extends Service<User, String> {

    /**
     * Repository class that handles CRUD operations for User objects
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Returns the userRepository
     *
     * @return userRepository
     */
    @Override
    protected JpaRepository<User, String> getRepository () {
        return userRepository;
    }

    /**
     * Finds a User with the given username
     *
     * @param username
     *            the username of the User to retrieve
     * @return the User with the given username
     */
    public User findByUsername ( String username ) {
        return userRepository.findByUsername( username );
    }

    /**
     * Returns the list of StudySets associated with the User with the given
     * username
     *
     * @param username
     *            the username of the User to retrieve study sets from
     * @return the list of StudySets associated with the User with the given
     *         username
     */
    public List<StudySet> findStudySetsByUsername ( String username ) {
        return userRepository.findByUsername( username ).getStudySets();
    }

}
