package dei.uc.pt.ar.paj;

import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import dei.uc.pt.ar.paj.MusicEntity;
import dei.uc.pt.ar.paj.UserEntity;

import java.io.Serializable;

@Named
@SessionScoped
public class NewMusic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3861004602652852231L;
	
	private String newName;
	private String newAlbum;
	private String newArtist;
	private String genre;
	private String path;

	private MusicEntity music;

	private Date added;

	private Date launched;

	private UserEntity user;
	
	public void init(){
		this.newAlbum=this.newArtist=this.newName="";
	}
	
	public boolean verify(){
		if(this.newAlbum.equals(""))
			return false;
		
		if(this.newArtist.equals(""))
			return false;
		
		if(this.newName.equals(""))
			return false;
		
		return true;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getNewAlbum() {
		return newAlbum;
	}

	public void setNewAlbum(String newAlbum) {
		this.newAlbum = newAlbum;
	}

	public String getNewArtist() {
		return newArtist;
	}

	public void setNewArtist(String newArtist) {
		this.newArtist = newArtist;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
