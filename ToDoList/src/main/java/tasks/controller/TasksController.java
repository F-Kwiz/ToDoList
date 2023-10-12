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
			TaskView();
			TaskViewGroupSelection();
			
			EditWindow();
		}
			//
			// TaskView Buttons
			//
			
		private void TaskView() {
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
	            	viewController.getCalendarView().importData(modelController.getDataModel().getData()); // ??? Has to be improved. also in Apply and Create Button ???
	            }
	        });
			
			/**
			 * When Edit Button is Clicked in TaskView. It will open an EditWindow, where the user can change attributes of Task
			 * Edit Window needs information about task from DataModel.
			 * 
			 */
			viewController.viewControllerCallbacks.setCallback(new ViewController.ViewControllerCallbacks.EditClickedCallback() {
	            @Override
	            public void startCallback(String groupname, String task_id) {
	            	viewController.getEditWindow().createEditWindow(modelController.getDataModel().getGroupByName(groupname).getTaskById(task_id).getMap());
	            }
	        });
			
			/**
			 * When Add Button is Clicked in TaskView. It will open an EditWindow, where the user can create attributes of a new Task
			 * Edit Window needs information about any task from group from DataModel.
			 * 
			 */
			viewController.viewControllerCallbacks.setCallback(new ViewController.ViewControllerCallbacks.AddClickedCallback() {
	            @Override
	            public void startCallback(String groupname) {
	            	viewController.getEditWindow().createAddWindow(modelController.getDataModel().getGroupByName(groupname).getPlaceboTask());
	            }
	        });
		}
		
		
		private void TaskViewGroupSelection() {
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
		
		
		private void EditWindow() {
			/**
			 * When Button Apply is clicked in EditWindow
			 * get new information from EditWindow about a task and writes them into task.
			 * then imports the group of given task into View
			 * 
			 */
			viewController.viewControllerCallbacks.setCallback(new ViewController.ViewControllerCallbacks.ApplyClickedCallback() {
				@Override
				public void startCallback(Map<String, Object> data) {
					modelController.getDataModel().getGroupByName((String)data.get("group")).editTask(data);
	            	importGroupDataIntoView((String)data.get("group"));
	            	viewController.getCalendarView().importData(modelController.getDataModel().getData());
				}
	        });
			
			/**
			 * When Button Create is clicked in EditWindow
			 * gets filled in information about task and adds task to a group
			 * then imports the group of given task into View
			 */
			viewController.viewControllerCallbacks.setCallback(new ViewController.ViewControllerCallbacks.CreateClickedCallback() {
				@Override
				public void startCallback(Map<String, Object> data) {
					modelController.getDataModel().getGroupByName((String)data.get("group")).addTask(data);
	            	importGroupDataIntoView((String)data.get("group"));
	            	viewController.getCalendarView().importData(modelController.getDataModel().getData());
				}
	        });
		}
		
	}
}
