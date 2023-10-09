package com.kallwies.tasks.controller;

import java.util.ArrayList;
import java.util.Map;

import com.kallwies.tasks.view.CalendarView;
import com.kallwies.tasks.view.GUI;

import javafx.stage.Stage;


public class TasksController {
	
	ViewController  viewController;
	ModelController modelController;
	
	ViewCallback viewCallback;
	
	public TasksController(ViewController viewController, ModelController modelController) {
		this.viewController = viewController;
		this.viewCallback = new ViewCallback();
		this.modelController = modelController;
		
		viewController.importData(modelController.getData());
		
	}
	
	
	
	/*
	 * Handles every callback from all View instances
	 */
	public class ViewCallback {
		
		public ViewCallback() {
			
			/*
			 * When save Button is clicked in View-TaskView
			 */
			viewController.viewControllerCallbacks.setCallback(new ViewController.ViewControllerCallbacks.SaveClickedInterface() {
	            @Override
	            public void runCallback(String groupname) {
	            	modelController.saveXml(groupname);
	            }
	        });
			
			
			/*
			 * When a group in GroupSelection is DoubleClicked
			 */
			viewController.viewControllerCallbacks.setCallback(new ViewController.ViewControllerCallbacks.GroupSelectedCallback() {
	            @Override
	            public void startCallback(String groupname) {
	            	viewController.getTaskView().importGroup(
	            			(Map<String, Object>) modelController.getDataModel().getGroupByName(groupname).getMap());
	            }
	        });
		}
		
	}
	
	
}
