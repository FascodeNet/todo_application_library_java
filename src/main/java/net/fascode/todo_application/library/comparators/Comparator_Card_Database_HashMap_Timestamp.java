package net.fascode.todo_application.library.comparators;

import net.fascode.todo_application.library.db.Card_Database;

import java.util.Comparator;
import java.util.Map;

public class Comparator_Card_Database_HashMap_Timestamp implements Comparator<Map.Entry<String, Card_Database>> {
    @Override
    public int compare(Map.Entry<String, Card_Database> o1, Map.Entry<String, Card_Database> o2) {
        return o1.getValue().compareTo(o2.getValue());
    }
}
