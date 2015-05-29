package dei.uc.pt.ar.paj;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;


@Named
@SessionScoped
public class Render implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7349283133209641674L;

	private boolean editPlayList;
	private boolean addMusicToPlayList;
	private boolean addMusic;
	private boolean editUser;
	private boolean editUserPassword;
	private boolean editMusic;
	private boolean newMusic;

	public void init(){
		this.newMusic=false;
		this.editMusic=false;
		this.editPlayList=false;
		this.addMusicToPlayList=false;
		this.addMusic=false;
		this.editUser=false;
		this.editUserPassword=false;
	}

	public boolean isNewMusic() {
		return newMusic;
	}

	public void setNewMusic(boolean newMusic) {
		this.newMusic = newMusic;
	}

	public boolean isEditMusic() {
		return editMusic;
	}

	public void setEditMusic(boolean editMusic) {
		this.editMusic = editMusic;
	}

	public void cancelEditUser(){
		this.editUser=false;
		this.editUserPassword=false;
	}

	public boolean isEditUserPassword() {
		return editUserPassword;
	}

	public void setEditUserPassword(boolean editUserPassword) {
		this.editUserPassword = editUserPassword;
	}

	public boolean isEditUser() {
		return editUser;
	}

	public void setEditUser(boolean editUser) {
		this.editUser = editUser;
	}

	public boolean isAddMusic() {
		return addMusic;
	}

	public void setAddMusic(boolean addMusic) {
		this.addMusic = addMusic;
	}

	public void browseMusics(){
		this.editPlayList=false;
	}

	public boolean isEditPlayList() {
		return editPlayList;
	}

	public void setEditPlayList(boolean editPlayList) {
		this.editPlayList = editPlayList;
	}

	public boolean isAddMusicToPlayList() {
		return addMusicToPlayList;
	}

	public void setAddMusicToPlayList(boolean addMusicToPlayList) {
		this.addMusicToPlayList = addMusicToPlayList;
	}

}
