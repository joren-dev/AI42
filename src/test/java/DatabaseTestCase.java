import nl.ai42.AI42Main;
import nl.ai42.utils.Database;
import nl.ai42.utils.Row;
import nl.ai42.utils.Table;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseTestCase {
    @Test
    public void testCreateTable() {
        // Arrange
        Database database = new Database("testingdb.db");

        // Act
        database.createTable("testingtable", new String[]{"column1", "testcolumn2"});

        // Assert
        Assertions.assertInstanceOf(Table.class, database.getTable("testingtable"));
    }

    @Test
    public void testInsert() {
        // Arrange
        Database database = new Database("testingdb.db");
        database.createTable("testingtable", new String[]{"key1", "key2"});

        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "valueOfKey1");
        data.put("key2", "valueOfKey2");

        // Act
        database.getTable("testingtable").insert(new Row(data));

        // Assert
        ArrayList<Row> actualRows = database.getTable("testingtable").select((row) -> true);
        ArrayList<Row> expectedRows = new ArrayList<>(List.of(new Row(data)));

        for (int i = 0; i < actualRows.size(); i++) {
            Assertions.assertEquals(actualRows.get(i).getValue("key1"), expectedRows.get(i).getValue("key1"));
            Assertions.assertEquals(actualRows.get(i).getValue("key2"), expectedRows.get(i).getValue("key2"));
        }
        Assertions.assertEquals(actualRows.size(), expectedRows.size());
    }
}
