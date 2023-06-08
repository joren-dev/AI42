import nl.ai42.managers.AIManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AIManagerTestCase {
    @Test
    public void testAsk() {
        // Act
        String response = AIManager.ask("Hi");

        // Assert
        Assertions.assertEquals("Process query.", response);
    }
}
