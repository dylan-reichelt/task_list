package app;

import java.time.LocalDate;

public class taskEntry {
	private String Description;
	private LocalDate dueDate;
	private String startDate;
	
	private int status;
	private int priority;
	
	void setDesc(String description)
	{
		Description = description;
	}
	
	void setPriority(int tempPrio)
	{
		priority = tempPrio;
	}
	
	void setStart(String start)
	{
		startDate = start;
	}
	
	void setDue(LocalDate due)
	{
		dueDate = due;
	}
	
	void setStatus(int tempStatus)
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
