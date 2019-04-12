package app;

import java.util.ArrayList;

import javafx.scene.control.TextArea;

/**
 * Creates the taskList this is what takes in the input of a task and then the functionality to
 * manipulate the taskList.
 * 
 * @author Dylan
 *
 */
public class taskList {
	ArrayList<taskEntry> taskArray = new ArrayList<taskEntry>(); // List for the tasks
	
	/**
     * Prints out the tasks to the task_text box
     * 
     * @param TextArea that it wants to update
     */
    public void refreshList(TextArea task_text, String sortValue)
    {
    	//
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
				while(sortedIndex < inserted && taskArray.get(taskIndex).getDescription().compareTo(sortedArray[sortedIndex].getDescription()) > 0)
				{
					sortedIndex++;
				}
				
				for(int insertIndex = sortedIndex; insertIndex < inserted; insertIndex++)
				{
					sortedArray[insertIndex + 1] = sortedArray[insertIndex];
				}
				
				sortedArray[sortedIndex] = taskArray.get(taskIndex);
				inserted++;
			}
			System.out.println("RAN DESCRIPTION");
			break;
		case "Due Date":
			
			break;
		case "Status":
			
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
    	
    	String input = "";
    	for(int i = 0; i < sortedArray.length; i++)
    	{
    		taskEntry tempEntry = sortedArray[i];
    		input = input + "Description: " + tempEntry.getDescription() + "\n" + 
    				"Priority: " + tempEntry.getPriority() + "\n" + 
    				"Due Date: " + tempEntry.getDue() + "\n\n";
    	}
    	
    	task_text.setText(input);
    	
    	//
    	/*String input = "";
    	for(int i = 0; i < taskArray.size(); i++)
    	{
    		taskEntry tempEntry = taskArray.get(i);
    		input = input + "Description: " + tempEntry.getDescription() + "\n" + 
    				"Priority: " + tempEntry.getPriority() + "\n" + 
    				"Due Date: " + tempEntry.getDue() + "\n\n";
    	}
    	
    	task_text.setText(input);*/
    }
    
    /**
     * Takes in a task and adds it to the taskArray depending on it's priority location
     * Shifts everything and increments all the data based off the priority number in the list
     * 
     * @param task
     */
    public boolean addToList(taskEntry task)
    {
		boolean duplicate = false;
		for(taskEntry existingEntry : taskArray)
		{
			if(existingEntry.getDescription().equals(task.getDescription()))
				duplicate = true;
		}
		
		if(duplicate == true)
			return false;			//entry not added (description not unique)
		else
		{
			int newPriority = task.getPriority();

			for(taskEntry existingEntry : taskArray)		//increment existing priorities
			{
				if(task.getPriority() <= existingEntry.getPriority())
					existingEntry.incrementPriority();
			}
			if(task.getPriority() < 1)							//if priority is less than 1 set it to 1
				newPriority = 1;
			if(task.getPriority() > (taskArray.size() + 1))	
				newPriority = taskArray.size() + 1;
			
			task.setPriority(newPriority);
			
			taskArray.add(task);
			return true;				//task added
		}
    	
    	/*// If task list is empty auto priority to 1 and then add it to the start
    	if(taskArray.size() < 1)
    	{
    		task.setPriority(1);
    		taskArray.add(task);
    	}
    	else
    	{
    		int location = 0;
        	boolean priorityHit = false;
    		
    		//Checks to see if something has the same priority if so then change the location
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
    		
    		//Priority number already exists
    		if(priorityHit == true)
    		{
    			task.setPriority(taskArray.size());
    			taskArray.add(location, task);
    			
    			//Shifts all the priority number after the location of the added task up one
    			for(int tempNum = location; tempNum < taskArray.size(); tempNum ++)
    			{
    				taskArray.get(tempNum).setPriority(tempNum + 1);
    			}
    		}
    		else
    		{
    			//If task doesn't conflict with another priority add it to the end
    			task.setPriority(taskArray.size() + 1);
    			taskArray.add(task);
    		}
    	}*/
    }
    
	public void delete(String description)
	{
		int index = 0;
		
		while(!description.equals(taskArray.get(index).getDescription()))
			index++;
		
		int incrementIndex = index;
		while(incrementIndex <= taskArray.size())
			taskArray.get(incrementIndex).decrementPriority();
		
		taskArray.remove(index);
	}
	
	public boolean changeDescription(String currentDescription, String newDescription)
	{
		boolean duplicate = false;
		for(taskEntry existingEntry : taskArray) {
			if(existingEntry.getDescription().equals(newDescription))
				duplicate = true;
		}
		
		if(duplicate == false)
			return false;
		else {
			int index = 0;
			
			while(!currentDescription.equals(taskArray.get(index).getDescription()))
				index++;
			
			taskArray.get(index).setDescription(newDescription);
			return true;
		}
	}
}
