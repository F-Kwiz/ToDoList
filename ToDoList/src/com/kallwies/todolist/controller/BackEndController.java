package com.kallwies.todolist.controller;

import com.kallwies.todolist.model.BirthdayComparator;
import com.kallwies.todolist.model.XmlHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


public class BackEndController implements IController {
    
    public BackEndController() {
    }
        
    
    @Override
    public ArrayList<Map<String, Object>> loadXml(String filePath) {
        ArrayList<Map<String, Object>> itemList = XmlHandler.XmlLoader.load(filePath);
        return itemList;     
    }
   
	@Override
	public void saveXml(ArrayList<Map<String, Object>> list, String filePath) {
		XmlHandler.XmlSaver.createXmlFile(list, filePath);
	}
/*	
    public void sortTasksByDueDate() {
    	Collections.sort(tasks, new BirthdayComparator());
    }
	*/
}