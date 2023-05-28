package nl.ai42.utils;

import java.io.*;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

class Row implements Serializable {
    private final HashMap<String, String> data;

    public Row() {
        this.data = new HashMap<>();
    }

    public String getValue(String column) {
        return this.data.get(column);
    }
}

class Table implements Serializable {
    private final ArrayList<Row> rows;

    public Table() {
        this.rows = new ArrayList<>();
    }

    public void insert(Row values) {
        this.rows.add(values);
    }

    public ArrayList<Row> select(Predicate<Row> predicate) {
        return this.rows.stream().filter(predicate).collect(Collectors.toCollection(ArrayList::new));
    }
}

public class Database implements Serializable {
    private final HashMap<String, Table> tables;

    public Database() {
        this.tables = new HashMap<>();
    }

    public Database(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(filePath);
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

    public void storeInFile(String filePath) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
    }
}
