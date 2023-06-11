package nl.ai42.managers.AIAdapter;

import nl.ai42.managers.AIManager;

public class AIManagerAdapter extends AIAdapter {
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
    protected String preprocessQuery(String query) {
        // Implement preprocessing logic specific to AIManager
        return query;
    }

    @Override
    protected String performQuery(String processedQuery) {
        // Implement AIManager query execution logic
        return processedQuery;
    }

    @Override
    protected String postprocessResult(String result) {
        // Implement postprocessing logic specific to AIManager
        return result;
    }
}

