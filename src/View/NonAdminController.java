package View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.application.Platform;
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
	private static final String filename= "albums.dat";
	private ArrayList<String> albums;
	public void start(){
		open();
		for (int i = 0; i<albums.size();i++)
		{
			if(!obsList.contains(albums.get(i)))
			{
			obsList.add(albums.get(i));
			}
		}
		listofalbums.setItems(obsList);
	}
	public void quit(ActionEvent e){
		Platform.exit();
		System.exit(0);
	}
	public void create(ActionEvent e)
	{
		if(!obsList.contains(album.getText()))
		{
			albums.add(album.getText());
			obsList.add(album.getText());
			save();
		}
		else{
			//give an alert saying album already exists!
		}
	}
	public void logout(ActionEvent e){
		try{
			handle(e, "/View/loginpage.fxml");
		}catch(Exception e1){
			//do nothing
		}
	}
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
		stage.show();
	}
}
