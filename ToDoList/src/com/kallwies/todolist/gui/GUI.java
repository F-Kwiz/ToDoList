package com.kallwies.todolist.gui;

import com.kallwies.todolist.model.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;



public class GUI extends Application {
	
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
		        Button AddButton = new Button("Add");
		        
		        menu.getChildren().add(AddButton);
		        menu.setPrefWidth(Double.MAX_VALUE/12);
        
		        //Table
		        TableColumn<Task, String> nameColumn = new TableColumn<>("Task Name");
		        TableColumn<Task, String> descriptionColumn = new TableColumn<>("Description");
		        TableColumn<Task, String> dayColumn = new TableColumn<>("Day");
		        
		        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
		        
		        tableView.getColumns().addAll(nameColumn, descriptionColumn, dayColumn);
		        tableView.getItems().addAll(
		            new Task("Buy groceries", "Milk, eggs, bread",12),
		            new Task("Finish report", "Due by end of the week", 31),
		            new Task("Call doctor", "Schedule an appointment", 9)
		        );	
				
			
		        VBox listbox = new VBox(tableView);
        
        
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
	
	
	public static void fillListView(TaskList list) {
		
        for (int i=0; i < list.getTasks().size();i++) {
        	tableView.getItems().addAll(list.getTasks().get(i));
        }
		
	}


	
    public static void main(String[] args) {
        Application.launch(args);
    }
	
}
