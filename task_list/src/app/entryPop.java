/**
 * Authors: Ignatius Akeeh, Kyle Gonzalez, Jackson Lewis, Dylan Reichelt
 * Team Project
 * entryPop.java
 * This class creates the entryPopup which allows for one to create a task
 * entry
 */

package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

class entryPop
{
	// CLASS VARIABLES
	taskEntry tempTask = new taskEntry();
	int prioNum;
	boolean prioRight = true;
	
	/**
	 * Creates the entry window and then adds it to the list. If it returns
	 * that the entry is invalid then it prompts the user to try again. If the
	 * user decides they no longer want to make a new task they simply click
	 * the x on the window.
	 * 
	 * @param stage
	 * @void
	 */
	public void entryWindow(Stage stage, taskList taskTable, ListView<String>
	text_list, String sortValue)
    {
    	//Creates the stage of the new window making it a module of the
		//	mainstage
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
        		boolean valid = false;
        		
        		try
        		{
        			prioNum = Integer.parseInt(prioText.getText());
        		}
        		catch(NumberFormatException e)
        		{
        			prioRight = false;
        		}
        		
        		if(prioRight == false)
        		{
        			prioRight = true;
        		}
        		else
        		{
        			tempTask.setDesc(descText.getText());
        			tempTask.setDue(dueDate.getValue());
        			
        			if(prioNum <= 0)
        			{
        				prioNum = 1;
        			}
        			
        			tempTask.setPriority(prioNum);
        			if(taskTable.addToList(tempTask))
        			{
        				valid = true;
        				taskTable.refreshList(text_list, sortValue);
        				entryWin.close();
        			}
        			else
        			{
        				
        			}
        		}
        		
        		if(valid == false)
        		{
        			//Creates A Popup Window if the entry is invalid
    				Stage invalidWin = new Stage();
    				invalidWin.initModality(Modality.WINDOW_MODAL);
    		    	invalidWin.initOwner(entryWin);
    		    	invalidWin.setTitle("Add Task Entry");
    		    	invalidWin.setX(entryWin.getX() + 200);
    		    	invalidWin.setY(entryWin.getY() + 200);
    		    	invalidWin.setHeight(200);
    		    	invalidWin.setWidth(400);
    		    	
    		    	//Label Text
    		    	Label invalidText = new Label("This entry is invalid,"
    		    			+ "\nPlease check entry and try again.");
    		    	invalidText.setFont(Font.font("verdana",
    		        		FontWeight.NORMAL,
    		        		FontPosture.REGULAR, 20));
    		    
    		    	//Creates okay button
    		    	Button okayButton = new Button("Okay");
    		    	okayButton.setStyle("-fx-font-size:20");
    		    	okayButton.setLayoutX(100);
    		    	okayButton.setLayoutY(60);
    		        okayButton.setPrefSize(100, 40);
    		        
    		        okayButton.setOnAction(new EventHandler<ActionEvent>()
    		        {
    		        	@Override
    		        	public void handle(ActionEvent event)
    		        	{
    		        		invalidWin.close();
    		        	}
    		        });
    		        
    		    	
    		    	//Adds the invalidWin to the pane and shows it
    		    	Pane tempLayout = new Pane();
    		    	tempLayout.getChildren().add(invalidText);
    		    	tempLayout.getChildren().add(okayButton);
    		    	Scene tempScene = new Scene(tempLayout);
    		    	invalidWin.setScene(tempScene);
    		    	invalidWin.show();
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
    }
}