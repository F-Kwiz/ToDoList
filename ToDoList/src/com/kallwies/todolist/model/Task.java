package com.kallwies.todolist.model;

import java.util.Map;

public class Task {
    public String title;
    public String description;
    public int day;
    public int month;
    public int year;
    public boolean completed;
    
    
    public Task(){
    	title = "";
    	description = "";
    	day = 0;
    	this.month = 0;
    	this.year = 0;
    }
    
    public Task(String n_title, String n_description, int n_Date, int month, int year){
    	title = n_title;
    	description = n_description;
    	day = n_Date;
    	this.month = month;
    	this.year = year;
    }
    
    
    public void fillWithMap(Map<String, Object> map) {
        try {
    	for (String key: map.keySet()) {
    		switch (key) {
    		case "title":
    			if (map.get(key) instanceof String) {
    			setTitle((String) map.get(key));
    			}
    			break;
    		case "description":
    			if (map.get(key) instanceof String) {
    			setDescription((String) map.get(key));
    			}
    			break;
    		case "day":
    			if (map.get(key) instanceof String) {
    			setDay(Integer.parseInt((String) map.get(key)));
    			}
    			break;
    		case "month":
    			if (map.get(key) instanceof String) {
    			setMonth(Integer.parseInt((String) map.get(key)));
    			}
    			break;
    		case "year":
    			if (map.get(key) instanceof String) {
    			setYear(Integer.parseInt((String) map.get(key)));
    			}
    			break;
            }
    	}
        }
    	catch (NumberFormatException e) {
                System.out.println("Invalid numeric string");
                //e.printStackTrace();
    		}
    	
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
