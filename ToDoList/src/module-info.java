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
	exports main.java.root;
	exports main.java.workspacefinder;
	exports main.java.tasks.view;
	exports main.java.tasks.model;
	exports main.java.tasks.controller;
}