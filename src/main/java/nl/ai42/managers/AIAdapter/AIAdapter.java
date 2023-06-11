package nl.ai42.managers.AIAdapter;

public abstract class AIAdapter {
    // Template method pattern
    // The standard/default logic of AIAdapter is defined here without determining the implementation
    // that's why it's a template method pattern.
    // You could have another class named AIPictureManagerAdapter, have it inherit from this and itll have the same
    // processQuery() method, but since its images the abstract methods preprocessQuery, performQuery and
    // postprocessResult would have different implementation. That's exactly how template method pattern is used.
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
