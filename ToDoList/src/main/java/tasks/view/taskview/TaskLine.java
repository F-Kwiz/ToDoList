package tasks.view.taskview;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


public class TaskLine extends VBox{
	
	boolean clicked = false;
	
	//Style
	String backgroundColor = "#FFFFE0";
	String rectangleColor = Color.valueOf(backgroundColor).darker().toString();
	
	// Main containers
	BorderPane main = new BorderPane();
	GridPane center = new GridPane();
	
	
	// Buttons and Text
	ToggleButton toggleButton = new ToggleButton("O");
	CheckBox checkBoxDone = new CheckBox("");
	
	Label id = new Label("ID");
	//Font titleFont = new Font(20.0);
	
	Label title = new Label("title");
	Font titleFont = new Font(20.0);
	
	Label text = new Label("description of Task");
	Font textFont = new Font(15.0);
	
	Label startdate = new Label("2023-09-01T14:30:00");
	
	Label enddate = new Label("2023-09-01T14:30:00");
	Font dateFont = new Font(15.0);
	
	// expanding
	ArrayList<TaskLine> subTasks = new ArrayList<>();
	
	
	public TaskLine() {
		
		// selects this TaskView on Click
        this.setOnMouseClicked(event -> handleClick());

        
        // Adds Vertical Line far Left in TaskLine
        Rectangle rectangle = new Rectangle();
        rectangle.setX(0);
        rectangle.setY(0);
        rectangle.setWidth(5);
        rectangle.heightProperty().bind(main.heightProperty());
        //rectangle.setStroke(Color.TRANSPARENT);
        rectangle.setFill(Color.valueOf(rectangleColor));
        
		
		// Add Buttons and Text

		toggleButton.setOnAction(event -> toggleSub());

		checkBoxDone.setOnAction(event -> checkBoxChecked());

        ColumnConstraints columnRectangle = new ColumnConstraints();
        columnRectangle.setPrefWidth(20);
		
        ColumnConstraints columnToggle = new ColumnConstraints();
        columnToggle.setPrefWidth(50);
		
        ColumnConstraints columnCheck = new ColumnConstraints();
        columnCheck.setPrefWidth(50);
		
        ColumnConstraints columnId = new ColumnConstraints();
        columnId.setPrefWidth(50);
        
        ColumnConstraints columnTitle = new ColumnConstraints();
        columnTitle.setPrefWidth(150);
        
        ColumnConstraints columnText = new ColumnConstraints();
        columnText.setPrefWidth(300); 
        
        ColumnConstraints columnDate = new ColumnConstraints();
        columnDate.setPrefWidth(100);
        
        center.getColumnConstraints().addAll(columnRectangle, columnToggle, columnCheck, columnId, columnTitle, columnText, columnDate);
		
        id.setFont(titleFont);
		title.setFont(titleFont);
		
		text.setFont(textFont);
		text.setTextAlignment(TextAlignment.LEFT);
		text.setStyle("-fx-overflow: hidden; -fx-text-overflow: ellipsis;");
		
		center.add(rectangle, 0, 0);
		center.add(toggleButton, 1,0);
		center.add(checkBoxDone, 2,0);
		center.add(id, 3, 0);
		center.add(title, 4,0);
		center.add(text, 5,0);
		center.add(startdate, 6,0);

		
		// RIGHT
		
		// blank
		
		main.setCenter(center);
		
		
		//Style

		main.setStyle("-fx-background-color: "+ backgroundColor +";");
		
		center.setAlignment(Pos.CENTER_LEFT);
		center.setMaxHeight(20);

		Separator bottomSeparator = new Separator();
		
		this.getChildren().add(main);
		this.getChildren().add(bottomSeparator);
		
		// listener to size to change size of children dynamically 
        main.widthProperty().addListener((obs, oldValue, newValue) -> {
            double windowWidth = main.getWidth();
        });
		
	}

	/*
	 * @import Map<String,Object> task <-- from TaskView
	 * fills Labels with the needed information of Task
	 * This is kind of initiating it
	 */
	public void fillTask(Map<String,Object> task) {
		for (String key: task.keySet()) {
			switch (key) {
				case "id": 
					id.setText((String)task.get(key));
					break;
				case "title": 
					title.setText((String)task.get(key));
					break;
				case "description": 
					text.setText((String)task.get(key));
					break;
				case "start-date": 
					startdate.setText((String)task.get(key));
					break;
				case "end-date": 
					enddate.setText((String)task.get(key));
					break;
				case "subtask":
					TaskLine newtask = new TaskLine();
					newtask.fillTask((Map<String,Object>)task.get(key));
					subTasks.add(newtask);
					break;
				default: // ask if subtask + id is the key and adds a new Task
			        Pattern pattern = Pattern.compile("^subtask-");
			        Matcher matcher = pattern.matcher(key);
					if (matcher.find()) {
						TaskLine newtaski = new TaskLine();
						newtaski.fillTask((Map<String,Object>)task.get(key));
						subTasks.add(newtaski);
					}
					break;
			}
			
		}
	}
	
	
	/*
	 * 
	 * creates a LinkedHashMap out of Attributes
	 * @return Map<String,Object>
	 */
	public Map<String,Object> exportMap(){
		Map<String, Object> taskMap = new LinkedHashMap<String, Object>();
		
		taskMap.put("title", title.getText());
		taskMap.put("description", text.getText());
		taskMap.put("start-date", startdate.getText());
		taskMap.put("end-date", enddate.getText());
		// IMPLEMENTATION: Add Subtasks ?
		
		return taskMap;
	}
	
	
	/*
	 * 
	 * colors taskline grey if inactive and gives color back if active again
	 */
	public void checkBoxChecked() {
		if (checkBoxDone.isSelected()) {
			main.setStyle("-fx-background-color: #808080;");
			for (TaskLine subTask: subTasks) {
				subTask.checkBoxDone.setSelected(true);
				subTask.checkBoxChecked();
			}
		} else {
			main.setStyle("-fx-background-color: " + backgroundColor + ";");
			for (TaskLine subTask: subTasks) {
				subTask.checkBoxDone.setSelected(false);
				subTask.checkBoxChecked();
			}
		}
	}
	
	
	/*
	 * 
	 * shows/hides children of a Task
	 */
	private void toggleSub() {
		if (subTasks.size() > 0) {
			if (toggleButton.isSelected()) {
					toggleButton.setText("-");
					for (TaskLine line:subTasks) {
						this.getChildren().add(line);
				}
			} else {
				toggleButton.setText("+");
				for (TaskLine line:subTasks) {
					this.getChildren().remove(line);
				}
			}
			}
		}
	
	private void handleClick() {
		if (clicked == false) {
	    this.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-cursor: hand;");
	    clicked = true;
	    if (onSelectedCallback != null) {
	    	onSelectedCallback.startCallback(id.getText());
	    }
		} else {
		    setUnselected();
		}
	}
	
	
	public void setUnselected() {
	    this.setStyle("");
		clicked = false;
	}
	
	
	/*
	 * GETTER/ SETTER
	 */
	public String getDescription() {
		return text.getText();
	}
	
	public String getTitle() {
		return title.getText();
	}
	
	public String getStartDate() {
		return startdate.getText();
	}
	
	public String getEndDate() {
		return enddate.getText();
	}
	
	public void setDescription(String n_text) {
		text.setText(n_text);
	}
	
	public void setTitle(String n_title) {
		title.setText(n_title);
	}
	
	public void setStartDate(String n_startdate) {
		startdate.setText(n_startdate);
	}
	
	public void setEndDate(String n_enddate) {
		enddate.setText(n_enddate);
	}
	
	
	///
	/// Callback
	///
	
	/**
	 * Callback when this Taskline is selected
	 * 
	 */
	public interface OnSelectedCallback{
		void startCallback(String id);
	}
	OnSelectedCallback onSelectedCallback;
	public void setCallback(OnSelectedCallback callback){
		onSelectedCallback = callback;
	}
	
}
