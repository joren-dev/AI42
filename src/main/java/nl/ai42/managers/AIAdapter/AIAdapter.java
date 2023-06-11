package nl.ai42.managers.AIAdapter;

public abstract class AIAdapter {
    // Template method pattern
    // The standard/default logic of AIAdapter is defined here without determining the implementation
    // that's why it's a template method pattern.
    public String processQuery(String query) {
        // Reformat in right order for instance.
        String processedQuery = preprocessQuery(query);

        // Execute query
        String result = performQuery(processedQuery);

        // Process result
        result = postprocessResult(result);

        return result;
    }

    // Abstract methods to be implemented by subclasses
    protected abstract String preprocessQuery(String query);

    protected abstract String performQuery(String processedQuery);

    protected abstract String postprocessResult(String result);
}
