import nl.ai42.utils.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RowTestCase {
    @Test
    public void testRowConstructor() {
        // Act
        Row row = new Row();

        // Assert
        Assertions.assertInstanceOf(Row.class, row);
    }
}
