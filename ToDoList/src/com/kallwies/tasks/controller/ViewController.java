package com.kallwies.tasks.controller;

import java.util.ArrayList;
import java.util.Map;

import com.kallwies.tasks.view.CalendarView;
import com.kallwies.tasks.view.EditWindow;
import com.kallwies.tasks.view.GUI;
import com.kallwies.tasks.view.Selection.*;
import com.kallwies.tasks.view.taskview.TaskView;

import javafx.stage.Stage;


/*
 * Is a GUI Factory and handles every callback of all Objects of View
 */
public class ViewController {
	
	GUI gui;
    
	TaskView taskView;
	Selection selection;
	
    CalendarView calendarView;
    EditWindow editWindow;
    
    ViewCallback callback;
    ViewControllerCallbacks viewControllerCallbacks;
    
    String workSpacePath;
    
    
	public ViewController(Stage main, String workSpacePath) {
		this.workSpacePath = workSpacePath;
		
		this.callback = new ViewCallback();
		this.viewControllerCallbacks = new ViewControllerCallbacks();
		
		// TaskView
		FileSelection fileSelection = new FileSelection();
		fileSelection.loadDirectory(workSpacePath);
		GroupSelection groupSelection = new GroupSelection();
		
		this.selection = new Selection(fileSelection, groupSelection);
		taskView = new TaskView(selection);
	    
		
		// GUI
		calendarView = new CalendarView();
	    editWindow = new EditWindow();
		gui = new GUI();
		try {
			gui.start(main, taskView, calendarView, editWindow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		callback.GUICallback(); // initiates every callback of gui
		callback.taskViewCallbacks();
	}
	
	public void importData(ArrayList<Map<String, Object>> data) {
		taskView.importData(data);
		calendarView.importData(data);
	}
	
	/*
	 * 
	 */
	public void showTaskView() {
		taskView.setVisible(true);
		calendarView.setVisible(false);
	}
	
	/*
	 * 
	 */
	public void showCalendarView() {
		calendarView.setVisible(true);
		taskView.setVisible(false);
	}
	
	
	///
	/// GETTER / SETTER 
	///
	
	public EditWindow getEditWindow() {
		return editWindow;
	}
	
	
	public Selection getSelection() {
		return selection;
	}
	
	public TaskView getTaskView() {
		return taskView;
	}
	
	
	
	
	///
	/// CALLBACK
	///
	
	/*
	 * Inner Class that handles View Callback
	 * Either it creates a callback for a callback to root it a level up to TasksController
	 * or a Callback can be rooted to other instances of View
	 */
	public class ViewCallback{
		
		private void GUICallback() {
			/*
			 * Is a button in GUI that makes TaskView visible when pressed
			 */
			gui.setCallback(new GUI.ShowTaskView() {
				@Override
				public void startCallback() {
					showTaskView();
				}
			});
			/*
			 * Is a button in GUI that makes CalendarView visible when pressed
			 */
			gui.setCallback(new GUI.ShowCalendarView(){
				@Override
				public void startCallback() {
					showCalendarView();
				}
			});
		}
		
		private void editWindowCallbacks() {
	        /*
	         * Add Callback connected with overlayWindow (EditWindow)
	         * It returns a map as soon a button is clicked in EditWindow
	         */
			editWindow.setCallback(new EditWindow.OnButtonClickedCallback() {
	            @Override
	            public void onButtonClicked(Map<String, Object> data) {
	                Map<String, Object> map = data;
	                taskView.editTask(map);
	            }
	        });
	        
			editWindow.setCallback(new EditWindow.OnCreateCallback() {
	            @Override
	            public void onButtonClicked(Map<String, Object> data) {
	                Map<String, Object> map = data;
	                taskView.addTask(map);
	            }
	        });
		}
			
		private void taskViewCallbacks() {
	        /*
	         * Is triggered when a Task is edited
	         * Throws new information from TaskView into calendarView
	         */
	        taskView.setOnTaskChanged(new TaskView.TaskChangedCallback() {
	            @Override
	            public void taskChanged(ArrayList<Map<String, Object>> list) {
	                calendarView.importData(list);
	            }
	        });
	        
	        /*
	         * Is triggered by EditButton in TaskView
	         * opens edit Window to edit a task
	         */
	        taskView.setOnEditClicked(new TaskView.EditClickedCallback() {
	            @Override
	            public void editClicked(Map<String, Object> task) {
	            	editWindow.createEditWindow(task);
	            }
	        });
	        /*
	         * Is triggered by addButton in TaskView
	         * opens edit Window to add a task to taskList in TaskView
	         */
	        taskView.setCallback(new TaskView.AddClickedCallback() {
	            @Override
	            public void addClicked(Map<String, Object> task) {
	            	editWindow.createAddWindow(task);
	            }
	        });
	        /*
	         * Is triggered by saveButton in TaskView
	         * saves a xml file of a certain group
	         */
	        taskView.setCallback(new TaskView.SaveClickedCallback() {
	            @Override
	            public void saveClicked(String groupName) { // ??? Probably an array in the future because of a group of a group ???
	            	if (viewControllerCallbacks.saveClickedInterface != null) {
	            		viewControllerCallbacks.saveClickedInterface.runCallback(groupName);
	            	}
	            }
	        });
	        
	        //
	        // Selection
	        //
	        
	        /*
	         * GroupSelected
	         */
	        selection.callback.setCallback(new Selection.Callback.GroupSelectedCallback() {
	            @Override
	            public void startCallback(String groupName) { // ??? Probably an array in the future because of a group of a group ???
	            	if (viewControllerCallbacks.groupSelectedCallback != null) {
	            		viewControllerCallbacks.groupSelectedCallback.startCallback(groupName);
	            	}
	            }
	        });
		}
		
		private void calendarViewCallbacks() {
			/*
			 *  Used when Calendar triggers an edit of an appointment (right click on appointment)
			 */
	        calendarView.setCallback(new CalendarView.EditCallback() {
	            @Override
	            public void runCallback(Map<String, Object> task) {
	            	editWindow.createEditWindow(task);
	            }
	        });
	        /*
	         * Used when Calendar triggers a view of an appointment (double click on appointment)
	         */
	        calendarView.setCallback(new CalendarView.ViewCallback() {
	            @Override
	            public void runCallback(Map<String, Object> task) {
	            	editWindow.createViewWindow(task);
	            }
	        });
		}		
	}
	
	
	/*
	 * An interface class that holds the interfaces of ViewController
	 */
	public class ViewControllerCallbacks {
		
		SaveClickedInterface saveClickedInterface;
		GroupSelectedCallback groupSelectedCallback;
		
		/*
		 * Save Button clicked in TaskView Interface
		 */
		interface SaveClickedInterface{
			void runCallback(String groupname);
		}

		void setCallback(SaveClickedInterface callback){
			saveClickedInterface = callback;
		}
		
		
		/*
		 * 
		 */
		interface GroupSelectedCallback{
			void startCallback(String groupname);
		}
		void setCallback(GroupSelectedCallback callback){
			this.groupSelectedCallback = callback;
		}
	}
}
