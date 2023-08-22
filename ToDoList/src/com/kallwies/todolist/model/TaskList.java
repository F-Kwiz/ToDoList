package com.kallwies.todolist.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;


public class TaskList {
    ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
    	if (task != null) {
        tasks.add(task);
    	}
    }

    public void displayTasks() {
        for (int i=0; i < tasks.size();i++) {
        	System.out.println(tasks.get(i).getTitle());
        }
    }

    public void markTaskAsCompleted(int index) {
    	if (index <= tasks.size()) {
    		tasks.get(index).setCompleted(true);
    	} else { System.out.println("index Out of Range");}
    }

    void editTask(int index) {
        // Bearbeite die Details einer Aufgabe /TODO
    }

    public void deleteTask(int index) {
    	if (index <= tasks.size()) {
    		tasks.remove(index);
    	} else { System.out.println("index Out of Range");}
    }
    
    public void clearTasks() {
		int size = tasks.size();
    	for (int i=0; i < size; i++) {
    		deleteTask(0);
    	}
    	System.out.println(tasks);
    }

    public void sortTasksByDueDate() {
    	Collections.sort(tasks, new BirthdayComparator());
    }
    
    
    public ArrayList<Map<String,Object>> createArrayList() {
    	
    	ArrayList<Map<String,Object>> maplist = new ArrayList<>();
    	
    	for (Task task: tasks) {
    		Map<String,Object> attributes = new HashMap<>();
    		attributes.put("title", task.getTitle());
    		attributes.put("description", task.getDescription());
    		attributes.put("day", task.getDay());
    		attributes.put("month", task.getMonth());
    		attributes.put("year", task.getYear());
    		maplist.add((Map<String,Object>) attributes);
    	}
    	
    	return maplist;
    	
    }
    
    
    
    public ArrayList<Task> getTasks() {
    	return tasks;
    }
}
