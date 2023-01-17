package quizzard.app.controllers;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MappingController {

    /**
     * Returns the login page
     *
     * @param model
     *            Data from the frontend
     * @return Page to be displayed
     */
    @GetMapping ( { "/", "login.html" } )
    public String login ( final Model model ) {
        return "login";
    }

    /**
     * Returns the create account page
     *
     * @param model
     * @return
     */
    @GetMapping ( "createaccount.html" )
    public String createAccount ( final Model model ) {
        return "createaccount";

    }

    /**
     * Returns the home page
     *
     * @param model
     * @return
     */
    @GetMapping ( "home.html" )
    public String home ( final Model model ) {

        return "home";
    }

    /**
     * Returns the manage account page
     *
     * @param model
     * @return
     */
    @GetMapping ( "manageaccount.html" )
    public String manageAccount ( final Model model ) {

        return "manageaccount";
    }

    /**
     * Returns the create study set page
     *
     * @param model
     * @return
     */
    @GetMapping ( "createstudyset.html" )
    public String createStudySet ( final Model model ) {

        return "createstudyset";
    }

    /**
     * Returns the create study set page
     *
     * @param model
     * @return
     */
    @GetMapping ( "managestudyset.html" )
    public String manageStudySet ( final Model model ) {

        return "managestudyset";
    }

    /**
     * Returns the study mode page
     *
     * @param model
     * @return
     */
    @GetMapping ( "study.html" )
    public String study ( final Model model ) {

        return "study";
    }

    /**
     * Returns the fill in the blank mode page
     *
     * @param model
     * @return
     */
    @GetMapping ( "fillblank.html" )
    public String fillInTheBlank ( final Model model ) {

        return "fillblank";
    }

    /**
     * Returns the multiple choice mode page
     *
     * @param model
     * @return
     */
    @GetMapping ( "multiplechoice.html" )
    public String multipleChoice ( final Model model ) {

        return "multiplechoice";
    }

    /**
     * Serves back the login image used
     *
     * @return Image for Dr. Jenkins
     * @throws IOException
     *             if file can't be found
     */
    @GetMapping ( value = "/loginImage3", produces = MediaType.IMAGE_JPEG_VALUE )
    public ResponseEntity<byte[]> getLoginImage () throws IOException {
        final ClassPathResource imgFile = new ClassPathResource( "images/loginImage3.jpg" );
        final byte[] bytes = StreamUtils.copyToByteArray( imgFile.getInputStream() );
        return ResponseEntity.ok().contentType( MediaType.IMAGE_JPEG ).body( bytes );
    }

    /**
     * Gives the logo
     *
     * @return
     * @throws IOException
     *             if file can't be found
     */
    @GetMapping ( value = "/Logo", produces = MediaType.IMAGE_JPEG_VALUE )
    public ResponseEntity<byte[]> getLogoImage () throws IOException {
        final ClassPathResource imgFile = new ClassPathResource( "images/Logo.jpg" );
        final byte[] bytes = StreamUtils.copyToByteArray( imgFile.getInputStream() );
        return ResponseEntity.ok().contentType( MediaType.IMAGE_JPEG ).body( bytes );
    }

    /**
     * Gives the first home screen image
     *
     * @return
     * @throws IOException
     *             if file can't be found
     */
    @GetMapping ( value = "/image2", produces = MediaType.IMAGE_JPEG_VALUE )
    public ResponseEntity<byte[]> getHomeImage1 () throws IOException {
        final ClassPathResource imgFile = new ClassPathResource( "images/image2.jpg" );
        final byte[] bytes = StreamUtils.copyToByteArray( imgFile.getInputStream() );
        return ResponseEntity.ok().contentType( MediaType.IMAGE_JPEG ).body( bytes );
    }

    /**
     * Gives the second home screen image
     *
     * @return
     * @throws IOException
     *             if file can't be found
     */
    @GetMapping ( value = "/image3", produces = MediaType.IMAGE_JPEG_VALUE )
    public ResponseEntity<byte[]> getHomeImage2 () throws IOException {
        final ClassPathResource imgFile = new ClassPathResource( "images/image3.jpg" );
        final byte[] bytes = StreamUtils.copyToByteArray( imgFile.getInputStream() );
        return ResponseEntity.ok().contentType( MediaType.IMAGE_JPEG ).body( bytes );
    }
}
