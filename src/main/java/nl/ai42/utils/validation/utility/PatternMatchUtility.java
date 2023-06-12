package nl.ai42.utils.validation.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatchUtility {

    public static boolean isValidName(final String name) {
        return name.matches("[a-zA-Z]+");
    }

    public static boolean isValidEmail(final String email) {
        if (email == null || email.isEmpty())
            return false;

        // Regex pattern to check if email address is valid
        final String pattern = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$";

        final Pattern regex = Pattern.compile(pattern);
        final Matcher matcher = regex.matcher(email);

        return matcher.matches();
    }

    public static boolean isValidFullName(final String client_type_name) {
        final String regex = "^[a-zA-Z][a-zA-Z ]*$";
        return client_type_name.matches(regex);
    }

    public static boolean isValidPassword(final String password) {
        // Minimum password length of 8 characters
        if (password.length() < 8)
            return false;

        // Contains at least one uppercase letter
        if (!Pattern.compile("[A-Z]").matcher(password).find())
            return false;

        // Contains at least one lowercase letter
        if (!Pattern.compile("[a-z]").matcher(password).find())
            return false;

        // Contains at least one digit
        if (!Pattern.compile("\\d").matcher(password).find())
            return false;

        // Contains at least one special character (non-alphanumeric)
        if (!Pattern.compile("[^a-zA-Z\\d]").matcher(password).find())
            return false;

        // Password meets all requirements
        return true;
    }

    public static boolean isValidDate(String date) {
        String pattern = "^\\d{4}-\\d{2}-\\d{2}$";
        return date.matches(pattern);
    }
}
