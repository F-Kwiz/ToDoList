package com.kallwies.tasks.view;


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
import jfxtras.scene.control.agenda.Agenda;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;



// Window opens with Columns as attribute field and you can edit them
// click apply afterwards and it will be saved

// in create Mode you can add a new Task

public class EditWindow extends BorderPane{
	
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
    GridPane grid = new GridPane();


	
    public EditWindow() {
    	
    	this.setBorder(new Border(borderStroke));
	    setStyle("-fx-background-color: rgba(255, 255, 255, 1.0);");
	    setVisible(false);
	    
		    // Create the top container
		    closeButton.setCancelButton(true);
		    closeButton.setOnAction(event -> this.close());
		    
		     // HBox that contains the close button to put it on the right side
	        topContainer.getChildren().add(closeButton);
	        topContainer.setAlignment(Pos.TOP_RIGHT);
	        
	        // Footer with another close and a create Button
		    dismissButton.setOnAction(event -> this.close());
		    applyButton.setOnAction(event -> this.create());
		    
		    bottomContainer.getChildren().add(dismissButton);
		    bottomContainer.getChildren().add(applyButton);
		    bottomContainer.setPadding(new Insets(10, 10, 10, 10)); 

		    
		    
        
        setTop(topContainer);
        grid.setHgap(10); // gap between columns
        bodyContainer.getChildren().add(grid);
        setCenter(bodyContainer);
        setBottom(bottomContainer);
        
    }
    
    
    
    /*
     * 
     * Import appointment-object and extract information into a Map<String, Object> to create an EditWindow
     * 
     */
    public void editAppointment(Agenda.Appointment appointment) {
    	
		Map<String, Object> attributeMap = new LinkedHashMap<String, Object>();

		
    	attributeMap.put("title", appointment.getSummary());
    	attributeMap.put("description", appointment.getDescription());
    	
    	// Makes sure to just use Strings in EditWindow
        LocalDateTime startDateTime = (LocalDateTime) appointment.getStartLocalDateTime();
        String formattedDateTime = startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    	attributeMap.put("start-date", formattedDateTime);
    	
        LocalDateTime endtDateTime = (LocalDateTime) appointment.getEndLocalDateTime();
        String formattedEndDateTime = endtDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    	attributeMap.put("end-date", formattedEndDateTime);
    	
    	createEditWindow(attributeMap);
    }
    private Map<String, Object> previousData;
    
    /*
     * 
     * Import appointment-object and extract information into a Map<String, Object> to create a ViewWindow
     * 
     */
    public void viewAppointment(Agenda.Appointment appointment) {
    	
		Map<String, Object> attributeMap = new LinkedHashMap<String, Object>();

		
    	attributeMap.put("title", appointment.getSummary());
    	attributeMap.put("description", appointment.getDescription());
    	
    	// Makes sure to just use Strings in EditWindow
        LocalDateTime startDateTime = (LocalDateTime) appointment.getStartLocalDateTime();
        String formattedDateTime = startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    	attributeMap.put("start-date", formattedDateTime);
    	
        LocalDateTime endtDateTime = (LocalDateTime) appointment.getEndLocalDateTime();
        String formattedEndDateTime = endtDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    	attributeMap.put("end-date", formattedEndDateTime);
    	
    	createViewWindow(attributeMap);
    }
    
    
    /*
     * Creates an Window with empty text field to create a new Task
     */
    public void createAddWindow(Map<String, Object> attributeMap) {
	    bottomContainer.setSpacing(this.getWidth()-200);
	    setColumns(attributeMap);
	    applyButton.setOnAction(event -> this.create());
	    applyButton.setDisable(false);
    	
	    setVisible(true);
    }
    
    // Creates the window in edit mode.
    // The text fields will be filled with known information
    // the Button on the bottom right corner turns into applyButton
    public void createEditWindow(Map<String, Object> attributeMap) {
    	
    	previousData = (new LinkedHashMap<String, Object>(attributeMap)); // Important to find a match in TaskView
    	
    	bottomContainer.setSpacing(this.getWidth()-200);
	    
	    setColumns(attributeMap);
	    fillTextOfGrid(attributeMap);
 
	    applyButton.setOnAction(event -> this.apply());  
	    applyButton.setDisable(false);
	    
	    setVisible(true);
	    
    }
    
    /*
     * @import Map<String, Object> attributeMap
     * Creates an View Window to see all attributes. It is not possible to change them
     * 
     */
    public void createViewWindow(Map<String, Object> attributeMap) {
	    bottomContainer.setSpacing(this.getWidth()-200);
	    setColumns(attributeMap, false);
	    fillTextOfGrid(attributeMap);
	    
	    applyButton.setDisable(true);
    	
	    setVisible(true);
    }
    
    /*
     * 
    * Uses an Map to create the Textfields with their Labels in EditWindow
    * 
    */
    public void setColumns(Map<String, Object> attributeMap) {
    	// goes through all keys and adds an label and a textfield in the new Window
    	int i = 0;
    	for (String key: attributeMap.keySet()) {
            
    		Label label = new Label(key);
            label.setStyle("-fx-padding: 5px;");
            
            TextField text = new TextField();
            
    		grid.add(label, 0, i);
    		grid.add(text, 1, i);
    		i += 1;
    	}
    }
    public void setColumns(Map<String, Object> attributeMap, boolean editable) {
    	// goes through all keys and adds an label and a textfield in the new Window
    	int i = 0;
    	for (String key: attributeMap.keySet()) {
            
    		Label label = new Label(key);
            label.setStyle("-fx-padding: 5px;");
            
            TextField text = new TextField();
        	text.setEditable(editable);
            
    		grid.add(label, 0, i);
    		grid.add(text, 1, i);
    		i += 1;
    	}
    }
    
    /* fills TextFields with information it finds in attributeMap
    *
    */
    public void fillTextOfGrid(Map<String, Object> attributeMap) {
        int i = 1;
        for (String key : attributeMap.keySet()) {
            TextField text = (TextField) grid.getChildren().get(i);
            Object value = attributeMap.get(key);
            
            if (value instanceof String) {
                text.setText((String) value);
            } else {
                // Handle other data types
                text.setText("Unsupported Type");
            }
            
            i += grid.getColumnCount();
        }
    }
    
    
    
    /* 
    * gets called by createButton creates a new Item in TaskView
    */
    private void create() {
    	// this method aspects a grid in the window with a label and a text column
    	// e.g.: Description = "HEllo"
    	

    	Map<String, Object> gridMap = new LinkedHashMap<>();
    	
    	// Create Map out of Grid with Label and Text
    	for (int i = 1; i <= grid.getChildren().size(); i += grid.getColumnCount()) {
    		Label label = (Label) grid.getChildren().get(i-1);
    		TextField text = (TextField) grid.getChildren().get(i);   		
    		gridMap.put(label.getText(), text.getText());	
    	}
    	
    	if (onCreateCallback != null) {
    		onCreateCallback.onButtonClicked(gridMap);
    	}
    	
    	close();
    	
    }
    
    // gets called by applyButton
    // Method that is run by the button Apply. It will change the selected Item
    private void apply() {
    	
    	Map<String, Object> gridMap = new LinkedHashMap<>();
    	
    	gridMap.put("previousData", previousData);
    	
    	// Create Map out of Grid with Label and Text
    	for (int i = 1; i <= grid.getChildren().size(); i += grid.getColumnCount()) {
    		Label label = (Label) grid.getChildren().get(i-1);
    		TextField text = (TextField) grid.getChildren().get(i);   		
    		gridMap.put(label.getText(), text.getText());	
    	}
    	
    	// 
        if (onApplyButtonClickedCallback != null) {
            onApplyButtonClickedCallback.onButtonClicked(gridMap);
        }
    	close();
    	
    }
    
    // gets called by closeButton
    // closes the window by making it invisible
    public void close() {
    	
    	grid.getChildren().clear();
    	setVisible(false);
    	
    }
    
    
    //
    // CALLBACK
    //
    
    /*
     *  Callback when apply Button is clicked in edit mode
     *  will send changed Map to the calling Object
     */
    public interface OnButtonClickedCallback {
        void onButtonClicked(Map<String, Object> data);
    }
    
    private OnButtonClickedCallback onApplyButtonClickedCallback; 

    public void setCallback(OnButtonClickedCallback callback) {
        this.onApplyButtonClickedCallback = callback;
    }
    
    

    /*
     *  Callback when creatButton is clicked in AddMode
     *  send a Map back to the Object that "listens" to this
     */
    public interface OnCreateCallback {
        void onButtonClicked(Map<String, Object> data);
    }
    
    private OnCreateCallback onCreateCallback; 

    public void setCallback(OnCreateCallback callback) {
        this.onCreateCallback = callback;
    }
    
}
