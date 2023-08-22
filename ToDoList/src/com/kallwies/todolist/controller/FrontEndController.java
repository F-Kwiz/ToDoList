package com.kallwies.todolist.controller;


import java.util.ArrayList;
import java.util.Map;


// This is the Controller of the View/GUI
// This Controller pulls from the other

public class FrontEndController implements IController {
	
	private BackEndController backEnd;
	
	public FrontEndController() {
		backEnd  = new BackEndController();
	}
	
	
	public void handleAddButtonClick() {
		backEnd.handleAddButtonClick();
	}

	
	@Override
	public ArrayList<Map<String, Object>> loadXml(String filePath) {
		return backEnd.loadXml(filePath);
	}
	
	@Override
	public void saveXml(ArrayList<Map<String, Object>> tasks, String filePath) {
		backEnd.saveXml(tasks, filePath);
	}

	
}
