package View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class is the controller for the admin page. This class handles all the functionalities that
 * are available for the admin.
 * @author Pranav Kanukollu, pvk9
 * @author Nishanth Athreya, nsa48
 */
public class AdminController {
	@FXML Button logout; 
	@FXML Button create;
	@FXML Button delete;
	@FXML Button list;
	@FXML Button quit;
	@FXML TextField username;
	@FXML ListView<String> listofusers;
	private ArrayList<String> users;
	protected static ObservableList<String> obsList =FXCollections.observableArrayList();
	private UserAlbum userAlbum;
	private static final String filename= "users.dat";
	private static final String filename2 = "userAlbums.dat";
	/**
	 * This method starts the controller, and opens all of the users and their respective albums and photos
	 * from the file which stores them.
	 */
	public void start()
    {
		open();
		openAlbums();
    }
	/**
	 * This method is an event handler for clicking the quit button, it exits out of the program completely.
	 * @param e ActionEvent object
	 */
	public void quit(ActionEvent e){
		save();
		saveAlbums();
		Platform.exit();
		System.exit(0);
	}
	/**
	 * This method is an event handler for clicking the logout button. It logs out of the admin page, but goes
	 * back to the login page.
	 * @param e ActionEvent object
	 */
	public void logout(ActionEvent e){
		try{
			save();
			saveAlbums();
			handle(e, "/View/loginpage.fxml");
		}catch(Exception e1){
			//do nothing
		}
	}
	/**
	 * This method is an event handler for clicking the create button. This allows the admin the create
	 * a new user.
	 * @param e
	 */
	public void create(ActionEvent e){
		for(int i = 0;i < users.size();i++){
			if(users.get(i).equals(username.getText()))
			{
				Alert alert = new Alert(AlertType.ERROR);
				  alert.setTitle("Error");
				  alert.setHeaderText("User already exists.");
				  alert.showAndWait();
				  return;
			}
		}
		//System.out.println(username.getText());
		obsList.add(username.getText());
		users.add(username.getText());
		userAlbum.addUser(username.getText());
		Alert alert = new Alert(AlertType.INFORMATION, "Username created.");
		alert.showAndWait();
		save();
		saveAlbums();
	}
	/**
	 * This method is an event handler for clicking the delete button. This allows the admin to delete a user.
	 * @param e
	 */
	public void delete(ActionEvent e)
	{
		if (!users.contains(username.getText()))
		{
			Alert alert = new Alert(AlertType.ERROR);
			  alert.setTitle("Error");
			  alert.setHeaderText("User not found.");
			  alert.showAndWait();
			  return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + username.getText() + "?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES)
		{
		obsList.remove(username.getText());
		users.remove(username.getText());
		userAlbum.deleteUser(username.getText());
		save();
		saveAlbums();
		}
		else
		{
			return;
		}
	}
	/**
	 * This method is an event handler for clicking the list users button. This goes to a new screen and displays
	 * all the users.
	 * @param e
	 */
	public void listUsers(ActionEvent e)
	{
		listofusers = new ListView<String>();
		for (int i = 0; i<users.size();i++)
		{
			if(!obsList.contains(users.get(i)))
			{
			obsList.add(users.get(i));
			}
		}
		//listofusers.setItems(obsList);
		//listofusers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>());
		try{
			handle(e,"/View/listofusers.fxml");
		}catch(Exception e1)
		{
			//do nothing
		}
	}
	/**
	 * This method returns the ObservableList object to get the list of users.
	 * @return ObservableList<String> 
	 */
	public ObservableList<String> getListofUsers()
	{
		return obsList;
	}
	/**
	 * This method handles different cases of changing screens. The various 
	 * possible paths of fxml documents are checked to see which screen to change to.
	 * @param e ActionEvent object
	 * @param path String variable, which is the path of the fxml document
	 * @throws IOException
	 */
	private void handle(ActionEvent e, String path) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		
		//System.out.println("loader obtained" + loader);
		BorderPane root = (BorderPane)loader.load();
		Stage stage = (Stage) list.getScene().getWindow();
		Scene scene = new Scene(root);
		scene.getStylesheets().add("/application/application.css");
		stage.setScene(scene);
		if(path.equals("/View/listofusers.fxml"))
		{
			//System.out.println("entered list of users");
			//root = (BorderPane)loader.load();
			ListofUsersController listController = loader.getController();
			//System.out.println(listController);
			listController.start();
			//System.out.println("started and back in list");
		}
		if(path.equals("/View/loginpage.fxml")){
			//System.out.println("entered login page");
			LoginController loginController = loader.getController();
			//System.out.println(loginController);
			loginController.start();
		}
		
		stage.show();
	}
	/*private void handle(ActionEvent e, String path) throws IOException{
		  Stage stage;
		  Parent root;
		  stage=(Stage) logout.getScene().getWindow();
		  //load up OTHER FXML document
		  root = FXMLLoader.load(getClass().getResource(path));
		  Scene scene = new Scene(root);
		  stage.setScene(scene);
		  FXMLLoader loader = new FXMLLoader(); 
		  if(path.equals("/View/loginpage.fxml")){
			  loader.setLocation(getClass().getResource(path)); 
			  AdminController loginController = loader.getController();
			  loginController.start();
		  }
		  stage.show();
		 }*/
	/**
	 * This method writes all of the users into a file by using serialization.
	 */
	private void save(){
		try {
			@SuppressWarnings("resource")
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(filename));
			oos.writeObject(users);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			//problem
		}
		
	}
	/**
	 * This method writes all of the albums and photos of each of the users into a file by using serialization.
	 */
	private void saveAlbums(){
		try {
			@SuppressWarnings("resource")
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(filename2));
			oos.writeObject(userAlbum);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			//problem
		}
		
	}
	/**
	 * This method reads from the file which stores all the users.
	 */
	@SuppressWarnings("unchecked")
	private void open(){
		try{
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(filename));
			users = (ArrayList<String>)ois.readObject();
			if(users == null)
				users = new ArrayList<String>();
		}catch(Exception e){
			//no users in system yet aside from Admin
			users = new ArrayList<String>();
		}
	}
	/**
	 * This method reads from the file which stores all the albums and photos of each user.
	 */
	private void openAlbums(){
		try{
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(filename2));
			userAlbum = (UserAlbum)ois.readObject();
			if(userAlbum == null)
				userAlbum = new UserAlbum();
		}catch(Exception e){
			//no users in system yet aside from Admin
			userAlbum = new UserAlbum();
		}
	}
}
