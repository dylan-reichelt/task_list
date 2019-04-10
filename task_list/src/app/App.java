package app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        toDoLabel.setLayoutY(10);
        
        //Making the drop down for filtering
        ObservableList<String> filter = 
        	FXCollections.observableArrayList(
        			"Filter By...",
        			"Priority",
        			"Description",
        			"Date Started");
        
        
        final ComboBox filterBox = new ComboBox(filter);
        filterBox.setPrefSize(250, 50);
        filterBox.setLayoutX(275);
        filterBox.setLayoutY(10);
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
        addButton.setLayoutY(10);
        addButton.setPrefSize(175, 50);
        
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
        restartButton.setLayoutY(10);
        restartButton.setPrefSize(175, 50);
       
        //Print Button
        Button printButton = new Button ("Print");
        printButton.setStyle("-fx-font-size:20");
        printButton.setLayoutX(950);
        printButton.setLayoutY(10);
        printButton.setPrefSize(150, 50);
        
        //Save Button
        Button saveButton = new Button ("Save");
        saveButton.setStyle("-fx-font-size:20");
        saveButton.setLayoutX(1125);
        saveButton.setLayoutY(10);
        saveButton.setPrefSize(150, 50);
        
        //Load Button
        Button loadButton = new Button ("Load");
        loadButton.setStyle("-fx-font-size:20");
        loadButton.setLayoutX(1300);
        loadButton.setLayoutY(10);
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

}