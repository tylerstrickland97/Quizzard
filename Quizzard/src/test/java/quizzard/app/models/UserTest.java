package quizzard.app.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import quizzard.application.TestConfig;

/**
 * Tests that the User model class stores its fields as expected
 *
 * @author Tyler Strickland
 *
 */
@RunWith ( SpringRunner.class )
@ContextConfiguration ( classes = { TestConfig.class } )
@SpringBootTest ( classes = TestConfig.class )
public class UserTest {

    /**
     * Tests that creating a new User using the constructor works as expected
     */
    @Test
    public void testConstructor () {
        User user = new User( "quizzard_lover", "quizzardlover2", "Tyler", "Strickland", "tylerstrickland@mail.com" );
        assertEquals( "quizzard_lover", user.getUsername() );
        assertEquals( "quizzardlover2", user.getPassword() );
        assertEquals( "Tyler", user.getFirstName() );
        assertEquals( "Strickland", user.getLastName() );
        assertEquals( "tylerstrickland@mail.com", user.getEmail() );

    }

    /**
     * Tests that using the edit user method to set new fields for the User
     * correctly updates each field
     */
    @Test
    public void testEditUser () {
        User user = new User( "quizzard_lover", "quizzardlover2", "Tyler", "Strickland", "tylerstrickland@mail.com" );
        assertEquals( "quizzard_lover", user.getUsername() );
        assertEquals( "quizzardlover2", user.getPassword() );
        assertEquals( "Tyler", user.getFirstName() );
        assertEquals( "Strickland", user.getLastName() );
        assertEquals( "tylerstrickland@mail.com", user.getEmail() );

        user.editUser( "quizzard_lover1", "newPassword", "tyler", "strickland", "tylerstrickland@newmail.com" );
        assertEquals( "quizzard_lover1", user.getUsername() );
        assertEquals( "newPassword", user.getPassword() );
        assertEquals( "tyler", user.getFirstName() );
        assertEquals( "strickland", user.getLastName() );
        assertEquals( "tylerstrickland@newmail.com", user.getEmail() );
    }

}
