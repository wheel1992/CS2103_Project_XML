import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

import org.joda.time.*;
import org.joda.time.format.*;



public class addTask {
	private static int ARRAY_INDEX_TITLE=0;
	private static int ARRAY_INDEX_START_DATE=1;
	private static int ARRAY_INDEX_START_TIME=2;
	private static int ARRAY_INDEX_END_DATE=3;
	private static int ARRAY_INDEX_END_TIME=4;
	private static int ARRAY_INDEX_REPEAT=5;
	private static int ARRAY_INDEX_DELAYTYPE=6;
	private static int ARRAY_INDEX_RECUR=7;
	private static int ONE_DAY=1;
	private static int ONE_WEEK=1;
	private static int ONE_MONTH=1;
	private static int ONE_YEAR=1;

	//DateTime today=new DateTime();	//jodatime
	//private static SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
	private static DateTimeFormatter dtf=DateTimeFormat.forPattern("dd/MM/yyyy");
	//private  static DateTime laterDate;

	public static String add(String[] inputArray){
		Task newTask;
		DateTime startDate=dtf.parseDateTime(inputArray[ARRAY_INDEX_START_DATE]);
		DateTime endDate=dtf.parseDateTime(inputArray[ARRAY_INDEX_END_DATE]);
		
		if(inputArray[ARRAY_INDEX_REPEAT].equalsIgnoreCase("n")){
			newTask=new Task(inputArray);
			///////////// add to hashmap
		}
		else{
			String delayType=inputArray[ARRAY_INDEX_DELAYTYPE];
			int recur=Integer.parseInt(inputArray[ARRAY_INDEX_RECUR]);
			while(recur>0){
				if(delayType.equalsIgnoreCase("d")){
					startDate.plusDays(ONE_DAY);
					inputArray[ARRAY_INDEX_START_DATE]=startDate.toString();
					endDate.plusDays(ONE_DAY);
					inputArray[ARRAY_INDEX_END_DATE]=endDate.toString();
				}
				else if (delayType.equalsIgnoreCase("w")) {
					startDate.plusWeeks(ONE_WEEK);
					inputArray[ARRAY_INDEX_START_DATE]=startDate.toString();
					endDate.plusWeeks(ONE_WEEK);
					inputArray[ARRAY_INDEX_END_DATE]=endDate.toString();
				}
				else if (delayType.equalsIgnoreCase("m")) {
					startDate.plusMonths(ONE_MONTH);
					inputArray[ARRAY_INDEX_START_DATE]=startDate.toString();
					endDate.plusMonths(ONE_MONTH);
					inputArray[ARRAY_INDEX_END_DATE]=endDate.toString();
				}
				else if (delayType.equalsIgnoreCase("y")) {
					startDate.plusYears(ONE_YEAR);
					inputArray[ARRAY_INDEX_START_DATE]=startDate.toString();
					endDate.plusYears(ONE_YEAR);
					inputArray[ARRAY_INDEX_END_DATE]=endDate.toString();
				}
			}
			newTask=new Task(inputArray);
			///////////// add to hashmap
		}
		return String.format(Message.MSG_SUCCESS, newTask.getTitle());
	}
}
