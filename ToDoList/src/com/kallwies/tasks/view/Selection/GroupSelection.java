package com.kallwies.tasks.view.Selection;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GroupSelection extends VBox {
	
	ListView<String> listView = new ListView<String>();
	Callback callback = new Callback();
	
	
	public GroupSelection() {
		
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		listViewStyle(); 
		
		// Adds an listener to a doubleClick on an listItem
		listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                loadTasks();
                }
		});
		
		
		getChildren().add(listView);
		
	}
	
	
	public void listViewStyle() {
        listView.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);

                    // Customize the text color based on the item content
                    if (item.equals("basketball")) {
                        setTextFill(Color.RED); // Set text color to red for "basketball"
                    } else {
                        setTextFill(Color.BLACK); // Set default text color to black
                    }
                }
            }
        });
	}
	
	/*
	 * gets called when listview is double clicked
	 */
	public void loadTasks() {
	    String selectedString = listView.getSelectionModel().getSelectedItem();
	    if (selectedString != null) {
	    	if (callback.groupSelectedCallback != null) {
	    		callback.groupSelectedCallback.startCallback(selectedString);
	    	}
	    }
	}
	
	
	///
	/// IMPORT
	///
	
	/*
	 * imports the names of all groups to listView
	 */
	public void importData(ArrayList<Map<String, Object>> groups) {
		for (Map<String, Object> group: groups) {
			listView.getItems().add((String) group.get("name"));
		}
	}
	
	
	public class Callback{
		
		GroupSelectedCallback groupSelectedCallback;
		
		
		/*
		 * Callback GroupSelectedCallback
		 * gets called when a group in listview was doubleClicked
		 */
		interface GroupSelectedCallback{
			void startCallback(String groupname);
		}
		void setCallback(GroupSelectedCallback callback){
			this.groupSelectedCallback = callback;
		}
		
	}
	
}
