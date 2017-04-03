package View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class InsideAlbumController {
	@FXML Button add;
	@SuppressWarnings("rawtypes")
	@FXML ChoiceBox choice;
	private UserAlbum userAlbum;
	private String user_name,album_name;
	private static final String filename = "userAlbums.dat";
	private ArrayList<String> pics;
	private int option;
	Stage stage;
	Scene scene;
	BorderPane pane;
	@SuppressWarnings("unchecked")
	public void start(String user, String album, BorderPane root){
		this.user_name = user;
		this.album_name = album;
		option = 0;
		choice.setItems(FXCollections.observableArrayList("Options", "Copy", "Move", "Remove"));
		choice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, Number value, Number new_value){
				option = new_value.intValue();
			}
		});
		openAlbums();
		pane = root;
		stage = (Stage) add.getScene().getWindow();
		ScrollPane scroll = new ScrollPane();
        TilePane tile = new TilePane();
        root.setStyle("-fx-background-color: DAE6F3;");
        //tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);
        pics = userAlbum.getPics(user_name, album_name);
        if(pics == null)
        	return;
        for(int i = 0;i < pics.size();i++){
        	System.out.println(pics.get(i));
        	ImageView imageView;
            imageView = createImageView(new File(pics.get(i)));
            tile.getChildren().addAll(imageView);
        }
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Horizontal
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
        scroll.setFitToWidth(true);
        scroll.setContent(tile);
        root.setCenter(scroll);
        scene = new Scene(root);
        stage.setScene(scene);
	}
	public void add(ActionEvent e){
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extentionFilter);
		//Set to user directory or go to default if cannot access
		String userDirectoryString = System.getProperty("user.home");
		File userDirectory = new File(userDirectoryString);
		if(!userDirectory.canRead()) {
		    userDirectory = new File("c:/");
		}
		fileChooser.setInitialDirectory(userDirectory);
		//Choose the file
		File chosenFile = fileChooser.showOpenDialog(null);
		//Make sure a file was selected, if not return default
		String path;
		if(chosenFile != null) {
		    path = chosenFile.getPath();
		    System.out.println(path);
		    if(userAlbum != null){
		    	userAlbum.addPic(this.user_name, this.album_name, path);
		    }
		} else {
		    //default return value
		    path = null;
		}
		saveAlbums();
		ScrollPane scroll = new ScrollPane();
        TilePane tile = new TilePane();
        pane.setStyle("-fx-background-color: DAE6F3;");
        //tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);
        pics = userAlbum.getPics(user_name, album_name);
        if(pics == null)
        	System.out.println("oh");
        if(pics == null)
        	return;
        for(int i = 0;i < pics.size();i++){
        	System.out.println(pics.get(i));
        	ImageView imageView;
            imageView = createImageView(new File(pics.get(i)));
            tile.getChildren().addAll(imageView);
        }
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
	
	private void saveAlbums(){
		try {
			@SuppressWarnings("resource")
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(filename));
			oos.writeObject(userAlbum);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			//problem
		}
		
	}
	
	private void openAlbums(){
		try{
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(filename));
			userAlbum = (UserAlbum)ois.readObject();
			if(userAlbum == null)
				userAlbum = new UserAlbum();
		}catch(Exception e){
			//no users in system yet aside from Admin
			userAlbum = new UserAlbum();
		}
	}
	private ImageView createImageView(final File imageFile) {
        // DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
        // The last two arguments are: preserveRatio, and use smooth (slower)
        // resizing

        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 105, 0, true,
                    true);
            imageView = new ImageView(image);
            imageView.setFitWidth(105);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    	if(mouseEvent.getClickCount() ==1){
                    		if(option == 3){//deleting
                    			userAlbum.deletePic(user_name, album_name, imageFile.getPath());
                    			ScrollPane scroll = new ScrollPane();
                    	        TilePane tile = new TilePane();
                    	        pane.setStyle("-fx-background-color: DAE6F3;");
                    	        //tile.setPadding(new Insets(15, 15, 15, 15));
                    	        tile.setHgap(15);
                    	        pics = userAlbum.getPics(user_name, album_name);
                    	        if(pics == null)
                    	        	System.out.println("oh");
                    	        if(pics == null)
                    	        	return;
                    	        for(int i = 0;i < pics.size();i++){
                    	        	System.out.println(pics.get(i));
                    	        	ImageView imageView;
                    	            imageView = createImageView(new File(pics.get(i)));
                    	            tile.getChildren().addAll(imageView);
                    	        }
                    	        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Horizontal
                    	        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
                    	        scroll.setFitToWidth(true);
                    	        scroll.setContent(tile);
                    	        pane.setCenter(scroll);
                    	        if(scene !=null)
                    	        	scene.setRoot(null);
                    	        try{
                    	        	scene = new Scene(pane);
                    	        	stage.setScene(scene);
                    	        }catch(Exception e){
                    	        	
                    	        }
                    	        saveAlbums();
                    	        //stage.setScene(scene);
                    			
                    		}
                    	}
                        if(mouseEvent.getClickCount() == 2){
                            try {
                                BorderPane borderPane = new BorderPane();
                                ImageView imageView = new ImageView();
                                Image image = new Image(new FileInputStream(imageFile));
                                imageView.setImage(image);
                                imageView.setStyle("-fx-background-color: BLACK");
                                imageView.setFitHeight(stage.getHeight() - 10);
                                imageView.setPreserveRatio(true);
                                imageView.setSmooth(true);
                                imageView.setCache(true);
                                borderPane.setCenter(imageView);
                                borderPane.setStyle("-fx-background-color: BLACK");
                                Stage newStage = new Stage();
                                newStage.setWidth(stage.getWidth());
                                newStage.setHeight(stage.getHeight());
                                newStage.setTitle(imageFile.getName());
                                Scene scene = new Scene(borderPane,Color.BLACK);
                                newStage.setScene(scene);
                                newStage.show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }


	
	
}
