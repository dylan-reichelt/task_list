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
    public void refreshList(ListView<String> task_text, String sortValue)
    {
    	
    	taskEntry[] sortedArray = new taskEntry[taskArray.size()];
		int inserted;
		
		switch(sortValue)
		{
		case "Description":
			if(taskArray.size() != 0)
				sortedArray[0] = taskArray.get(0);
			inserted = 1;
			for(int taskIndex = 1; taskIndex < sortedArray.length; taskIndex++)
			{
				int sortedIndex = 0;
				while(sortedIndex < inserted && taskArray.get(taskIndex).getDesc().compareTo(sortedArray[sortedIndex].getDesc()) > 0)
				{
					sortedIndex++;
				}
				
				for(int insertIndex = inserted; insertIndex > sortedIndex; insertIndex--)
				{
					sortedArray[insertIndex] = sortedArray[insertIndex - 1];
				}
				
				sortedArray[sortedIndex] = taskArray.get(taskIndex);
				inserted++;
			}
			System.out.println("RAN DESCRIPTION");
			break;
		case "Due Date":
			if(taskArray.size() != 0)
				sortedArray[0] = taskArray.get(0);
			inserted = 1;
			for(int taskIndex = 1; taskIndex < sortedArray.length; taskIndex++)
			{
				int sortedIndex = 0;
				while(sortedIndex < inserted && taskArray.get(taskIndex).getDue().compareTo(sortedArray[sortedIndex].getDue()) > 0)
				{
					sortedIndex++;
				}
				
				for(int insertIndex = inserted; insertIndex > sortedIndex; insertIndex--)
				{
					sortedArray[insertIndex] = sortedArray[insertIndex - 1];
				}
				
				sortedArray[sortedIndex] = taskArray.get(taskIndex);
				inserted++;
			}
			System.out.println("RAN STATUS");
			break;
		case "Status":
			if(taskArray.size() != 0)
				sortedArray[0] = taskArray.get(0);
			inserted = 1;
			for(int taskIndex = 1; taskIndex < sortedArray.length; taskIndex++)
			{
				int sortedIndex = 0;
				while(sortedIndex < inserted && taskArray.get(taskIndex).getNumericalStatus() > (sortedArray[sortedIndex].getNumericalStatus()))
				{
					sortedIndex++;
				}
				
				for(int insertIndex = inserted; insertIndex > sortedIndex; insertIndex--)
				{
					sortedArray[insertIndex] = sortedArray[insertIndex - 1];
				}
				
				sortedArray[sortedIndex] = taskArray.get(taskIndex);
				inserted++;
			}
			System.out.println("RAN STATUS");
			break;
		default:
			if(taskArray.size() != 0)
				sortedArray[0] = taskArray.get(0);
			inserted = 1;
			for(int taskIndex = 1; taskIndex < sortedArray.length; taskIndex++)
			{
				int sortedIndex = 0;
				while(sortedIndex < inserted && taskArray.get(taskIndex).getPriority() > (sortedArray[sortedIndex].getPriority()))
				{
					sortedIndex++;
				}
				
				for(int insertIndex = inserted; insertIndex > sortedIndex; insertIndex--)
				{
					sortedArray[insertIndex] = sortedArray[insertIndex - 1];
				}
				
				sortedArray[sortedIndex] = taskArray.get(taskIndex);
				inserted++;
			}
			System.out.println("RAN DEFAULT");
			break;
		}
    	
    	
    	task_text.getItems().clear();
    	for(int i = 0; i < sortedArray.length; i++)
    	{
    		taskEntry tempEntry = sortedArray[i];
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
    			valid = false;
    	
    		}
    	}
    	if(task.getDesc() == null || task.getDue() == null)
		{
    		valid = false;
		}
    	return valid;
    
    }
}
