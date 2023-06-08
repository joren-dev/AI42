import nl.ai42.utils.Database;
import nl.ai42.utils.Row;
import nl.ai42.utils.Table;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseTestCase implements Serializable {
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

    @Test
    public void testConstructDatabaseFromFile() {
        // Arrange
        File file = new File("testingdb.db");
        if (file.exists()) {
            file.delete();
        }

        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "valueOfKey1");
        data.put("key2", "valueOfKey2");
        Database database = new Database("testingdb.db");
        database.createTable("testingtable", new String[]{"key1", "key2"});
        database.getTable("testingtable").insert(new Row(data));
        database.storeInFile();

        // Act
        Database loaded = new Database("testingdb.db");

        // Assert
        ArrayList<Row> actualRows = loaded.getTable("testingtable").select((row) -> true);
        ArrayList<Row> expectedRows = database.getTable("testingtable").select((row) -> true);

        for (int i = 0; i < actualRows.size(); i++) {
            Assertions.assertEquals(actualRows.get(i).getValue("key1"), expectedRows.get(i).getValue("key1"));
            Assertions.assertEquals(actualRows.get(i).getValue("key2"), expectedRows.get(i).getValue("key2"));
        }
        Assertions.assertEquals(actualRows.size(), expectedRows.size());
    }

    @Test
    public void testConstructDatabaseFromFileWithError() {
        // Act / Assert
        Assertions.assertThrows(RuntimeException.class, () -> new Database("brokenTestDatabase.db"));
    }

    @Test
    public void testDropDatabase() {
        // Arrange
        Database database = new Database("testingdb.db");
        database.createTable("newTable", new String[]{"col1", "col2"});
        database.createTable("newTable2", new String[]{"col1", "col2"});
        HashMap<String, String> data = new HashMap<>();
        data.put("col1", "val1");
        data.put("col2", "val2");
        Row row = new Row(data);
        database.getTable("newTable").insert(row);

        // Act
        database.dropDatabase();

        // Assert
        Assertions.assertNull(database.getTable("newTable"));
        Assertions.assertNull(database.getTable("newTable2"));
    }

    @Test
    public void testDropTable() {
        // Arrange
        Database database = new Database("testingdb.db");
        database.createTable("newTable", new String[]{"col1", "col2"});
        database.createTable("newTable2", new String[]{"col1", "col2"});
        HashMap<String, String> data = new HashMap<>();
        data.put("col1", "val1");
        data.put("col2", "val2");
        Row row = new Row(data);
        database.getTable("newTable").insert(row);

        // Act
        database.dropTable("newTable");

        // Assert
        Assertions.assertNull(database.getTable("newTable"));
        Assertions.assertInstanceOf(Table.class, database.getTable("newTable2"));
    }

    @Test
    public void testConstructFromNonExistingFile() {
        // Act
        Database database = new Database("nonexistingFile.db");

        // Assert
        Assertions.assertNotNull(database);
    }

    @Test
    public void testStoreError() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        Database database = new Database("testingdb.db");
        database.storeInFile();

        database = new Database("testingdb.db");
        Field field = Database.class.getDeclaredField("file_path");
        field.setAccessible(true);
        field.set(database, "src");
        field.setAccessible(false);

        // Act / Assert
        Assertions.assertThrows(RuntimeException.class, database::storeInFile);
    }
}
