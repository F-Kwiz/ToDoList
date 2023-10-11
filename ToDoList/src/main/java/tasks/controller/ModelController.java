package tasks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tasks.model.BirthdayComparator;
import tasks.model.FileFinder;
import tasks.model.XmlHandler;
import tasks.model.data.DataModel;
import tasks.model.data.TaskGroup;


/*
 * Model Controller doesn't know task
 * task will be transformed into Map in Task already
 */
public class ModelController{
    
	private String workSpacePath;
	private DataModel data;
	
    public ModelController(DataModel dataModel) {
    	this.data = dataModel;
    }
        
    public Map<String, Object> loadXml(String filePath) {
        Map<String, Object> itemList = XmlHandler.XmlLoader.loadFile(filePath);
        return itemList;     
    }
   
	public void loadAllXml(String path) {
		// Find all xml files in workspace
        List<String> xmlFilesPaths = FileFinder.findXMLFiles(path);
		data.importList(XmlHandler.XmlLoader.loadAllFiles(xmlFilesPaths));
	}
    
	
	public void saveXml(String groupName) {
		// need to get data
		TaskGroup group = data.getGroupByName(groupName);
		XmlHandler.XmlSaver.createXmlFile(group.getMap(), group.getPath());
	}
	
	
	public ArrayList<Map<String, Object>> getData(){
		return data.getData();
	}

	public DataModel getDataModel() {
		return data;
	}
	
	/*	
    public void sortTasksByDueDate() {
    	Collections.sort(tasks, new BirthdayComparator());
    }
	*/
}