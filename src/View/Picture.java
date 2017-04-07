package View;

import java.io.Serializable;

public class Picture implements Serializable {
	private static final long serialVersionUID = 1L;
	private String path;
	private String caption;
	public Picture(String path, String caption)
	{
		this.path = path;
		this.caption = caption;
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
	public boolean equals(Object o){
		if(o instanceof Picture){
			Picture pic = (Picture)o;
			if(this.path.equals(pic.getPath()))
				return true;
		}
		return false;
	}
}
