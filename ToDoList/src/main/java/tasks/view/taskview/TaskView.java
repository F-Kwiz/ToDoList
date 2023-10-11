package tasks.view.taskview;


import java.util.ArrayList;
import java.util.Map;

import tasks.view.selection.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class TaskView extends SplitPane {
	
	VBox taskList = new VBox(); // holds all Tasklines
	VBox view = new VBox();
	Selection selection;
	
	String selectedTaskId = "";
	
	Label activeGroupText = new Label("Table");
	
	public TaskView(Selection selection) {
		this.selection = selection;
		this.getItems().add(selection);
		addButtons();
	}
	
	
	/*
	 * Adds Button and the TaskLines to View
	 * then adds View to TaskView
	 */
	private void addButtons() {
		//Buttons
    	HBox menu = new HBox();
    	menu.setSpacing(20);
    	
    	// File Title
    	Font fileTitleFont = Font.font("Arial", FontWeight.BOLD, 20);
    	activeGroupText.setFont(fileTitleFont);
    	menu.getChildren().add(activeGroupText);
    	menu.setMargin(activeGroupText, new javafx.geometry.Insets(0, 40, 0, 80));
    	
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
		
        view.getChildren().add(menu);
        view.getChildren().add(taskList);
        
        this.getItems().add(view);
	}
	
	
	///
	/// Initial
	/// IMPORT
	///
	
	
	/**
	 * imports groups to GroupSelection so it can be selected by a user
	 * 
	 * @param ArrayList<Map<String, Object>> tasks <-- from parent/Listener
	 */
	public void importData(ArrayList<Map<String, Object>> groups) {
		selection.importData(groups);
	}
	
	/**
	 * imports Task of a group in TaskView
	 * 
	 * @param ArrayList<Map<String, Object>> group From parent/Listener. A group in a project
	 */
    private ArrayList<Map<String, Object>> getTasksFromGroup(Map<String, Object> group) {
        if (group.containsKey("tasks")) {
            Object tasksObject = group.get("tasks");
            if (tasksObject instanceof ArrayList<?>) {
                try {
                    @SuppressWarnings("unchecked")
                    ArrayList<Map<String, Object>> taskArrayList = (ArrayList<Map<String, Object>>) tasksObject;
                    return taskArrayList;
                } catch (ClassCastException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
	

	
	///
	/// GET AND SET
	///
	
	/*
	 * @import Map<String, Object> map <-- from parent
	 * adds a new task
	 * 
	 */
	public void addTask(Map<String, Object> task) {
		TaskLine newLine = new TaskLine();
		newLine.fillTask(task);
		taskList.getChildren().add(newLine);
	}
	
	
	/**
	 * Get Id and group of selected Task
	 * 
	 * 
	 */
    public String getSelectedTask() {
    	return this.selectedTaskId;
    }
	
	
	/*
	 * sets the Children of taskList
	 * will clear Children first
	 * 
	 */
	public void importGroup(Map<String, Object> group) {
		
		activeGroupText.setText((String)group.get("group"));
		
		ArrayList<Map<String, Object>> groupTasks = (ArrayList<Map<String, Object>>) group.get("tasks"); // ??? will crash when cast does not work ???
		taskList.getChildren().clear();
		for (Map<String, Object> task : groupTasks) {
			TaskLine newLine = new TaskLine();
			newLine.fillTask(task);
			taskList.getChildren().add(newLine);
		}
		initiateTaskLineCallbacks();
	}
	
	
	///
	/// Handle Buttons
	///
	
	/*
	 * 
	 */
	private void handleAddButtonClick(){
		if (selection.getSelectedGroup() != null) {
	    	if (addClickedCallback != null) {
	    		addClickedCallback.startCallback(activeGroupText.getText());
	    	}
		}
	}
	

	/*
	 * 
	 */
	private void handleEditButtonClick(){
		if (getSelectedTask() != null) {
			 if (editClickedCallback != null) {
				editClickedCallback.startCallback(activeGroupText.getText(), getSelectedTask());
			 }
		}
	}
	
	
	/**
	 * gets Called by the Delete Button 
	 * gets the task from getSelected() and throws it into Callback deleteClickedCallback
	 * 
	 * @see deleteClickedCallback
	 * @see getSelected
	 */
	private void handleDeleteButtonClick(){
		if (getSelectedTask() != null) {
			 if (deleteClickedCallback != null) {
				 deleteClickedCallback.startCallback(activeGroupText.getText(), getSelectedTask());
			 }
		}
	}
	
	
	/*
	 * 
	 */
	private void handleSaveButtonClick(){
		if (!activeGroupText.getText().equals("")) {
			 if (saveClickedCallback != null) { 
				 saveClickedCallback.startCallback(activeGroupText.getText());
			 }
		}
	}
	
	
	 ///
	 /// CALLBACKS
	 ///
	
	
	//
	// TaskLine Callbacks
	//
	
	private void initiateTaskLineCallbacks() {
		for (Object o: taskList.getChildren()) {
			if (o instanceof TaskLine) {
				((TaskLine) o).setCallback(new TaskLine.OnSelectedCallback() {
					@Override
					public void startCallback(String id) {
						selectedTaskId = id;
					}
				});
			}
		}
	}
	
	
	//
	// Own Callbacks
	//
	
	/*
	 * Callback When Task is changed
	 */
    public interface DeleteClickedCallback {
        void startCallback(String groupName, String task_id);
    }
    
    private DeleteClickedCallback deleteClickedCallback; 

    public void setCallback(DeleteClickedCallback callback) {
        this.deleteClickedCallback = callback;
    }
	
    
    
	/*
	 * Callback When Edit is clicked
	 */
    public interface EditClickedCallback {
        void startCallback(String groupName, String task_id);
    }
    
    private EditClickedCallback editClickedCallback; 

    public void setCallback(EditClickedCallback callback) {
        this.editClickedCallback = callback;
    }
    
    
	/**
	 * Callback When Add is clicked
	 * 
	 */
    public interface AddClickedCallback {
        void startCallback(String groupName);
    }
    
    private AddClickedCallback addClickedCallback; 

    public void setCallback(AddClickedCallback callback) {
        this.addClickedCallback = callback;
    }
    
    
	/*
	 * Callback When Save is clicked
	 */
    public interface SaveClickedCallback {
        void startCallback(String groupName);
    }
    
    private SaveClickedCallback saveClickedCallback; 

    public void setCallback(SaveClickedCallback callback) {
        this.saveClickedCallback = callback;
    }

}
