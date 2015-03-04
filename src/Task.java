
public class Task {
	
	/*
	 * Variables
	 * */
	int _id;
	String _title;
	long _start_millisecond;
	long _end_millisecond;
	/*
	String _start_time;
	String _end_time; // HH:mm
	String _start_date; // dd/MM/YY
	String _end_date;
	*/
	
	boolean _isDone;
	
	//Default constructor
	public Task(){}
	
	//public Task(int id, String title, String startMs, String endMs, String startTime, String startDate, String endTime, String endDate, boolean isDone){
	public Task(int id, String title, long startMs, long endMs, boolean isDone){
		this._id = id;
		this._title = title;
		this._start_millisecond = startMs;
		this._end_millisecond = endMs;
		/*
		this._start_time = startTime;
		this._start_date = startDate;
		this._end_time = endTime;
		this._end_date = endDate;
		*/
		this._isDone = isDone;
	}
	
	public void setID(int id){
		this._id = id;
	}
	
	public void setTitle(String title){
		this._title = title;
	}
	
	public void setStartMillisecond(long startMs){
		this._start_millisecond = startMs;
	}
	
	
	public void setEndMillisecond(long endMs){
		this._end_millisecond = endMs;
	}
	
	/*
	public void setStartTime(String startTime){
		this._start_time = startTime;
	}
	
	
	public void setEndTime(String endTime){
		this._end_time = endTime;
	}
	
	
	public void setStartDate(String startDate){
		this._start_date = startDate;
	}
	
	
	public void setEndDate(String endDate){
		this._end_date = endDate;
	}
	*/
	
	public void setIsDone(boolean d){
		this._isDone = d;
	}
	
	public int getID(){
		return this._id;
	}
	
	public String getTitle(){
		return this._title;
	}
	
	public long getStartMillisecond(){
		return this._start_millisecond;
	}
	
	public long getEndMillisecond(){
		return this._end_millisecond;
	}
	
	/*
	public String getStartTime(){
		return this._start_time;
	}
	
	public String getStartDate(){
		return this._start_date;
	}
	
	public String getEndTime(){
		return this._end_time;
	}
	
	public String getEndDate(){
		return this._end_date;
	}
	*/
	
	public boolean getIsDone(){
		return this._isDone;
	}
	
	
	
}

