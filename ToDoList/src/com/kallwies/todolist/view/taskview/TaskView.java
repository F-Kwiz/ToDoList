package com.kallwies.todolist.view.taskview;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.kallwies.todolist.view.EditWindow.OnButtonClickedCallback;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class TaskView extends VBox {
	
	VBox taskList = new VBox();
	
	public TaskView() {
		addButtons();
	}
	
	/*
	 * Adds Button to this View
	 */
	private void addButtons() {
		//Buttons
    	HBox menu = new HBox();
    	menu.setSpacing(20);
    	
    	// File Title
    	Label fileName = new Label("Table");
    	Font fileTitleFont = Font.font("Arial", FontWeight.BOLD, 20);
    	fileName.setFont(fileTitleFont);
    	menu.getChildren().add(fileName);
    	menu.setMargin(fileName, new javafx.geometry.Insets(0, 40, 0, 80));
    	
		// ADD BUTTON
        Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAddButtonClick();
            }});

        
        //EDIT BUTTON
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> handleEditButtonClick());
        
        //DELETE BUTTON
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> handleDeleteButtonClick());
        
        // SAVE BUTTON
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> handleSaveButtonClick());
        
        menu.getChildren().add(addButton);
        menu.getChildren().add(editButton);
        menu.getChildren().add(deleteButton);
        menu.getChildren().add(saveButton);
		
        this.getChildren().add(menu);
        this.getChildren().add(taskList);
	}
	
	
	
	/*
	 * @import ArrayList<Map<String, Object>> tasks <-- from parent/Listener
	 * Creates all needed TaskLines with information from XML-Files
	 * 
	 */
	public void loadInTasks(ArrayList<Map<String, Object>> tasks) {
		
		taskList.getChildren().clear();
		
		for(Map<String, Object> task: tasks) {
			TaskLine newLine = new TaskLine();
			newLine.fillTask(task);
			taskList.getChildren().add(newLine);
		}
	}
	
	
	/*
	 * 
	 * Creates ArrayList<Map<String,Object>> out of tasks in this.tasklist
	 * @return ArrayList<Map<String,Object>>
	 */
	public ArrayList<Map<String,Object>> exportTasks() {
		ArrayList<Map<String,Object>> ev_tasks = new ArrayList<Map<String,Object>>();
		for (Object node: taskList.getChildren()) {
			if (node instanceof TaskLine) {
				TaskLine taskLine = (TaskLine) node;
				ev_tasks.add(taskLine.exportMap());
			}
		}
		return ev_tasks;
	}
	
	
	
	/*
	 * @import Map<String, Object> map <-- from parent
	 * adds a new task
	 * 
	 */
	public void addTask(Map<String, Object> task) {
		if (findMatch(task) == null) {
			TaskLine newLine = new TaskLine();
			newLine.fillTask(task);
			taskList.getChildren().add(newLine);
		} else {System.out.println("GUI/TaskView-addTask: A similiar task already exists!");}
	}
	
	
	
	/*
	 * @import Map<String, Object> map <-- from GUI
	 * finds the matching task and changes it
	 * 
	 */
	public void editTask(Map<String, Object> map) {
		if (map.containsKey("previousData")) {
			@SuppressWarnings("unchecked")
			Map<String, Object> previousData = (Map<String, Object>) map.get("previousData"); 
			TaskLine taskLineToEdit = findMatch(previousData); // finds match over prevíousData before attributes were changed in EditWindow
			if (taskLineToEdit != null) {
				taskLineToEdit.setTitle((String)map.get("title"));
				taskLineToEdit.setDescription((String)map.get("description"));
				taskLineToEdit.setStartDate((String)map.get("start-date"));
				taskLineToEdit.setEndDate((String)map.get("end-date"));
		        if (taskChangedCallback != null) { // Calls Callback because a task is changed
		        	ArrayList<Map<String,Object>> exportedTasks = exportTasks();
		        	if (exportedTasks.size() != 0) {
		        		taskChangedCallback.taskChanged(exportedTasks);
		        	} else {System.out.println("GUI/TaskView-editTask: exportedTasks are empty, no change will be displayed (exportTask)");}
		        }
			} else {System.out.println("GUI/TaskView-editTask: no matching Task found");}
		} else {System.out.println("GUI/TaskView-editTask: no prevoius Data found in map");}
	}
	
	
	/*
	 * @import Map<String, Object> matchData <-- from: this.editTask()
	 * Checks all taskLines for a match with the information given by data and returns the match
	 * @return
	 */
	public TaskLine findMatch(Map<String, Object> matchData) {
		TaskLine matchedTask = null;
		for (Node node: taskList.getChildren()) {
	        if (node instanceof TaskLine) {
	            TaskLine taskLine = (TaskLine) node;
	            if (taskLine.getStartDate().equals(matchData.get("start-date")) == false) {
	            	continue;
	            }
	            if (taskLine.getEndDate().equals(matchData.get("end-date")) == false) {
	            	continue;
	            }
	            if (taskLine.getTitle().equals(matchData.get("title")) == false) {
	            	continue;
	            } else {matchedTask = taskLine; break;}
	        }
		}
		return matchedTask;
	}
	
	

	/*
	 * 
	 */
	private void handleAddButtonClick(){
		 if (addClickedCallback != null) {
			 for (Node node: taskList.getChildren()) {
				 if (node instanceof TaskLine) {
					 TaskLine taskLine = (TaskLine) node;
					 addClickedCallback.addClicked(taskLine.exportMap());
				        if (taskChangedCallback != null) {
				        	ArrayList<Map<String,Object>> exportedTasks = exportTasks();
				        	if (exportedTasks.size() != 0) {
				        		taskChangedCallback.taskChanged(exportedTasks);
				        	}
				        }
					 return;
				 }
			 }
		} // Add addClickCallback with null, to create a new strcuture in an empty taskview view
	}
	

	/*
	 * 
	 */
	private void handleEditButtonClick(){
		 if (editClickedCallback != null) {
			 for (Node node: taskList.getChildren()) {
				 if (node instanceof TaskLine) {
					 TaskLine taskLine = (TaskLine) node;
					 if (taskLine.clicked == true) {
						 editClickedCallback.editClicked(taskLine.exportMap());
					 }
				 }
			 }
		 }
	}
	
	
	/*
	 * 
	 */
	private void handleDeleteButtonClick(){
        Iterator<Node> iterator = taskList.getChildren().iterator(); // needed because something will be removed while iterating
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (node instanceof TaskLine) {
                TaskLine taskLine = (TaskLine) node;
                if (taskLine.clicked) {
                    iterator.remove(); // Remove the element using the iterator
                }
            }
        }
        if (taskChangedCallback != null) {
        	ArrayList<Map<String,Object>> exportedTasks = exportTasks();
        	if (exportedTasks.size() != 0) {
        		taskChangedCallback.taskChanged(exportedTasks);
        	}
        }
	}
	
	
	/*
	 * 
	 */
	private void handleSaveButtonClick(){
		 if (saveClickedCallback != null) {
			 saveClickedCallback.saveClicked();
		 }
	}
	
	
	 //
	 // CALLBACKS
	 //
	
	/*
	 * Callback When Task is changed
	 */
    public interface TaskChangedCallback {
        void taskChanged(ArrayList<Map<String, Object>> list);
    }
    
    private TaskChangedCallback taskChangedCallback; 

    public void setOnTaskChanged(TaskChangedCallback callback) {
        this.taskChangedCallback = callback;
    }
	
    
    
	/*
	 * Callback When Edit is clicked
	 */
    public interface EditClickedCallback {
        void editClicked(Map<String, Object> data);
    }
    
    private EditClickedCallback editClickedCallback; 

    public void setOnEditClicked(EditClickedCallback callback) {
        this.editClickedCallback = callback;
    }
    
    
	/*
	 * Callback When Add is clicked
	 */
    public interface AddClickedCallback {
        void addClicked(Map<String, Object> task);
    }
    
    private AddClickedCallback addClickedCallback; 

    public void setCallback(AddClickedCallback callback) {
        this.addClickedCallback = callback;
    }
    
    
	/*
	 * Callback When Save is clicked
	 */
    public interface SaveClickedCallback {
        void saveClicked();
    }
    
    private SaveClickedCallback saveClickedCallback; 

    public void setCallback(SaveClickedCallback callback) {
        this.saveClickedCallback = callback;
    }
}
