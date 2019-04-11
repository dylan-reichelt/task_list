package cse360project;

import java.util.ArrayList;

public class EntryImplementer {
	static ArrayList<Entry> entryList = new ArrayList<Entry>(1);
	static int size = 0;
	
	public static void main(String[] args) {
		
	}
	
	public static boolean addEntry(String description, int priority, String dueDate) {
		boolean duplicate = false;
		for(Entry existingEntry : entryList) {
			if(existingEntry.getDescription().equals(description))
				duplicate = true;
		}
		
		if(duplicate == false)
			return false;
		else {
			int newPriority = priority;

			for(Entry existingEntry : entryList) {
				if(priority < existingEntry.getPriority())
					existingEntry.incrementPriority();
			}
			if(priority < 1)
				newPriority = 1;
			if(priority > (size + 1))
				newPriority = size + 1;
			
			entryList.add(new Entry(description, newPriority, dueDate));
			size++;
			return true;
		}
	}
	
	public static void delete(String description) {
		int index = 0;
		
		while(!description.equals(entryList.get(index).getDescription()))
			index++;
		
		int incrementIndex = index;
		while(incrementIndex <= size)
			entryList.get(incrementIndex).decrementPriority();
		
		entryList.remove(index);
		size--;
	}
	
	public static boolean changeDescription(String currentDescription, String newDescription) {
		boolean duplicate = false;
		for(Entry existingEntry : entryList) {
			if(existingEntry.getDescription().equals(newDescription))
				duplicate = true;
		}
		
		if(duplicate == false)
			return false;
		else {
			int index = 0;
			
			while(!currentDescription.equals(entryList.get(index).getDescription()))
				index++;
			
			entryList.get(index).setDescription(newDescription);
			return true;
		}
	}
	
	//public static void changePriority(String currentDescription, )
}