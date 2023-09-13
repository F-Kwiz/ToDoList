package com.kallwies.todolist.view;


import com.kallwies.todolist.controller.*;
import com.kallwies.todolist.view.taskview.TaskView;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;



public class GUI extends Application {
	
	static FrontEndController controller = new FrontEndController();
	static String savePath = "src/com/kallwies/todolist/controller/data.xml";
	
	StackPane root = new StackPane();
    Scene scene = new Scene(root, 800, 640);
    VBox mainWindow = new VBox();
	HBox menus = new HBox();
    
    TaskView taskView = new TaskView();
    CalendarView calendarView = new CalendarView();
    
    EditWindow overlayWindow = new EditWindow();
    
    
	@Override
	public void start(Stage main) throws Exception {
		
        main.setTitle("Tasks");
    	Font titleFont = Font.font("Arial", FontWeight.BOLD, 30);
    	

        
        	Label title = new Label("Tasks");
        	title.setAlignment(Pos.CENTER);
        	title.setFont(titleFont);
        	

        		
        		// Create TreeView for File Selection
        		FileSelection fileSelection = new FileSelection(main);
        		
        		// Add Listeners 
	                fileSelection.selectedFilePath().addListener((observable, oldValue, newValue) -> {
	                    loadFile((String) newValue);
	                    savePath = newValue;
	                });
	        		
	                menus.getChildren().add(fileSelection);
        	
				// Create Window for the View of the Tables
				StackPane listbox = new StackPane();
			        
				   // Add a Box for the TableView
				   taskView.setVisible(false);
				   listbox.getChildren().add(taskView);
				   listbox.getChildren().add(calendarView);
				   menus.getChildren().add(listbox);
			        
				 // Create Menu on the right to switch mode
				 VBox modes = new VBox();
				 
				 Button taskViewButton = new Button("TaskView");
				 taskViewButton.setOnAction(event -> showTaskView());
				 
				 Button calendarViewButton = new Button("CalendarView");
				 calendarViewButton.setOnAction(event -> showCalendarView());
				 

				 modes.getChildren().add(taskViewButton);
				 modes.getChildren().add(calendarViewButton);
				 
				 menus.getChildren().add(modes);
			     

	    //
	    mainWindow.getChildren().add(title);
	    mainWindow.getChildren().add(menus);
        
        
        root.getChildren().add(mainWindow);
        

        root.getChildren().add(overlayWindow); // add EditWindow to root, it is invisible at first
        
        root.setMargin(overlayWindow, new Insets(100));

        main.setScene(scene);
        

        // Listener to adjust the Width of the Objects
        main.widthProperty().addListener((obs, oldValue, newValue) -> {
            double windowWidth = main.getWidth();
            mainWindow.setPrefWidth(windowWidth);
            title.setPrefWidth(windowWidth);
            //menu.setPrefWidth(windowWidth / 12);
            listbox.setPrefWidth(windowWidth-(windowWidth/12*2));
        });
        

        // CALLBACKS
        editWindowCallbacks();
        taskViewCallbacks();
        calendarViewCallbacks();
        // CALLBACKS
        
        main.show();   

	}
	
	//
	// CALLBACKS
	//
	private void editWindowCallbacks() {
        /*
         * Add Callback connected with overlayWindow (EditWindow)
         * It returns a map as soon a button is clicked in EditWindow
         */
        overlayWindow.setCallback(new EditWindow.OnButtonClickedCallback() {
            @Override
            public void onButtonClicked(Map<String, Object> data) {
                Map<String, Object> map = data;
                taskView.editTask(map);
            }
        });
        
        overlayWindow.setCallback(new EditWindow.OnCreateCallback() {
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
                calendarView.fillCalendar(list);
            }
        });
        
        /*
         * Is triggered by EditButton in TaskView
         * opens edit Window to edit a task
         */
        taskView.setOnEditClicked(new TaskView.EditClickedCallback() {
            @Override
            public void editClicked(Map<String, Object> task) {
            	overlayWindow.createEditWindow(task);
            }
        });
        /*
         * Is triggered by addButton in TaskView
         * opens edit Window to add a task to taskList in TaskView
         */
        taskView.setCallback(new TaskView.AddClickedCallback() {
            @Override
            public void addClicked(Map<String, Object> task) {
            	overlayWindow.createAddWindow(task);
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
            	overlayWindow.createEditWindow(task);
            }
        });
        /*
         * Used when Calendar triggers a view of an appointment (double click on appointment)
         */
        calendarView.setCallback(new CalendarView.ViewCallback() {
            @Override
            public void runCallback(Map<String, Object> task) {
            	overlayWindow.createViewWindow(task);
            }
        });
	}
	//
	// CALLBACKS
	//
	
	
	/*
	 * @import String filePath <-- from Listener on FileSelection
	 * Loads data from an XML File
	 */
	public void loadFile(String filePath) {
		if (filePath != "") {
			ArrayList<Map<String, Object>> list = controller.loadXml(filePath);
			if (list.size() > 0) {
				taskView.loadInTasks(list);
				calendarView.fillCalendar(list);
			} 
		} else {System.out.println("Method loadFile in GUI got an empty String as filePath");}
	}

	
	public void showTaskView() {
		taskView.setVisible(true);
		calendarView.setVisible(false);
	}
	
	public void showCalendarView() {
		calendarView.setVisible(true);
		taskView.setVisible(false);
	}
	
	public EditWindow getEditWindow() {
		return overlayWindow;
	}
	
	
	
    public static void main(String[] args) {
        Application.launch(args);
    }
	
}
