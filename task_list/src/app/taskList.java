package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
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
    
    /**
     * Clears the ListView of all the entries in the array list. Clears the array list itself and the ListView.
     * 
     * @param task_text A ListView will be passed in order to clear the ListView's taskEntries
     */
    public void restartList(ListView<String> task_text) {
    	task_text.getItems().clear();
    	taskArray.clear();
    }
    
    /**
     * Adds a task to the list taskArray. If it is added returns true. If it is not a valid task then it returns false and won't add it
     * to the list. This adds to the list according to priority.
     * 
     * @return taskArray.size() Gives the number of taskEntries in the taskArray array list
     */
    public int getSize() {
    	return taskArray.size();
    }
    
    /**
     * Parses the current entries in the array list to a String format. The format it is written
     * in is description, priority number, start date, due date, and status.
     * 
     * @return textParse returns a String that will be written to a text file in order to be parsed into
     * 		   the task list at a later point in time	
     */
    public String parseList() {
    	String textParse = "";
    	for(int i = 0; i < taskArray.size(); i++)
    	{
    		// Writes the following string into the text file
    		taskEntry tempEntry = taskArray.get(i);
    		textParse += tempEntry.getDesc() + ", "
    				+ tempEntry.getPriority() + ", "
    				+ tempEntry.getDue().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")) + ", "
    				+ tempEntry.getDue().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")) + ", "
    				+ tempEntry.getStatus() + ", "
    				+ System.getProperty("line.separator");
    	} 
    	return textParse;
    }
    
    /**
     * Saves the taskList's entries to a new text file called "TaskList.txt". The entries are parsed 
     * into a certain format to load later. The file "TaskList.txt" will be saved to the user's 
     * Downloads directory.
     * 
     * @param taskTable passes the list of tasks to parse and write current entries to a text file	
     */
    public void saveList(taskList taskTable) {
    	try {
    		String home = System.getProperty("user.home");
    		String fileName = "tasklist";
    		// Saves text file to Downloads directory
    		File file = new File(home + "/Downloads/" + fileName + ".txt");
        	PrintWriter pw = new PrintWriter(file);
        	pw.println(taskTable.parseList());
        	System.out.println(fileName + ".txt" + "saved at " + home + "/Downloads/" + fileName + ".txt!");
        	pw.close();
        } catch (FileNotFoundException e){
        	e.printStackTrace();
        }
    }
   
    /**
     * Parses the selected file into entries that will be added to the task list. The ListView's 
     * contents will be updated to display the loaded entries. If a valid file is not chosen, an
     * error message will be displayed.
     * 
     * @param 
     * @return textParse returns a String that will be written to a text file in order to be parsed into
     * 		   the task list at a later point in time	
     */
    public void loadList(taskList taskTable) {
    	ArrayList<taskEntry> taskArray = new ArrayList<taskEntry>();
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.home") // setting default directory to downloads folder
				+ System.getProperty("file.separator") + "Downloads"));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt"); // apply text file filter
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You opened " + chooser.getSelectedFile().getName());
			String filename = chooser.getSelectedFile().getName();
			String path = chooser.getSelectedFile().getPath();
			try {
    			File file = new File(path);
    			if(verifyParse(file) == true) {
    				BufferedReader br = new BufferedReader(new FileReader(file)); 
            		String read; 
            		try {
            			while ((read = br.readLine()) != null && !(read.equals(""))) {
            				System.out.println("PARSING...");
            				String [] parse = read.split(", ");
            				
            				String description = parse[0];
            				System.out.println("description = " + description);
            				
            				int priority = Integer.parseInt(parse[1]);
            				System.out.println("priority = " + priority);
            				
            				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            				LocalDate startDate = LocalDate.parse(parse[2], format);
            				System.out.println("start date = " + startDate);
            				
            				LocalDate endDate = LocalDate.parse(parse[3], format);
            				System.out.println("end date = " + endDate);
            				
            				int status = 0;
            				if(parse[4].equals("Not Started")) {
            					status = 0;
            				}
            				else if(parse[4].equals("In Progress")) {
            					status = 1;
            				}
            				else if(parse[4].equals("Complete")) {
            					status = 2;
            				}
            				else if(parse[4].equals("Deleted")) {
            					status = 3;
            				}
            				System.out.println("status = " + status);
            				
            				taskEntry taskLoad = new taskEntry();
            				taskLoad.setDesc(description);
            				taskLoad.setPriority(priority);
            				taskLoad.setStart(startDate);
            				taskLoad.setDue(startDate);
            				taskLoad.setStatus(status);
            				
            				taskTable.addToList(taskLoad);
            				
            			}
            		}catch(IOException e) {
        			
        		}
    		}
    		}catch(FileNotFoundException e) {
    			
    		}
		}
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
    
    /**
     * This method will accept a file parameter and check its contents to see if the strings
     * can be parsed into objects for the taskList.
     * 
     * @param file this file variable will be passed to check its parsing viability
     * @return boolean this boolean value will return true if the file is valid to parse, false if invalid.
     */
    private boolean verifyParse(File file) {
    	boolean invalidLength = false;
    	boolean invalidPriority = false;
    	boolean invalidStartDate = false;
    	boolean invalidDueDate = false;
    	boolean invalidStatus = false;
    	
    	try {
    		BufferedReader parseReader = new BufferedReader(new FileReader(file));
    		System.out.println(file.getName());
    		String read;
    		try {
    			if(parseReader.readLine() != null) {
        			while ((read = parseReader.readLine()) != null && !(read.equals(""))) {
        				String [] parsedLine = read.split(", ");
        				if(parsedLine.length == 5) {
            				if(parsedLine[0] == null) { // shows empty text file
            					invalidLength = true;
            					System.out.println("The file is empty.");
            				}else {
            					System.out.println("The file is not empty.");
            				}
            				
            				if(parsedLine[1].length() > 0) { // checks if the second string can be parsed into an int
            					try {
            						Integer.parseInt(parsedLine[1]);
            					}
            					catch(NumberFormatException error) {
            						invalidPriority = true; // turns true if the number is false
            						System.out.println("Invalid priority number.");
            					}
            				}else {
            					invalidPriority = true;
            				}
            				
            				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            				if(parsedLine[2].length() > 0) { // checks if third string can be parsed into a LocalDate
            					try {
            						LocalDate.parse(parsedLine[2], format);
            					}
            					catch(DateTimeParseException error) {
            						invalidStartDate = true; // true if the string can't be parsed
            						System.out.println("Invalid start date.");
            					}
            				}else {
            					invalidStartDate = true;
            				}
            				
            				if(parsedLine[3].length() > 0) { // checks if third string can be parsed into a LocalDate
            					try {
            						LocalDate.parse(parsedLine[3], format);
            					}
            					catch(DateTimeParseException error) {
            						invalidDueDate = true; // true if the string can't be parsed
            						System.out.println("Invalid due date.");
            					}
            				}else {
            					invalidDueDate = true;
            				}
            				
            				if(parsedLine[4].length() > 0) { // checks if fourth string is a valid status
            					if( (parsedLine[4].equals("Not Started")) == false && (parsedLine[4].equals("In Progress") == false) 
            							&& (parsedLine[4].equals("Complete")) == false && (parsedLine[4].equals("Deleted") == false)) {
            						invalidStatus = true; // true if the status is not one of these strings
            						System.out.println("Invalid status.");
            					}
            				}else {
            					invalidStatus = true; 
            				}
        				}
        				else {
        					invalidLength = true; // true if the line cannot be parsed into a String array with 5 elements
        					System.out.println("The file is invalid.");
        				}
      
        			}
    			}
    			else {
    				invalidLength = true; // true if the file is empty
    				System.out.println("Invalid. The file is empty");
    			}

    		}catch(IOException exception) {
    			
    		}
    	}catch(FileNotFoundException e) {
    		
    	}
    	
    	// if any of the invalid flags are true, return false
    	if(invalidLength || invalidPriority ||	
    			invalidStartDate || invalidDueDate ||
    			invalidStatus) {
    		// notification for invalid text file.
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error - Invalid text file");
			alert.setHeaderText(null);
			alert.setContentText("Invalid text file. Please load the file initially saved as tasklist.txt.");
			alert.showAndWait();
    		return false;
    	}
    	return true; // if no flags are true, proceed
    	
    }
    
    /**
     * Will return the array number of an item based off the priority -1 (which is the index in the actual array). This
     * will allow for edit and other things to access a task when it's not in the priority order in the filter.
     * the string must be in the format:
     * 
     *  Description: Hm
	 *	Priority: 1
	 *	Start Date: NA
	 *	Due Date: 2019-04-27
	 *	Status: Not Started
     * 
     * @param input
     * @return int
     */
	public int stringToPriority(String input)
	{
		String lines[] = input.split("\\r?\\n");
		String prioritySplit[] = lines[1].split(" ");
		int priorityInt = Integer.parseInt(prioritySplit[1]);
		return priorityInt;
	}
	
    /**
     * Returns a taskEntry in the taskArray to allow something to grab it
     * 
     * @param arrayNum
     * @return taskEntry
     */
    public taskEntry getTask(int arrayNum)
    {
    	return taskArray.get(arrayNum);
    }
}
