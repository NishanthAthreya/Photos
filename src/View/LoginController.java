package View;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class LoginController {
 @FXML Button exit;
 @FXML Button go;
 @FXML TextField text;
 public void start()
    {
	 
    }
 public void exit(ActionEvent e){
  
 }
 public void go(ActionEvent e){
  System.out.println("go");
  if(text.getText().equalsIgnoreCase("Admin")){
   try {
    handle(e);
   } catch (IOException e1) {
    //do nothing
   }
  }
  }
 private void handle(ActionEvent e) throws IOException{
  System.out.println("handle");
  Stage stage;
  Parent root;
  stage=(Stage) go.getScene().getWindow();
  //load up OTHER FXML document
  root = FXMLLoader.load(getClass().getResource("/View/adminpage.fxml"));
  Scene scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
  
 }
}