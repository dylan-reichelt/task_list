package app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

//import com.sun.javafx.binding.Logging;

import app.taskEntry;
import app.taskList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.scene.control.Button;package app;

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
        task_text.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	@Override
        	public void handle(MouseEvent click) {
        		if(click.getClickCount() == 2)
        		{
        			String selectedString = task_text.getSelectionModel().getSelectedItem();
        			System.out.println(selectedString);
        		}
        	}
        });
        
        
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
        		tempWin.entryWindow(stage, taskTable, task_text);
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
        			alert.setContentText("The current task list is empty. There is nothing to clear.");
        			alert.showAndWait();
        		}
        		else {
        			Alert alert = new Alert(AlertType.CONFIRMATION);
        			alert.setTitle("Confirmation");
        			alert.setHeaderText(null);
        			alert.setContentText("Are you sure you would like to delete all tasks?");
            		Optional<ButtonType> response = alert.showAndWait();
            		if(response.get() == ButtonType.OK) {
            			taskTable.restartList(task_text);
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
        		Alert alert = new Alert(AlertType.CONFIRMATION);
    			alert.setTitle("Confirmation");
    			alert.setHeaderText(null);
    			alert.setContentText("Are you sure you would like to save your current task list?");
        		Optional<ButtonType> response = alert.showAndWait();
        		if(response.get() == ButtonType.OK) {
        			try {
                    	PrintWriter pw = new PrintWriter("test.txt");
                    	pw.println(taskTable.parseList());
                    	pw.close();
                    } catch (FileNotFoundException e){
                    	e.printStackTrace();
                    }
        		}
        		else {
        			// Close the dialog
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
        		JFileChooser chooser = new JFileChooser();
        		FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT files", "txt");
        		chooser.setFileFilter(filter);
        		int returnVal = chooser.showOpenDialog(null);
        		if(returnVal == JFileChooser.APPROVE_OPTION) {
        			System.out.println("You opened " + chooser.getSelectedFile().getName());
        			String filename = chooser.getSelectedFile().getName();
        			try {
            			File file = new File(filename); 
              		  
                		BufferedReader br = new BufferedReader(new FileReader(file)); 
                		  
                		String st; 
                		try {
                			while ((st = br.readLine()) != null) 
                				System.out.println(st); 
                		}catch (IOException e) {
                			
                		}
                		
            		} catch(FileNotFoundException e) {
            			Alert alert = new Alert(AlertType.INFORMATION);
            			alert.setTitle("Error - File not found");
            			alert.setHeaderText(null);
            			alert.setContentText("File not found. Please load the file initially saved as test.txt.");
            			alert.showAndWait();
            			System.out.println("File not found");
            		}
        			
        		}
        		
        		
        		
        		
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
        task_text.setOnMouseClicked(new EventHandler<MouseEvent>(){
        	@Override
        	public void handle(MouseEvent click) {
        		if(click.getClickCount() == 2)
        		{
        			String selectedString = task_text.getSelectionModel().getSelectedItem();
        			System.out.println(selectedString);
        		}
        	}
        });
        
        
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
        		tempWin.entryWindow(stage, taskTable, task_text);
        	}
        });
        
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
        
        //Load Button
        Button loadButton = new Button ("Load");
        loadButton.setStyle("-fx-font-size:20");
        loadButton.setLayoutX(1300);
        loadButton.setLayoutY(20);
        loadButton.setPrefSize(150, 50);
        
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