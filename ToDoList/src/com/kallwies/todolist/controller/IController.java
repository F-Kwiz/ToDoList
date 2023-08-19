package com.kallwies.todolist.controller;

import com.kallwies.todolist.model.*;
import java.util.ArrayList;


public interface IController {
	void handleAddButtonClick();
	
	TaskList loadXml(String filePath);
	void saveXml(ArrayList<Task> list, String filePath);
}
