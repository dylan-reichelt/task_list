package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class creates the entryPopup which allows for one to create a task entry
 * 
 * @functions
 * entryWindow(Stage stage)
 * 
 * @author Dylan
 *
 */
class entryPop
{
	taskEntry tempTask = new taskEntry();
	int prioNum;
	boolean prioRight = true;
	
	/**
	 * Creates the entry window and then waits until the accept button is clicked or the window is closed to
	 * return data. Returns a taskEntry object so it can be added to the arrayList later.
	 * 
	 * @param stage
	 * @return taskEntry
	 */
	public taskEntry entryWindow(Stage stage)
    {
    	//Creates the stage of the new window making it a module of the mainstage
    	Stage entryWin = new Stage();
    	entryWin.initModality(Modality.WINDOW_MODAL);
    	entryWin.initOwner(stage);
    	entryWin.setTitle("Add Task Entry");
    	entryWin.setX(stage.getX() + 100);
    	entryWin.setY(stage.getY() + 100);
    	entryWin.setHeight(450);
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
        dateLable.setLayoutY(310);
        dueDate.setLayoutX(15);
        dueDate.setLayoutY(340);
        dueDate.setEditable(false);
        dueDate.setMaxSize(200, 75);
        dueDate.setStyle("-fx-font-size:20");
        
        //Create Priority Label and text box
        Label prioLabel = new Label("Priority Number:");
        prioLabel.setFont(Font.font("verdana",
        		FontWeight.NORMAL,
        		FontPosture.REGULAR, 20));
        prioLabel.setLayoutX(250);
        prioLabel.setLayoutY(310);
        
        TextArea prioText = new TextArea("Number");
        prioText.setLayoutX(250);
        prioText.setLayoutY(340);
        prioText.setMinSize(200, 45);
        prioText.setMaxSize(200, 45);
        prioText.setStyle("-fx-font-size:20");
        
        //Create the accept button and the event on click
        Button acceptButton = new Button ("Accept");
        acceptButton.setStyle("-fx-font-size:20");
        acceptButton.setLayoutX(550);
        acceptButton.setLayoutY(340);
        acceptButton.setPrefSize(150, 40);
        
        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event)
        	{
        		try
        		{
        			prioNum = Integer.parseInt(prioText.getText());
        		}
        		catch(NumberFormatException e)
        		{
        			System.out.println("Sorry priority not a number");
        			prioRight = false;
        		}
        		
        		if(dueDate.getValue() == null || prioRight == false)
        		{
        			System.out.println("Wrong Input");
        			prioRight = true;
        		}
        		else
        		{
        			tempTask.setDesc(descText.getText());
        			tempTask.setDue(dueDate.getValue());
        			tempTask.setPriority(prioNum);
        			entryWin.close();
        		}
        	}
        });
    	
        //Adding the items to the pane
    	Pane layout = new Pane();
    	layout.getChildren().add(descLabel);
    	layout.getChildren().add(descText);
    	layout.getChildren().add(dueDate);
    	layout.getChildren().add(dateLable);
    	layout.getChildren().add(prioLabel);
    	layout.getChildren().add(prioText);
    	layout.getChildren().add(acceptButton);
    	Scene entryScene = new Scene(layout);
    	entryWin.setScene(entryScene);
    	entryWin.showAndWait();
    	return tempTask;
    }
}