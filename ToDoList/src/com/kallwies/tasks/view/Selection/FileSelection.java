package com.kallwies.tasks.view.Selection;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javafx.beans.property.SimpleObjectProperty;


// ??? needs to be completely overhauled. ???
// ??? model logic appears in this View Item. That has to be fixed ???

/*
 * A VBox with a TreeView to show all necessary files in a directory
 */
public class FileSelection extends VBox {
	
	TreeItem<String> rootItem = new TreeItem<>("root");
	TreeView<String> tree = new TreeView<String>(rootItem);
	Button loadButton = new Button("Load Workspace");
	Button loadFileButton = new Button("Load File");
	Button saveButton = new Button("Save File");
	
	String workSpacePath = ""; 
	String filePath = "";
	
	
	public FileSelection() {
		
		loadFileButton.setOnAction(event -> loadFile());
		//saveButton.setOnAction(event -> loadDirectory());
		
		getChildren().add(loadFileButton);
		getChildren().add(tree);
		
		// Add Listener to doubleClick in TreeView
		tree.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                loadFile();
                }
		});
		
	}
	
	/*
	 * gets called when an item in treeview is doubleClicked
	 */
	private void loadFile() {
		TreeItem treeItem = tree.getSelectionModel().selectedItemProperty().get();
		// implement further logic here
	}
	
	
	public String getPathOfItem(TreeItem treeItem) {
		String path = "";
		if (treeItem != null) {
			if (treeItem.getParent() != null) {
				path = (String) treeItem.getValue();
		
			    while (treeItem.getParent().getParent() != null) {
				        treeItem = treeItem.getParent();
				        path = treeItem.getValue() + "\\" + path;
			    }
			    path = workSpacePath + "\\" + path; 
			}
		} else {System.out.println("Fileselection-getPathOfItem: No File was selected. treeItem == null");}
		return path;
	}
	
	
	public void loadDirectory(String path) { 
		if (path != null) {
			if (!path.equals("")) {
				workSpacePath = path;
		        File selectedDirectory = new File(workSpacePath);
		        if (selectedDirectory != null) {
		            rootItem.getChildren().clear();
			        rootItem.setValue(selectedDirectory.getName());
					populateTreeView(rootItem, selectedDirectory);	
		        }
			} else {System.out.println("View-FileSelection: the path was an empty String: \"\" ");}
		}
	}
	
	
	private void populateTreeView(TreeItem<String> parentItem, File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                TreeItem<String> item = new TreeItem<>(file.getName());
                parentItem.getChildren().add(item);

                if (file.isDirectory()) {
                    populateTreeView(item, file);
                }
            }
        }
    }	
}

