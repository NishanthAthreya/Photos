package View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class SearchResultsController {

	BorderPane pane;
	Stage stage;
	Scene scene;
	UserAlbum userAlbum;
	String user;
	ArrayList<Picture> pics;
	@FXML Button back;
	@FXML Button create;
	@FXML TextField album;
	public void start(ArrayList<Picture> result, BorderPane root, Stage stage1, UserAlbum us, String user)
	{
		//System.out.println("entered");
		//System.out.println(result.size());
		pics = result;
		this.user = user;
		userAlbum = us;
		pane = root;
		stage = (Stage)back.getScene().getWindow();
		ScrollPane scroll = new ScrollPane();
        TilePane tile = new TilePane();
        tile.setHgap(1500);
        tile.setVgap(25);
		for (int i = 0; i<result.size();i++)
		{
			//System.out.println("Path: " + result.get(i).getPath());
			HBox hbox;
			hbox= createHbox(new File(result.get(i).getPath()), new Label(result.get(i).getCaption()));
            tile.getChildren().addAll(hbox);
		}
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Horizontal
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
        scroll.setFitToWidth(true);
        scroll.setContent(tile);
        root.setCenter(scroll);
        if(scene != null)
        	scene.setRoot(null);
        try{
        	scene = new Scene(pane);
        	stage.setScene(scene);
        }catch(Exception e1){
        	
        }
	}
	private HBox createHbox(final File imageFile, Label l)
	{
		HBox hbox = new HBox();
		//Label l = new Label("search");
		  ImageView imageView = null;
	        try {
	        	
	            final Image image = new Image(new FileInputStream(imageFile), 105, 0, true,
	                    true);
	           // Label l = new Label("search");
	           
	            imageView = new ImageView(image);
	            
	            imageView.setFitWidth(105);
	 } catch (FileNotFoundException ex) {
         ex.printStackTrace();
     }
   //  System.out.println(l.getText());
     l.setGraphic(imageView);
     hbox.setSpacing(10);
     hbox.getChildren().add((l));
     return hbox;
}
	public void createAlbum(ActionEvent e)
	{
		try{
			handle(e, "/View/nonadminpage.fxml");
		}catch(IOException e1)
		{
			//do nothing
		}
	}
	public void back(ActionEvent e)
	{
		try{
			handle(e, "/View/searchphotos.fxml");
		}catch(IOException e1)
		{
			//do nothing
		}
	}
	private void save(){
		try {
			@SuppressWarnings("resource")
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream("userAlbums.dat"));
			oos.writeObject(userAlbum);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			//problem
		}
	}
	private void handle(ActionEvent e, String path) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		
		//System.out.println("loader obtained" + loader);
		BorderPane root = (BorderPane)loader.load();
		Stage stage = (Stage) back.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		if (path.equals("/View/searchphotos.fxml"))
		{
			SearchedPhotosController search = loader.getController();
			search.start(userAlbum, user);
		}
		if (path.equals("/View/nonadminpage.fxml"))
		{
			NonAdminController nonadmin = loader.getController();
			userAlbum.addAlbum(user, album.getText());
			for (int i = 0; i<pics.size();i++)
			{
			userAlbum.addPic(user, album.getText(), pics.get(i).getPath());
			}
			save();
			nonadmin.start(user);
		}
		stage.show();
	}
}