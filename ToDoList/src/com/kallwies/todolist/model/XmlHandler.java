package com.kallwies.todolist.model;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;



public class XmlHandler {	

	static public class XmlLoader{
		
		private static Map<String,Object> itemMap = new HashMap<>();
		private static ArrayList<Map<String, Object>> itemList = new ArrayList<>();

		static public ArrayList<Map<String, Object>> load(String filePath) {
	        
			itemMap = new HashMap<>();
			itemList = new ArrayList<>();
			
	        try {
	        	// initiates the Parser for XML
	            SAXBuilder saxBuilder = new SAXBuilder();
	            Document document = saxBuilder.build(new File(filePath));
	            
	            
	            // the root-element is tasklist
	            Element rootElement = document.getRootElement();
	            // throws all tasks under tasklist into an arraylist named itemElements
	            List<Element> itemElements = rootElement.getChildren("task");
	            
	            // First Iterates through all tags of tag Task
	            // Then in the next for-loop saves tagname and tagvalue into an Map
	            // In the end there is an arraylist witch is filled with Maps with the information of every task
	            for (Element itemElement : itemElements) {
	            	
	            	for (Element item: itemElement.getChildren()) {
	            		itemMap.put(item.getName(), item.getText());
	            	}
	            	itemList.add(new HashMap<>(itemMap));
	                itemMap.clear();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return itemList;
	    	}
	}
	
	
	static public class XmlSaver{
		
	    public static void createXmlFile(ArrayList<Map<String, Object>> list, String filePath) {
	        Element taskListElement = new Element("tasklist");
	        Document document = new Document(taskListElement);
	        
	        // Gehe durch alle Maps aus list
	        for (Map<String, Object> tags : list) {
	            Element taskElement = new Element("task");
	            // Save all information from Map into taskElement:Element
		        for (String key : tags.keySet()) {
		        	taskElement.addContent(new Element(key).setText((String) tags.get(key)));
		        }
	            taskListElement.addContent(taskElement);
	        }

	        XMLOutputter xmlOutputter = new XMLOutputter();
	        xmlOutputter.setFormat(Format.getPrettyFormat());

	        try {
	            FileWriter fileWriter = new FileWriter(filePath);
	            xmlOutputter.output(document, fileWriter);
	            fileWriter.close();
	            System.out.println("XML file created successfully.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
		
	}
}
		
	


