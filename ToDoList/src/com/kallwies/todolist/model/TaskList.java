package com.kallwies.todolist.model;

import java.util.ArrayList;

public class TaskList {
    static ArrayList<Task> tasks = new ArrayList<>();

    void addTask(Task task) {
        tasks.add(task);
    }

    void displayTasks() {
        for (int i=0; i < tasks.size();i++) {
        	System.out.println(tasks.get(i).getTitle());
        }
    }

    void markTaskAsCompleted(int index) {
        // Markiere eine Aufgabe als erledigt
    }

    void editTask(int index) {
        // Bearbeite die Details einer Aufgabe
    }

    void deleteTask(int index) {
        // Lösche eine Aufgabe aus der Liste
    }

    void sortTasksByDueDate() {
        // Sortiere die Aufgaben nach Fälligkeitsdatum
    }

    public static ArrayList<Task> getTasks() {
    	return tasks;
    }
}
