package View;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * This class
 * @author pkanukollu
 *
 */
public class SearchedPhotosController {

	private static final String filename = "userAlbums.dat";
	@FXML TextField tagname;
	@FXML TextField tagvalue;
	@FXML TextField startDate;
	@FXML TextField endDate;
	@FXML ListView tags;
	@FXML Button add;
	@FXML Button search;
	@FXML Button back;
	private UserAlbum al;
	String u;
	String x = "";
	private ObservableList<String> obsList = FXCollections.observableArrayList();
	private HashMap<String,String> searchTags = new HashMap<String,String>();
	ArrayList<Picture> result = new ArrayList<Picture>();
	public void start(UserAlbum userAlbum, String user)
	{
		//System.out.println(userAlbum.getPics(user,"jonn").get(0).getPath());
		al = userAlbum;
		//openAlbums();
		//System.out.println(user);
		u = user;
		//System.out.println(u);
		//tags.setItems(obsList);
	/*	Iterator<String> iter = al.getAlbums(u);		//all albums of user
		//System.out.println(u);
		ArrayList<Picture> pics = new ArrayList<Picture>();
		ArrayList<String> albums = new ArrayList<String>();
		while(iter.hasNext())
		{
			//System.out.println("entered");
			albums.add(iter.next());
		}
		//searchTags.put("name", "pranav");
		for (int j = 0; j<albums.size();j++){
		//while (iter.hasNext())
		//{
			//System.out.println("entered");
			pics = al.getPics(u, albums.get(j)); //all pics in current album
			//System.out.println(pics.get(0).getPath());
			for (int i = 0; i< pics.size();i++)
			{
				//System.out.println("entered " + i);
				HashMap<String,String> tags = pics.get(i).getTags();	//all tags of current pic
				//System.out.println(tags.containsKey("name"));
				for (HashMap.Entry<String, String> entry : searchTags.entrySet())	//going through all tag filters
				{
					System.out.println("entered");
					for (HashMap.Entry<String, String> entry2 : tags.entrySet())	//going through all tags of current pic
					{
						if (entry.getKey().equals(entry2.getKey())&&entry.getValue().equals(entry2.getValue()))
						{
							result.add(pics.get(i));
						}
					}
				}
			}
		}*/
		//System.out.println(result.size());
	}
	public void setUser(String user)
	{
		u = user;
		//x = user;
	}
	public void add(ActionEvent e)
	{
		searchTags.put(tagname.getText(), tagvalue.getText());
		obsList.add("Tag: " + tagname.getText() + ", Value: " + tagvalue.getText());
		tags.setItems(obsList);
		/*Iterator<String> iter = al.getAlbums(u);		//all albums of user
		//System.out.println(u);
		ArrayList<Picture> pics = new ArrayList<Picture>();
		ArrayList<String> albums = new ArrayList<String>();
		while(iter.hasNext())
		{
			//System.out.println("entered");
			albums.add(iter.next());
		}
		//searchTags.put("name", "pranav");
		for (int j = 0; j<albums.size();j++){
		//while (iter.hasNext())
		//{
			//System.out.println("entered");
			pics = al.getPics(u, albums.get(j)); //all pics in current album
			//System.out.println(pics.get(0).getPath());
			for (int i = 0; i< pics.size();i++)
			{
				//System.out.println("entered " + i);
				HashMap<String,String> tags = pics.get(i).getTags();	//all tags of current pic
				//System.out.println(tags.containsKey("name"));
				for (HashMap.Entry<String, String> entry : searchTags.entrySet())	//going through all tag filters
				{
					//System.out.println("entered");
					for (HashMap.Entry<String, String> entry2 : tags.entrySet())	//going through all tags of current pic
					{
						if (entry.getKey().equals(entry2.getKey())&&entry.getValue().equals(entry2.getValue()))
						{
							result.add(pics.get(i));
						}
					}
				}
			}
		}*/
		//this.start(al, u);
	}
	public void back(ActionEvent e)
	{
		try{
			handle(e,"/View/nonadminpage.fxml");
		}catch(IOException e1)
		{
			//do nothing
		}
	}
	public void search(ActionEvent e)
	{
		//System.out.println(u);
		//System.out.println("entered search");
		try{
			handle(e, "/View/searchresults.fxml");
		}catch(IOException e1)
		{
			//do nothing
		}
	}
	private void openAlbums(){
		try{
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream("userAlbums.dat"));
			al = (UserAlbum)ois.readObject();
			if(al == null)
				al = new UserAlbum();
		}catch(Exception e){
			//no users in system yet aside from Admin
			al = new UserAlbum();
		}
	}
	private void handle(ActionEvent e, String path) throws IOException
	{
		//u = x;
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		BorderPane root = (BorderPane)loader.load();
		Stage stage = (Stage) search.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		if (path.equals("/View/nonadminpage.fxml"))
		{
			NonAdminController nonadmin = loader.getController();
			nonadmin.start(u);
		}
		if (path.equals("/View/searchresults.fxml"))
		{
			//openAlbums();
			//System.out.println("entered handle");
			//UserAlbum userAl = new UserAlbum();
			//System.out.println(u);
			SearchResultsController searchRes = loader.getController();
			ArrayList<Picture> result = new ArrayList<Picture>();
			//System.out.println(x);
			Iterator<String> iter = al.getAlbums(u);		//all albums of user
			//System.out.println(u);
			ArrayList<Picture> pics = new ArrayList<Picture>();
			ArrayList<String> albums = new ArrayList<String>();
			while(iter.hasNext())
			{
				albums.add(iter.next());
			}
			//System.out.println(tagname.getText());
			if (startDate.getText()!="")
			{
				//System.out.println("entered");
				String startD = startDate.getText();
				String endD = endDate.getText();
				Picture s = new Picture(" ", "");
				Picture t = new Picture(" ","");
				s.calendar(startD);
				t.calendar(endD);
				Calendar startDateAndTime = s.getCalendar();
				Calendar endDateAndTime = t.getCalendar();
				//System.out.println(startDateAndTime.toString());
				//System.out.println(endDateAndTime.toString());
				for (int j = 0; j<albums.size();j++)
				{
					pics = al.getPics(u, albums.get(j)); //all pics in current album
					for (int i = 0; i< pics.size();i++)
					{
						
						if (pics.get(i).getCalendar().compareTo(startDateAndTime)>=0 && pics.get(i).getCalendar().compareTo(endDateAndTime)<=0)
						{
							result.add(pics.get(i));
						}
					}
				}
				//System.out.println(startDate.getText());
			//System.out.println("x");
			
			}
			else{	//search by date and time
				//Calendar calen = Calendar.getInstance();
				for (int j = 0; j<albums.size();j++)
				{
					pics = al.getPics(u, albums.get(j)); //all pics in current album
					for (int i = 0; i< pics.size();i++)
					{
						boolean flag = true;
						HashMap<String,String> tags = pics.get(i).getTags();	//all tags of current pic
						for (HashMap.Entry<String, String> entry : searchTags.entrySet())	//going through all tags
						{
							if (!(tags.containsKey(entry.getKey())&&tags.containsValue(entry.getValue()))){
							//for (HashMap.Entry<String, String> entry2 : searchTags.entrySet())	//going through all tag filters
							//{
								//System.out.println(pics.get(i).getPath() + ":" + "tag: " + entry.getKey() + " search tag: " + entry2.getKey() + "value: " + entry.getValue() + "search value: " + entry2.getValue());
								//if (!(entry.getKey().equals(entry2.getKey())&&entry.getValue().equals(entry2.getValue())))
								
									//result.add(pics.get(i));
								flag = false;
								break;
								}
							}
							if (flag==true)
							{
								result.add(pics.get(i));
							}
						}
					}
			}
			
			searchRes.start(result, root, stage, al, u);
		}
		stage.show();
	}
}
