package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

//import com.sun.javafx.binding.Logging;

import app.taskEntry;
import app.taskList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class App extends Application { 
	
	// CLASS VARIABLES
	taskList taskTable = new taskList();
	
    @Override
    public void start(Stage stage) {
    	mainWindow(stage);
    }

    public static void main(String[] args) {
        launch();
    }
    
    /**
     * This function Creates the main window of the application.
     * Giving the users options to add entry, delete, save, and exit
     * as well as allowing them to see their tasks and filter them
     * 
     * @param stage
     * 
     */
    
    public void mainWindow(Stage stage)
    {	
    	// Settings for label
        Label toDoLabel = new Label("To-Do List");
        toDoLabel.setFont(Font.font("verdana",
        							FontWeight.BOLD,
        							FontPosture.ITALIC, 40));
        toDoLabel.setLayoutX(15);
        toDoLabel.setLayoutY(20);
        
        //Text of tasks
        ListView<String> task_text = new ListView<String>();
        task_text.setPrefWidth(1425);
        task_text.setPrefHeight(790);
        task_text.setLayoutX(15);
        task_text.setLayoutY(95);
        task_text.setEditable(false);
        task_text.setStyle("-fx-font-size:20");

        
        //Making the drop down for filtering
        ObservableList<String> filter = 
        	FXCollections.observableArrayList(
        			"Filter By...",
        			"Description",
        			"Due Date",
        			"Priority",
        			"Status");
        
        
        final ComboBox<String> filterBox = new ComboBox<String>(filter);
        filterBox.setPrefSize(250, 50);
        filterBox.setLayoutX(275);
        filterBox.setLayoutY(20);
        filterBox.setStyle("-fx-font-size:20");
        
        if(filterBox.getValue() == null)
        {
        	filterBox.setValue("Filter By...");
        }
      filterBox.valueProperty().addListener(new ChangeListener<String>() {

      	@Override public void changed(ObservableValue val, String t, String t1) {
      		taskTable.refreshList(task_text, filterBox.getValue());
      	}
      	
      });
		// Autoloading tasks from text file if file exists
		taskTable.autoLoadTasks(taskTable);
		taskTable.refreshList(task_text, filterBox.getValue());
        
		task_text.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	@Override
        	public void handle(MouseEvent click) {
        		if(click.getClickCount() == 2)
        		{
        			String selectedString = task_text.getSelectionModel().getSelectedItem();
        			int priorityNum = taskTable.stringToPriority(selectedString) - 1;
        			taskEntry oldTask = taskTable.getTask(priorityNum);
        			
        	    	Stage editWindow = new Stage();
        	    	editWindow.initModality(Modality.WINDOW_MODAL);
        	    	editWindow.initOwner(stage);
        	    	editWindow.setTitle("Edit or Delete Task");
        	    	editWindow.setX(stage.getX() + 100);
        	    	editWindow.setY(stage.getY() + 100);
        	    	editWindow.setHeight(300);
        	    	editWindow.setWidth(650);
        	    	
        	        Button editButton = new Button ("Edit Task");
        	        editButton.setStyle("-fx-font-size:20");
        	        editButton.setLayoutX(50);
        	        editButton.setLayoutY(190);
        	        editButton.setPrefSize(150, 40);
        	        
        	        editButton.setOnAction(new EventHandler<ActionEvent>() {
        	        	@Override
        	        	public void handle(ActionEvent event)
        	        	{
        	        		
        	        		Stage edit = new Stage();
                	    	edit.initModality(Modality.WINDOW_MODAL);
                	    	edit.initOwner(stage);
                	    	edit.setTitle("Edit Task Entry");
                	    	edit.setX(stage.getX() + 100);
                	    	edit.setY(stage.getY() + 100);
                	    	edit.setHeight(450);
                	    	edit.setWidth(800);
                	    	
                	    	Label descLabel = new Label("New Description");
                	    	descLabel.setFont(Font.font("verdana",
                					FontWeight.BOLD,
                					FontPosture.REGULAR, 20));
                	    	descLabel.setLayoutX(15);
                	        descLabel.setLayoutY(20);
                	        
                	        TextArea descText = new TextArea(oldTask.getDesc());
                	        descText.setPrefSize(470, 200);
                	        descText.setLayoutX(15);
                	        descText.setLayoutY(50);
                	        descText.setFont(Font.font("verdana",
                	        		FontWeight.NORMAL,
                	        		FontPosture.REGULAR,
                	        		18));
                	        
                	        Label startDateLabel = new Label("Start Date:");
                	        startDateLabel.setFont(Font.font("verdana",
                	        		FontWeight.NORMAL,
                	        		FontPosture.REGULAR, 20));
                	        DatePicker startEndDate = new DatePicker();
                	        startDateLabel.setLayoutX(500);
                	        startDateLabel.setLayoutY(150);
                	        startEndDate.setLayoutX(500);
                	        startEndDate.setLayoutY(180);
                	        startEndDate.setEditable(false);
                	        startEndDate.setMaxSize(200, 75);
                	        startEndDate.setStyle("-fx-font-size:20");
                	        startDateLabel.setVisible(false);
                	        startEndDate.setVisible(false);
                	        
                	        ObservableList<String> status = 
                	            	FXCollections.observableArrayList(
                	            			"Not Started",
                	            			"In Progress",
                	            			"Complete");
                	            
                	            
                	        final ComboBox<String> statusBox = new ComboBox<String>(status);
                	        statusBox.setPrefSize(250, 50);
                	        statusBox.setLayoutX(500);
                	        statusBox.setLayoutY(50);
                	        statusBox.setStyle("-fx-font-size:20");
                	        statusBox.setValue(oldTask.getStatus());
                	        
                	        statusBox.valueProperty().addListener(new ChangeListener<String>() {

                	          	@Override public void changed(ObservableValue val, String t, String t1) {
                	          		if(statusBox.getValue().equals("In Progress") && oldTask.getStatus().equals("Not Started")) {
                	          			startDateLabel.setText("Start Date");
                	          			startDateLabel.setVisible(true);
                	          			startEndDate.setVisible(true);
                	          		}
                	          		else if(statusBox.getValue().equals("Complete")) {
                	          			startDateLabel.setText("Complete Date");
                	          			startDateLabel.setVisible(true);
                	          			startEndDate.setVisible(true);
                	          		}
                	          	}
                	          	
                	          });
                	        
                	        //Create the datePicker for dueDate and Label
                	        Label dateLabel = new Label("Due Date:");
                	        dateLabel.setFont(Font.font("verdana",
                	        		FontWeight.NORMAL,
                	        		FontPosture.REGULAR, 20));
                	        DatePicker dueDate = new DatePicker();
                	        dateLabel.setLayoutX(15);
                	        dateLabel.setLayoutY(310);
                	        dueDate.setLayoutX(15);
                	        dueDate.setLayoutY(340);
                	        dueDate.setEditable(false);
                	        dueDate.setMaxSize(200, 75);
                	        dueDate.setStyle("-fx-font-size:20");
                	        dueDate.setValue(oldTask.getDue());
                	        
                	        //Create Priority Label and text box
                	        Label prioLabel = new Label("Priority Number:");
                	        prioLabel.setFont(Font.font("verdana",
                	        		FontWeight.NORMAL,
                	        		FontPosture.REGULAR, 20));
                	        prioLabel.setLayoutX(250);
                	        prioLabel.setLayoutY(310);
                	        
                	        TextArea prioText = new TextArea(Integer.toString(oldTask.getPriority()));
                	        prioText.setLayoutX(250);
                	        prioText.setLayoutY(340);
                	        prioText.setMinSize(200, 45);
                	        prioText.setMaxSize(200, 45);
                	        prioText.setStyle("-fx-font-size:20");
                	        
                	        Label errorLabel = new Label("Error");
                	        errorLabel.setFont(Font.font("verdana",
                	        		FontWeight.NORMAL,
                	        		FontPosture.REGULAR, 20));
                	        errorLabel.setLayoutX(500);
                	        errorLabel.setLayoutY(280);
                	        errorLabel.setVisible(false);
                	        
                	        //Create the accept button and the event on click
                	        Button acceptButton = new Button ("Accept");
                	        acceptButton.setStyle("-fx-font-size:20");
                	        acceptButton.setLayoutX(550);
                	        acceptButton.setLayoutY(340);
                	        acceptButton.setPrefSize(150, 40);
                	        
                	        //
                	        
                	        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                	        	@Override
                	        	public void handle(ActionEvent event)
                	        	{
                	        		taskEntry newTask = new taskEntry();
                	        		boolean validNew = true;
                	        		int newPriority = 0;
                	        		
                	        		try
                	        		{
                	        			newPriority = Integer.parseInt(prioText.getText());
                	        		}
                	        		catch(NumberFormatException e)
                	        		{
                	        			errorLabel.setText("Priority not a number!");
                	        			errorLabel.setVisible(true);
                	        			validNew = false;
                	        		}
                	        		
                	        		if(validNew && statusBox.getValue().equals("Not Started") && oldTask.getStatus().equals("In Progress"))
                	        		{
                	        			errorLabel.setText("Task already started!");
                	        			errorLabel.setVisible(true);
                	        			validNew = false;
                	        		}
                	        		
                	        		if(validNew && statusBox.getValue().equals("Complete") && oldTask.getStatus().equals("Not Started"))
                	        		{
                	        			errorLabel.setText("Task was never started!");
                	        			errorLabel.setVisible(true);
                	        			validNew = false;
                	        		}
                	        		
                	        		if(validNew && startEndDate.getValue() == null && !statusBox.getValue().equals(oldTask.getStatus()))
                	        		{
                	        			if(statusBox.getValue().equals("In Progress"))
                	        				errorLabel.setText("Start Date not valid!");
                	        			else
                	        				errorLabel.setText("Complete Date not valid!");
                	        			errorLabel.setVisible(true);
                	        			validNew = false;
                	        		}
                	        		
                	        		if(validNew)	//entries are valid
                	        		{
                	        			newTask.setDesc(descText.getText());
                	        			newTask.setPriority(newPriority);
                	        			newTask.setDue(dueDate.getValue());
                	        			
                	        			if(statusBox.getValue().equals(oldTask.getStatus()))
                	        			{
                	        				newTask.setStatus(statusBox.getValue());
                	        				newTask.setStart(oldTask.getStart());
                	        			}
                	        			else if(statusBox.getValue().equals("In Progress"))
                	        			{
                	        				newTask.setStatus("In Progress");
                	        				newTask.setStart(startEndDate.getValue());
                	        			}
                	        			else if(statusBox.getValue().equals("Complete"))
                	        			{
                	        				newTask.setStatus("Complete");
                	        				newTask.setCompleteDate(startEndDate.getValue());
                	        			}
                	        			else
                	        			{
                	        				newTask.setStatus("Not Started");
                	        			}
                	        			
                    	        		taskTable.removeTask(priorityNum);
                    	        		if (taskTable.addToList(newTask))
                    	        		{
                    	        			System.out.println("VALID EDIT");
                    	        			edit.close();
                    	        		}
                    	        		else
                    	        		{
                    	        			errorLabel.setText("Decription already used!");
                    	        			errorLabel.setVisible(true);
                    	        			taskTable.addToList(oldTask);
                    	        		}
                    	        		taskTable.refreshList(task_text, filterBox.getValue());
                	        		}
                	        		

                	        		
                	        		
                	        	}
                	        });
                	        
                	        //
                	        
                	    	Pane editPane = new Pane();
                	    	editPane.getChildren().add(descLabel);
                	    	editPane.getChildren().add(descText);
                	    	editPane.getChildren().add(dateLabel);
                	    	editPane.getChildren().add(dueDate);
                	    	editPane.getChildren().add(prioLabel);
                	    	editPane.getChildren().add(prioText);
                	    	editPane.getChildren().add(acceptButton);
                	    	editPane.getChildren().add(statusBox);
                	    	editPane.getChildren().add(startDateLabel);
                	    	editPane.getChildren().add(startEndDate);
                	    	editPane.getChildren().add(errorLabel);
                	    	Scene entryScene = new Scene(editPane);
                	    	edit.setScene(entryScene);
                	    	edit.showAndWait();
        	        		
        	        		editWindow.close();
        	        	}
        	        });
        	        
        	        Button deleteButton = new Button ("Delete Task");
        	        deleteButton.setStyle("-fx-font-size:20");
        	        deleteButton.setLayoutX(250);
        	        deleteButton.setLayoutY(190);
        	        deleteButton.setPrefSize(150, 40);
        	        
        	        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
        	        	@Override
        	        	public void handle(ActionEvent event)
        	        	{
                			Alert alert = new Alert(AlertType.CONFIRMATION);
                			alert.setTitle("Delete");
                			alert.setHeaderText(null);
                			alert.setContentText("Are you sure you would like to delete this task?");
                    		Optional<ButtonType> response = alert.showAndWait();
                    		if(response.get() == ButtonType.OK)
                    		{
                    			System.out.println(oldTask.getTaskPrint());
                    			System.out.println(priorityNum);
                    			taskTable.deleteTask(priorityNum);
                    			taskTable.refreshList(task_text, filterBox.getValue());
                    			editWindow.close();
                    		}
                    		else {
                    			// Close the dialog
                    		}
        	        	}
        	        });
        	        
        	        Button cancelButton = new Button ("Cancel");
        	        cancelButton.setStyle("-fx-font-size:20");
        	        cancelButton.setLayoutX(450);
        	        cancelButton.setLayoutY(190);
        	        cancelButton.setPrefSize(150, 40);
        	        
        	        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
        	        	@Override
        	        	public void handle(ActionEvent event)
        	        	{
        	        		editWindow.close();
        	        	}
        	        });
        	        
        	        TextArea task = new TextArea(oldTask.getTaskPrint());
        	        task.setStyle("-fx-font-size:20");
        	        task.setLayoutX(0);
        	        task.setLayoutY(0);
        	        task.setPrefSize(650, 170);
        	    	
        	    	Pane layout = new Pane();
        	    	layout.getChildren().add(cancelButton);
        	    	layout.getChildren().add(deleteButton);
        	    	layout.getChildren().add(editButton);
        	    	layout.getChildren().add(task);
        	    	Scene entryScene = new Scene(layout);
        	    	editWindow.setScene(entryScene);
        	    	editWindow.showAndWait();
        			
        		}
        		else if(click.getClickCount() == 9)
        		{
        			Alert alert = new Alert(AlertType.CONFIRMATION);
        			alert.setTitle("Delete");
        			alert.setHeaderText(null);
        			alert.setContentText("Are you sure you would like to delete this task?");
            		Optional<ButtonType> response = alert.showAndWait();
            		if(response.get() == ButtonType.OK)
            		{
            			String selectedString = task_text.getSelectionModel().getSelectedItem();
            			int priorityNum = taskTable.stringToPriority(selectedString) - 1;
            			taskEntry tempTask = taskTable.getTask(priorityNum);
            			System.out.println(tempTask.getTaskPrint());
            			System.out.println(priorityNum);
            			taskTable.deleteTask(priorityNum);
            			taskTable.refreshList(task_text, filterBox.getValue());
            		}
            		else {
            			// Close the dialog
            		}
        		}
        	}
        });
        
        
        //Add Task Button Settings
        Button addButton = null;
        try {
			FileInputStream plusPic = new FileInputStream("../resources/plus_sign.png");
			Image plusImage = new Image(plusPic);
			ImageView plusView = new ImageView(plusImage);
			plusView.setFitHeight(25);
			plusView.setFitWidth(25);
			addButton = new Button(" Add Task", plusView);
			
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("IN CATCH");
			addButton = new Button("Add Task");
			e.printStackTrace();
		}
        addButton.setStyle("-fx-font-size:20");
        addButton.setLayoutX(550);
        addButton.setLayoutY(20);
        addButton.setPrefSize(175, 50);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event)
        	{
        		entryPop tempWin = new entryPop();
        		tempWin.entryWindow(stage, taskTable, task_text, filterBox.getValue());
        	}
        });
        // deleted = -1
        // completed = 0 
        
        //Restart Button
        Button restartButton = null;
        try {
			FileInputStream plusPic = new FileInputStream("../resources/restart_button.png");
			Image plusImage = new Image(plusPic);
			ImageView plusView = new ImageView(plusImage);
			plusView.setFitHeight(35);
			plusView.setFitWidth(35);
			restartButton = new Button(" Restart", plusView);
			
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("IN CATCH");
			restartButton = new Button("Restart");
			e.printStackTrace();
		}
        
        restartButton.setStyle("-fx-font-size:20");
        restartButton.setLayoutX(750);
        restartButton.setLayoutY(20);
        restartButton.setPrefSize(175, 50);
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event)
        	{
        		System.out.println("CURRENT TASK LIST SIZE: " + taskTable.getSize());
        		if(taskTable.getSize() == 0){
        			Alert alert = new Alert(AlertType.INFORMATION);
        			alert.setTitle("Error - Nothing to clear");
        			alert.setHeaderText(null);
        			alert.setContentText("The current task list is empty. There are no tasks to clear.");
        			alert.showAndWait();
        		}
        		else {
        			Alert alert = new Alert(AlertType.CONFIRMATION);
        			alert.setTitle("Confirmation");
        			alert.setHeaderText(null);
        			alert.setContentText("Are you sure you would like to delete all tasks?"
        					+ " All tasks in the text file will also be deleted.");
            		Optional<ButtonType> response = alert.showAndWait();
            		if(response.get() == ButtonType.OK) {
            			taskTable.restartList(task_text, 0);
            		}
            		else {
            			// Close the dialog
            		}
        		}
        	}
        });
        
        //Print Button
        Button printButton = new Button ("Print");
        printButton.setStyle("-fx-font-size:20");
        printButton.setLayoutX(950);
        printButton.setLayoutY(20);
        printButton.setPrefSize(150, 50);
        
        //Save Button
        Button saveButton = new Button ("Save");
        saveButton.setStyle("-fx-font-size:20");
        saveButton.setLayoutX(1125);
        saveButton.setLayoutY(20);
        saveButton.setPrefSize(150, 50);
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event)
        	{
        		// Error message will appear if the list is empty
        		if(taskTable.getSize() == 0) { 
        			Alert alert = new Alert(AlertType.INFORMATION);
        			alert.setTitle("Error - Task List is empty");
        			alert.setHeaderText(null);
        			alert.setContentText("The current task list is empty. There are no tasks to save.");
        			alert.showAndWait();
        		}
        		else { // proceed if the list is not empty
        			Alert alert = new Alert(AlertType.CONFIRMATION);
        			alert.setTitle("Confirmation");
        			alert.setHeaderText(null);
        			alert.setContentText("Are you sure you would like to save your current task list?");
            		Optional<ButtonType> response = alert.showAndWait();
            		if(response.get() == ButtonType.OK) {
            			taskTable.saveList(taskTable); // saves the list to a text file
            		}
            		else {
            			// Close the dialog
            		}
        		}
        		
        	}
        });
        
        //Load Button
        Button loadButton = new Button ("Load");
        loadButton.setStyle("-fx-font-size:20");
        loadButton.setLayoutX(1300);
        loadButton.setLayoutY(20);
        loadButton.setPrefSize(150, 50);
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event)
        	{
        		if(taskTable.getSize() > 0) {
        			Alert alert = new Alert(AlertType.CONFIRMATION);
        			alert.setTitle("Confirmation");
        			alert.setHeaderText(null);
        			alert.setContentText("Loading a list involves deleting all current tasks. "
        					+ "Are you sure you would like to delete all tasks?");
            		Optional<ButtonType> response = alert.showAndWait();
            		if(response.get() == ButtonType.OK) {
            			taskTable.restartList(task_text, 1);
            		}
            		else {
            			// Close the dialog
            		}
        		}
            	taskTable.loadList(taskTable);
            	taskTable.refreshList(task_text, filterBox.getValue());
        	}
        });
        
        //Adding the pane and the items to the pane
        Pane layout = new Pane();
        layout.getChildren().add(toDoLabel);
        layout.getChildren().add(filterBox);
        layout.getChildren().add(addButton);
        layout.getChildren().add(restartButton);
        layout.getChildren().add(printButton);
        layout.getChildren().add(saveButton);
        layout.getChildren().add(loadButton);
        layout.getChildren().add(task_text);
        
        //Adding the layout to the scene and setting up scene
        Scene scene = new Scene (layout);

        //set Stage boundaries to visible bounds of the main screen
        stage.setWidth(1475);
        stage.setHeight(960);
        stage.setScene(scene);
        stage.setTitle("TMI"); //TMI For Task Management Interface
        stage.show();
    }
}