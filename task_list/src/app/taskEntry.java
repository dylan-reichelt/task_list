package app;

import java.time.LocalDate;

public class taskEntry {
	private String Description;
	private LocalDate dueDate;
	private LocalDate startDate;
	
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
	
	public void setStatus(int tempStatus)
	{
		status = tempStatus;
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
	
	public String getStatus()
	{
		if(status == 0)
		{
			return "Not Started";
		}
		else if(status == 1)
		{
			return "Started";
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
	
	public void decrementPriority()
	{
		priority--;
	}
	
	public void incrementPriority()
	{
		priority++;
	}
}