package nl.ai42.managers;

import java.util.Locale;

interface AIAdapter {
    String processQuery(String query);
}

public class AIManager {
    private static AIAdapter adapter;

    public static void initialize() {
        adapter = AIAdapterFactory.createAIAdapter();
    }

    public static String ask(String query) {
        if (adapter == null) {
            throw new IllegalStateException("AIManager is not initialized. Call initialize() first.");
        }

        return adapter.processQuery(query);
    }
}

class AIAdapterImpl implements AIAdapter {
    public String processQuery(String query) {
        String lowerCaseQuery = query.toLowerCase(Locale.ROOT);

        if (lowerCaseQuery.contains("hello") || lowerCaseQuery.contains("hi")) {
            return this.handleGreeting();
        } else if (lowerCaseQuery.contains("how are you")) {
            return this.handleHowAreYou();
        } else if (lowerCaseQuery.contains("goodbye") || lowerCaseQuery.contains("bye")) {
            return this.handleGoodbye();
        } else if (lowerCaseQuery.contains("help")) {
            return this.handleHelp();
        } else {
            return this.handleDefault();
        }
    }

    private String handleGreeting() {
        return "Hello there!";
    }

    private String handleHowAreYou() {
        return "I'm doing well, thank you!";
    }

    private String handleGoodbye() {
        return "Goodbye! Have a nice day!";
    }

    private String handleHelp() {
        return "I'm here to assist you. How can I help?";
    }

    private String handleDefault() {
        return "I'm sorry, I didn't understand. Can you please rephrase?";
    }
}

class AIAdapterFactory {
    public static AIAdapter createAIAdapter() {
        return new AIAdapterImpl();
    }
}


