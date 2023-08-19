package com.kallwies.todolist.view;

import com.kallwies.todolist.model.*;
import com.kallwies.todolist.controller.*;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.event.*;
import java.util.ArrayList;



public class GUI extends Application {
	static FrontEndController controller = new FrontEndController();
	static String filePath = "src/com/kallwies/todolist/controller/data.xml";
	static String savePath = "src/com/kallwies/todolist/controller/datasa.xml";
	
    static TableView<Task> tableView = new TableView<>();
    
    
	@Override
	public void start(Stage main) throws Exception {
        main.setTitle("Tasks");
    	Font titleFont = Font.font("Arial", FontWeight.BOLD, 30);
        
        VBox root = new VBox();
        
        	Label title = new Label("Tasks");
        	title.setAlignment(Pos.CENTER);
        	title.setFont(titleFont);
        	
        	HBox menus = new HBox();
        	
		        //Buttons
	        	VBox menu = new VBox();
	        	
        		// ADD BUTTON
		        Button addButton = new Button("Add");
		        addButton.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		                controller.handleAddButtonClick();
		            }});
		        // lambda alternative
		        //addButton.setOnAction(event -> controller.handleAddButtonClick());
		        
		        //LOAD BUTTON
		        Button loadButton = new Button("Load");
		        loadButton.setOnAction(event -> handleLoadButtonClick());
		        
		        // SAVE BUTTON
		        Button saveButton = new Button("Save");
		        saveButton.setOnAction(event -> handleSaveButtonClick());
		        
		        
		        menu.getChildren().add(addButton);
		        menu.getChildren().add(loadButton);
		        menu.getChildren().add(saveButton);
        
		        //Table
		        TableColumn<Task, String> nameColumn = new TableColumn<>("Task Name");
		        TableColumn<Task, String> descriptionColumn = new TableColumn<>("Description");
		        TableColumn<Task, Integer> dayColumn = new TableColumn<>("Day");
		        TableColumn<Task, Integer> monthColumn = new TableColumn<>("Month");
		        TableColumn<Task, Integer> yearColumn = new TableColumn<>("Year");
		        
		        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
		        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
		        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

		        VBox listbox = new VBox(tableView);
		        tableView.getColumns().addAll(nameColumn, descriptionColumn, dayColumn, monthColumn, yearColumn);
        
        
	        menus.getChildren().add(menu);
	        menus.getChildren().add(listbox);
        
        root.getChildren().add(title);
        root.getChildren().add(menus);
        
        Scene scene = new Scene(root, 400, 200);
        main.setScene(scene);
        
        // Listener to adjust the Width of the Objects
        main.widthProperty().addListener((obs, oldValue, newValue) -> {
            double windowWidth = main.getWidth();
            root.setPrefWidth(windowWidth);
            title.setPrefWidth(windowWidth);
            menu.setPrefWidth(windowWidth / 12);
            listbox.setPrefWidth(windowWidth-(windowWidth/12*2));
        });
        
        main.show();   

	}
	
	public static void handleAddButton(ActionEvent event) {
		System.out.println(event.getEventType());
		
	}
	
	public void handleLoadButtonClick() {
		TaskList list = controller.loadXml(filePath);
		fillListView(list);
	}
	
	public void handleSaveButtonClick() {
		ArrayList<Task> tasks = new ArrayList<>(tableView.getItems());
		controller.saveXml(tasks, savePath);
		
	}
	
	
	public static void fillListView(TaskList list) {
		tableView.getItems().clear();
		
        for (int i=0; i < list.getTasks().size();i++) {
        	tableView.getItems().addAll(list.getTasks().get(i));
        }
		
	}


	
    public static void main(String[] args) {
        Application.launch(args);
    }
	
}
