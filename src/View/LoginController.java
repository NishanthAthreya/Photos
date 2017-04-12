package View;
import java.io.File;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class is the controller for the login page. This is where the user enters a username and goes to 
 * another screen based on the username entered.
 * @author Pranav Kanukollu, pvk9
 * @author Nishanth Athreya, nsa48
 */
public class LoginController {
 @FXML Button exit;
 @FXML Button go;
 @FXML TextField text;
 private ArrayList<String> users;
 private UserAlbum userAlbum;
 private static final String filename= "users.dat";
 private static final String filename2= "userAlbums.dat";
 /**
  * This method starts the login page. It opens the contents from all the serialized objects.
  */
 public void start()
    {
	 openAlbums();
	 open();
	 for(int i = 0;i < users.size();i++){
		if(users.get(i).equals("stock"))
			return;
	 }
	 users.add("stock");
	 userAlbum.addUser("stock");
	 userAlbum.addAlbum("stock", "stock");
	 for(int i = 1;i < 11;i++){
		 String path = "src/View/stockpic" + i + ".jpg";
		 File file = new File(path);
		 //System.out.println(file.getAbsolutePath());
		 //userAlbum.addPic("stock", "stock", path);
		 userAlbum.addPic("stock", "stock", file.getAbsolutePath());
	 }
	 saveAlbums();
    }
 /**
  * This method is an event handler for clicking the quit button, it exits out of the program completely.
  * @param e ActionEvent object
  */
 public void exit(ActionEvent e){
	 Platform.exit();
     System.exit(0);
 }
 /**
  * This method is an event handler for clicking the go button. It changes screen based on the username entered.
  * @param e ActionEvent object
  */
 public void go(ActionEvent e){
  //System.out.println("go");
  boolean flag = false;
  if(text.getText().equalsIgnoreCase("Admin")){
   try {
    handle(e,"/View/adminpage.fxml");
   } catch (IOException e1) {
    //do nothing
   }
  }
  else{
	  try{
	  for(int i = 0;i < users.size();i++){
		  if(users.get(i).equals(text.getText()))
		  {
			flag = true;
			  handle(e,"/View/nonadminpage.fxml");
		  }
	  }
	  if (flag == false)
	  {
		  Alert alert = new Alert(AlertType.ERROR);
		  alert.setTitle("Error");
		  alert.setHeaderText("User not found.");
		  alert.showAndWait();
	  }
	  } catch (IOException e2)
	  {
		  //do nothing
	  }
  }
  }
 /**
  * This method serializes all of the users along with their respective albums and photos by writing them into
  * a file.
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
  * This method reads in all the contents from the file which is storing all the user's albums.
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
	/**
	 * This method handles different cases of changing screens based on the username entered. The various 
	 * possible paths of fxml documents are checked to see which screen to change to.
	 * @param e ActionEvent object
	 * @param path String variable, which is the path of the fxml document
	 * @throws IOException
	 */
 private void handle(ActionEvent e, String path) throws IOException{
 // System.out.println("handle");
  /*Stage stage;
  Parent root;
  stage=(Stage) go.getScene().getWindow();
  //load up OTHER FXML document
  root = FXMLLoader.load(getClass().getResource(path));
  Scene scene = new Scene(root);
  stage.setScene(scene);
  FXMLLoader loader = new FXMLLoader(); */
  FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
  BorderPane root = (BorderPane)loader.load();
  //Stage stage = new Stage();
  Stage stage = (Stage) go.getScene().getWindow();
  Scene scene = new Scene(root);
  stage.setScene(scene);
  if(path.equals("/View/adminpage.fxml")){
	  AdminController adminController = loader.getController();
	  adminController.start();
  }
  /*if (text.getText().equalsIgnoreCase("stock") && path.equals("/View/nonadminpage.fxml"))
  {
	  StockController stock = loader.getController();
	  stock.start();
  }*/
  if (path.equals("/View/nonadminpage.fxml"))
  {
	  NonAdminController nonadmin = loader.getController();
	  nonadmin.start(text.getText());
	  //non admin controller stuff
  }
  /*if (path.equals("/View/stockpage.fxml"))
  {
	  StockController stock = loader.getController();
	  stock.start();
  }*/
  stage.show();
  
 }
 /**
  * This method opens all of the users by reading them from the file which is storing all of them.
  */
 @SuppressWarnings("unchecked")
	private void open(){
		try{
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(filename));
			users = (ArrayList<String>)ois.readObject();
		}catch(Exception e){
			//no users in system yet aside from Admin
			users = new ArrayList<String>();
		}
	}
}