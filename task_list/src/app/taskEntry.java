package app;

import java.time.LocalDate;

public class taskEntry {
	private String Description;
	private LocalDate dueDate;
	private LocalDate startDate;
	private LocalDate completeDate;
	
	private int status = 0;
	private int priority;
	
	public void setDesc(String description)
	{
		Description = description;
	}
	
	public void setPriority(int tempPrio)
	{
		priority = tempPrio;
	}
	
	public void setStart(LocalDate start)
	{
		startDate = start;
	}
	
	public void setDue(LocalDate due)
	{
		dueDate = due;
	}
	
	public void setCompleteDate(LocalDate complete)
	{
		completeDate = complete;
	}
	
	public void setStatus(int tempStatus)
	{
		status = tempStatus;
	}
	
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
	
	public String getDesc()
	{
		return Description;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	public LocalDate getDue()
	{
		return dueDate;
	}
	
	public LocalDate getStart()
	{
		return startDate;
	}
	
	public LocalDate getCompleteDate()
	{
		return completeDate;
	}
	
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
	
	public int getNumericalStatus()
	{
		return status;
	}
	
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
