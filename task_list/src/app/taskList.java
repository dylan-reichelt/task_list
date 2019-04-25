package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	ArrayList<taskEntry> deletedTasks = new ArrayList<taskEntry>();
	
	public void deleteTask(int index) {
		for(int i = index; i < taskArray.size() - 1; i++) {
			taskArray.set(i, taskArray.get(i + 1));
			taskArray.get(i).setPriority(i + 1);
		}
		
		deletedTasks.add(taskArray.get(taskArray.size() - 1));
		taskArray.remove(taskArray.size() - 1);
	}

	
	/**
	 * Remove function for editing
	 * @param index
	 */
	public void removeTask(int index)
	{
		for(int i = index; i < taskArray.size() - 1; i++) {
			taskArray.set(i, taskArray.get(i + 1));
			taskArray.get(i).setPriority(i + 1);
		}
		
		taskArray.remove(taskArray.size() - 1);
	}
	
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
	System.out.println("Adding");
  	if(task.getNumericalStatus() == 2) {
  		deletedTasks.add(task);
  		return true;
  	}
  	
  	int location = 0;
  	boolean priorityHit = false;
  	
  	if(task.getPriority() < 1)
  		task.setPriority(1);
  	
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
  	System.out.println("Added file not valid");
  	return false;
  }
    
    /**
     * Clears the ListView of all the entries in the array list. Clears the array list itself and the ListView.
     * 
     * @param flag that indicates if 0, the list and file are being cleared, if 1, only the list is cleared 
     * @param task_text A ListView will be passed in order to clear the ListView's taskEntries
     */
    public void restartList(ListView<String> task_text, int restartOrLoad) {
    	task_text.getItems().clear();
    	taskArray.clear();
    	File existingTaskFile = new File(System.getProperty("user.home") 
				+ System.getProperty("file.separator") + "Downloads"
				+ System.getProperty("file.separator") + "tasklist.txt");
//    	if(restartOrLoad == 0) { // clear the task file if "restart" is chosen
//    		if(existingTaskFile.exists()) { // clears the file if it exists
//        		try {
//            		PrintWriter writer = new PrintWriter(existingTaskFile);
//            		writer.println("");
//            		writer.close();
//        		} catch(FileNotFoundException exception) {
//        			
//        		}
//        	}
//    	}
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
    	for(int listIndex = 0; listIndex < taskArray.size(); listIndex++)
    	{
    		// Writes the following string into the text file
    		taskEntry tempEntry = taskArray.get(listIndex);
    		String startDate;
    		if(tempEntry.getStart() != null) {
    			startDate = tempEntry.getStart().format(DateTimeFormatter.ofPattern("dd LLLL yyyy"));
    		}
    		else {
    			startDate = "not set";
    		}
    		textParse += "Active" + "// "
    				+ tempEntry.getDesc() + "// "
    				+ tempEntry.getPriority() + "// "
    				+ startDate + "// "
    				+ tempEntry.getDue().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")) + "// "
    				+ tempEntry.getStatus() + "// "
    				+ System.getProperty("line.separator");
    	}
    	String deleteParse = "";
    	for(int deleteIndex = 0; deleteIndex < deletedTasks.size(); deleteIndex++) {
    		taskEntry deleteEntry = deletedTasks.get(deleteIndex);
    		String startDate;
    		if(deleteEntry.getStart() != null) {
    			startDate = deleteEntry.getStart().format(DateTimeFormatter.ofPattern("dd LLLL yyyy"));
    		}
    		else {
    			startDate = "not set";
    		}
    		textParse += "Deleted" + "// "
    				+ deleteEntry.getDesc() + "// "
    				+ deleteEntry.getPriority() + "// "
    				+ startDate + "// "
    				+ deleteEntry.getDue().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")) + "// "
    				+ deleteEntry.getStatus() + "// "
    				+ System.getProperty("line.separator");
    	}
    	textParse += deleteParse;
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
     * Parses the selected file into entries that will be added to the task list if the "Load" button is 
     * clicked. The ListView's contents will be updated to display the loaded entries. If a valid file is
     *  not chosen, an error message will be displayed.
     * 
     * @param taskTable the list of tasks 
     */
    public void loadList(taskList taskTable) {
			JFileChooser chooser = new JFileChooser(); // allows user selection of documents
			chooser.setCurrentDirectory(new File(System.getProperty("user.home") // setting default directory to downloads folder
						+ System.getProperty("file.separator") + "Downloads"));
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt"); // apply text file filter
			chooser.setFileFilter(filter); 
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You opened " + chooser.getSelectedFile().getName());
					String path = chooser.getSelectedFile().getPath();
					File newTaskFile = new File(path);
					taskTable.loadTasks(taskTable, newTaskFile);
			}
    }

		/**
     * If there is currently a parsable file named "tasklist.txt" in the downloads directory of the user's 
     * computer, that file will be parsed into the task list as soon as the app is started.
     * 
     * @param taskTable pass the table that will be filled with tasks from tasklist.txt
     */
    public void autoLoadTasks(taskList taskTable) {
			File existingTaskFile = new File(System.getProperty("user.home") 
					+ System.getProperty("file.separator") + "Downloads"
					+ System.getProperty("file.separator") + "tasklist.txt");
				// if a file called "tasklist.txt" exists in the downloads directory and it is parsable,
				// that file will be loaded
			if(existingTaskFile.exists() && verifyParse(existingTaskFile) == true) {
				taskTable.loadTasks(taskTable, existingTaskFile);
				for(int i = 0; i < deletedTasks.size(); i++) {
					System.out.println(deletedTasks.get(i).getDesc());
				}
			}else {
				System.out.println("There is not file named tasklist.txt in the Downloads directory.");
			}
    }

		/**
     * Parses the tasklist.txt into the task array list. Uses values indicated from tasklist.txt
     * to create objects to load into the list. Tasks will either be added to deleted or active tasks.
     * 
     * @param taskTable passes the list that will be filled with the list from the text file
     * @param taskFile passes the file that will be checked if it is valid to parse, then values from
     * 					this text file will be used for the list.
     */
    private void loadTasks(taskList taskTable, File taskFile) {
			try {
				if(verifyParse(taskFile) == true) { // continue if the text file is valid
					BufferedReader br = new BufferedReader(new FileReader(taskFile)); 
					String read; 
							try {
								while ((read = br.readLine()) != null && !(read.equals(""))) {
									System.out.println("PARSING...");
									String [] parsedLine = read.split("// "); // split line into an array using , as a delimiter
									
									String activeOrDeleted = parsedLine[0]; // indicates whether to add to the deleted tasks or active tasks
									
									String description = parsedLine[1];
									
									int priority = Integer.parseInt(parsedLine[2]);
									
									LocalDate startDate;
									DateTimeFormatter format = DateTimeFormatter.ofPattern("dd LLLL yyyy");
									if(parsedLine[3].equals("not set")) { // set start date to null if it has not been assigned
										startDate = null;
									}else { // assign start date if it is assigned
										startDate = LocalDate.parse(parsedLine[3], format);
									}
									
									LocalDate endDate = LocalDate.parse(parsedLine[4], format);
									
									int status = 0;
									// Chooses which status to assign to the taskLoad object
									if(parsedLine[5].equals("Not Started")) { status = 0; }
									else if(parsedLine[5].equals("In Progress")) { status = 1; }
									else if(parsedLine[5].equals("Complete")) { status = 2; }
									else if(parsedLine[5].equals("Deleted")) { status = 3; }
									
									// Creates new taskEntry object with obtained variables
									taskEntry taskLoad = new taskEntry();
									taskLoad.setDesc(description);
									taskLoad.setPriority(priority);
									taskLoad.setStart(startDate);
									taskLoad.setDue(endDate);
									taskLoad.setStatus(status);
									if(activeOrDeleted.equals("Active")) {
										taskTable.addToList(taskLoad); // adds task to the list
									}else if(activeOrDeleted.equals("Deleted")) {
										deletedTasks.add(taskLoad);
									}
									
									
								}
							}catch(IOException exception) {
								System.out.println("IOException");
					}
			}else {
				System.out.println("Not valid file");
			}
			}catch(FileNotFoundException exception) {
				System.out.println("file not found");
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
    	for(int num = 0; num < deletedTasks.size(); num++)
    	{
    		taskEntry tempTask = deletedTasks.get(num);
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
     * @return valid this boolean value will return true if the file is valid to parse, false if invalid.
     */
    private boolean verifyParse(File file) {
    	// flags to indicate invalid text file
    	boolean invalidLength = false;
    	boolean invalidPriority = false;
    	boolean invalidStartDate = false;
    	boolean invalidDueDate = false;
    	boolean invalidStatus = false;
    	boolean validFile = true;
    	
    	try {
    		BufferedReader parseReader = new BufferedReader(new FileReader(file));
    		String read; // string that will hold current string being read from the file
    		try {
    			if(parseReader.readLine() != null) {
        			while ((read = parseReader.readLine()) != null && !(read.equals(""))) {
        				String [] parsedLine = read.split("// ");
        				// DESCRIPTION
        				if(parsedLine.length == 6) {
            				if(parsedLine[0] == null) { // shows empty text file
            					invalidLength = true;
            					System.out.println("The file is empty.");
            				}else {
            					System.out.println("The file is not empty.");
            				}
            				
            				// PRIORITY NUMBER
            				if(parsedLine[2].length() > 0) { // checks if the priority number is not empty
            					try {
            						Integer.parseInt(parsedLine[2]); // checks if the second string can be parsed into an int
            					}
            					catch(NumberFormatException error) {
            						invalidPriority = true; // turns true if the number cannot be parsed
            						System.out.println("Invalid priority number.");
            					}
            				}else { // catches error if there is no priority
            					invalidPriority = true;
            				}
            				
            				// STARTDATE
            				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            				if(parsedLine[3].length() > 0) { // checks if the start date is not empty
            					if(parsedLine[3].equals("not set")) {
            						// Proceed if the task does not have a start date
            					}else {
            						try {
                						LocalDate.parse(parsedLine[3], format); // checks if third string can be parsed into a LocalDate
                					}
                					catch(DateTimeParseException error) {
                						invalidStartDate = true; // true if the string can't be parsed into a local date
                						System.out.println("Invalid start date.");
                					}
            					}
            				}else {
            					invalidStartDate = true; // flags true if the start date is empty
            				}
            				
            				// ENDDATE
            				if(parsedLine[4].length() > 0) { // checks if the due date is not empty
            					try {
            						LocalDate.parse(parsedLine[4], format); // checks if third string can be parsed into a LocalDate
            					}
            					catch(DateTimeParseException error) {
            						invalidDueDate = true; // true if the string can't be parsed
            						System.out.println("Invalid due date.");
            					}
            				}else {
            					invalidDueDate = true; // flags true if the end date is empty
            				}
            				
            				// STATUS
            				System.out.println(parsedLine[5]);
            				if(parsedLine[5].length() > 0) { // checks if fourth string is a valid status
            					if( (parsedLine[5].equals("Not Started")) == false && (parsedLine[5].equals("In Progress") == false) 
            							&& (parsedLine[5].equals("Complete")) == false && (parsedLine[5].equals("Deleted") == false)) { // checks if fourth string is a valid status
            						invalidStatus = true; // true if the status is not one of these strings
            						System.out.println("Invalid status.");
            					}
            				}else {
            					invalidStatus = true;  // flags true if the status is empty
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
    	}catch(FileNotFoundException exception) {
    		
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
    		validFile = false;
    	}else {
    		validFile = true;
    	}
    	return validFile; // if no flags are true, proceed
    	
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

