package tasks.view;


import tasks.view.taskview.TaskView;

import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;

import java.util.ArrayList;
import java.util.Map;



public class GUI extends StackPane {
	
	static String savePath = "src/com/kallwies/todolist/controller/data.xml";
	
	StackPane root = new StackPane();
    Scene scene = new Scene(root, 800, 640);
    VBox mainWindow = new VBox();
	HBox menus = new HBox();
    
	/*
    TaskView taskView = new TaskView();
    CalendarView calendarView = new CalendarView();
    
    EditWindow overlayWindow = new EditWindow();
    */
    
	public void start(Stage main, TaskView taskView, CalendarView calendarView, EditWindow overlayWindow) throws Exception {
		
        main.setTitle("Tasks");
        		
        	// Create MenuBar
        	addMenuBar();
        
				// Create Window for the View of the Tables
				StackPane listbox = new StackPane();
			        
				   // Add a Box for the TableView
				   taskView.setVisible(false);
				   listbox.getChildren().add(taskView);
				   listbox.getChildren().add(calendarView);
				menus.getChildren().add(listbox);
			        
			     

	    //
	    mainWindow.getChildren().add(menus);
        root.getChildren().add(mainWindow);
        root.getChildren().add(overlayWindow); // add EditWindow to root, it is invisible at first
        
        root.setMargin(overlayWindow, new Insets(100));

        main.setScene(scene);
        

        // Listener to adjust the Width of the Objects
        main.widthProperty().addListener((obs, oldValue, newValue) -> {
            double windowWidth = main.getWidth();
            mainWindow.setPrefWidth(windowWidth);
            listbox.setPrefWidth(windowWidth);
        });
        
        main.heightProperty().addListener((obs, oldValue, newValue) -> {
            double windowHeight = main.getHeight();
            setPrefHeight(windowHeight);
            listbox.setPrefHeight(windowHeight);
        });

        main.show();   
	}
	
	
	/*
	 * @import
	 * a method that adds the menuBar at the top of the window
	 * @return
	 */
	private void addMenuBar() {
		// root: MenuBar-instance
        MenuBar menuBar = new MenuBar();
        
	        // Menu 1: WorkSpace
        		// Add Items
		        Menu menuWorkSpace = new Menu("WorkSpace");
		        MenuItem menuItem1 = new MenuItem("Change WorkSpace");
		        menuWorkSpace.getItems().add(menuItem1);
	        menuBar.getMenus().add(menuWorkSpace);
	        
	        
	        // Menu 2: View
	        Menu menuView = new Menu("View");
		        // Add Items
		        MenuItem menuTaskView = new MenuItem("Show Tasks");
		        menuTaskView.setOnAction(event -> showTaskView());
		        
		        MenuItem menuCalendarView = new MenuItem("Show Calendar");
		        menuCalendarView.setOnAction(event -> showCalendarView());
		        
		        menuView.getItems().add(menuTaskView);
		        menuView.getItems().add(menuCalendarView);
	        menuBar.getMenus().add(menuView);
        
        // Add to main
        mainWindow.getChildren().add(menuBar);
	}

	/*
	 * starts the callback showTaskView if implemented
	 */
	private void showTaskView() {
		if (showTaskView != null) {
			showTaskView.startCallback();
		}
	}
	
	/*
	 * starts the callback showCalendarView if implemented
	 */
	private void showCalendarView() {
		if (showCalendarView != null) {
			showCalendarView.startCallback();
		}
	}

	
	// Callback
	
	public interface ShowTaskView{
		public void startCallback();
	}
	ShowTaskView showTaskView;
	public void setCallback(ShowTaskView callback) {
		showTaskView = callback;
	}
	
	
	public interface ShowCalendarView{
		public void startCallback();
	}
	ShowCalendarView showCalendarView;
	public void setCallback(ShowCalendarView callback) {
		showCalendarView = callback;
	}
	
	
}
