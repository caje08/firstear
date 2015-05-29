package dei.uc.pt.ar.paj;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import dei.uc.pt.ar.paj.MusicEntity;

import java.io.Serializable;



@Named
@SessionScoped
public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1333929301315965245L;
	
	private String track;

	public Player() {
		super();
	}
	
	public void playMusic(MusicEntity music){
		this.track = "resources/mp3/test.mp3";
	}

	public String getMusic() {
		return track;
	}

	public void setMusic(String music) {
		this.track = music;
	}
	

}
