package tasks.controller;

import java.util.Map;

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
	
	
	private void importGroupDataIntoView(String groupname) {
    	viewController.getTaskView().importGroup(
    			(Map<String, Object>) modelController.getDataModel().getGroupByName(groupname).getMap() 
    			);
	}
	
	
	/**
	 * Handles every callback from all View instances
	 * 
	 */
	public class ViewCallback {
		
		/**
		 * Constructor
		 * implements the Callback automatically on instance
		 * 
		 */
		public ViewCallback() {
			
			/**
			 * When save Button is clicked in View-TaskView
			 * 
			 */
			viewController.viewControllerCallbacks.setCallback(new ViewController.ViewControllerCallbacks.SaveClickedCallback() {
	            @Override
	            public void startCallback(String groupname) {
	            	modelController.saveXml(groupname);
	            }
	        });
			
			/**
			 * When Delete Button is pressed in TaskView
			 * Deletes Task in group in DataModel. Then updates TaskView
			 * 
			 */
			viewController.viewControllerCallbacks.setCallback(new ViewController.ViewControllerCallbacks.DeleteClickedCallback() {
	            @Override
	            public void startCallback(String groupname, String task_id) {
	            	modelController.getDataModel().deleteTask(groupname, task_id);
	            	importGroupDataIntoView(groupname);
	            }
	        });
			
			
			
			
			/**
			 * When a group in GroupSelection is DoubleClicked
			 * loads the Map from the doubleClicked group and calls importGroup of TaskView with the group Map as parameter
			 * 
			 */
			viewController.viewControllerCallbacks.setCallback(new ViewController.ViewControllerCallbacks.GroupSelectedCallback() {
	            @Override
	            public void startCallback(String groupname) {
	            	viewController.getTaskView().importGroup(
	            			(Map<String, Object>) modelController.getDataModel().getGroupByName(groupname).getMap() 
	            			);
	            }
	        });
			
			
		}
		
	}
	
	
}
