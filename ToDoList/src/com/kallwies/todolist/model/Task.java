package com.kallwies.todolist.model;

public class Task {
    public String title;
    public String description;
    public int day;
    public int month;
    public int year;
    public boolean completed;
    
    
    public Task(String n_title, String n_description, int n_Date, int month, int year){
    	title = n_title;
    	description = n_description;
    	day = n_Date;
    	this.month = month;
    	this.year = year;
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

	public boolean isCompleted() {
		return completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
    
	
	
}
