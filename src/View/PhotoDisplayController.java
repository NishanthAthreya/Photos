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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PhotoDisplayController {

	@FXML Button back;
	@FXML Button next;
	@FXML Button previous;
	@FXML ImageView imageView;
	private String user;
	private String album;
	//private String path;
	//private ArrayList<String> pics = new ArrayList<String>();
	private UserAlbum userAlbum;
	Stage stage;
	Scene scene;
	BorderPane pane;
	int index = 0;
	boolean nextVal = true;
	ArrayList<Picture> pictures = new ArrayList<Picture>();
	public void start(String path, String user, String album, BorderPane root, ArrayList<Picture> pics)
	{
		this.user = user;
		this.album = album;
		this.index = pics.indexOf(new Picture(path, " "));
		//this.path = path;
		userAlbum = new UserAlbum();
		//ArrayList<String> pics = userAlbum.getPics(user, album);
		//System.out.println("first pic: " + pics.get(0)+ " " + "second pic: " + pics.get(1) + " " + "third pic: " + pics.get(2));
		pane = root;
		for (int i = 0; i<pics.size();i++)
		{
			pictures.add(i, pics.get(i));
		}
		String cap = "";
		for (int i = 0; i<pictures.size();i++)
		{
			if (pictures.get(i).getPath().equals(path))
			{
				cap = pictures.get(i).getCaption();
			}
		}
		VBox vbox = new VBox();
		Label l = new Label("Caption: " + cap);
		//index = pics.indexOf(path);
		imageView = createImageView(new File(path));
		l.setGraphic(imageView);
		//hbox.setSpacing(10);
		vbox.getChildren().add(l);
		GridPane g = (GridPane)pane.getCenter();
		g.add(vbox, 1, 0);
		//g.add(back, g.get, 0);
		pane.setCenter(g);
		Stage stage = (Stage) vbox.getScene().getWindow();
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
			handle(e, "/View/insideAlbumPage.fxml", this.user,this.album);
		}catch(IOException e1)
		{
			//do nothing
		}
	}
	/*public void next(ActionEvent e)
	{
		if(index + 1 < pictures.size()){
			Picture pic = pictures.get(index + 1);
			index++;
			imageView = createImageView(new File(pic.getPath()));
			GridPane g = (GridPane)pane.getCenter();
			g.add(imageView, 1, 0);
			//g.add(back, g.get, 0);
			pane.setCenter(g);
			Stage stage = (Stage) imageView.getScene().getWindow();
	        if(scene != null)
	        	scene.setRoot(null);
	        try{
	        	scene = new Scene(pane);
	        	stage.setScene(scene);
	        }catch(Exception e1){
	        	
	        }
		}
		//try{
			//nextVal = true;
			//handle(e, "/View/photodisplay.fxml",this.user,this.album);
		//}catch(IOException e1)
		//{
			//do nothing
		//}
	}
	public void previous(ActionEvent e)
	{
		//try{
			//nextVal = false;
			//handle(e, "/View/photodisplay.fxml",this.user,this.album);
			if(index - 1 >= 0){
				Picture pic = pictures.get(index - 1);
				index--;
				imageView = createImageView(new File(pic.getPath()));
				GridPane g = (GridPane)pane.getCenter();
				g.add(imageView, 1, 0);
				//g.add(back, g.get, 0);
				pane.setCenter(g);
				Stage stage = (Stage) imageView.getScene().getWindow();
		        if(scene != null)
		        	scene.setRoot(null);
		        try{
		        	scene = new Scene(pane);
		        	stage.setScene(scene);
		        }catch(Exception e1){
		        	
		        }
			}
			
		//}catch(IOException e2)
		//{
			//do nothing
		//}
	}*/
	public void next(ActionEvent e)
		{
			try{
				nextVal = true;
				handle(e, "/View/photodisplay.fxml",this.user,this.album);
			}catch(IOException e1)
			{
				//do nothing
			}
		}
		public void previous(ActionEvent e)
		{
			try{
				nextVal = false;
				handle(e, "/View/photodisplay.fxml",this.user,this.album);
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
            imageView.setFitWidth(250);
            imageView.setPreserveRatio(true);
		}catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
	}
	private void handle(ActionEvent e, String path, String user_name, String user_album) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
		BorderPane root = (BorderPane)loader.load();
		Stage stage = (Stage) back.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		if(path.equals("/View/insideAlbumPage.fxml"))
		{
			//InsideAlbumController inside = new InsideAlbumController();
			InsideAlbumController inside = loader.getController();
			inside.start(this.user, this.album, root, stage);
		}
		if (path.equals(("/View/photodisplay.fxml"))&&nextVal==true)
		{
			
			PhotoDisplayController photoDisp = loader.getController();
			try{
			photoDisp.start(pictures.get(index+1).getPath(), user_name, user_album, root, pictures);
			}catch(IndexOutOfBoundsException e1)
			{
				// put alert here saying no more next!!!!!
			}
		}
		if (path.equals(("/View/photodisplay.fxml"))&&nextVal==false)
		{
			PhotoDisplayController photoDisp = loader.getController();
			try{
			photoDisp.start(pictures.get(index-1).getPath(), user_name, user_album, root, pictures);
			}catch(IndexOutOfBoundsException e1)
			{
				// put alert here saying no more previous!!!!!
			}
		}
		stage.show();
	}
}
