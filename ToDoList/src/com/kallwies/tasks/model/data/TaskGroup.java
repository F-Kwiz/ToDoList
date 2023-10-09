package com.kallwies.tasks.model.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskGroup {
	
	private String name = "";
	private String path = "";
	
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	/*
	 * Constructor
	 */
	public TaskGroup(String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	
	/*
	 * Exports information about TaskGroup as a Map
	 */
	public Map<String,Object> getMap(){
		Map<String,Object> list = getMeta();
		
		ArrayList<Map<String,Object>> tasklist = new ArrayList<Map<String,Object>>();
		for (Task task: getTasks()) {
			tasklist.add(task.getMap());
		}
		list.put("tasks", tasklist);
		return list;
	}
	
	
	/*
	 * Exports meta-information about TaskGroup as a Map
	 * Without Tasks
	 */
	public Map<String,Object> getMeta(){
		Map<String,Object> list = new HashMap<String,Object>();
		
		list.put("group", name);
		list.put("path", path);
		
		return list;
	}
	
	
	/*
	 * Add Task to list of tasks
	 */
	public void addTask(Task task) {
		tasks.add(task);
	}
	
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	
	/*
	 * go through tasks and find task with the right id
	 */
	public Task getTaskById(String id) {
		for (Task task: tasks) {
			if (task.getId().equals(id)) {
				return task;
			}
		}
		return null;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	
}
