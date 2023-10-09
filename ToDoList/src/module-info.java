/**
 * 
 */
/**
 * 
 */
module ToDoList {
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.controls;
	requires org.jdom2;
	requires jfxtras.all;
	requires java.xml;
	exports com.kallwies.root;
	exports com.kallwies.workspacefinder;
	exports com.kallwies.tasks.view;
	exports com.kallwies.tasks.model;
	exports com.kallwies.tasks.controller;
}