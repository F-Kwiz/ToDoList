package com.kallwies.tasks.controller;


import javafx.stage.Stage;

import org.w3c.dom.Document;

import com.kallwies.tasks.model.data.DataModel;

public class TasksConstructor {
	
	String workSpacePath = "";
	
	TasksController controller;
	ViewController  viewController;
	ModelController modelController;
	
	
	public static TasksController start(Stage main, String workSpacePath) {
		
		DataModel dataModel = new DataModel();
		ModelController modelController = new ModelController(dataModel); // needs workspacepath
		modelController.loadAllXml(workSpacePath);
		
		ViewController viewController = new ViewController(main, workSpacePath); // needs Stage
		TasksController controller = new TasksController(viewController, modelController);
		return controller;
	}
	
	
	
}
