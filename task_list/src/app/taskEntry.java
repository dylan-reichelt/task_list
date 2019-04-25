/**
 * Authors: Ignatius Akeeh, Kyle Gonzalez, Jackson Lewis, Dylan Reichelt
 * Team Project
 * taskEntry.java
 * This class is the task entry object that stores information about the task
 */

package app;

import java.time.LocalDate;

public class taskEntry
{
	// CLASS VARIABLES
	private String description;
	private LocalDate dueDate;
	private LocalDate startDate;
	private LocalDate completeDate;
	private int status = 0;
	private int priority;
	
	/**
	 * Set Description
	 * @param newDescription
	 */
	public void setDesc(String newDescription)
	{
		description = newDescription;
	}
	
	/**
	 * Set Priority
	 * @param newPriority
	 */
	public void setPriority(int newPriority)
	{
		priority = newPriority;
	}
	
	/**
	 * Set Start Date
	 * @param newStart
	 */
	public void setStart(LocalDate newStart)
	{
		startDate = newStart;
	}
	
	/**
	 * Set Due Date
	 * @param newDueDate
	 */
	public void setDue(LocalDate newDueDate)
	{
		dueDate = newDueDate;
	}
	
	/**
	 * Set date task was completed
	 * @param newComplete
	 */
	public void setCompleteDate(LocalDate newComplete)
	{
		completeDate = newComplete;
	}
	
	/**
	 * Set Status using integer
	 * @param newStatus
	 */
	public void setStatus(int newStatus)
	{
		status = newStatus;
	}
	
	/**
	 * Set Status using string
	 * @param newStatus
	 */
	public void setStatus(String newStatus)
	{
		switch(newStatus)
		{
		case "Not Started":
			status = 0;
			break;
		case "In Progress":
			status = 1;
			break;
		case "Complete":
			status = 2;
			break;
		case "Deleted":
			status = 3;
			break;
		}
	}
	
	/**
	 * Returns task description
	 * @return
	 */
	public String getDesc()
	{
		return description;
	}
	
	/**
	 * Returns task priority
	 * @return
	 */
	public int getPriority()
	{
		return priority;
	}
	
	/**
	 * Returns task due date
	 * @return
	 */
	public LocalDate getDue()
	{
		return dueDate;
	}
	
	/**
	 * Returns task start date
	 * @return
	 */
	public LocalDate getStart()
	{
		return startDate;
	}
	
	/**
	 * Returns task completed date
	 * @return
	 */
	public LocalDate getCompleteDate()
	{
		return completeDate;
	}
	
	/**
	 * Returns task status as a string
	 * @return
	 */
	public String getStatus()
	{
		if(status == 0)
		{
			return "Not Started";
		}
		else if(status == 1)
		{
			return "In Progress";
		}
		else if(status == 2)
		{
			return "Complete";
		}
		else if(status == 3)
		{
			return "Deleted";
		}
		return "Not Started";
	}
	
	/**
	 * Returns task status as an integer
	 * @return
	 */
	public int getNumericalStatus()
	{
		return status;
	}
	
	/**
	 * Returns formated string of task information
	 * @return
	 */
	public String getTaskPrint()
	{
		String tempString = "Description: " + getDesc() + "\n" +
				"Priority: " + getPriority();
				
		
		if(getStart() == null)
		{
			tempString = tempString + "\nStart Date: NA";
		}
		else
		{
			tempString = tempString + "\nStart Date: " + getStart();
		}
		
		tempString = tempString + "\nDue Date: " + getDue() + "\n" +
				"Status: " + getStatus();
		
		return tempString;
	}
}