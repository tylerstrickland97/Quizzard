package quizzard.app.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.lang.NonNull;

/**
 * Class that represents a single person using the application. One user will
 * have a username, password, and their own list of studysets
 *
 * @author Tyler Strickland
 *
 */
@Entity
public class User extends DomainObject {

    /**
     * Represents the username a User will hold in the Quizzard system
     */
    @Id
    @NonNull
    private String         username;

    /**
     * The password that a User will use to login to Quizzard
     */
    @NonNull
    private String         password;

    /**
     * First name of the User
     */
    @NonNull
    private String         firstName;

    /**
     * Last name of the User
     */
    @NonNull
    private String         lastName;

    /**
     * Email of the User
     */
    @NonNull
    private String         email;

    /**
     * The list of study sets that a User has
     */
    @OneToMany ( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private List<StudySet> studySets;

    /**
     * Empty constructor for Hibernate
     */
    public User () {

    }

    /**
     * Constructor that takes a username and password parameter to create a new
     * User object
     *
     * @param username
     *            the username of the new User
     * @param password
     *            the password of the new User
     */
    public User ( String username, String password, String firstName, String lastName, String email ) {
        setUsername( username );
        setPassword( password );
        setFirstName( firstName );
        setLastName( lastName );
        setEmail( email );
    }

    /**
     * Sets the username of the User
     *
     * @param username
     *            the username to set for the User
     */
    private void setUsername ( String username ) {
        this.username = username;
    }

    /**
     * Sets the password of the User
     *
     * @param password
     *            the password to set for the User
     */
    private void setPassword ( String password ) {
        this.password = password;
    }

    /**
     * Sets the first name of the User
     *
     * @param firstName
     *            the first name of the User to set
     */
    private void setFirstName ( String firstName ) {
        this.firstName = firstName;
    }

    /**
     * Sets the laset name of the User
     *
     * @param lastName
     *            the last name of the User to set
     */
    private void setLastName ( String lastName ) {
        this.lastName = lastName;
    }

    /**
     * Sets the email of the User
     *
     * @param email
     *            the email of the User to set
     */
    private void setEmail ( String email ) {
        this.email = email;
    }

    /**
     * Returns the User's username
     *
     * @return username of the User
     */
    public String getUsername () {
        return username;
    }

    /**
     * Returns the User's password
     *
     * @return password of the User
     */
    public String getPassword () {
        return password;
    }

    /**
     * Returns the User's first name
     *
     * @return first name of the User
     */
    public String getFirstName () {
        return firstName;
    }

    /**
     * Returns the User's last name
     *
     * @return last name of the User
     */
    public String getLastName () {
        return lastName;
    }

    /**
     * Returns the User's email
     *
     * @return email of the User
     */
    public String getEmail () {
        return email;
    }

    /**
     * Edits the User object
     *
     * @param username
     *            the new username for the User
     * @param password
     *            the new password for the User
     * @param first
     *            the new first name for the User
     * @param last
     *            the new last name for the User
     * @param email
     *            the new email for the User
     */
    public void editUser ( String username, String password, String first, String last, String email ) {
        setUsername( username );
        setPassword( password );
        setFirstName( first );
        setLastName( last );
        setEmail( email );
    }

    /**
     * Returns the id used by Hibernate for the User object, which in this case
     * is the username
     *
     * @return the ID used by Hibernate for this User
     */
    @Override
    public Serializable getId () {
        return username;
    }

}
