package com.kallwies.todolist.model;

import java.util.Comparator;

public class BirthdayComparator implements Comparator<Task> {
	@Override
    public int compare(Task a, Task b) {
        if (a.getYear() != b.getYear()) {
            return Integer.compare(a.getYear(), b.getYear());
        }
        if (a.getMonth() != b.getMonth()) {
            return Integer.compare(a.getMonth(), b.getMonth());
        }
        return Integer.compare(a.getDay(), b.getDay());
    }
}
