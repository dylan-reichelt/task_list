package app;

public class taskEntry {
	private String Description;
	private String dueDate;
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
	
	void setDue(String due)
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
	
	public String getDue()
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
