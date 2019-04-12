package app;

import java.time.LocalDate;

public class taskEntry {
	private String Description;
	private LocalDate dueDate;
	private String startDate;
	
	private int status;
	private int priority;
	
	public void setDesc(String description)
	{
		Description = description;
	}
	
	public void setPriority(int tempPrio)
	{
		priority = tempPrio;
	}
	
	public void setStart(String start)
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
	
	public String getStart()
	{
		return startDate;
	}
	
	public int getStatus()
	{
		return status;
	}
}
