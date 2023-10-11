package tasks.view;


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




/**
 * Window opens with Columns as attribute field and you can edit them. click apply afterwards and it will be saved
 * 
 * 
 */
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

    Map<String, Object> activeTask = new HashMap<String, Object>();
	
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
    
   
    ///
    /// IMPORT
    ///
    
    /**
     * Creates an Window with empty text field to create a new Task
     *      
     * @param task Map<String, Object> - A task has to be given, for an orientation. Edit Window uses task to create the right Label and textFields
     */
    public void createAddWindow(Map<String, Object> task) {
	    bottomContainer.setSpacing(this.getWidth()-200);
	    setColumns(task);
	    applyButton.setOnAction(event -> this.create());
	    applyButton.setDisable(false);
    	
	    setVisible(true);
    }
    
    /**
    * Creates the window in edit mode.
	* The text fields will be filled with known information
	* the Button on the bottom right corner turns into applyButton
	* 
    * @param task
    */
    public void createEditWindow(Map<String, Object> task) {
    	
    	activeTask = (new HashMap<String, Object>(task));
    	
    	bottomContainer.setSpacing(this.getWidth()-200);
	    
	    setColumns(task);
	    fillTextOfGrid(task);
 
	    applyButton.setOnAction(event -> this.apply());  
	    applyButton.setDisable(false);
	    
	    setVisible(true);
	    
    }
    
    /**
     * Creates an View Window to see all attributes. It is not possible to change them
     * 
     * @param task Map<String, Object>
     */
    public void createViewWindow(Map<String, Object> task) {
    	
    	activeTask = (new HashMap<String, Object>(task));
    	
	    bottomContainer.setSpacing(this.getWidth()-200);
	    setColumns(task, false);
	    fillTextOfGrid(task);
	    
	    applyButton.setDisable(true);
    	
	    setVisible(true);
    }
    
    /**
     * Uses an Map to create the Textfields with their Labels in EditWindow
     * 
     * @param attributeMap Map<String, Object>
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
    
    /**
     * goes through all keys and adds an label and a textfield in the new Window
     * 
     * @param attributeMap Map<String, Object> - a map that holds all information about a task
     * @param editable a boolean that determines if textfields in Edit Window can be edited
     */
    public void setColumns(Map<String, Object> attributeMap, boolean editable) {
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
    
    /** 
     * fills TextFields with information it finds in attributeMap
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
    
    
    
    /**
    * gets called by applyButton creates a new Item in TaskView
    * this method aspects a grid in the window with a label and a text column
    *  e.g.: Description = "HEllo"
    * 
    */
    private void create() {

    	Map<String, Object> gridMap = new HashMap<>();
    	
    	// Create Map out of Grid with Label and Text
    	for (int i = 1; i <= grid.getChildren().size(); i += grid.getColumnCount()) {
    		Label label = (Label) grid.getChildren().get(i-1);
    		TextField text = (TextField) grid.getChildren().get(i);   		
    		gridMap.put(label.getText(), text.getText());	
    	}
    	
    	if (onCreateCallback != null) {
    		onCreateCallback.startCallback(gridMap);
    	}
    	
    	close();
    }
    
    /**
     * gets called by applyButton
     * Method that is run by the button Apply. It will change the selected Item
     * 
     * 
     */
    private void apply() {
        if (applyClickedCallback != null) {
        	applyClickedCallback.startCallback(activeTask);
        }
    	close();
    	
    }
    
    /**
     * gets called by closeButton
     * closes the window by making it invisible
     * 
     */
    public void close() {
    	activeTask = null;
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
    public interface ApplyClickedCallback {
        void startCallback(Map<String, Object> data);
    }
    
    private ApplyClickedCallback applyClickedCallback; 

    public void setCallback(ApplyClickedCallback callback) {
        this.applyClickedCallback = callback;
    }
    
    

    /*
     *  Callback when creatButton is clicked in AddMode
     *  send a Map back to the Object that "listens" to this
     */
    public interface OnCreateCallback {
        void startCallback(Map<String, Object> data);
    }
    
    private OnCreateCallback onCreateCallback; 

    public void setCallback(OnCreateCallback callback) {
        this.onCreateCallback = callback;
    }
    
}
