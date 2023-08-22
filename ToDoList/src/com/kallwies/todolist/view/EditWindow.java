package com.kallwies.todolist.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

// Window opens with Columns as attribute field and you can edit them
// click apply afterwards and will be saved into Tasklist

// in create Mode you can add a new Task into TaskList

public class EditWindow extends BorderPane{
    
	// Style of the border of the window
    BorderStroke borderStroke = new BorderStroke(
            Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(4)
        );
    HBox topContainer = new HBox();
    Button closeButton = new Button("X");

	
    public EditWindow() {
    	this.setBorder(new Border(borderStroke));
	    setStyle("-fx-background-color: rgba(255, 255, 255, 1.0);");
	    setVisible(false);
	    
		    // Create the top container
		    closeButton.setCancelButton(true);
		    closeButton.setOnAction(event -> this.close());
		    
		     // HBox to hold the button
	        topContainer.getChildren().add(closeButton);
	        topContainer.setAlignment(Pos.TOP_RIGHT);
        
        setTop(topContainer);
        
    }
    
    
    public void createAddWindow() {
	    
	    setVisible(true);
    }
    
    
    public void close() {
    	
    	getChildren().clear();
    	setVisible(false);
    	
    }
    
}
