package com.kallwies.todolist.model;

import java.util.Comparator;
import java.util.Map;

public class BirthdayComparator implements Comparator<Map<String,Object>> {

	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	@Override
    public int compare(Map<String,Object> a, Map<String,Object> b) {
        if (a.getYear() != b.getYear()) {
            return Integer.compare(a.getYear(), b.getYear());
        }
        if (a.getMonth() != b.getMonth()) {
            return Integer.compare(a.getMonth(), b.getMonth());
        }
        return Integer.compare(a.getDay(), b.getDay());
    }*/
}
