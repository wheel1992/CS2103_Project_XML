import java.util.*;

public class Main {

	private static final String XML_TASK_FILE_PATH = "init_task.xml"; 
	
	public static void main(String[] args){
		Parser mParser = new Parser();
		
		
		HashMap<Integer, Task> mTaskTable= new HashMap<Integer, Task>();
		mTaskTable = mParser.XmltoTable(XML_TASK_FILE_PATH);
		
		System.out.println("mTaskTable.size()  " + mTaskTable.size());
		
				
		//Task task = new Task();
		//task.setID(88);
		//System.out.println(mParser.xmlDeleteTask(XML_TASK_FILE_PATH, task));
		
		
		//Task task2 = new Task();
		//task2.setID(1001);
		//System.out.println(mParser.XmlAddTask(XML_TASK_FILE_PATH, task2));
		
		mTaskTable = dummyTable(1000);
		System.out.println(mParser.tableToXml(XML_TASK_FILE_PATH, mTaskTable));
		
	}
	
	private static HashMap<Integer, Task> dummyTable(int maxCount){
		HashMap<Integer, Task> mTable = new HashMap<Integer, Task>();
		
		for(int i=0; i< maxCount; i++){
			Task t = new Task(i,
					"Title " + i, 
					"1424232000000",
					"1424203200000",
					"12:00",
					"18/02/15",
					"16:00",
					"18/02/15",
					false);
			
			mTable.put(i, t);
		}
		
		return mTable;
		
	}
}

