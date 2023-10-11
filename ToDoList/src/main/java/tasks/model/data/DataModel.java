package tasks.model.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


/*
 * handles all Objects of class Task
 * it searches a Task after a given attribute
 * or sorts Tasks after a given attribute and gives the list back.
 * it transforms Task into Map or Arraylist if necessary.
 * DataModel will never return a POJO.
 */
public class DataModel {
    
	private ArrayList<Task> tasks = new ArrayList<Task>();
    private ArrayList<TaskGroup> groups = new ArrayList<TaskGroup> ();
    
    
    public DataModel() {
        
    }
    
    
    ///
    /// IMPORT
    ///
    
    /*
     * fills DataModel with Tasks and TaskGroup to create the mainData for the whole program
     */
    public void importList(ArrayList<Map<String, Object>> tasklist) {
        for (Map<String, Object> group : tasklist) {
            String groupName = (String) group.getOrDefault("name", "");
            String groupPath = (String) group.getOrDefault("path", "");
            
            // If the group name doesn't exist, add it
            if (!checkIfGroupNameExists(groupName)) {
                addGroup(groupName, groupPath);
            }

            importTasksOfGroup(group, groupName);
        }
    }
    
    
	/*
	 * @ import: String groupname
	 * Checks if groupname does already exist in name of a group inside of the list groups
	 * @ returns: boolean
	 */
	public boolean checkIfGroupNameExists(String groupname) {
		for (TaskGroup group: groups) {
			if (group.getName().equals(groupname)) {
				return true;
			}
		}
		return false;
	}
    
	
    /*
     * @ import: String, String
     * create an TaskGroup object and add it to groups
     * 
     */
	public void addGroup(String groupname, String path) {
		if (!checkIfGroupNameExists(groupname)) {
			TaskGroup newgroup = new TaskGroup(groupname, path);
			groups.add(newgroup);
		} else {System.out.println("DataModel-addGroup: a group with the name " + groupname + " does already exist.");}
	}
    
	
    /*
     * 
     */
    private void importTasksOfGroup(Map<String, Object> group, String groupName) {
        if (group.containsKey("tasks")) {
            Object tasksObject = group.get("tasks");
            if (tasksObject instanceof ArrayList<?>) {
                try {
                    @SuppressWarnings("unchecked")
                    ArrayList<Map<String, Object>> taskArrayList = (ArrayList<Map<String, Object>>) tasksObject;
                    addTasksToGroup(taskArrayList, groupName);
                } catch (ClassCastException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    /*
     * gets tasks from a map of a group and adds them to a TaskGroup instance
     */
    private void addTasksToGroup(ArrayList<Map<String, Object>> taskArrayList, String groupName) {
        for (Map<String, Object> task : taskArrayList) {
            Task newTask = new Task();
            newTask.fillWithMap(task);
            tasks.add(newTask);
            getGroupByName(groupName).addTask(newTask);
        }
    }
    
    
    
    
    
	
	///
	/// EXPORT
	///
	/// It is similar to getter, but the getter in export serve export data out of the programm
    
    /*
     * 
     * exports all tasks as map assigned to a specific group
     * @ export: ArrayList<Map<String, Object>> list
     */
    public ArrayList<Map<String, Object>> getData(){
    	// ArrayList = surrounding
    	// Map<String, Object> = Group
    	// Object of Group: Map<String,Object> = Task
    	ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	
    	// LOOP 1
    	// go through all groups
    	for (TaskGroup group: groups) {
    		Map<String, Object> groupMap = new LinkedHashMap<String, Object>();
    		groupMap.put("name", group.getName()); // save groupname as header
    		groupMap.put("path", group.getPath()); // save grouppath as header
    		
    		// tasklist that will be filled by LOOP 2
        	ArrayList<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>();
        	
        	// LOOP 2
    		// goes through all tasks of group
        	if (group.getTasks() != null) {
		    	for (Task task: group.getTasks()) {
		    		taskList.add(task.getMap());
		    	}
        	} else {System.out.println("DataModel-getData: while gathering data the list of tasks of group " + group.getName() + " was empty.");}
	    	// add taskList to map after it is filled by LOOP 2
    		groupMap.put("tasks", taskList);
    		
    		// adds a new instance of the groupMap to the final list.
    		// The new instance prevents the loop from overwriting groupMap in list. 
    		list.add(new LinkedHashMap<String, Object>(groupMap));
    	}
    	return list;
    }
	
    
    
    ///
    /// GET and SET
    /// 
    
    
    public ArrayList<Map<String, Object>> getAllGroupsMeta(){
    	ArrayList<Map<String, Object>> groupMetaList = new ArrayList<Map<String, Object>>();
    	for (TaskGroup group: groups) {
    		groupMetaList.add(group.getMeta());
    	}
    	return groupMetaList;
    }
    
	/*
	 * Iterates through all groups and gets group by Name
	 */
	public TaskGroup getGroupByName(String groupname) {
		for (TaskGroup group: groups) {
			if (group.getName().equals(groupname)) {
				return group;
			}
		}
		return null;
	}
	

	/**
	 * Removes a task from group
	 * 
	 * @param task_meta Map<String, String> map with meta-information of task like "group" and "id".
	 */
	public void deleteTask(String groupname, String task_id) {
		getGroupByName(groupname).deleteTask(task_id);
	}
	
}
