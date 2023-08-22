package com.kallwies.todolist.view;


import com.kallwies.todolist.controller.*;

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
	static String filePath = "src/com/kallwies/todolist/controller/data.xml";
	static String savePath = "src/com/kallwies/todolist/controller/new.xml";
	StackPane mainWindow = new StackPane();
	
	
    TableView tableView = new TableView();
    EditWindow overlayWindow = new EditWindow();
    
    
	@Override
	public void start(Stage main) throws Exception {
        main.setTitle("Tasks");
    	Font titleFont = Font.font("Arial", FontWeight.BOLD, 30);
    	
        VBox listWindow = new VBox();
        
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
		                handleAddButtonClick();
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

		        
		        
		        // Add a Box for the TableView
		        VBox listbox = new VBox(tableView);
        
        
	        menus.getChildren().add(menu);
	        menus.getChildren().add(listbox);
        
	    //
	   listWindow.getChildren().add(title);
	   listWindow.getChildren().add(menus);
        
        
        mainWindow.getChildren().add(listWindow);
        mainWindow.getChildren().add(overlayWindow);
        mainWindow.setMargin(overlayWindow, new Insets(100));
        
        Scene scene = new Scene(mainWindow, 800, 640);
        main.setScene(scene);
        
        // Listener to adjust the Width of the Objects
        main.widthProperty().addListener((obs, oldValue, newValue) -> {
            double windowWidth = main.getWidth();
            listWindow.setPrefWidth(windowWidth);
            title.setPrefWidth(windowWidth);
            menu.setPrefWidth(windowWidth / 12);
            listbox.setPrefWidth(windowWidth-(windowWidth/12*2));
        });
        
        
        main.show();   

	}
	
	
	
	public void handleAddButtonClick() {
		
		overlayWindow.createAddWindow();
		
	}
	
	public void handleLoadButtonClick() {
		ArrayList<Map<String, Object>> list = controller.loadXml(filePath);
		fillListView(list);
	}
	
	public void handleSaveButtonClick() {
		ArrayList<Map<String, Object>> mapList = new ArrayList<>();
		for (Object value: tableView.getItems()) {
			mapList.add((Map<String, Object>) value);
		}
		controller.saveXml(mapList, savePath);
		
	}
	
	
	public void fillListView(ArrayList<Map<String, Object>> list) {
		
		tableView.getItems().clear();
		tableView.getColumns().clear();
		
		ObservableList<Map<String, Object>> items =
			    FXCollections.<Map<String, Object>>observableArrayList();
		Map<String, Object> item1 = new HashMap<>();

		// Goes through the first object in the list and creates columns for all keys of it
        for (String key : list.get(0).keySet()) {
    		TableColumn<Map, String> column = new TableColumn<>(key);
    		column.setCellValueFactory(new MapValueFactory<>(key));
    		tableView.getColumns().add(column);
        }
        
        // Goes through all Objects in the list and saves them into an variable which holds it for tableView
        for (Map<String, Object> Element: list) {
        	for (String key: Element.keySet()) {
        		item1.put(key, Element.get(key));
        	}
        	items.add(new HashMap<>(item1));
        	item1.clear();
        }
        
        tableView.getItems().addAll(items);
	}


	
    public static void main(String[] args) {
        Application.launch(args);
    }
	
}
