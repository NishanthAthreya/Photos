package View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;
public class Picture implements Serializable {
	private static final long serialVersionUID = 1L;
	private String path;
	private String caption;
	int month;
	int date;
	int year;
	int hour;
	int minute;
	int second;
	String dateAndTime;
	Calendar cal = Calendar.getInstance();
	//ArrayList<String> tags = new ArrayList<String>();
	HashMap<String,String> tags = new HashMap<String,String>();
	public Picture(String path, String caption)
	{
		
		this.path = path;
		this.caption = caption;
	}
	public String getPath(String user, int picNum)
	{
		//if(user.equalsIgnoreCase("stock"))
			//return ".\\src\\View\\stockpic"+Integer.toString(picNum+1)+".jpg";
		//else{
			return path;
		//}
	}
	public String getPath()
	{
		
		return path;
	}
	public String getCaption()
	{
		return caption;
	}
	public void setCaption(String newCaption)
	{
		caption = newCaption;
	}
	public String toString()
	{
		return path;
	}
	public HashMap<String,String> getTags()
	{
		return tags;
	}
	public void addTag(String tagName, String tagValue)
	{
		tags.put(tagName, tagValue);
		//tags.add(tag);
	}
	public void removeTag(String tagName, String tagValue)
	{
		tags.remove(tagName, tagValue);
		//tags.remove(tag);
	}
	public boolean equals(Object o){
		if(o instanceof Picture){
			Picture pic = (Picture)o;
			if(this.path.equals(pic.getPath()))
				return true;
		}
		return false;
	}
	public void setTags(HashMap<String,String> newTags)
	{
		tags = newTags;
	}
	/*public void setDateAndTime(int month, int day, int year, int hour, int minute, int second)
	{
		this.month = month;
		this.day = day;
		this.year = year;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}*/
	public void setDateAndTime(String dateAndTime)
	{
		this.dateAndTime = dateAndTime;
	}
	public String getDateAndTime()
	{
		return dateAndTime;
	}
	public void calendar(String dateAndTime)
	{
		//cal = Calendar.getInstance();
		month = Integer.parseInt(dateAndTime.substring(0, 2));
		date = Integer.parseInt(dateAndTime.substring(3,5));
		year = Integer.parseInt(dateAndTime.substring(6,10));
		hour = Integer.parseInt(dateAndTime.substring(11,13));
		minute = Integer.parseInt(dateAndTime.substring(14,16));
		second = Integer.parseInt(dateAndTime.substring(17));
		cal.set(year, month, date, hour, minute, second);
		cal.set(Calendar.MILLISECOND,0);
	}
	public Calendar getCalendar()
	{
		return cal;
	}
}
