package com.kallwies.todolist.controller;

import com.kallwies.todolist.model.*;
import java.util.ArrayList;
import java.util.Map;


public class BackEndController implements IController {
    private TaskList taskList = new TaskList();
    
    public BackEndController() {
        taskList = new TaskList();
    }
        
    
    
    // Weitere Methoden für Aktualisierung, Filterung, etc.

    public TaskList getTaskList() {
        return taskList;
    }

    @Override
    public ArrayList<Map<String, Object>> loadXml(String filePath) {
    	
        ArrayList<Map<String, Object>> itemList = XmlHandler.XmlLoader.load(filePath);
        
        taskList.clearTasks();
        
        // Logic to fill tasklist
        for (Map<String, Object> itemMap : itemList) {
        	Task task = new Task();
        	task.fillWithMap(itemMap);
        	taskList.addTask(task);
        }
        
        taskList.sortTasksByDueDate();
        
        return taskList.createArrayList();     
    }
   
	@Override
	public void saveXml(ArrayList<Map<String, Object>> list, String filePath) {
		XmlHandler.XmlSaver.createXmlFile(list, filePath);
	}
	

	@Override
	public void handleAddButtonClick() {
		taskList.addTask(null);
		
	}




	
}