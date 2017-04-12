package application;
	
import View.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * This class is the main class for the Photos Library application.
 * @author Pranav Kanukollu, pvk9
 * @author Nishanth Athreya, nsa48
 */
public class Photos extends Application {
	@Override
	/**
	 * This method is the main start method where the login screen is created.
	 */
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();  

			loader.setLocation(getClass().getResource("/View/loginpage.fxml"));
			BorderPane root = (BorderPane)loader.load(); 
			LoginController loginController = loader.getController();
			loginController.start();
			Scene scene = new Scene(root);
			scene.getStylesheets().add("/application/application.css");
			//scene.getStylesheets().add(getClass().getResource("loginpage.fxml"));
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
