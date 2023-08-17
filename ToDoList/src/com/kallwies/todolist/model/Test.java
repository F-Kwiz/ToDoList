package com.kallwies.todolist.model;

public class Test {
	public static void main (String [] args){
		System.out.println("YOO");
		
		TaskList list1 = new TaskList();
		
		Task task1 = new Task("Wash", "everything", 12);
		list1.addTask(task1);
		list1.displayTasks();
		
	}
}
