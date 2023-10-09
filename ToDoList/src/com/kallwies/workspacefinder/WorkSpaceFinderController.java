package com.kallwies.workspacefinder;


import java.util.Map;

import com.kallwies.workspacefinder.WorkSpaceFinderMenu.LoadButtonCallback;

import javafx.scene.layout.HBox;


public class WorkSpaceFinderController extends HBox {
	
	private WorkSpaceFinderMenu menu = new WorkSpaceFinderMenu();
	private WorkSpaceFinderSelections selections = new WorkSpaceFinderSelections();
	
	public WorkSpaceFinderController() {
		connectCallbacks();
		this.getChildren().add(menu);
		this.getChildren().add(selections);
		
	}
	
	private void connectCallbacks() {
		
		// Menu
		
		/*
		 * Load Callback from menu
		 * this gets triggered when the loadButton was pressed 
		 * no parameters will be passed. because the selected path is saved inside the Singleton WorkSpaceFinderPreferences - selected
		 */
		menu.setCallback(new WorkSpaceFinderMenu.LoadButtonCallback() {
			@Override
			public void runCallback() {
				if (pathCallback != null) {
					pathCallback.runCallback();
				}
			}
        });
		
		/*
		 * 
		 */
		menu.setCallback(new WorkSpaceFinderMenu.DeleteButtonCallback() {
			@Override
			public void runCallback() {
				selections.getRecent().deleteSelected();
			}
        });
		
		//Selections
		
		/*
		 * Double click Callback from Selection
		 * this gets triggered when the a path was doubleclicked
		 * no parameters will be passed. because the selected path is saved inside the Singleton WorkSpaceFinderPreferences - selected
		 */
		selections.setCallback(new WorkSpaceFinderSelections.DoubleClickCallback() {
			@Override
			public void runCallback() {
				if (pathCallback != null) {
					pathCallback.runCallback();
				}
			}
        });
	}
	
	
	// Callback
	
    public interface PathCallback {
        void runCallback();
    }
    private PathCallback pathCallback; 
    
    public void setCallback(PathCallback callback) {
        this.pathCallback = callback;
    }
	
	
}
