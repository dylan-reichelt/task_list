package cse360project;

public class Entry {
	private String description;
	private int priority;
	private String dueDate;
	private String status;
	private String statusDate;
	
	public Entry(String description, int priority, String dueDate) {
		this.description = description;
		this.priority = priority;
		this.dueDate = dueDate;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public void incrementPriority() {
		priority++;
	}
	
	public void decrementPriority() {
		priority--;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public String getDueDate() {
		return dueDate;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}
	
	public String getStatusDate() {
		return statusDate;
	}
	
	
}
