package app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.sun.javafx.binding.Logging;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.scene.control.Button;
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
        		entryWindow(stage);
        		//CREATE TASK FROM taskEntry Class AND ADD IT TO THE BIG LIST
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
        
        //Text of tasks
        TextArea task_text = new TextArea();
        task_text.setPrefWidth(1445);
        task_text.setPrefHeight(790);
        task_text.setLayoutX(15);
        task_text.setLayoutY(95);
        
        task_text.setEditable(false);
        
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
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        stage.setWidth(1475);
        stage.setHeight(960);
        stage.setScene(scene);
        stage.setTitle("TMI"); //TMI For Task Management Interface
        stage.show();
    }

    
    /**
     * This creates the entryWindow Pop-up to put in the information for a task.
     * It then returns the entry as a new taskEntry so it can be loaded into the main window.
     * 
     * @param stage
     * @return taskEntry
     */
    private Object entryWindow(Stage stage)
    {
    	//Creates the stage of the new window making it a module of the mainstage
    	Stage entryWin = new Stage();
    	entryWin.initModality(Modality.WINDOW_MODAL);
    	entryWin.initOwner(stage);
    	entryWin.setTitle("Add Task Entry");
    	entryWin.setX(stage.getX() + 100);
    	entryWin.setY(stage.getY() + 100);
    	entryWin.setHeight(600);
    	entryWin.setWidth(800);
    	
    	//Creates the descLabel and textBox to type in
    	Label descLabel = new Label("Description");
    	descLabel.setFont(Font.font("verdana",
				FontWeight.BOLD,
				FontPosture.REGULAR, 20));
    	descLabel.setLayoutX(15);
        descLabel.setLayoutY(20);
        
        TextArea descText = new TextArea("Description...");
        descText.setLayoutX(15);
        descText.setLayoutY(50);
        descText.setFont(Font.font("verdana",
        		FontWeight.NORMAL,
        		FontPosture.REGULAR,
        		18));
        
        //Create the datePicker for dueDate and Label
        Label dateLable = new Label("Due Date:");
        dateLable.setFont(Font.font("verdana",
        		FontWeight.NORMAL,
        		FontPosture.REGULAR, 20));
        DatePicker dueDate = new DatePicker();
        dateLable.setLayoutX(15);
        dateLable.setLayoutY(300);
        dueDate.setLayoutX(15);
        dueDate.setLayoutY(330);
        dueDate.setEditable(false);
        dueDate.setMaxSize(200, 75);
        dueDate.setStyle("-fx-font-size:20");
        
        //Create Priority Label and text box
        Label prioLabel = new Label("Priority:");
        prioLabel.setFont(Font.font("verdana",
        		FontWeight.NORMAL,
        		FontPosture.REGULAR, 20));
        prioLabel.setLayoutX(250);
        prioLabel.setLayoutY(300);
    	
        //Adding the items to the pane
    	Pane layout = new Pane();
    	layout.getChildren().add(descLabel);
    	layout.getChildren().add(descText);
    	layout.getChildren().add(dueDate);
    	layout.getChildren().add(dateLable);
    	layout.getChildren().add(prioLabel);
    	
    	Scene entryScene = new Scene(layout);
    	Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    	
    	taskEntry tempTask = new taskEntry();
    	
    	entryWin.setScene(entryScene);
    	entryWin.show();
    	return tempTask;
    }
}