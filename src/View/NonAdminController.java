package View;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NonAdminController {
	@FXML Button quit;
	@FXML Button logout;
	@FXML Button createAlbum;
	@FXML Button viewAlbum;
	public void start(){
		
	}
	public void quit(ActionEvent e){
		Platform.exit();
		System.exit(0);
	}
	public void logout(ActionEvent e){
		try{
			handle(e, "/View/loginpage.fxml");
		}catch(Exception e1){
			//do nothing
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
