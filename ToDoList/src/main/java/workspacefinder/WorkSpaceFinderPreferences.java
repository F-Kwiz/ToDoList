package workspacefinder;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WorkSpaceFinderPreferences {
	private static double width;
    private static double height;
    private static String workSpacePath;
    private static Label selected; 
    private static Stage mainStage;
    
    public static double getWidth() {
        return width;
    }

    public static double getHeight() {
        return height;
    }

    public static void setWidth(double width) {
    	WorkSpaceFinderPreferences.width = width;
    }

    public static void setHeight(double height) {
    	WorkSpaceFinderPreferences.height = height;
    }

	public static Label getSelected() {
		return selected;
	}

	public static String getSelectedString() {
		if (selected != null) {
			return selected.getText();
		} else {return "";}
	}
	
	public synchronized static void setSelected(Label selected) {
		WorkSpaceFinderPreferences.selected = selected;
	}

	public static Stage getMainStage() {
		return mainStage;
	}

	public static void setMainStage(Stage mainStage) {
		WorkSpaceFinderPreferences.mainStage = mainStage;
	}

	public static String getWorkSpacePath() {
		return workSpacePath;
	}

	public static void setWorkSpacePath(String workSpacePath) {
		WorkSpaceFinderPreferences.workSpacePath = workSpacePath;
	}
  
}
