package View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class NonAdminController {
	@FXML Button quit;
	@FXML Button logout;
	@FXML Button create;
	@FXML Button rename;
	@FXML Button delete;
	@FXML Button viewAlbum;
	@FXML Button searchPhotos;
	@FXML TextField album;
	@FXML ListView<String> listofalbums;
	private ObservableList<String> obsList = FXCollections.observableArrayList();
	//private static final String filename= "albums.dat";
	private static final String filename= "userAlbums.dat";
	//private static final String
	private UserAlbum userAlbum;
	private String userName;
	//private ArrayList<String> albums;
	private String selected;
	public void start(String user){
		userName = user;//user who logged in
		if(user.equals("stock")){
			try{
			selected = "stock";
			String path = "/View/insideAlbumPage.fxml";
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			BorderPane root = (BorderPane)loader.load();
			Stage stage = (Stage) logout.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			InsideAlbumController inside = loader.getController();
			inside.start(this.userName, selected, root, stage);
			}catch(Exception e){
				//nothing
			}
		}
		open();
		if(userAlbum != null){
			Iterator<String> itr = userAlbum.getAlbums(user);
			if(itr != null){
				while(itr.hasNext()){
					String album_name = itr.next();
					if(!obsList.contains(album_name)){
						obsList.add(album_name);
					}
				}
			}
		}
		listofalbums.setItems(obsList);
		listofalbums.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

    		@Override
    		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
    			// TODO Auto-generated method stub
    			//System.out.println("Selected item: " + arg2);
    			if(arg2 != null){
    				selected = arg2;
    			}
    		}
        });
		/*for (int i = 0; i<albums.size();i++)
		{
			if(!obsList.contains(albums.get(i)))
			{
			obsList.add(albums.get(i));
			}
		}
		listofalbums.setItems(obsList);*/
	}
	public void quit(ActionEvent e){
		save();
		Platform.exit();
		System.exit(0);
	}
	public void create(ActionEvent e)
	{
		if(!obsList.contains(album.getText()))
		{
			//albums.add(album.getText());
			//obsList.add(album.getText());
			userAlbum.addAlbum(userName, album.getText());
			obsList.add(album.getText());
			listofalbums.setItems(obsList);
			save();
		}
		else{
			//give an alert saying album already exists!
		}
	}
	public void delete(ActionEvent e)
	{
		if(selected!=null)
		{
			userAlbum.deleteAlbum(userName, selected);
			obsList.remove(selected);
			save();
		}
	}
	public void rename(ActionEvent e)
	{
		if(selected!=null)
		{
			if(album.getText().equals(""))
			{
				album.setText(selected);
			}
			else if(album.getText().length()>0 && !(album.getText().equals(selected)))
			{
				String s = selected;
				String a = album.getText();
				create(e);
				userAlbum.addAlbum(userName, a);
				ArrayList<Picture> pics = userAlbum.getPics(userName, selected);
				for (int i = 0; i<pics.size();i++)
				{
					userAlbum.addPic(userName, a, pics.get(i).getPath());
				}
				userAlbum.deleteAlbum(userName, s);
				obsList.remove(s);
			}
			save();
		}
	}
	public void search(ActionEvent e)
	{
		try{
			handle(e,"/View/searchphotos.fxml");
		}catch(IOException e1)
		{
			//do nothing
		}
	}
	public void logout(ActionEvent e){
		save();
		try{
			handle(e, "/View/loginpage.fxml");
		}catch(Exception e1){
			//do nothing
		}
	}
	public void viewAlbum(ActionEvent e){
		save();
		if(selected != null){
			try{
				handle(e, "/View/insideAlbumPage.fxml");
			}catch(Exception e1){
				//do nothing
			}
		}
	}
	private void open(){
		try{
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(filename));
			userAlbum = (UserAlbum)ois.readObject();
		}catch(Exception e){
			//no users in system yet aside from Admin
		}
	}
	private void save(){
		try {
			@SuppressWarnings("resource")
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(filename));
			oos.writeObject(userAlbum);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			//problem
		}
	}
	/*private void save2(String filename)
	{
		try {
			@SuppressWarnings("resource")
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(filename));
			oos.writeObject(userAlbum);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			//problem
		}
	}*/
	/*
	@SuppressWarnings("unchecked")
	private void open(){
		try{
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(filename));
			albums = (ArrayList<String>)ois.readObject();
			if(albums == null)
				albums = new ArrayList<String>();
		}catch(Exception e){
			//no users in system yet aside from Admin
			albums = new ArrayList<String>();
		}
	}
	private void save(){
		try {
			@SuppressWarnings("resource")
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(filename));
			oos.writeObject(albums);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			//problem
		}
	}
	*/
	private void handle(ActionEvent e, String path) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		BorderPane root = (BorderPane)loader.load();
		Stage stage = (Stage) logout.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		if(path.equals("/View/loginpage.fxml")){
			LoginController loginController = loader.getController();
			loginController.start();
		}
		else if(path.equals("/View/insideAlbumPage.fxml")){
			InsideAlbumController inside = loader.getController();
			inside.start(this.userName, selected, root, stage);
		}
		else if(path.equals("/View/searchphotos.fxml"))
		{
			SearchedPhotosController search = loader.getController();
			//search.setUser(userName);
			//save2("myalbums.dat");
			search.start(userAlbum,userName);
		}
		stage.show();
	}
}
