package View;

public class Picture {
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
}
