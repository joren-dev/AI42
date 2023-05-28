package nl.ai42.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Table implements Serializable {
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
