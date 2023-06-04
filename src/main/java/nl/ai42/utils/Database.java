package nl.ai42.utils;

import nl.ai42.AI42Main;

import java.io.*;
import java.util.HashMap;

public class Database implements Serializable {
    private final HashMap<String, Table> tables;

    private final String file_path;


    public Database(String file_path) {
        this.file_path = file_path;

        try {
            File file = new File(file_path);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file_path);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Database result = (Database) objectInputStream.readObject();
                this.tables = result.tables;
            } else {
                this.tables = new HashMap<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a table by name
     *
     * @param name the name of the table to retrieve
     * @return the table you requested or null if the table doesn't exist
     */
    public Table getTable(String name) {
        return this.tables.get(name);
    }

    /**
     * Create a table with a name and a specific set of columns
     * NOTE: if the table exists already, it is overwritten
     *
     * @param name the name of the table to create
     * @param columns the names of the columns
     */
    public void createTable(String name, String[] columns) {
        this.tables.put(name, new Table());
    }

    public void dropTable(String name) {
        this.tables.remove(name);
    }

    public void dropDatabase() {
        this.tables.clear();
    }

    public void storeInFile() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(this.file_path);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
    }
}
