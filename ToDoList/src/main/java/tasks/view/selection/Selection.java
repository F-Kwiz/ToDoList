package tasks.view.selection;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


public class Selection extends TabPane{
	
	FileSelection fileSelection;
	GroupSelection groupSelection;
	
	public Callback callback = new Callback();
	
	public Selection(FileSelection fileSelection, GroupSelection groupSelection) {
		this.fileSelection = fileSelection;
		this.groupSelection = groupSelection;
		
        Tab groupTab = new Tab("Group Selection"  , groupSelection);
        Tab fileTab = new Tab("File Selection", fileSelection);

        getTabs().add(groupTab);
        getTabs().add(fileTab);
        
    	callback.groupSelectionCallback();
	}
	
	
	///
	/// Import
	///
	
	public void importData(ArrayList<Map<String, Object>> groups) {
		groupSelection.importData(groups);
	}
	
	
	///
	/// GET AND SET
	///
	
	public String getSelectedGroup() {
		return groupSelection.getSelectedGroup();
	}
	
	
	/**
	 * Every Callback of class Selection
	 * 
	 */
	public class Callback{
		
		GroupSelectedCallback groupSelectedCallback;
		
		
		
		/*
		 * connects with Callbacks of GroupSelection
		 */
		public void groupSelectionCallback() {
			
			/*
			 * Selected Callback
			 */
			groupSelection.callback.setCallback(new GroupSelection.Callback.GroupSelectedCallback() {
				@Override
				public void startCallback(String groupname) {
					if (groupSelectedCallback != null) {
						groupSelectedCallback.startCallback(groupname);
					}
				}
			});
		}
		
		
		/*
		 * Callback GroupSelectedCallback
		 * gets called when a group in listview was doubleClicked
		 */
		public interface GroupSelectedCallback{
			void startCallback(String groupname);
		}
		public void setCallback(GroupSelectedCallback callback){
			this.groupSelectedCallback = callback;
		}
		
	}
}
	
