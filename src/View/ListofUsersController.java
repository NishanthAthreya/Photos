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

public class ListofUsersController {
	@FXML ListView<String> listofusers;
	@FXML Button back;
	//private ObservableList<String> obsList;
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
	public void back(ActionEvent e)
	{
		try{
			handle(e,"/View/adminpage.fxml");
		}catch(Exception e1)
		{
			//do nothing
		}
	}
	private void handle(ActionEvent e, String path) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		
		//System.out.println("loader obtained" + loader);
		BorderPane root = (BorderPane)loader.load();
		Stage stage = (Stage) back.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		if(path.equals("/View/adminpage.fxml"))
		{
			AdminController admin = loader.getController();
			admin.start();
		}
		stage.show();
	}
}
