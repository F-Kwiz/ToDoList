package com.kallwies.todolist.model;

import java.util.ArrayList;
import java.util.Collections;


public class TaskList {
    ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
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
    	for (int i=0; i < tasks.size();i++) {
    		deleteTask(i);
    	}
    	
    }

    public void sortTasksByDueDate() {
    	Collections.sort(tasks, new BirthdayComparator());
    }

    public ArrayList<Task> getTasks() {
    	return tasks;
    }
}
