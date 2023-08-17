package com.kallwies.todolist.model;

public class Task {
    public String title;
    public String description;
    public int day;
    public boolean completed;
    
    
    public Task(String n_title, String n_description, int n_Date){
    	title = n_title;
    	description = n_description;
    	day = n_Date;
    }
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int dueDate) {
		this.day = dueDate;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
    
}
