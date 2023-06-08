import nl.ai42.utils.ValidationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ValidationUtilsTestCase {
    @Test
    public void testIsValidName() {
        // Act
        boolean henk = ValidationUtils.is_valid_name("Henk");
        boolean pieter = ValidationUtils.is_valid_name("Pieter");
        boolean frans123 = ValidationUtils.is_valid_name("Frans123");
        boolean hashtagPiet = ValidationUtils.is_valid_name("#Piet");

        // Assert
        Assertions.assertTrue(henk);
        Assertions.assertTrue(pieter);
        Assertions.assertFalse(frans123);
        Assertions.assertFalse(hashtagPiet);
    }

    @Test
    public void testIsValidEmail() {
        // Act
        boolean henk = ValidationUtils.is_valid_email("henk@gmail.com");
        boolean pieter = ValidationUtils.is_valid_email("pietergmail.com");
        boolean piet = ValidationUtils.is_valid_email("p.krediet@student.hhs.nl");
        boolean frans = ValidationUtils.is_valid_email("frans@gmailcom");

        // Assert
        Assertions.assertTrue(henk);
        Assertions.assertFalse(pieter);
        Assertions.assertTrue(piet);
        Assertions.assertFalse(frans);
    }
}
