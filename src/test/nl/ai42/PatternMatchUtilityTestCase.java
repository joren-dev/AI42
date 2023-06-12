package nl.ai42;

import nl.ai42.utils.validation.utility.PatternMatchUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class PatternMatchUtilityTestCase {
    @Test
    public void isValidName_ValidName_ReturnsTrue() {
        // Act
        boolean henk = PatternMatchUtility.is_valid_name("Henk");
        boolean pieter = PatternMatchUtility.is_valid_name("Pieter");

        // Assert
        Assertions.assertTrue(henk);
        Assertions.assertTrue(pieter);
    }

    @Test
    public void isValidName_InvalidName_ReturnsFalse() {
        // Act
        boolean frans123 = PatternMatchUtility.is_valid_name("Frans123");
        boolean hashtagPiet = PatternMatchUtility.is_valid_name("#Piet");

        // Assert
        Assertions.assertFalse(frans123);
        Assertions.assertFalse(hashtagPiet);
    }

    @Test
    public void isValidEmail_ValidEmail_ReturnsTrue() {
        // Act
        boolean henk = PatternMatchUtility.is_valid_email("henk@gmail.com");
        boolean piet = PatternMatchUtility.is_valid_email("p.krediet@student.hhs.nl");

        // Assert
        Assertions.assertTrue(henk);
        Assertions.assertTrue(piet);
    }

    @Test
    public void isValidEmail_InvalidEmail_ReturnsFalse() {
        // Act
        boolean pieter = PatternMatchUtility.is_valid_email("pietergmail.com");
        boolean frans = PatternMatchUtility.is_valid_email("frans@gmailcom");

        // Assert
        Assertions.assertFalse(pieter);
        Assertions.assertFalse(frans);
    }

    @Test
    public void isValidEmailWithNull_NullEmail_ReturnsFalse() {
        // Act
        boolean result = PatternMatchUtility.is_valid_email(null);

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void isValidEmailWithEmptyString_EmptyEmail_ReturnsFalse() {
        // Act
        boolean result = PatternMatchUtility.is_valid_email("");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void isValidFullNameInvalidCharacter_InvalidCharacterInFullName_ReturnsFalse() {
        // Act
        boolean result = PatternMatchUtility.is_valid_full_name("H3nk de Steen");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void isValidFullNameEmptyString_EmptyFullName_ReturnsFalse() {
        // Act
        boolean result = PatternMatchUtility.is_valid_full_name("");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void isValidPasswordTooShort_PasswordTooShort_ReturnsFalse() {
        // Act
        boolean result = PatternMatchUtility.is_valid_password("Sh0rt");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void isValidPasswordEdgeCases_TooShortAndLongEnoughPasswords_ReturnsFalseAndTrue() {
        // Act
        boolean tooShort = PatternMatchUtility.is_valid_password("$h0rTpw");
        boolean longEnough = PatternMatchUtility.is_valid_password("$h0rTpw!");

        // Assert
        Assertions.assertFalse(tooShort);
        Assertions.assertTrue(longEnough);
    }

    @Test
    public void isValidPasswordNoCapitals_PasswordWithoutCapitals_ReturnsFalse() {
        // Act
        boolean result = PatternMatchUtility.is_valid_password("all_lowercase with $ymb0l$ @nd d1g1t$, but n0 c@p1t@1$");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void isValidPasswordShoutyCaps_PasswordWithShoutyCaps_ReturnsFalse() {
        // Act
        boolean result = PatternMatchUtility.is_valid_password("TH1S P@S$WORD SHOULD N0T BE ACCEPTED!!!");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void isValidPasswordNoDigits_PasswordWithoutDigits_ReturnsFalse() {
        // Act
        boolean result = PatternMatchUtility.is_valid_password("IC*MOz;!PrGmde_H&^q'*'JzZzqZ(?),<giLi(RO");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void isValidPasswordNoSpecialCharacter_PasswordWithoutSpecialCharacter_ReturnsFalse() {
        // Act
        boolean result = PatternMatchUtility.is_valid_password("dCrunM5XMCoBvWs6KSLgR3rVgBMMUFob7HW34AY8");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void isValidPasswordWithOkayIshPassword_ValidPassword_ReturnsTrue() {
        // Act
        boolean result = PatternMatchUtility.is_valid_password("n@Xduc\\'u=QHdr<B\\\\DR%:GAKE|/eY{7|<+9]L6Ymx$H+_x^3!g)?$H]z=ushUTAfn1/W(9O,$fYJF+\"*~");

        // Assert
        Assertions.assertTrue(result);
    }
}
