package tasks.controller;

import java.util.ArrayList;
import java.util.Map;

import tasks.view.CalendarView;
import tasks.view.EditWindow;
import tasks.view.GUI;
import tasks.view.selection.*;
import tasks.view.taskview.TaskView;
import workspacefinder.WorkSpaceFinderGUI;
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
		callback.editWindowCallbacks();
	}
	
	public void importData(ArrayList<Map<String, Object>> data) {
		taskView.importData(data);
		calendarView.importData(data);
	}
	
	/**
	 * import a group in View
	 * 
	 * @param group
	 */
	public void importGroup(Map<String, Object> group) {
		taskView.importGroup(group);
		//calendarView.importGroup(group);
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
	
	public CalendarView getCalendarView() {
		return calendarView;
	}
	
	
	///
	/// CALLBACK
	///
	
	/**
	 * Inner Class that handles every Callback of ViewControllers "children"
	 * Either it creates a callback for a callback to root it a level up to TasksController
	 * or a Callback can be rooted to other instances of View
	 * 
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
		
		/**
		 * initiates every Callback of EditWindow
		 * 
		 */
		private void editWindowCallbacks() {
	        /**
	         * Add Callback connected with overlayWindow (EditWindow)
	         * It returns a map as soon a button is clicked in EditWindow
	         * 
	         */
			editWindow.setCallback(new EditWindow.ApplyClickedCallback() {
	            @Override
	            public void startCallback(Map<String, Object> data) {
	            	if (viewControllerCallbacks.applyClickedCallback != null) {
	            		viewControllerCallbacks.applyClickedCallback.startCallback(data);
	            	}
	            }
	        });
	        
			editWindow.setCallback(new EditWindow.OnCreateCallback() {
	            @Override
	            public void startCallback(Map<String, Object> data) {
	            	if (viewControllerCallbacks.createClickedCallback != null) {
	            		viewControllerCallbacks.createClickedCallback.startCallback(data);
	            	}
	            }
	        });
		}
		
		
		/**
		 * initiates every Callback of TaskView
		 * 
		 */
		private void taskViewCallbacks() {
			
			/**
			 * determines the Callback of TaskView.
			 * passes information to own Callback
			 *
			 * @see TasksController
			 * @see TaskView
			 */
	        taskView.setCallback(new TaskView.DeleteClickedCallback() {
	            @Override
	            public void startCallback(String groupname, String task_id) {
	            	if (viewControllerCallbacks.deleteClickedCallback != null) {
	            		viewControllerCallbacks.deleteClickedCallback.startCallback(groupname, task_id);
	            	}
	            }
	        });
	        
	        /*
	         * Is triggered by EditButton in TaskView
	         * opens edit Window to edit a task
	         */
	        taskView.setCallback(new TaskView.EditClickedCallback() {
	            @Override
	            public void startCallback(String groupname, String task_id) {
	            	if (viewControllerCallbacks.editClickedCallback != null) {
	            		viewControllerCallbacks.editClickedCallback.startCallback(groupname, task_id);
	            	}
	            }
	        });
	        
	        /*
	         * Is triggered by addButton in TaskView
	         * opens edit Window to add a task to taskList in TaskView
	         */
	        taskView.setCallback(new TaskView.AddClickedCallback() {
				@Override
				public void startCallback(String groupName) {
					if (viewControllerCallbacks.addClickedCallback != null) {
						viewControllerCallbacks.addClickedCallback.startCallback(groupName);
					}
				}
	        });
	        
	        /*
	         * Is triggered by saveButton in TaskView
	         * saves a xml file of a certain group
	         */
	        taskView.setCallback(new TaskView.SaveClickedCallback() {
				@Override
				public void startCallback(String groupName) {
					if (viewControllerCallbacks.saveClickedCallback != null) {
						viewControllerCallbacks.saveClickedCallback.startCallback(groupName);
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
		
		
		/**
		 * initiates every Callback from CalendarView
		 * 
		 */
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
	
	
	/**
	 * Holds every callback that is called by ViewController itself
	 * 
	 */
	public class ViewControllerCallbacks {
		
		// TaskView
		// TaskView Buttons
		DeleteClickedCallback deleteClickedCallback;
		SaveClickedCallback saveClickedCallback;
		EditClickedCallback editClickedCallback;
		AddClickedCallback addClickedCallback;
		
		// Group Selection
		GroupSelectedCallback groupSelectedCallback;
		
		// Edit Window
		CreateClickedCallback createClickedCallback;
		ApplyClickedCallback applyClickedCallback;
		
		
		
		/**
		 * Save Button clicked in TaskView
		 * 
		 */
		interface SaveClickedCallback{
			void startCallback(String groupname);
		}

		void setCallback(SaveClickedCallback callback){
			saveClickedCallback = callback;
		}
		
		
		/**
		 * Delete Button clicked in TaskView
		 */
		interface DeleteClickedCallback{
			void startCallback(String groupname, String task_id);
		}

		void setCallback(DeleteClickedCallback callback){
			deleteClickedCallback = callback;
		}
		
		
		/**
		 * Edit Button clicked in TaskView
		 */
		interface EditClickedCallback{
			void startCallback(String groupname, String task_id);
		}

		void setCallback(EditClickedCallback callback){
			editClickedCallback = callback;
		}
		
		
		/**
		 * Add Button clicked in TaskView. Gets a task from group and gives it to EditWindow. EditWindoe needs a template
		 */
		interface AddClickedCallback{
			void startCallback(String groupname);
		}

		void setCallback(AddClickedCallback callback){
			addClickedCallback = callback;
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
		
		
		//
		// EDIT WINDOW
		//
		
		/**
		 * When Create on EditWindow is clicked
		 * 
		 */
		interface CreateClickedCallback{
			void startCallback(Map<String, Object> data);
		}
		void setCallback(CreateClickedCallback callback){
			this.createClickedCallback = callback;
		}
		
		/**
		 * 
		 */
		interface ApplyClickedCallback{
			void startCallback(Map<String, Object> data);
		}
		void setCallback(ApplyClickedCallback callback){
			this.applyClickedCallback = callback;
		}
		
	}
}
