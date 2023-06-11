package nl.ai42.managers.AIAdapter;

import nl.ai42.managers.AIManager;

public class AIManagerAdapter implements AIAdapter {
    private static AIManagerAdapter instance;
    private AIManager aiManager;

    private AIManagerAdapter() {
        aiManager = new AIManager();
    }

    public static AIManagerAdapter getInstance() {
        if (instance == null)
            instance = new AIManagerAdapter();

        return instance;
    }

    @Override
    public String processQuery(String query) {

        // Process data before calling aiManager.ask

        return aiManager.ask(query);
    }
}

