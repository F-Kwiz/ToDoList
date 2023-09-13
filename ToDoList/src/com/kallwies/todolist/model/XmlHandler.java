package com.kallwies.todolist.model;

import org.jdom2.Attribute;
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
	
	// This methods checks the last characters of a file after the dot(".") to determine the file-type
	static public String checkFileType(String filePath) {
        int lastIndex = filePath.lastIndexOf(".");
        if (lastIndex != -1) {
            return filePath.substring(lastIndex + 1);
        }
		return "";
	}
	
	// Check if elements has an certain attribute
    public static boolean hasAttribute(Element element, String attributeName) {
        Attribute attribute = element.getAttribute(attributeName);
        return attribute != null;
    }
   
    
	static public class XmlLoader{
		
		private static Map<String,Object> itemMap = new HashMap<>();
		private static ArrayList<Map<String, Object>> itemList = new ArrayList<>();

		
		static public ArrayList<Map<String, Object>> load(String filePath) {
	        
			String fileType = checkFileType(filePath);
			
			itemMap = new HashMap<>();
			itemList = new ArrayList<>();
			
			if (fileType.equals("xml"))
			{
		        try {
		        	// initiates the Parser for XML
		            SAXBuilder saxBuilder = new SAXBuilder();
		            Document document = saxBuilder.build(new File(filePath));
		            
		            
		            // the root-element is root
		            Element rootElement = document.getRootElement();
		            
		            // throws all elements under root into an list named itemElements
		            List<Element> itemElements = rootElement.getChildren("task");
		            
		            // First Iterates through all tags of tag Element
		            // Then in the next for-loop saves tagname and tagvalue into an Map
		            // In the end there is an arraylist witch is filled with Maps with the information of every Element
		            for (Element itemElement : itemElements) {
		            	//Loop through tags underneath task
		            	itemMap = getItems(itemElement);
		            	itemList.add(new HashMap<>(itemMap));
		                itemMap.clear();
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				} else {System.out.println("XmlLoader: The file behind filePath was not an .xml file, or couldn't be found");}
	        return itemList;
	    	}
		
		
		// recursive function to get items out of elements.
		// It is possible that a task holds a subtask which is another task again, thats why it is recursive.
		static public Map<String,Object> getItems(Element itemElement) {
			Map<String,Object> itemMapLocal = new HashMap<>();
        	for (Element item: itemElement.getChildren()) {
        		if (item.getName().equals("subtask")) {
        			Map<String, Object> subtaskMap = getItems(item);
        			if (subtaskMap.containsKey("id")) {
        				item.setName("subtask-" + subtaskMap.get("id"));
        			} else {System.out.println("XmlHandler-XmlLoader: No tag \"id\" found in Element subtask");}
                    itemMapLocal.put(item.getName(), subtaskMap);
        		} else {
        			itemMapLocal.put(item.getName(), item.getText());
        		}
        	}
			return itemMapLocal;
		}	
	}
	
	
	static public class XmlSaver{
		
	    public static void createXmlFile(ArrayList<Map<String, Object>> list, String filePath) {
	    	
			String fileType = checkFileType(filePath);
	    	if (fileType.equals("xml")) {
		        Element taskListElement = new Element("root");
		        Document document = new Document(taskListElement);
		        
		        // go through all maps of list
		        for (Map<String, Object> tags : list) {
		            Element taskElement = new Element("task");
		            // Save all information from Map into taskElement:Element
			        for (String key : tags.keySet()) {
			            Object value = tags.get(key);
			        	if (value instanceof String) {
			                taskElement.addContent(new Element(key).setText((String) value));
			            } else if (value instanceof Integer) {
			                taskElement.addContent(new Element(key).setText(String.valueOf(value)));
			            }
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
}
		
	


