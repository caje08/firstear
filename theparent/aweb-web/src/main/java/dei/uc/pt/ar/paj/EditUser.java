package dei.uc.pt.ar.paj;

import java.util.Date;

import javax.enterprise.context.SessionScoped;

import javax.inject.Named;

import dei.uc.pt.ar.paj.UserEntity;

import java.io.Serializable;

@Named
@SessionScoped
public class EditUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1979160830875020805L;

	private String newUserName;
	private String oldPass;
	private String newPass;
	private String repeatPass;	
	private String newEmail;
	private String teste;

	private Date birth;

	private UserEntity activeUser;

	public void init(UserEntity activeUser){
		this.activeUser=activeUser;
		this.newUserName=this.activeUser.getName();
		this.newEmail=this.activeUser.getEmail();
		this.oldPass=this.newPass=this.repeatPass="";
		this.teste="NewUserName";
	}

	public UserEntity getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(UserEntity activeUser) {
		this.activeUser = activeUser;
	}

	public String getNewUserName() {
		return newUserName;
	}

	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getRepeatPass() {
		return repeatPass;
	}

	public void setRepeatPass(String repeatPass) {
		this.repeatPass = repeatPass;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public UserEntity saveUserChanges(){
		if(!this.newUserName.isEmpty())
			this.activeUser.setName(this.newUserName);

		if(!this.newEmail.isEmpty())
			this.activeUser.setEmail(this.newEmail);

		return this.activeUser;
	}

	public UserEntity saveUserPasswordChanges() {
		//POR COMPLETAR!!!!!!!!!!!!!!!
		return this.activeUser;
	}

}
