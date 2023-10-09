package com.kallwies.root;


import com.kallwies.tasks.controller.TasksConstructor;
import com.kallwies.tasks.controller.TasksController;
import com.kallwies.workspacefinder.*;

import javafx.application.Application;
import javafx.stage.Stage;


public class Root extends Application{
	
    private Stage primaryStage;
	
	String workSpacePath = "";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		
		WorkSpaceFinderGUI workSpaceFinderGUI = new WorkSpaceFinderGUI();
		
		workSpaceFinderGUI.start(primaryStage);
		
		// Callback that initiate the loading of TasksController
		workSpaceFinderGUI.setCallback(new WorkSpaceFinderGUI.PathCallback() {
			@Override
			public void runCallback(String path) {
				workSpacePath = path;
				try {
					/*
					 * the constructor of the application Tasks gets called and returns the tasksController.
					 * With a callback it would be possible to get back to the level of root to change the Scene again for example
					 */
					TasksController tasksController = TasksConstructor.start(primaryStage, path);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}});
		
		
		
	}
	
    
    public String getWorkSpacePath() {
		return workSpacePath;
	}

	public void setWorkSpacePath(String workSpacePath) {
		this.workSpacePath = workSpacePath;
	}



	public static void main(String[] args) {
        Application.launch(args);
    }
	
}
