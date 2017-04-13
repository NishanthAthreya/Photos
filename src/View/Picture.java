package View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;
/**
 * This class defines a picture, which has several attributes, such as caption, tags, date-and-time, etc.
 * @author Pranav Kanukollu, pvk9
 * @author Nishanth Athreya, nsa48
 */
public class Picture implements Serializable {
	private static final long serialVersionUID = 1L;
	private String path;
	private String caption;
	private String name;
	int month;
	int date;
	int year;
	int hour;
	int minute;
	int second;
	String dateAndTime = "";
	Calendar cal = Calendar.getInstance();
	//ArrayList<String> tags = new ArrayList<String>();
	HashMap<String,String> tags = new HashMap<String,String>();
	/**
	 * Constructor.
	 * @param path String variable, for the path of the picture.
	 * @param caption String variable, for the caption of the picture.
	 */
	public Picture(String path, String caption)
	{
		
		this.path = path;
		this.caption = caption;
		
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	/**
	 * This method returns the path of the picture.
	 * @param user String
	 * @param picNum int
	 * @return String
	 */
	public String getPath(String user, int picNum)
	{
		//if(user.equalsIgnoreCase("stock"))
			//return ".\\src\\View\\stockpic"+Integer.toString(picNum+1)+".jpg";
		//else{
			return path;
		//}
	}
	/**
	 * This method returns the path of the picture.
	 * @return String
	 */
	public String getPath()
	{
		
		return path;
	}
	/**
	 * This method returns the caption of the picture.
	 * @return String
	 */
	public String getCaption()
	{
		return caption;
	}
	/**
	 * This method sets the caption of the picture to a new caption.
	 * @param newCaption
	 */
	public void setCaption(String newCaption)
	{
		caption = newCaption;
	}
	/**
	 * This method returns the toString version of the picture, which is its path.
	 * @return String
	 */
	public String toString()
	{
		return path;
	}
	/**
	 * This method returns the tags of the picture.
	 * @return HashMap<String,String>
	 */
	public HashMap<String,String> getTags()
	{
		return tags;
	}
	/**
	 * This method adds a tag to the hashmap of tags.
	 * @param tagName String
	 * @param tagValue String
	 */
	public void addTag(String tagName, String tagValue)
	{
		tags.put(tagName, tagValue);
		//tags.add(tag);
	}
	/**
	 * This method removes a tag.
	 * @param tagName String
	 * @param tagValue String
	 */
	public void removeTag(String tagName, String tagValue)
	{
		tags.remove(tagName, tagValue);
		//tags.remove(tag);
	}
	/**
	 * This method checks if two pictures are equal by overwriting the equals method in Object.
	 * @param o Object
	 * @return boolean
	 */
	public boolean equals(Object o){
		if(o instanceof Picture){
			Picture pic = (Picture)o;
			if((this.name != null && this.name.equals(pic.getName())) || (this.path.equals(pic.getPath())))
				return true;
		}
		return false;
	}
	/**
	 * This method sets the tags to a new hashmap of tags.
	 * @param newTags HashMap<String,String>
	 */
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
	/**
	 * This method sets the date and time to a new date and time.
	 * @param dateAndTime String
	 */
	public void setDateAndTime(String dateAndTime)
	{
		this.dateAndTime = dateAndTime;
	}
	/**
	 * This method returns the date and time of the picture.
	 * @return String
	 */
	public String getDateAndTime()
	{
		return dateAndTime;
	}
	/**
	 * This method creates a calendar instance of the pictures date and time given a String version.
	 * @param dateAndTime String
	 */
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
	/**
	 * This method returns the Calendar version of the date and time of the picture.
	 * @return Calendar
	 */
	public Calendar getCalendar()
	{
		return cal;
	}
}
