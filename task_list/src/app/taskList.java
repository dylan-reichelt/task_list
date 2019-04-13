package app;

import java.util.ArrayList;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

public class taskList {
	ArrayList<taskEntry> taskArray = new ArrayList<taskEntry>(); // List for the tasks
	
	/**
     * Prints out the tasks to the task_text box
     */
    public void refreshList(ListView<String> task_text)
    {
    	task_text.getItems().clear();
    	for(int i = 0; i < taskArray.size(); i++)
    	{
    		taskEntry tempEntry = taskArray.get(i);
    		String input = tempEntry.getTaskPrint();
    		task_text.getItems().add(input);
    	} 
    }
    
    /**
     * Takes in a task and adds it to the taskArray depending on it's priority location
     * Shifts everything and increments all the data based off the priority number in the list
     * 
     * @param task
     */
    public void addToList(taskEntry task)
    {
    	
    	int newPriority = task.getPriority();
    	int location = 0;
    	boolean priorityHit = false;
    	
    	if(taskArray.size() < 1)
    	{
    		task.setPriority(1);
    		taskArray.add(task);
    	}
    	else
    	{
    		//Checks to see if something has the same priority
    		for(int tempNum = 0; tempNum < taskArray.size(); tempNum++)
    		{
    			taskEntry tempTask = taskArray.get(tempNum);
    			if(task.getPriority() == tempTask.getPriority())
    			{
    				System.out.println("PRIORITY HIT");
    				priorityHit = true;
    				location = tempNum;
    			}
    		}
    		
    		if(priorityHit == true)
    		{
    			task.setPriority(taskArray.size());
    			taskArray.add(location, task);
    			
    			for(int tempNum = location; tempNum < taskArray.size(); tempNum ++)
    			{
    				taskArray.get(tempNum).setPriority(tempNum + 1);
    			}
    		}
    		else
    		{
    			task.setPriority(taskArray.size() + 1);
    			taskArray.add(task);
    		}
    		
    	}
    }
}
