package com.kallwies.todolist.controller;

import com.kallwies.todolist.model.Task;
import com.kallwies.todolist.model.TaskList;
import java.util.ArrayList;

// This is the Controller of the View/GUI
// This Controller pulls from the other

public class FrontEndController implements IController {
	
	private BackEndController backEnd;
	
	public FrontEndController() {
		backEnd  = new BackEndController();
	}
	
	
	public void handleAddButtonClick() {
		
		System.out.println("hello");
	}

	
	@Override
	public TaskList loadXml(String filePath) {
		return backEnd.loadXml(filePath);
	}
	
	@Override
	public void saveXml(ArrayList<Task> list, String filePath) {
		backEnd.saveXml(list, filePath);
	}

	
}
