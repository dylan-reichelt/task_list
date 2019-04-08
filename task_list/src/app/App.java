package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class App extends Application {

    @Override
    public void start(Stage stage) {
    	ObservableList<String> tasks = FXCollections.observableArrayList();
    	ListView<String> manifest;
    	manifest.setItems(tasks);
    	
        String version = System.getProperty("java.version");
        Label l = new Label("Hello, JavaFX 11, running on "+version);
        Scene scene = new Scene (new StackPane(l));
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        stage.setX((primaryScreenBounds.getWidth() - 640) / 2);
        stage.setY((primaryScreenBounds.getHeight() - 480) / 2);
        stage.setWidth(primaryScreenBounds.getWidth()/3);
        stage.setHeight(primaryScreenBounds.getHeight()/3);
        stage.setScene(scene);
        stage.setTitle("TMI"); //TMI For Task Management Interface
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}