package app;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.scene.control.Alert;
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
     * Adds a task to the list taskArray. If it is added returns true. If it is not a valid task then it returns false and won't add it
     * to the list. This adds to the list according to priority.
     * 
     * @param task
     * @return boolean
     */
    public boolean addToList(taskEntry task)
    {
    	int location = 0;
    	boolean priorityHit = false;
    	
    	if(isValid(task))
    	{
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
    		return true;
    	}
    	return false;
    }
    
    public void restartList(ListView<String> task_text) {
    	task_text.getItems().clear();
    	taskArray.clear();
    }
    
    public int getSize() {
    	return taskArray.size();
    }
    
    public String parseList() {
    	String textParse = "";
    	for(int i = 0; i < taskArray.size(); i++)
    	{
    		taskEntry tempEntry = taskArray.get(i);
    		textParse += tempEntry.getDesc() + System.getProperty("line.separator")
    				+ tempEntry.getPriority() + System.getProperty("line.separator")
    				+ tempEntry.getDue().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")) + System.getProperty("line.separator")
    				+ tempEntry.getDue().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")) + System.getProperty("line.separator")
    				+ tempEntry.getStatus() + System.getProperty("line.separator")
    				+ System.getProperty("line.separator");
    	} 
    	return textParse;
    }
    
    /**
     * This takes in a task that wants to be added. Checks to make sure that the description doesn't already exist,
     * That the desc or due aren't null. If they are then it returns false
     * @param task
     * @return boolean
     */
    private boolean isValid(taskEntry task)
    {
    	boolean valid = true;
    	for(int num = 0; num < taskArray.size(); num++)
    	{
    		taskEntry tempTask = taskArray.get(num);
    		if(tempTask.getDesc().equalsIgnoreCase(task.getDesc()))
    		{
    			System.out.println("NOT VALID 1");
    			valid = false;
    	
    		}
    	}
    	if(task.getDesc() == null || task.getDue() == null)
		{
    		System.out.println("NOT VALID 2");
    		valid = false;
		}
    	System.out.println("VALID");
    	return valid;
    
    }
}
