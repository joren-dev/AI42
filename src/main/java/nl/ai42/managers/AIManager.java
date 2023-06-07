package nl.ai42.managers;

import java.util.Locale;

public class AIManager {
    /**
     * This is the function that turns a question into an answer.
     *
     * @param query the question to ask the AI.
     * @return the response from the AI.
     */
    public static String ask(final String query) {
        String lowerCaseQuery = query.toLowerCase(Locale.ROOT);
        if (lowerCaseQuery.contains("hello") || lowerCaseQuery.contains("hi")) {
            return "Hello there!";
        } else if (lowerCaseQuery.contains("how are you")) {
            return "I'm doing well, thank you!";
        } else if (lowerCaseQuery.contains("goodbye") || lowerCaseQuery.contains("bye")) {
            return "Goodbye! Have a nice day!";
        } else if (lowerCaseQuery.contains("help")) {
            return "I'm here to assist you. How can I help?";
        } else {
            return "I'm sorry, I didn't understand. Can you please rephrase?";
        }
    }
}
