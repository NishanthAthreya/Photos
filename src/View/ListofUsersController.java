package View;

//import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

/**
 * This class is simply a list view of all the users for the admin to view.
 * @author Pranav Kanukollu, pvk9
 * @author Nishanth Athreya, nsa48
 */
public class ListofUsersController {
	@FXML ListView<String> listofusers;
	@FXML Button back;
	//private ObservableList<String> obsList;
	/**
	 * This method starts the controller by setting the listview to be the list of users.
	 */
	public void start()
	{
		//System.out.println("called list");
		AdminController ad = new AdminController();
		//ad.getListofUsers();
		/*for (int i = 0;i<ad.getListofUsers().size();i++)
		{
			System.out.println(ad.getListofUsers().get(i));
		}*/
		listofusers.setItems(ad.getListofUsers());
	}
	/**
	 * This method is an event handler for clicking the back button. This goes back to admin page.
	 * @param e
	 */
	public void back(ActionEvent e)
	{
		try{
			handle(e,"/View/adminpage.fxml");
		}catch(Exception e1)
		{
			//do nothing
		}
	}
	/**
	 * This method handles the case of changing screens. The various 
	 * possible paths of fxml documents are checked to see which screen to change to.
	 * @param e ActionEvent object
	 * @param path String variable, which is the path of the fxml document
	 * @throws IOException
	 */
	private void handle(ActionEvent e, String path) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		
		//System.out.println("loader obtained" + loader);
		BorderPane root = (BorderPane)loader.load();
		Stage stage = (Stage) back.getScene().getWindow();
		Scene scene = new Scene(root);
		scene.getStylesheets().add("/application/application.css");
		stage.setScene(scene);
		if(path.equals("/View/adminpage.fxml"))
		{
			AdminController admin = loader.getController();
			admin.start();
		}
		stage.show();
	}
}
