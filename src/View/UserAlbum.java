package View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UserAlbum implements Serializable{
	private static final long serialVersionUID = 1L;
	//private HashMap<String, ArrayList<String>> albums;
	private HashMap<String, HashMap<String, ArrayList<String>>> albums;
	//private HashMap<String, String> pics;
	public UserAlbum(){
		albums = new HashMap<String, HashMap<String, ArrayList<String>>>();
	}
	public void addUser(String user){
		albums.put(user, null);//adding user
	}
	public void addAlbum(String user, String album_name){
		if(albums.containsKey(user)){
			if(albums.get(user) == null){//no albums exist for users
				HashMap<String, ArrayList<String>> album_list = new HashMap<String, ArrayList<String>>();
				album_list.put(album_name, new ArrayList<String>());
				albums.put(user, album_list);
			}else{//at least one album already present for user
				albums.get(user).put(album_name, new ArrayList<String>());
			}
		}
	}
	public void addPic(String user, String album, String pic){
		if(albums.get(user) != null){//user exists
			if(albums.get(user).containsKey(album)){//album exists
				if(albums.get(user).get(album) == null){//no pics in album
					ArrayList<String> pics = new ArrayList<String>();
					pics.add(pic);
				}else{//at least one pic in 
					albums.get(user).get(album).add(pic);
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
				albums.get(user).get(album).remove(pic);
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
	public ArrayList<String> getPics(String user, String album){
		if(albums.containsKey(user)){
			if(albums.get(user) != null){
				return albums.get(user).get(album);
			}
		}
		return null;
	}
}
