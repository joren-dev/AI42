import nl.ai42.utils.database.Database;
import nl.ai42.utils.database.Row;
import nl.ai42.utils.database.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseTestCase {
    private Database database;

    @BeforeEach
    public void setUp() {
        // Create a new database instance before each test
        database = new Database("testingdb.db");
    }

    @Test
    public void createTable_ValidTable_CreatesTable() {
        // Arrange
        String tableName = "testingtable";
        String[] columns = {"column1", "testcolumn2"};

        // Act
        database.createTable(tableName, columns);

        // Assert
        Assertions.assertInstanceOf(Table.class, database.getTable(tableName));
    }

    @Test
    public void insert_ValidData_InsertsRow() {
        // Arrange
        String tableName = "testingtable";
        String[] columns = {"key1"};
        database.createTable(tableName, columns);

        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "valueOfKey1");

        // Act
        database.getTable(tableName).insert(new Row(data));

        // Assert
        ArrayList<Row> rows = database.getTable(tableName).select((row) -> true);
        Assertions.assertEquals(1, rows.size());
        Row insertedRow = rows.get(0);
        Assertions.assertEquals("valueOfKey1", insertedRow.getValue("key1"));
    }

    @Test
    public void constructDatabaseFromFile_ValidFile_LoadsData() {
        // Arrange
        String tableName = "testingtable";
        String[] columns = {"key1"};
        HashMap<String, String> data = new HashMap<>();
        data.put("key1", "valueOfKey1");

        // Create a database and store data in a file
        Database originalDatabase = new Database("testingdb.db");
        originalDatabase.createTable(tableName, columns);
        originalDatabase.getTable(tableName).insert(new Row(data));
        originalDatabase.storeInFile();

        // Act
        Database loadedDatabase = new Database("testingdb.db");

        // Assert
        ArrayList<Row> rows = loadedDatabase.getTable(tableName).select((row) -> true);
        Assertions.assertEquals(1, rows.size());
        Row loadedRow = rows.get(0);
        Assertions.assertEquals("valueOfKey1", loadedRow.getValue("key1"));
    }

    @Test
    public void dropDatabase_ExistingDatabase_DropsDatabase() {
        // Arrange
        String tableName = "newTable";
        String[] columns = {"col1", "col2"};

        database.createTable(tableName, columns);

        // Act
        database.dropDatabase();

        // Assert
        Assertions.assertNull(database.getTable(tableName));
    }

    @Test
    public void dropTable_ExistingTable_DropsTable() {
        // Arrange
        String tableName1 = "newTable";
        String tableName2 = "newTable2";
        String columnName = "col1";

        database.createTable(tableName1, new String[]{columnName});
        database.createTable(tableName2, new String[]{columnName});

        // Act
        database.dropTable(tableName1);

        // Assert
        Assertions.assertNull(database.getTable(tableName1));
        Assertions.assertNotNull(database.getTable(tableName2));
    }

    @Test
    public void constructFromNonExistingFile_NonExistingFile_CreatesEmptyDatabase() {
        // Act
        Database database = new Database("nonexistingFile.db");

        // Assert
        Assertions.assertNotNull(database);
    }
}
