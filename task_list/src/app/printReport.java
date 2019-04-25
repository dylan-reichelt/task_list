package app;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * This class will take the tasK list array and print every entry into a text file.
 * The entry will be printed out in a readable format.
 * 
 * @author ignatius
 *
 */
public class printReport {
	/**
	 * an array that hold all entries that needed to be output into a text file
	 */
	taskList taskReport = null;
	
	/**
	 * Constructor take in taskTable and put in taskReport when print button is clicked.
	 * Then it print what is in taskReport into a text file.
	 */
	public printReport(taskList taskTable) {
		taskReport = taskTable;
		saveToFile();
	}
	
	/**
	 * This method open a save dialog that allow user 
	 *   to save the report  in any location in the computer.
	 * Then using the file path of the save file and the taskReport object, 
	 *    the printToFile method is called.
	 */
	public void saveToFile() {
		JFrame saveFrame = new JFrame(); 
		JFileChooser saveDialog = new JFileChooser();
		saveDialog.setDialogTitle("Specify a text file to save, must end in .txt"); 
		int fileSelection = saveDialog.showSaveDialog(saveFrame);
		
		if(fileSelection == JFileChooser.APPROVE_OPTION) {
			File saveFile =  saveDialog.getSelectedFile();
			printToFile(taskReport, saveFile.getAbsolutePath());
		}
	}
	
	
	/**
	 * This method take in a taskLisk object and output every entries into a text file.
	 * 
	 * @param task the taskList object
	 */
	public void printToFile(taskList task, String filepath) {
		try {
			//open file
			String file = filepath;
			FileWriter outputFile = new FileWriter(file);
			BufferedWriter output = new BufferedWriter(outputFile);
			
			//write to file
			output.write("         Official report         ");
			output.newLine();
			output.write("---------------------------------");
			output.newLine();
			output.write("Total Number of task(s): " + task.count());			
			output.newLine();
			
			for(int index = 0; index < task.count(); index++) {
				output.newLine();
				output.write("Description: " + task.get(index).getDesc());
				output.newLine();
				output.write("Priority: " + task.get(index).getPriority());
				output.newLine();
				if(task.get(index).getStart() == null)
				{
					output.write("Start Date: NA");
				}
				else
				{
					output.write("Start Date: " + task.get(index).getStart());
				}
				output.newLine();
				output.write("Due Date: " + task.get(index).getDue());
				output.newLine();
				output.write("Status: " + task.get(index).getStatus());
				output.newLine();
			}//end of for loop
			
			
			//close file
			output.close();
			
			
			//open Report.txt to the screen to be view after the file write is finish
			ProcessBuilder pb = new ProcessBuilder("Notepad.exe", file);
			pb.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of try-catch statement
	}//end of method printToFile
	

}//end of class
