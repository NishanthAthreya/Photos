package View;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminController {
	@FXML Button logout; 
	@FXML Button create;
	@FXML Button delete;
	@FXML Button list;
	@FXML Button quit;
	@FXML TextField username;
	private ArrayList<String> users;
	private static final String filename= "users.dat";
	public void start()
    {
		open();
    }
	public void quit(ActionEvent e){
		save();
		Platform.exit();
		System.exit(0);
	}
	public void logout(ActionEvent e){
		try{
			save();
			handle(e, "/View/loginpage.fxml");
		}catch(Exception e1){
			//do nothing
		}
	}
	public void create(ActionEvent e){
		for(int i = 0;i < users.size();i++){
			if(users.get(i).equals(username.getText()))
					return;//already exists
		}
		users.add(username.getText());
	}
	private void handle(ActionEvent e, String path) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		BorderPane root = (BorderPane)loader.load();
		Stage stage = (Stage) create.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		if(path.equals("/View/loginpage.fxml")){
			LoginController loginController = loader.getController();
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
}
