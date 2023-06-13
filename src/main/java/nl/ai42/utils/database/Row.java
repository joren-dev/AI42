package nl.ai42.utils.database;

import java.io.Serializable;
import java.util.HashMap;

public class Row implements Serializable {
    private final HashMap<String, String> data;

    public Row() {
        this.data = new HashMap<>();
    }

    public Row(HashMap<String, String> data) {
        this.data = data;
    }

    public String getValue(String column) {
        return this.data.get(column);
    }
}
