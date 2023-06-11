package nl.ai42.utils;

import nl.ai42.AI42Main;

import java.io.*;
import java.util.HashMap;

public class Database implements Serializable {
    private final HashMap<String, Table> tables;
    private final String file_path;

    public Database(final String file_path) {
        this.file_path = file_path;
        this.tables = new HashMap<>();
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            File file = new File(file_path);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file_path);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Database result = (Database) objectInputStream.readObject();
                this.tables.putAll(result.tables);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Table getTable(String name) {
        return this.tables.get(name);
    }

    public void createTable(String name, String[] columns) {
        this.tables.put(name, new Table());
    }

    public void dropTable(String name) {
        this.tables.remove(name);
    }

    public void dropDatabase() {
        this.tables.clear();
    }

    public void storeInFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.file_path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
