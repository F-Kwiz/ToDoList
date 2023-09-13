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
	exports com.kallwies.todolist.view;
	exports com.kallwies.todolist.model;
	exports com.kallwies.todolist.controller;
}