package tasks.model.data;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskGroup {
	
	private String name = "";
	private String path = "";
	
	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	/**
	 * Constructor.	
	 * Takes over the import
	 * 
	 */
	public TaskGroup(String name, String path) {
		this.name = name;
		
		
		// replaces the filename of path to the groups name
		if (checkFileType(path).equals("xml")) {
			if 	(!name.equals("")) {
		        String newFileName = name + ".xml";
		        Path n_path = Paths.get(path);
		        Path newPath = n_path.resolveSibling(newFileName);
				this.path = newPath.toString();
			} else {
		        String newFileName = "undefined" + ".xml";
		        Path n_path = Paths.get(path);
		        Path newPath = n_path.resolveSibling(newFileName);
				this.path = newPath.toString();
			}
		}
	}
	
	
	///
	/// EXPORT
	///
	
	/**
	 * Exports information about TaskGroup as a Map
	 * 
	 */
	public Map<String,Object> getMap(){
		Map<String,Object> list = getMeta();
		
		ArrayList<Map<String,Object>> tasklist = new ArrayList<Map<String,Object>>();
		for (Task task: getTasks()) {
			tasklist.add(task.getMap());
		}
		list.put("tasks", tasklist);
		return list;
	}
	
	
	/**
	 * Exports meta-information about TaskGroup as a Map
	 * Without Tasks
	 * 
	 */
	public Map<String,Object> getMeta(){
		Map<String,Object> list = new HashMap<String,Object>();
		
		list.put("group", name);
		list.put("path", path);
		
		return list;
	}
	
	
	/**
	 * Add Task to list of tasks
	 * 
	 */
	public void addTask(Task task) {
		tasks.add(task);
	}
	
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	
	
	/**
	 * Helper Function to determine the filetype of a file in a path as String
	 * 
	 * @param filePath
	 * @return filetype String like: xml, txt, etc.
	 */
	public String checkFileType(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex != -1) {
            return fileName.substring(lastIndex + 1);
        }
        return "";
    }
	
	
	///
	/// GETTERS/ SETTERS
	///
	
	/**
	 * go through tasks and find task with the right id
	 * 
	 */
	public Task getTaskById(String id) {
		for (Task task: tasks) {
			if (task.getId().equals(id)) {
				return task;
			}
		}
		return null;
	}
	
	public void deleteTask(String id) {
		tasks.remove(getTaskById(id));
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	
}
