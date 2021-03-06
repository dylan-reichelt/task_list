/**
 * Authors: Ignatius Akeeh, Kyle Gonzalez, Jackson Lewis, Dylan Reichelt
 * Team Project
 * taskList.java
 * This class manages a list of of task entries
 */

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

public class taskList
{
	// CLASS VARIABLES
	ArrayList<taskEntry> taskArray = new ArrayList<taskEntry>();
	ArrayList<taskEntry> deletedTasks = new ArrayList<taskEntry>();
	
	/**
	 * Deletes the task at deletedIndex
	 * @param deletedIndex is index of task to be deleted
	 */
	public void deleteTask(int deleteIndex)
	{
		//Keeping elements sorted by priority
		deletedTasks.add(taskArray.get(deleteIndex));
		for(int index = deleteIndex; index < taskArray.size() - 1; index++) {
			taskArray.set(index, taskArray.get(index + 1));
			taskArray.get(index).setPriority(index + 1);
		}
		
		taskArray.remove(taskArray.get(deleteIndex));
	}

	
	/**
	 * Removes entry at removeIndex without adding it to deleted array (Used
	 * for editing only)
	 * @param removeIndex is index of task that is being edited
	 */
	public void removeTask(int removeIndex)
	{
		for(int index = removeIndex; index < taskArray.size() - 1; index++) {
			taskArray.set(index, taskArray.get(index + 1));
			taskArray.get(index).setPriority(index + 1);
		}
		
		taskArray.remove(taskArray.size() - 1);
	}
	
	/**
	 * Outputs taskList tasks to task_text. They are sorted based on current
	 * sort value
	 * @param task_text list of tasks used in GUI
	 * @param sortValue current value on sort drop down
	 */
  public void refreshList(ListView<String> task_text, String sortValue)
  {
  	
  		taskEntry[] sortedArray = new taskEntry[taskArray.size()];
		int inserted;
		
		switch(sortValue)
		{
		case "Description":
			//Sort by Description
			
			if(taskArray.size() != 0)
			{
				sortedArray[0] = taskArray.get(0);
			}
			inserted = 1;
			
			for(int taskIndex = 1; taskIndex < sortedArray.length; taskIndex++)
			{
				int sortedIndex = 0;
				while(sortedIndex < inserted && taskArray.get(taskIndex)
						.getDesc().compareTo(sortedArray[sortedIndex]
								.getDesc()) > 0)
				{
					sortedIndex++;
				}
				
				for(int insertIndex = inserted; insertIndex > sortedIndex; 
						insertIndex--)
				{
					sortedArray[insertIndex] = sortedArray[insertIndex - 1];
				}
				
				sortedArray[sortedIndex] = taskArray.get(taskIndex);
				inserted++;
			}
			
			break;
		case "Due Date":
			//Sort by Due Date
			
			if(taskArray.size() != 0)
			{
				sortedArray[0] = taskArray.get(0);
			}
			inserted = 1;
			
			for(int taskIndex = 1; taskIndex < sortedArray.length; taskIndex++)
			{
				int sortedIndex = 0;
				while(sortedIndex < inserted && taskArray.get(taskIndex)
						.getDue().compareTo(sortedArray[sortedIndex].getDue())
						> 0)
				{
					sortedIndex++;
				}
				
				for(int insertIndex = inserted; insertIndex > sortedIndex;
						insertIndex--)
				{
					sortedArray[insertIndex] = sortedArray[insertIndex - 1];
				}
				
				sortedArray[sortedIndex] = taskArray.get(taskIndex);
				inserted++;
			}
			
			break;
		case "Status":
			//Sort by Status
			
			if(taskArray.size() != 0)
			{
				sortedArray[0] = taskArray.get(0);
			}
			inserted = 1;
			
			for(int taskIndex = 1; taskIndex < sortedArray.length; taskIndex++)
			{
				int sortedIndex = 0;
				while(sortedIndex < inserted && taskArray.get(taskIndex)
						.getNumericalStatus() > (sortedArray[sortedIndex]
								.getNumericalStatus()))
				{
					sortedIndex++;
				}
				
				for(int insertIndex = inserted; insertIndex > sortedIndex;
						insertIndex--)
				{
					sortedArray[insertIndex] = sortedArray[insertIndex - 1];
				}
				
				sortedArray[sortedIndex] = taskArray.get(taskIndex);
				inserted++;
			}

			break;
		default:
			//Sort by Priority by default
			
			if(taskArray.size() != 0)
			{
				sortedArray[0] = taskArray.get(0);
			}
			inserted = 1;
			
			for(int taskIndex = 1; taskIndex < sortedArray.length; taskIndex++)
			{
				int sortedIndex = 0;
				while(sortedIndex < inserted && taskArray.get(taskIndex)
						.getPriority() > (sortedArray[sortedIndex]
								.getPriority()))
				{
					sortedIndex++;
				}
				
				for(int insertIndex = inserted; insertIndex > sortedIndex;
						insertIndex--)
				{
					sortedArray[insertIndex] = sortedArray[insertIndex - 1];
				}
				
				sortedArray[sortedIndex] = taskArray.get(taskIndex);
				inserted++;
			}

			break;
		}
  	
  	//Add items from sorted array to task_text
  	task_text.getItems().clear();
  	for(int index = 0; index < sortedArray.length; index++)
  	{
  		taskEntry tempEntry = sortedArray[index];
  		String input = tempEntry.getTaskPrint();
  		task_text.getItems().add(input);
  	} 
  }
    
  /**
   * Adds a task to the list taskArray. If it is added returns true. If it is
   *  not a valid task then it returns false and won't add it to the list.
   *  This adds to the list according to priority.
   * 
   * @param task is task to be added
   * @return boolean
   */
  public boolean addToList(taskEntry task)
  {
	//If task is Completed
  	if(task.getNumericalStatus() == 2)
  	{
  		deletedTasks.add(task);
  		return true;
  	}
  	
  	int location = 0;
  	boolean priorityHit = false;
  	
  	//Set negative or zero priorities to 1
  	if(task.getPriority() < 1)
  	{
  		task.setPriority(1);
  	}
  	
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
  					priorityHit = true;
  					location = tempNum;
  				}
  			}
  		
  			if(priorityHit == true)
  			{
  				task.setPriority(taskArray.size());
  				taskArray.add(location, task);
  			
  				for(int tempNum = location; tempNum < taskArray.size();
  						tempNum ++)
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
   * Clears the ListView of all the entries in the array list. Clears the
   * array list itself and the ListView.
   * 
   * @param task_text A ListView will be passed in order to clear the
   * ListView's taskEntries
   */
    public void restartList(ListView<String> task_text, int restartOrLoad)
    {
    	task_text.getItems().clear();
    	taskArray.clear();
    	deletedTasks.clear();
    	File existingTaskFile = new File(System.getProperty("user.home") 
				+ System.getProperty("file.separator") + "Downloads"
				+ System.getProperty("file.separator") + "tasklist.txt");
    	if(restartOrLoad == 0) {
    		// clear the task file if "restart" is chosen
    		if(existingTaskFile.exists()) {
    			// clears the file if it exists
        		try
        		{
            		PrintWriter writer = new PrintWriter(existingTaskFile);
            		writer.println("");
            		writer.close();
        		} 
        		catch(FileNotFoundException exception)
        		{
        			
        		}
        	}
    	}
    }
    
    /**
     * Adds a task to the list taskArray. If it is added returns true. If it is
     * not a valid task then it returns false and won't add it to the list.
     * This adds to the list according to priority.
     * 
     * @return taskArray.size() Gives the number of taskEntries in the
     * taskArray array list
     */
    public int getSize()
    {
    	return taskArray.size();
    }
    
    /**
     * Returns the size of deleted/completed array
     */
    public int deleteSize()
    {
    	return deletedTasks.size();
    }
    
    /**
     * Parses the current entries in the array list to a String format. The
     * format it is written in is description, priority number, start date, due
     * date, and status.
     * 
     * @return textParse returns a String that will be written to a text file
     * in order to be parsed into the task list at a later point in time	
     */
    public String parseList()
    {
    	String textParse = "";
    	
    	for(int listIndex = 0; listIndex < taskArray.size(); listIndex++)
    	{
    		// Writes the following string into the text file
    		taskEntry tempEntry = taskArray.get(listIndex);
    		String startDate;
    		if(tempEntry.getStart() != null)
    		{
    			startDate = tempEntry.getStart().format(DateTimeFormatter
    					.ofPattern("dd LLLL yyyy"));
    		}
    		else
    		{
    			startDate = "not set";
    		}
    		textParse += "Active" + "// "
    				+ tempEntry.getDesc() + "// "
    				+ tempEntry.getPriority() + "// "
    				+ startDate + "// "
    				+ tempEntry.getDue().format(DateTimeFormatter.ofPattern(
    						"dd LLLL yyyy")) + "// "
    				+ tempEntry.getStatus() + "// "
    				+ System.getProperty("line.separator");
    	}
    	String deleteParse = "";
    	for(int deleteIndex = 0; deleteIndex < deletedTasks.size();
    			deleteIndex++)
    	{
    		taskEntry deleteEntry = deletedTasks.get(deleteIndex);
    		String startDate;
    		if(deleteEntry.getStart() != null)
    		{
    			startDate = deleteEntry.getStart().format(DateTimeFormatter
    					.ofPattern("dd LLLL yyyy"));
    		}
    		else
    		{
    			startDate = "not set";
    		}
    		
    		textParse += "Deleted" + "// "
    				+ deleteEntry.getDesc() + "// "
    				+ deleteEntry.getPriority() + "// "
    				+ startDate + "// "
    				+ deleteEntry.getDue().format(DateTimeFormatter.ofPattern(
    						"dd LLLL yyyy")) + "// "
    				+ deleteEntry.getStatus() + "// "
    				+ System.getProperty("line.separator");
    	}
    	
    	textParse += deleteParse;
    	return textParse;
    }
    
    /**
     * Saves the taskList's entries to a new text file called "TaskList.txt".
     * The entries are parsed into a certain format to load later. The file
     * "TaskList.txt" will be saved to the user's Downloads directory.
     * 
     * @param taskTable passes the list of tasks to parse and write current
     * entries to a text file	
     */
    public void saveList(taskList taskTable)
    {
    	try
    	{
    		String home = System.getProperty("user.home");
    		String fileName = "tasklist";
    		// Saves text file to Downloads directory
    		File file = new File(home + "/Downloads/" + fileName + ".txt");
        	PrintWriter pw = new PrintWriter(file);
        	pw.println(taskTable.parseList());
        	
        	pw.close();
        }
    	catch (FileNotFoundException e)
    	{
        	e.printStackTrace();
        }
    }
   
    /**
     * Parses the selected file into entries that will be added to the task
     * list. The ListView's contents will be updated to display the loaded
     * entries. If a valid file is not chosen, an error message will be
     * displayed.
     * 
     * @param 
     * @return textParse returns a String that will be written to a text file
     * in order to be parsed into the task list at a later point in time	
     */
    public void loadList(taskList taskTable)
    {
    		// allows user selection of documents
			JFileChooser chooser = new JFileChooser();
			
			// setting default directory to downloads folder
			chooser.setCurrentDirectory(new File(System.getProperty(
					"user.home")
						+ System.getProperty("file.separator") + "Downloads"));
			
			// apply text file filter
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					".txt", "txt");
			chooser.setFileFilter(filter); 
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
					String path = chooser.getSelectedFile().getPath();
					File newTaskFile = new File(path);
					taskTable.loadTasks(taskTable, newTaskFile);
			}
    }

		/**
     * If there is currently a parsable file named "tasklist.txt" in the
     * downloads directory of the user's computer, that file will be parsed
     * into the task list as soon as the app is started.
     * 
     * @param taskTable pass the table that will be filled with tasks from
     * tasklist.txt
     */
    public void autoLoadTasks(taskList taskTable)
    {
			File existingTaskFile = new File(System.getProperty("user.home")
					+ System.getProperty("file.separator") + "Downloads"
					+ System.getProperty("file.separator") + "tasklist.txt");
				// if a file called "tasklist.txt" exists in the downloads
				//	directory and it is parsable,
				// that file will be loaded
			if(existingTaskFile.exists() && verifyParse(existingTaskFile)
					== true)
			{
				taskTable.loadTasks(taskTable, existingTaskFile);
				for(int i = 0; i < deletedTasks.size(); i++)
				{
					
				}
			}
			else
			{
				
			}
    }

	/**
     * Parses the tasklist.txt into the task array list. Uses values indicated
     * from tasklist.txt to create objects to load into the list. Tasks will
     * either be added to deleted or active tasks.
     * 
     * @param taskTable passes the list that will be filled with the list from
     * the text file
     * @param taskFile passes the file that will be checked if it is valid to
     * parse, then values from this text file will be used for the list.
     */
    private void loadTasks(taskList taskTable, File taskFile)
    {
			try
			{
				if(verifyParse(taskFile) == true)
				{ 
					// continue if the text file is valid
					BufferedReader br = new BufferedReader(
							new FileReader(taskFile)); 
					String read; 
							try
							{
								while ((read = br.readLine()) != null && 
										!(read.equals("")))
								{
									// split line into an array using , as a
									//	delimiter
									String [] parsedLine = read.split("// ");
									
									// indicates whether to add to the deleted
									//	tasks or active tasks
									String activeOrDeleted = parsedLine[0]; 
									
									String description = parsedLine[1];
									
									int priority = Integer.parseInt(
											parsedLine[2]);
									
									LocalDate startDate;
									DateTimeFormatter format = 
											DateTimeFormatter.ofPattern(
													"dd LLLL yyyy");
									if(parsedLine[3].equals("not set"))
									{
										// set start date to null if it has not
										//	been assigned
										startDate = null;
									}
									else 
									{
										// assign start date if it is assigned
										startDate = LocalDate.parse(
												parsedLine[3], format);
									}
									
									LocalDate endDate = LocalDate.parse(
											parsedLine[4], format);
									
									int status = 0;
									// Chooses which status to assign to the
									//	taskLoad object
									if(parsedLine[5].equals("Not Started"))
									{ 
										status = 0;
									}
									else if(parsedLine[5].equals("In Progress"))
									{
										status = 1;
									}
									else if(parsedLine[5].equals("Complete"))
									{ 
										status = 2;
									}
									else if(parsedLine[5].equals("Deleted"))
									{
										status = 3;
									}
									
									// Creates new taskEntry object with
									//	obtained variables
									taskEntry taskLoad = new taskEntry();
									taskLoad.setDesc(description);
									taskLoad.setPriority(priority);
									taskLoad.setStart(startDate);
									taskLoad.setDue(endDate);
									taskLoad.setStatus(status);
									if(activeOrDeleted.equals("Active"))
									{
										taskTable.addToList(taskLoad);
										// adds task to the list
									}
									else if(activeOrDeleted.equals("Deleted"))
									{
										deletedTasks.add(taskLoad);
									}
									
									
								}
							}
							catch(IOException exception)
							{

							}
				}
				else
				{
					
				}
			}
			catch(FileNotFoundException exception)
			{

			}
    }

    
    /**
     * This takes in a task that wants to be added. Checks to make sure that
     * the description doesn't already exist, that the desc or due aren't null.
     * If they are then it returns false
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
     * This method will accept a file parameter and check its contents to see
     * if the strings can be parsed into objects for the taskList.
     * 
     * @param file this file variable will be passed to check its parsing 
     * viability
     * @return valid this boolean value will return true if the file is valid
     * to parse, false if invalid.
     */
    private boolean verifyParse(File file)
    {
    	// flags to indicate invalid text file
    	boolean invalidLength = false;
    	boolean invalidPriority = false;
    	boolean invalidStartDate = false;
    	boolean invalidDueDate = false;
    	boolean invalidStatus = false;
    	boolean validFile = true;
    	
    	try
    	{
    		BufferedReader parseReader = new BufferedReader
    				(new FileReader(file));
    		// string that will hold current string being read from the file
    		String read; 
    		try
    		{
    			if(parseReader.readLine() != null)
    			{
        			while ((read = parseReader.readLine()) != null &&
        					!(read.equals("")))
        			{
        				String [] parsedLine = read.split("// ");
        				// DESCRIPTION
        				if(parsedLine.length == 6)
        				{
            				if(parsedLine[0] == null)
            				{
            					// shows empty text file
            					invalidLength = true;

            				}
            				else
            				{

            				}
            				
            				// PRIORITY NUMBER
            				if(parsedLine[2].length() > 0)
            				{
            					// checks if the priority number is not empty
            					try
            					{
            						// checks if the second string can be
            						//	parsed into an int
            						Integer.parseInt(parsedLine[2]); 
            					}
            					catch(NumberFormatException error)
            					{
            						// turns true if the number cannot be parsed
            						invalidPriority = true;
            					}
            				}
            				else
            				{ // catches error if there is no priority
            					invalidPriority = true;
            				}
            				
            				// STARTDATE
            				DateTimeFormatter format = DateTimeFormatter
            						.ofPattern("dd LLLL yyyy");
            				if(parsedLine[3].length() > 0)
            				{ 
            					// checks if the start date is not empty
            					if(parsedLine[3].equals("not set"))
            					{
            						// Proceed if the task does not have a
            						//	start date
            					}
            					else
            					{
            						try
            						{
            							// checks if third string can be parsed
            							//	into a LocalDate
                						LocalDate.parse(parsedLine[3], format);
                					}
                					catch(DateTimeParseException error)
            						{
                						// true if the string can't be parsed
                						//	into a local date
                						invalidStartDate = true;
                					}
            					}
            				}
            				else
            				{
            					// flags true if thestart date is empty
            					invalidStartDate = true;
            				}
            				
            				// ENDDATE
            				if(parsedLine[4].length() > 0)
            				{ // checks if the due date is not empty
            					try
            					{
            						// checks if third string can be parsed
            						//	into a LocalDate
            						LocalDate.parse(parsedLine[4], format); 
            					}
            					catch(DateTimeParseException error)
            					{
            						// true if the string can't be parsed
            						invalidDueDate = true; 
            					}
            				}
            				else
            				{
            					// flags true if the end date is empty
            					invalidDueDate = true;
            				}
            				
            				// STATUS
            				if(parsedLine[5].length() > 0)
            				{ // checks if fourth string is a valid status
            					if( (parsedLine[5].equals("Not Started"))
            							== false && (parsedLine[5].equals(
            									"In Progress") == false) && 
            							(parsedLine[5].equals("Complete"))
            							== false && (parsedLine[5].equals(
            									"Deleted") == false))
            					{
            						// checks if fourth string is a valid 
            						//	status
            						
            						// true if the status is not one of these
            						//	strings
            						invalidStatus = true; 
            					}
            				}
            				else
            				{
            					// flags true if the status is empty
            					invalidStatus = true; 
            				}
        				}
        				else
        				{
        					// true if the line cannot be parsed into a String
        					//	array with 5 elements
        					invalidLength = true; 
        				}
        			}
    			}
    			else
    			{
    				invalidLength = true; // true if the file is empty
    			}
    		}
    		catch(IOException exception)
    		{
    			
    		}
    	}
    	catch(FileNotFoundException exception)
    	{
    		
    	}
    	
    	// if any of the invalid flags are true, return false
    	if(invalidLength || invalidPriority || invalidStartDate || 
    			invalidDueDate || invalidStatus)
    	{
    		// notification for invalid text file.
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error - Invalid text file");
			alert.setHeaderText(null);
			alert.setContentText("Invalid text file. Please load the file "
					+ "initially saved as tasklist.txt.");
			alert.showAndWait();
    		validFile = false;
    	}
    	else
    	{
    		validFile = true;
    	}
    	return validFile; // if no flags are true, proceed
    	
    }
    	
    
    /**
     * Will return the array number of an item based off the priority -1 (which
     * is the index in the actual array). This will allow for edit and other
     * things to access a task when it's not in the priority order in the
     * filter. the string must be in the format:
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
		String lines[] = input.split("Priority: ");
		String prioritySplit[] = lines[1].split("\n");
		int priorityInt = Integer.parseInt(prioritySplit[0]);
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
	
    /**
     * Prints all taskList data to Report.txt in the downloads folder
     */
    public void print()
    {
    	try 
    	{
        	String home = System.getProperty("user.home");
        	String fileName = "Report";
        	
        	File file = new File(home + "/Downloads/" + fileName + ".txt");
        	PrintWriter pw = new PrintWriter(file);
        	pw.println("To Do List");
        	pw.println();
        	
        	for (int index = 0; index < taskArray.size(); index++)
        	{
        		taskEntry tempTask = taskArray.get(index);
        		pw.println(tempTask.getDesc());
        		pw.println("Priority: " + tempTask.getPriority());
        		pw.println("Due on " + tempTask.getDue().toString());
        		
        		if(tempTask.getStatus().equals("In Progress"))
        		{
        			pw.println("Started on " + tempTask.getStart().toString());
        		}
        		else
        		{
        			pw.println("Not Started");
        		}
        		
        		pw.println();
        	}
        	
        	for (int index = 0; index < deletedTasks.size(); index++)
        	{
        		taskEntry tempTask = deletedTasks.get(index);
        		pw.println(tempTask.getDesc());
        		pw.println("Due " + tempTask.getDue().toString());
        		
        		if(tempTask.getStatus().equals("Complete"))
        		{
        			pw.println("Started on " + tempTask.getStart().toString());
        			pw.println("Completed on " + tempTask.getCompleteDate()
        			.toString());
        		}
        		else
        		{
        			if(tempTask.getStart() == null)
        			{
        				pw.println("Not Started");
        			}
        			else
        			{
        				pw.println("Started on " + tempTask.getStart()
        					.toString());
        			}
        			
        			pw.println("Deleted");
        		}
        		
        		pw.println();
        	}
        	
        	pw.close();
    	}
    	catch (FileNotFoundException e)
    	{
    		e.printStackTrace();
    	}
    }
}

