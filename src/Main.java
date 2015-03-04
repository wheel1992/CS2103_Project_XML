import java.util.*;

public class Main {

	private static final String XML_TASK_FILE_PATH = "init_task.xml"; 
	
	public static void main(String[] args){
		Storage mParser = new Storage();
		
		
		ArrayList<Task> mArrayTask= new ArrayList<Task>();
		mArrayTask = mParser.XmltoTable(XML_TASK_FILE_PATH);
		
		System.out.println("mTaskTable.size()  " + mArrayTask.size());
		
				
		//Task task = new Task();
		//task.setID(88);
		//System.out.println(mParser.xmlDeleteTask(XML_TASK_FILE_PATH, task));
		
		
		//Task task2 = new Task();
		//task2.setID(1001);
		//System.out.println(mParser.XmlAddTask(XML_TASK_FILE_PATH, task2));
		mParser.setMaxNumberOfTasks(1000);
		mArrayTask = dummyTable(1000);
		System.out.println(mParser.tableToXml(XML_TASK_FILE_PATH, mArrayTask));
		
	}
	
	private static ArrayList<Task> dummyTable(int maxCount){
		ArrayList<Task> mArray = new ArrayList<Task>();
		

		for(int i=0; i< maxCount; i++){
			Task t = new Task(i + 1,
					"Title " + (i + 1), 
					1424232000000L,
					1424203200000L,
					false);
			
			mArray.add(t);
		}
		
		return mArray;
		
	}
}

