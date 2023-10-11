package workspacefinder;

import java.io.File;
import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class WorkSpaceFinderMenu extends StackPane{
	
	private double width_ration = 0.2;
	private VBox buttons = new WorkSpaceFinderMenuButtons();
	
	public WorkSpaceFinderMenu() {
		this.setPrefWidth(WorkSpaceFinderPreferences.getWidth()*width_ration);
		this.getChildren().add(buttons);
	}
	
	public class WorkSpaceFinderMenuButtons extends VBox{
		
		DirectoryChooser directoryChooser = new DirectoryChooser();
		
		private Button loadButton = new Button("Load");
		private Button createButton = new Button("Create");
		private Button explorerButton = new Button("Open in Explorer");
		private Button deleteButton = new Button("Delete");
		
		public WorkSpaceFinderMenuButtons() {
			loadButton.setMaxWidth(Double.MAX_VALUE);
			loadButton.setOnAction(event -> load());
			this.getChildren().add(loadButton);
			
			createButton.setMaxWidth(Double.MAX_VALUE);
			createButton.setOnAction(event -> createPath());
			this.getChildren().add(createButton);
			
			explorerButton.setMaxWidth(Double.MAX_VALUE);
			explorerButton.setOnAction(event -> openExplorer());
			this.getChildren().add(explorerButton);
			
			deleteButton.setMaxWidth(Double.MAX_VALUE);
			deleteButton.setOnAction(event -> deletePath());
			this.getChildren().add(deleteButton);
		}
		
		public void createPath() {
	        File selectedDirectory = directoryChooser.showDialog(WorkSpaceFinderPreferences.getMainStage());
	        if (selectedDirectory != null) {
				if (loadButtonCallback != null) {
					String workSpacePath = selectedDirectory.getAbsolutePath();
					WorkSpaceFinderPreferences.setWorkSpacePath(workSpacePath);
					loadButtonCallback.runCallback();
				}
	        }
		}
		
		/*
		 * Called by load button
		 */
		public void load() {
			if (loadButtonCallback != null) {
				loadButtonCallback.runCallback();
			}
		}
		
		/*
		 * Called by delete Button
		 */
		public void deletePath() {
			if (deleteButtonCallback != null) {
				deleteButtonCallback.runCallback();
			}
		}
		
		public void openExplorer() {
				String path = WorkSpaceFinderPreferences.getSelectedString();
			    try {
		            String os = System.getProperty("os.name").toLowerCase();
		            ProcessBuilder processBuilder; // ProcessBuilder runs external programs

		            if (os.contains("win")) {
		                // Windows
		                processBuilder = new ProcessBuilder("explorer.exe", path);
		                
		            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
		                // Unix/Linux/Mac
		                processBuilder = new ProcessBuilder("xdg-open", path);
		            } else {
		                System.out.println("The Explorer is not supported on your operating System. Supports Windows, Unix, Linux, Mac");
		                return;
		            }

		            processBuilder.start();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		}
	}
	
	// CALLBACKS
	
	/*
	 * Callback for the load button
	 */
	public interface LoadButtonCallback{
		public void runCallback();
	}
    private LoadButtonCallback loadButtonCallback; 

    public void setCallback(LoadButtonCallback callback) {
        this.loadButtonCallback = callback;
    }
    
    
    /*
     * Callback when delete button is pressed
     */
	public interface DeleteButtonCallback{
		public void runCallback();
	}
    private DeleteButtonCallback deleteButtonCallback; 

    public void setCallback(DeleteButtonCallback callback) {
        this.deleteButtonCallback = callback;
    }
}
