package com.kallwies.todolist.controller;

import com.kallwies.todolist.model.*;
import java.util.List;
import java.util.ArrayList;


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
    public TaskList loadXml(String filePath) {
    	
        List<Task> itemList = XmlHandler.XmlLoader.load(filePath);
        
        taskList.clearTasks();
        
        for (Task item : itemList) {
        	taskList.addTask(item);
        }
        
        return taskList;     
    }
   
	@Override
	public void saveXml(ArrayList<Task> list, String filePath) {
		XmlHandler.XmlSaver.createXmlFile(list, filePath);
	}
	

	@Override
	public void handleAddButtonClick() {
		// TODO Auto-generated method stub
		
	}




	
}