package com.kallwies.todolist.view;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javafx.beans.property.SimpleObjectProperty;



public class FileSelection extends VBox {
	
	Stage main;
	TreeItem<String> rootItem = new TreeItem<>("root");
	TreeView<String> tree = new TreeView<String>(rootItem);
	Button loadButton = new Button("Load Workspace");
	Button loadFileButton = new Button("Load File");
	Button saveButton = new Button("Save File");
	DirectoryChooser directoryChooser = new DirectoryChooser();
	
	private SimpleObjectProperty<String> selectedFileProperty = new SimpleObjectProperty<>();
	String workspacePath = ""; 
	String filePath = "";
	
	
	public FileSelection(Stage stage) {
		main = stage;
		
		loadButton.setOnAction(event -> loadDirectory());
		loadFileButton.setOnAction(event -> loadFile());
		saveButton.setOnAction(event -> loadDirectory());
		
		getChildren().add(loadButton);
		getChildren().add(loadFileButton);
		getChildren().add(tree);
		
		// Add Listener to doubleClick in TreeView
		tree.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                loadFile();
                }
		});
		
	}
	
	
	private void loadFile() {
		TreeItem treeItem = tree.getSelectionModel().selectedItemProperty().get();
		setFilePath(getPathOfItem(treeItem));
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
			    path = workspacePath + "\\" + path; 
			}
		} else {System.out.println("Fileselection-getPathOfItem: No File was selected. treeItem == null");}
		return path;
	}
	
	
	private void loadDirectory() { 
        File selectedDirectory = directoryChooser.showDialog(main);
        if (selectedDirectory != null) {
            workspacePath = selectedDirectory.getAbsolutePath();
            rootItem.getChildren().clear();
	        rootItem.setValue(selectedDirectory.getName());
			populateTreeView(rootItem, selectedDirectory);	
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
	
	
    public SimpleObjectProperty<String> selectedFilePath() {
        return selectedFileProperty;
    }
	
    public String getFilePath() {
        return selectedFileProperty.get();
    }

    public void setFilePath(String filePath) {
        selectedFileProperty.set(filePath);
    }
	
	
}

