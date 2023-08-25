package com.kallwies.todolist.controller;

import com.kallwies.todolist.model.*;
import java.util.Map;
import java.util.ArrayList;


public interface IController {
	ArrayList<Map<String, Object>> loadXml(String filePath);
	void saveXml(ArrayList<Map<String, Object>> list, String filePath);
}
