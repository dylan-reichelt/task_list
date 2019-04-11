package app;

import java.util.ArrayList;

import javafx.scene.control.TextArea;

public class taskList {
	ArrayList<taskEntry> taskArray = new ArrayList<taskEntry>(); // List for the tasks
	
	/**
     * Prints out the tasks to the task_text box
     */
    public void refreshList(TextArea task_text)
    {
    	String input = "";
    	for(int i = 0; i < taskArray.size(); i++)
    	{
    		taskEntry tempEntry = taskArray.get(i);
    		input = input + "Description: " + tempEntry.getDesc() + "\n" + 
    				"Priority: " + tempEntry.getPriority() + "\n" + 
    				"Due Date: " + tempEntry.getDue() + "\n\n";
    	}
    	
    	task_text.setText(input);
    }
    
    /**
     * Takes in a task and adds it to the taskArray depending on it's priority location
     * Shifts everything and increments all the data based off the priority number in the list
     * 
     * @param task
     */
    public void addToList(taskEntry task)
    {
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
