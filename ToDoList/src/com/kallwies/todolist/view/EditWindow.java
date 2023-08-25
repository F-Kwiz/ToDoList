package com.kallwies.todolist.view;



import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;



// Window opens with Columns as attribute field and you can edit them
// click apply afterwards and will be saved into Tasklist

// in create Mode you can add a new Task into TaskList

public class EditWindow extends BorderPane{
    
	GUI parent;
	
	// Style of the border of the window
    BorderStroke borderStroke = new BorderStroke(
            Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(4)
        );
    // header - Footnote
    HBox topContainer = new HBox();
    HBox bottomContainer = new HBox();
    Button closeButton = new Button("X");
    Button applyButton = new Button("Apply");
    Button dismissButton = new Button("Close");
    
    // body container
    HBox bodyContainer = new HBox();
    GridPane grid = new GridPane(); // Verwenden Sie einen GridPane-Container



	
    public EditWindow(GUI parent) {
    	
    	this.parent = parent;
    	
    	this.setBorder(new Border(borderStroke));
	    setStyle("-fx-background-color: rgba(255, 255, 255, 1.0);");
	    setVisible(false);
	    
		    // Create the top container
		    closeButton.setCancelButton(true);
		    closeButton.setOnAction(event -> this.close());
		    
		     // HBox to hold the button
	        topContainer.getChildren().add(closeButton);
	        topContainer.setAlignment(Pos.TOP_RIGHT);
	        
	        // Foot
		    dismissButton.setOnAction(event -> this.close());
		    applyButton.setOnAction(event -> this.create());
		    
		    bottomContainer.getChildren().add(dismissButton);
		    bottomContainer.getChildren().add(applyButton);
		    bottomContainer.setPadding(new Insets(10, 10, 10, 10)); 

		    
		    
        
        setTop(topContainer);
        grid.setHgap(10); // Abstand zwischen Spalten
        bodyContainer.getChildren().add(grid);
        setCenter(bodyContainer);
        setBottom(bottomContainer);
        
    }
    
    
    public void createAddWindow(ObservableList<TableColumn<?, ?>> columns) {
	    bottomContainer.setSpacing(this.getWidth()-200);
	    setColumns(columns);
	    applyButton.setOnAction(event -> this.create());
    	
	    setVisible(true);
    }
    
    public void createEditWindow(ObservableList<TableColumn<?, ?>> columns) {
	    bottomContainer.setSpacing(this.getWidth()-200);
	    setColumns(columns);
	    applyButton.setOnAction(event -> this.apply());
    	
	    setVisible(true);
    
    }
    
    
    public void setColumns(ObservableList<TableColumn<?, ?>> columns) {
    	// go through all Columns and add textinput in the new Window
    	for (int i = 0; i < columns.size(); i++) {
    		TableColumn<?, ?> column = columns.get(i);
            
    		Label label = new Label(column.getText());
            label.setStyle("-fx-padding: 5px;");
            
            TextField text = new TextField();
            
    		grid.add(label, 0, i);
    		grid.add(text, 1, i);
    	}
    	
    }
    
    
    
    private void create() {
    	// this methods aspect a grid in the window with one label and another text column
    	// e.g.: Description = "HEllo"
    	

    	Map<String, Object> gridMap = new HashMap<>();
    	
    	// Create Map out of Grid with Label and Text
    	for (int i = 1; i <= grid.getChildren().size(); i += grid.getColumnCount()) {
    		Label label = (Label) grid.getChildren().get(i-1);
    		TextField text = (TextField) grid.getChildren().get(i);   		
    		gridMap.put(label.getText(), text.getText());	
    	}

    	parent.addItem(gridMap);

    	close();
    	
    }
    
    
    private void apply() {
    	
    	Map<String, Object> gridMap = new HashMap<>();
    	
    	// Create Map out of Grid with Label and Text
    	for (int i = 1; i <= grid.getChildren().size(); i += grid.getColumnCount()) {
    		Label label = (Label) grid.getChildren().get(i-1);
    		TextField text = (TextField) grid.getChildren().get(i);   		
    		gridMap.put(label.getText(), text.getText());	
    	}

    	parent.editItem(gridMap);
    	
    	close();
    	
    }
    
    
    public void close() {
    	
    	grid.getChildren().clear();
    	setVisible(false);
    	
    }
    
}
