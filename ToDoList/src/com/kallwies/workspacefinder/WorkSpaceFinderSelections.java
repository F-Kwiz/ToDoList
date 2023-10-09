package com.kallwies.workspacefinder;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/*
 * CLASS
 * Wraps around inner classes and handles the looks and the positioning
 */
public class WorkSpaceFinderSelections extends VBox {
	
	private double width_ration = 0.8;
	private Font font = Font.font("Arial", FontWeight.BOLD, 16);
	
	private WorkSpaceFinderSelectionsRecent recent = new WorkSpaceFinderSelectionsRecent();
    
	private Label selected; 
	
	
	/*
	 * Constructor
	 */
	public WorkSpaceFinderSelections() {
		this.setPrefWidth(WorkSpaceFinderPreferences.getWidth()*width_ration);
		this.getChildren().add(recent);
	}
	
	
	
	public WorkSpaceFinderSelectionsRecent getRecent() {
		return recent;
	}
	
	
	public Label getSelected() {
		return selected;
	}
	
	/*
	 * Sets selected here and in Singleton Preferences
	 * When null gets passed, sets null and "" in Singleton
	 */
	public void setSelected(Label selected) {
		this.selected = selected;
		WorkSpaceFinderPreferences.setSelected(selected);
		if (selected != null) {
			WorkSpaceFinderPreferences.setWorkSpacePath(selected.getText());
		} else {WorkSpaceFinderPreferences.setWorkSpacePath("");}
	}



	/*
	 * CLASS
	 * Shows all recent used workspaces
	 */
	public class WorkSpaceFinderSelectionsRecent extends VBox {
		
		private Label recentLabel = new Label("Recent");
		private VBox paths = new VBox();
		private ScrollPane scrollPane = new ScrollPane(paths);
	    
		
		/*
		 * Constructor
		 */
		public WorkSpaceFinderSelectionsRecent() {
			recentLabel.setFont(font);
			this.getChildren().add(recentLabel);
			
			addPath("C:\\Users\\Kwiz\\Documents\\Tasks");
			
			scrollPane.setFitToWidth(true);
			scrollPane.setMaxHeight(100.0);
			this.getChildren().add(scrollPane);
		}
		
		/*
		 * adds a Label to the VBox path, which holds all paths in it. Those paths lead to workspaces 
		 */
		private long lastClickTime = 0;
		public void addPath(String path) {
			Label label = new Label(path);
            label.setOnMouseClicked(event -> {
            	long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime < 250) { // Check if doubleClicked (time difference in milliseconds)
                    if (doubleClickCallback != null) {
                    	WorkSpaceFinderPreferences.setSelected(label);
                    	doubleClickCallback.runCallback(); // runs Callback with path (String) as parameter
                    }
                } else {unselectAllPaths(); selectPath(label);} // on clicked
                lastClickTime = currentTime;
            });
			paths.getChildren().add(label);
		}
		
	    private void selectPath(Label label) {
	    	label.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-text-fill: blue;");
	    	setSelected(label);
	    }
	    
	    /*
	     * Changes the Style of a given label to blank
	     */
	    private void unselectPath(Label label) {
	    	label.setStyle("");
	    }
	    
	    /*
	     * changes the style of all labels in path. So it looks like all paths are unselected
	     */
	    private void unselectAllPaths() {
	        for (var child : paths.getChildren()) {
	            if (child instanceof Label) {
	            	unselectPath((Label) child);
	            	setSelected(null);
	            }
	        }
	    }
	    
	   
	    /*
	     * deletes given label out of paths
	     */
	    public void deletePath(Label path) {
	        List<Node> nodesToRemove = new ArrayList<>();
	        for (Node node : paths.getChildren()) {
	            if (node instanceof Label && node == path) {
	                nodesToRemove.add(node);
	            }
	        }
	        paths.getChildren().removeAll(nodesToRemove);
	    }
	    
	    /*
	     * Does not need a parameter because selected is known in the class
	     */
	    public void deleteSelected() {
	    	deletePath(selected);
	    	setSelected(null);
	    }
	    
	}
	
	
	//
	// Callbacks
	//
	
	/*
	 * Callback for when a Label is doubleClicked
	 */
	public interface DoubleClickCallback {
		void runCallback();
	}
    private DoubleClickCallback doubleClickCallback; 

    public void setCallback(DoubleClickCallback callback) {
        this.doubleClickCallback = callback;
    }

}
