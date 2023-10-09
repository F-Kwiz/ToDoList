package com.kallwies.tasks.view;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.kallwies.tasks.view.taskview.TaskView.AddClickedCallback;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.AppointmentImplLocal;



public class CalendarView extends VBox{
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    
	Agenda agenda = new Agenda();

	
	public CalendarView() {
		
		HBox menu = new HBox();
		Button jumpLeft = new Button ("<--");
		jumpLeft.setOnAction(event -> jumpLeft());
		Button jumpRight = new Button ("-->");
		jumpRight.setOnAction(event -> jumpRight());
		
		Button jumpBack = new Button ("Today");
		jumpBack.setOnAction(event -> jumpBack());
		
		
		menu.getChildren().add(jumpLeft);
		menu.getChildren().add(jumpBack);
		menu.getChildren().add(jumpRight);
		

		agenda.setAllowDragging(false); // Prevent the user from dragging around appointments
		agenda.setAllowResize(false); // Prevent the user from resizing appointments
		
		// Behavior when appointment is right-clicked
        agenda.setEditAppointmentCallback(appointmentToEdit -> {
        	if (editCallback != null) {
        		editCallback.runCallback(createMapOutOfAppointment((AppointmentImplLocal) appointmentToEdit));
        	}
        	return null;
        });
        
        // Behavior when appointment is double-clicked
		agenda.setActionCallback(appointmentToEdit ->{
        	if (viewCallback != null) {
        		viewCallback.runCallback(createMapOutOfAppointment((AppointmentImplLocal) appointmentToEdit));
        	}
			return null;
		});
		
		
	    this.getChildren().add(menu);
	    this.getChildren().add(agenda);
	    
	}
	
	/*
	 * @import ArrayList<Map<String, Object>> tasks <-- from: 
	 * Gets task information saved to a map and creates an appointment with given information
	 * 
	 */
	public void importData(ArrayList<Map<String, Object>> tasks){
		agenda.appointments().clear();
		for (Map<String, Object> task: tasks) {
				Agenda.AppointmentImplLocal appointment = createAgendaOutOfTask(task);
				if (isAppointmentReady(appointment)) {
					agenda.appointments().add(appointment);
				} else {System.out.println("GUI-CalendarView: Appointment is missing the necessary information, to be added to agenda");}
		}
	}
	
	/*
	 * @import Agenda.AppointmentImplLocal appointment
	 * gets an appointment and exports its information as map
	 * @return Map<String, Object> map
	 */
	public Map<String, Object> createMapOutOfAppointment(Agenda.AppointmentImplLocal appointment){
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (appointment != null) {
			map.put("title", appointment.getSummary());
			map.put("description", appointment.getDescription());
			String startdate = appointment.getStartLocalDateTime().format(formatter);
			map.put("start-date", startdate);
			String enddate = appointment.getEndLocalDateTime().format(formatter);
			map.put("end-date", enddate);
		}
		return map;
	}
	
	
	/*
	 * @import Map<String, Object> matchData <-- from: this.fillCalendar()
	 * Gets task information saved to a map and creates an appointment with given information
	 * @return Agenda.AppointmentImplLocal newAgenda
	 */
	public Agenda.AppointmentImplLocal createAgendaOutOfTask(Map<String, Object> task) {
		Agenda.AppointmentImplLocal newAgenda = new Agenda.AppointmentImplLocal();
		for (String key:task.keySet()) {
			switch (key) {
			case "title": 
				newAgenda.setSummary((String)task.get(key));
				break;
			case "description": 
				newAgenda.setDescription((String)task.get(key));
				break;
			case "start-date": 
				try {
	                String dateTimeString = (String) task.get(key);
	                System.out.println(dateTimeString);
	                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
	                newAgenda.setStartLocalDateTime(dateTime);
				}
				catch(DateTimeParseException e) {
					System.out.println("GUI/CalendarView-createAgendaOutOfTask: start-date had the wrong format.: " + e);
				}
                break;
			case "end-date":
				try {
	                String enddateTimeString = (String) task.get(key);
	                LocalDateTime enddateTime = LocalDateTime.parse(enddateTimeString, formatter);
	                newAgenda.setEndLocalDateTime(enddateTime);
				}
				catch (DateTimeParseException e) {
					System.out.println("GUI/CalendarView-createAgendaOutOfTask: end-date had the wrong format.: " + e);
				}
                break;
			}
		}
		return newAgenda;
	}
	
	/*
	 * @import Agenda.AppointmentImplLocal appointment<-- from: this.createAgendaOutOfTask()
	 * Checks if appointment has all necessary information to be passed into agenda
	 * @return boolean
	 */
	public boolean isAppointmentReady(Agenda.AppointmentImplLocal appointment) {
		if (appointment.getDescription() == null) {
			System.out.println("GUI-CalendarView: No description found.");
			return false;
		}
		if (appointment.getStartLocalDateTime() == null) {
			System.out.println("GUI-CalendarView: No startdate found.");
			return false;
		}
		if (appointment.getEndLocalDateTime() == null) {
			System.out.println("GUI-CalendarView: No enddate found.");
			return false;
		}
		if (appointment.getSummary() == null) {
			System.out.println("GUI-CalendarView: No summary found.");
			return false;
		}
		
		return true;
	}
	
	
	//
	// BUTTON METHODS
	//
	
	/*
	 * changes the displayed Date to a week prior
	 */
	public void jumpLeft() {
		LocalDateTime startDate = agenda.getDisplayedLocalDateTime();
		startDate = startDate.minusWeeks(1);
        agenda.setDisplayedLocalDateTime(startDate);
	}
	
	/*
	 * changes the displayed Date to a week after
	 */
	public void jumpRight() {
		LocalDateTime startDate = agenda.getDisplayedLocalDateTime();
		startDate = startDate.plusWeeks(1);
        agenda.setDisplayedLocalDateTime(startDate);
	}
	
	/*
	 * changes the displayed Date back to today
	 */
	public void jumpBack() {
		LocalDateTime currentDate = LocalDate.now().atStartOfDay();
        agenda.setDisplayedLocalDateTime(currentDate);
	}
	
	
	//
	// CALLBACK
	//
	
	/*
	 * Callback to edit an appointment
	 */
    public interface EditCallback {
        void runCallback(Map<String, Object> task);
    }
    
    private EditCallback editCallback; 

    public void setCallback(EditCallback callback) {
        this.editCallback = callback;
    }
    
	/*
	 * Callback to view an appointment
	 */
    public interface ViewCallback {
        void runCallback(Map<String, Object> task);
    }
    
    private ViewCallback viewCallback; 

    public void setCallback(ViewCallback callback) {
        this.viewCallback = callback;
    }
	
}
