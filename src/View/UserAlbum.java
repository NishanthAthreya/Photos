package View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UserAlbum implements Serializable{
	private static final long serialVersionUID = 1L;
	//private HashMap<String, ArrayList<String>> albums;
	private HashMap<String, HashMap<String, ArrayList<Picture>>> albums;
	//private HashMap<String, String> pics;
	//private HashMap<String, HashMap<String, ArrayList<Picture>>> albums;
	public UserAlbum(){
		//albums = new HashMap<String, HashMap<String, ArrayList<String>>>();
		albums = new HashMap<String, HashMap<String, ArrayList<Picture>>>();
	}
	public void addUser(String user){
		albums.put(user, null);//adding user
	}
	public void addAlbum(String user, String album_name){
		if(albums.containsKey(user)){
			if(albums.get(user) == null){//no albums exist for users
				//HashMap<String, ArrayList<String>> album_list = new HashMap<String, ArrayList<String>>();
				HashMap<String, ArrayList<Picture>> album_list = new HashMap<String, ArrayList<Picture>>();
				//album_list.put(album_name, new ArrayList<String>());
				album_list.put(album_name, new ArrayList<Picture>());
				albums.put(user, album_list);
			}else{//at least one album already present for user
				//albums.get(user).put(album_name, new ArrayList<String>());
				albums.get(user).put(album_name, new ArrayList<Picture>());
			}
		}
	}
	public void addPic(String user, String album, String pic){
		Picture picture = new Picture(pic, "sample");
		if(albums.get(user) != null){//user exists
			if(albums.get(user).containsKey(album)){//album exists
				if(albums.get(user).get(album) == null){//no pics in album
					ArrayList<Picture> pics = new ArrayList<Picture>();
					pics.add(picture);
				}else{//at least one pic in 
					albums.get(user).get(album).add(picture);
				}
			}
		}
	}
	public void deleteUser(String user){
		albums.remove(user);
	}
	public void deleteAlbum(String user, String album){
		if(albums.containsKey(user)){
			if(albums.get(user).containsKey(album)){
				albums.get(user).remove(album);
			}
		}
	}
	public void deletePic(String user, String album, String pic){
		if(albums.containsKey(user)){
			if(albums.get(user).containsKey(album) && albums.get(user).get(album) != null){
				albums.get(user).get(album).remove(new Picture(pic, "sample"));
			}
		}
	}
	public Iterator<String> getAlbums(String user){
		if(albums.containsKey(user)){
			if(albums.get(user) != null){
				return albums.get(user).keySet().iterator();
			}
		}
		return null;
	}
	public ArrayList<Picture> getPics(String user, String album){
		if(albums.containsKey(user)){
			if(albums.get(user) != null){
				return albums.get(user).get(album);
			}
		}
		return null;
	}
}
