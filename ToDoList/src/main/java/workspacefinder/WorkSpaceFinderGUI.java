package workspacefinder;


import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WorkSpaceFinderGUI extends StackPane {

	private Stage main;
	final int scene_width = 640;
	final int scene_height = 400;
	Scene scene = new Scene(this, scene_width, scene_height);
	//private WorkSpaceFinder workSpaceFinder = new WorkSpaceFinder();
	private WorkSpaceFinderController controller;

	public void start(Stage main) throws Exception {
		this.main = main;
		main.setTitle("Workspace-Opener");
		
		WorkSpaceFinderPreferences.setWidth(scene_width);
		WorkSpaceFinderPreferences.setHeight(scene_height);
		WorkSpaceFinderPreferences.setMainStage(main);
		
		// DirectoryCreator.main(null);
		
		
		controller = new WorkSpaceFinderController();
		this.getChildren().add(controller);
		
		
		/*
		 * Sets the Callback of controller
		 * Runs PathCallback of this class if Callback is called by controller
		 */
        controller.setCallback(new WorkSpaceFinderController.PathCallback() {
			@Override
            public void runCallback() {
            	if (pathCallback != null) {
            		pathCallback.runCallback(WorkSpaceFinderPreferences.getWorkSpacePath());
            	}
            }
        });
		
        main.setScene(scene);
        main.show();        
	}
	
	
	// CALLBACKS
	/*
	 * gets called when a path is loaded
	 */
    public interface PathCallback {
        void runCallback(String path);
    }
    private PathCallback pathCallback; 

    public void setCallback(PathCallback callback) {
        this.pathCallback = callback;
    }
	
    
	
    // Initiating method if needed
    public static void main(String[] args) {
    	System.out.println("Class is not meant to be run on its own. If it is needed create a Class as Application and add a Scene first");
        //Application.launch(args);
    }
}
