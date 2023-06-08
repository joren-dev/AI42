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

    @Test
    public void testIsValidEmailWithNull() {
        // Act
        boolean result = ValidationUtils.is_valid_email(null);

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidEmailWithEmptyString() {
        // Act
        boolean result = ValidationUtils.is_valid_email("");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidFullNameInvalidCharacter() {
        // Act
        boolean result = ValidationUtils.is_valid_full_name("H3nk de Steen");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidFullNameEmptyString() {
        // Act
        boolean result = ValidationUtils.is_valid_full_name("");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidPasswordTooShort() {
        // Act
        boolean result = ValidationUtils.is_valid_password("Sh0rt");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidPasswordEdgeCases() {
        // Act
        boolean tooShort = ValidationUtils.is_valid_password("$h0rTpw");
        boolean longEnough = ValidationUtils.is_valid_password("$h0rTpw!");

        // Assert
        Assertions.assertFalse(tooShort);
        Assertions.assertTrue(longEnough);
    }

    @Test
    public void testIsValidPasswordNoCapitals() {
        // Act
        boolean result = ValidationUtils.is_valid_password("all_lowercase with $ymb0l$ @nd d1g1t$, but n0 c@p1t@1$");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidPasswordShoutyCaps() {
        // Act
        boolean result = ValidationUtils.is_valid_password("TH1S P@S$WORD SHOULD N0T BE ACCEPTED!!!");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidPasswordNoDigits() {
        // Act
        boolean result = ValidationUtils.is_valid_password("IC*MOz;!PrGmde_H&^q'*'JzZzqZ(?),<giLi(RO");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidPasswordNoSpecialCharacter() {
        // Act
        boolean result = ValidationUtils.is_valid_password("dCrunM5XMCoBvWs6KSLgR3rVgBMMUFob7HW34AY8");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValidPasswordWithOkayIshPassword() {
        // Act
        boolean result = ValidationUtils.is_valid_password("n@Xduc\\'u=QHdr<B\\\\DR%:GAKE|/eY{7|<+9]L6Ymx$H+_x^3!g)?$H]z=ushUTAfn1/W(9O,$fYJF+\"*~");

        // Assert
        Assertions.assertTrue(result);
    }
}
