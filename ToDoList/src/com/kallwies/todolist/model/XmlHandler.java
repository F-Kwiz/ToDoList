package com.kallwies.todolist.model;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;



public class XmlHandler {	

	static public class XmlLoader{
		
		static public List<Task> load(String filePath) {
	        List<Task> itemList = new ArrayList<>();
	        
	        try {
	            SAXBuilder saxBuilder = new SAXBuilder();
	            Document document = saxBuilder.build(new File(filePath));
	            
	            Element rootElement = document.getRootElement();
	            List<Element> itemElements = rootElement.getChildren("task");
	            
	            for (Element itemElement : itemElements) {
	                String title = itemElement.getChildText("title");
	                String description = itemElement.getChildText("description");
	                int day = Integer.parseInt(itemElement.getChildText("day"));
	                int month = Integer.parseInt(itemElement.getChildText("month"));
	                int year = Integer.parseInt(itemElement.getChildText("year"));
	                Task item = new Task(title, description, day, month, year);
	                itemList.add(item);
	            }
	        
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        return itemList;
	    	}
	}
	
	static public class XmlSaver{
		
	    public static void createXmlFile(ArrayList<Task> list, String filePath) {
	        Element taskListElement = new Element("tasklist");
	        Document document = new Document(taskListElement);

	        for (Task task : list) {
	            Element taskElement = new Element("task");
	            taskElement.addContent(new Element("title").setText(task.getTitle()));
	            taskElement.addContent(new Element("description").setText(task.getDescription()));
	            taskElement.addContent(new Element("day").setText(String.valueOf(task.getDay())));
	            taskElement.addContent(new Element("month").setText(String.valueOf(task.getMonth())));
	            taskElement.addContent(new Element("year").setText(String.valueOf(task.getYear())));
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
		
	


