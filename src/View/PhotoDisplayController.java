package View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class PhotoDisplayController {

	@FXML Button back;
	@FXML ImageView imageView;
	private String user;
	private String album;
	private String path;
	//private ArrayList<String> pics = new ArrayList<String>();
	private UserAlbum userAlbum;
	Stage stage;
	Scene scene;
	BorderPane pane;
	public void start(String path, String user, String album, BorderPane root)
	{
		this.user = user;
		this.album = album;
		this.path = path;
		//ArrayList<String> pics = userAlbum.getPics(user, album);
		pane = root;
		//System.out.println(back);
		//stage = (Stage) back.getScene().getWindow();
		ScrollPane scroll = new ScrollPane();
        TilePane tile = new TilePane();
		//ImageView imageView;
		imageView = createImageView(new File(path));
		tile.getChildren().addAll(imageView);
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Horizontal
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
        scroll.setFitToWidth(true);
        scroll.setContent(tile);
        pane.setCenter(scroll);
        if(scene != null)
        	scene.setRoot(null);
        try{
        	scene = new Scene(pane);
        	stage.setScene(scene);
        }catch(Exception e1){
        	
        }
	}
	public void back(ActionEvent e)
	{
		try{
			handle(e, "/View/insideAlbumPage.fxml");
		}catch(IOException e1)
		{
			//do nothing
		}
	}
	private ImageView createImageView(File file)
	{
		ImageView imageView = null;
		try {
        	
            final Image image = new Image(new FileInputStream(file), 105, 0, true, true);
            imageView = new ImageView(image);
            imageView.setFitWidth(400);
            imageView.setPreserveRatio(true);
		}catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
	}
	private void handle(ActionEvent e, String path) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		BorderPane root = (BorderPane)loader.load();
		Stage stage = (Stage) back.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		if(path.equals("/View/insideAlbumPage.fxml"))
		{
			InsideAlbumController inside = new InsideAlbumController();
			inside.start(user, album, root);
		}
		stage.show();
	}
}
