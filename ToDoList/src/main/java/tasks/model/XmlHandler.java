package tasks.model;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
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
import java.nio.file.Path;
import java.nio.file.Paths;



public class XmlHandler {	
	
	final static String elementName = "task"; // the name of the tag that represents the body information of the xml file
	
	// This methods checks the last characters of a file after the dot(".") to determine the file-type
	static public String checkFileType(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex != -1) {
            return fileName.substring(lastIndex + 1);
        }
        return "";
    }
	
	// Check if element has an certain attribute
    public static boolean hasAttribute(Element element, String attributeName) {
        Attribute attribute = element.getAttribute(attributeName);
        return attribute != null;
    }
   
    
	static public class XmlLoader{
		
		private static ArrayList<Map<String, Object>> finalList = new ArrayList<Map<String, Object>>();
		
		private static Map<String,Object> groupMap = new HashMap<String,Object>();
		private static ArrayList<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
		
		private static Map<String,Object> itemMap = new HashMap<String,Object>();

		
		/**
		 * Gets a list with paths and feeds them to method loadFile
		 * Then proceeds to save the return of method loadFile into finalList
		 * 
		 * @param paths List<String>
		 * @return finalList ArrayList<Map<String, Object>> 
		 */
		static public ArrayList<Map<String, Object>> loadAllFiles(List<String> paths) {
			for (String path: paths) {
				finalList.add(new HashMap<String,Object>(loadFile(path)));
			}
			return finalList;
		}
		
		/**
		 * loads an xml file behind path and puts its elements into a Map
		 * The Map contains header information and a body "tasks" with the elements of the file. 
		 * 
		 */
		static public Map<String, Object> loadFile(String filePath) {
	        
			String fileType = checkFileType(filePath);
			
			if (fileType.equals("xml"))
			{
				
				// reset all lists and maps first
				groupMap = new HashMap<String,Object>();
				itemList = new ArrayList<Map<String, Object>>();
				itemMap = new HashMap<String,Object>();
				
				
		        try {
		        	// initiates the Parser for XML
		            SAXBuilder saxBuilder = new SAXBuilder();
		            Document document = saxBuilder.build(new File(filePath));
		            
		            // the root-element is root
		            Element rootElement = document.getRootElement();
		            

		            //get the header out of the xml file
        			groupMap.put("path", filePath);
		            Element xmlhead = rootElement.getChild("head");
		            if (xmlhead != null) {
		                for (Element headItem : xmlhead.getChildren()){ 
			            	switch(headItem.getName()) {
			            		case "group":
			            			groupMap.put("name", headItem.getText()); // ??? replace by filename ???
			            			break;
			            	}	
			            }
		            }
		            
		            
		            // gets all children tags of root with the name that is given by elementName a Constant that is defined at the top of the class
		            List<Element> itemElements = rootElement.getChildren(elementName);
		            
		            // Iterates through all tags and adds its elements to itemList
		            for (Element itemElement : itemElements) {
		            	itemMap = getItems(itemElement);
		            	itemList.add(new HashMap<>(itemMap));
		            }
		            // adds filled itemList to groupMap elementName + "s".
					groupMap.put(elementName + "s", itemList);
		        } catch (IOException | JDOMException e) {
		            e.printStackTrace();
		        }
				} else {System.out.println("XmlLoader: The file behind filePath was not an .xml file, or couldn't be found");}
	        return groupMap;
	    	}
		
		
		/*
		 * Method that returns Child elements out of an Element
		 */
		static public Map<String,Object> getItems(Element itemElement) {
			Map<String,Object> itemMapLocal = new HashMap<>();
        	for (Element item: itemElement.getChildren()) {
    			itemMapLocal.put(item.getName(), item.getText());
        	}
			return itemMapLocal;
		}
	}
	
	
	
	static public class XmlSaver{
		
	    public static void createXmlFile(Map<String, Object> group, String filePath) {
	    	
			String fileType = checkFileType(filePath);
	    	if (fileType.equals("xml")) {
		        Element taskListElement = new Element("root");
		        Document document = new Document(taskListElement);
		        
		        // add header from group to a element
		        Element headElement = new Element("head");
		        for (String key : group.keySet()) {
		        	if (key != elementName + "s") {
			            Object value = group.get(key);
			        	headElement.addContent(new Element(key).setText((String) value));
		        	}
		        }
			    taskListElement.addContent(headElement);
			    
		        // go through all maps of list
		        for (Map<String, Object> tags : (ArrayList<Map<String, Object>>) group.get(elementName + "s")) {
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
		
	


