package dei.uc.pt.ar.paj;


import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dei.uc.pt.ar.paj.MusicEntity;
import dei.uc.pt.ar.paj.PlaylistEntity;
import dei.uc.pt.ar.paj.UserEntity;
import dei.uc.pt.ar.paj.VirtualEJB;
import dei.uc.pt.ar.paj.Render;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


@Named
@SessionScoped
public class ActiveSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	VirtualEJB ejb;

	@Inject
	Render render;

	@Inject
	EditPlayList editPlayList;

	@Inject
	EditUser editUser;

	@Inject
	EditMusic editMusic;
	
	@Inject
	NewMusic newMusic;

	private HttpSession session;

	private UserEntity activeUser;
	
	private PlaylistEntity activePlayList;
	
//	private ArrayList <PlaylistEntity> userPlayLists;
	
	private MusicEntity activeMusic;
	
	private ArrayList <MusicEntity> musicSearch;

	private String newPlayListName;
	private String search;
	
	private Date date;

	private boolean getMusicsByUser;
	private boolean sessionLoggedIn;
	private boolean isSearch;

	public void init(UserEntity user){
		this.isSearch=false;
		
		this.newPlayListName="";

		this.render.init();

		this.activeUser=user;

//		this.userPlayLists=this.ejb.getPlayLists(activeUser);
		
		//Vai fora com a DB
		this.activeUser.setUserplaylists(this.ejb.getPlayLists(activeUser));
		this.sessionLoggedIn=true;
		startSession();
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getNewPlayListName() {
		return newPlayListName;
	}

	public void setNewPlayListName(String newPlayListName) {
		this.newPlayListName = newPlayListName;
	}

	public boolean isGetMusicsByUser() {
		return getMusicsByUser;
	}

	public void setGetMusicsByUser(boolean getMusicsByUser) {
		this.getMusicsByUser = getMusicsByUser;
	}

	
//	public ArrayList<PlaylistEntity> getUserPlayLists() {
//		return userPlayLists;
//	}
//
//	public void setUserPlayLists(ArrayList<PlaylistEntity> userPlayLists) {
//		this.userPlayLists = userPlayLists;
//	}

	public UserEntity getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(UserEntity activeUser) {
		this.activeUser = activeUser;
	}

	public void setActivePlayList(PlaylistEntity activePlayList) {
		this.activePlayList = activePlayList;
		this.editPlayList.setPlayListToEdit(activePlayList);
		this.render.setEditPlayList(true);
	}

	public PlaylistEntity getActivePlayList() {
		return activePlayList;
	}

	public MusicEntity getActiveMusic() {
		return activeMusic;
	}

	public void setActiveMusic(MusicEntity activeMusic) {
		this.activeMusic = activeMusic;
	}
	
	
	
	//Music Browser
	public void browseMusics(){
		this.render.browseMusics();
		this.getMusicsByUser=false;
		this.isSearch=false;
		getMusicLibrary();
	}

	public void browseMusicsFromUser(){
		this.render.browseMusics();
		this.getMusicsByUser=true;
		this.isSearch=false;
		getMusicLibrary();
	}

	public ArrayList<MusicEntity> getMusicLibrary(){
		if(isSearch)
			return this.musicSearch;

		//BD
		if(this.getMusicsByUser)
			return this.ejb.getMusics(this.activeUser);

		//BD
		return this.ejb.getMusics();
	}

	public void sortPlayList(int index){
		//queries � EJB
	}
	
	//Search
	public void searchMusic(){
		this.render.browseMusics();
		this.isSearch=true;
		//BD
		this.musicSearch=this.ejb.searchMusic(this.search);
	}

	public void searchMusicByTrack(){
		this.render.browseMusics();
		this.isSearch=true;
		//BD
		this.musicSearch=this.ejb.searchMusicByTrack(this.search);
	}
	
	public void searchMusicByArtist(){
		this.render.browseMusics();
		this.isSearch=true;
		//BD
		this.musicSearch=this.ejb.searchMusicByArtist(this.search);
	}
	//Music Browser
	
	
	
	//User Edits
	public void editUser(){
		this.editUser.init(this.activeUser);
		this.render.setEditUser(true);
	}

	public void editUserCancel(){
		render.cancelEditUser();
	}

	public void saveUserChanges(){
		this.render.cancelEditUser();

		this.activeUser=this.editUser.saveUserChanges();

		//BD
		init(this.ejb.update(this.activeUser));
	}

	public void saveUserPasswordChanges(){
		this.render.cancelEditUser();

		//POR COMPLETAR!!!!!!!!!!!!!!!!!!
		this.activeUser=this.editUser.saveUserPasswordChanges();

		//BD
		init(this.ejb.update(this.activeUser));
	}
	//User Edits



	//Music Edits
	public void editThisMusic(MusicEntity music){
		if(this.editMusic.editThisMusic(music, this.activeUser)){
			this.render.setEditMusic(true);
		}else this.render.setEditMusic(false);
	}

	public String userPresentationMusic(MusicEntity music){
		if(music.getOwner()==this.activeUser)
			return "Yours! Edit this Music";
		return music.getOwner().getName();
	}

	public void saveMusicChanges(){
		this.render.setEditMusic(false);

		//BD
		this.ejb.update(this.editMusic.saveChanges());

		//BD
		init(this.ejb.update(this.activeUser));
	}
	//Music Edits
	
	
	
	//New Music
	public void newMusicStart(){
		this.newMusic.init();
		this.render.setNewMusic(true);
	}
	
	public void newMusicEnd(){
		this.date = new Date();
		
		if(this.newMusic.verify()){
			MusicEntity music=new MusicEntity(this.newMusic.getNewName(), this.newMusic.getNewArtist(), this.newMusic.getNewAlbum(), "2015", this.activeUser, "path", "2015/05/28", "Gen�rico");
			this.ejb.add(music);
			this.render.setNewMusic(false);
		}
	}
	
	public void newMusicCancel(){
		this.render.setNewMusic(false);
	}
	//New Music
	
	
	
	//New PlayList
	public void createPlayList(){
		if(!this.newPlayListName.equals("")){
			PlaylistEntity newPlayList=new PlaylistEntity();
			newPlayList.setDesignacao(this.newPlayListName);
			newPlayList.setUtilizador(this.activeUser);

			//BD
			this.ejb.add(newPlayList);

			//BD
			init(this.ejb.update(this.activeUser));
		}	
	}
	//New PlayList
	
	
	
	//Add Music To PlayList
	public void addMusicToPlayListEnd(PlaylistEntity playList){

		this.activePlayList=this.editPlayList.addMusicToPlayListEnd(playList);

		//BD
		this.activePlayList=this.ejb.update(this.activePlayList);

		this.render.setEditPlayList(true);
		this.render.setAddMusicToPlayList(false);
	}
	//Add Music To PlayList
	
	
	
	//Remove PlayList
	public void removePlayList(PlaylistEntity playList){
		this.render.init();

		//BD
		this.ejb.remove(playList);
		
		//Vai fora depois da DB?
		this.activeUser.setUserplaylists(this.ejb.getPlayLists(activeUser));

//		//BD
//		this.userPlayLists=this.ejb.getPlayLists(this.activeUser);
	}
	//Remove PlayList









	//Logout
	public void logout(){
		redirect();
		this.activeUser=null;
		this.activePlayList=null;
//		this.userPlayLists.clear();
		this.activeMusic=null;
		endSession();
	}

	private void redirect(){
		String redirect="login.xhtml";
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
		try {
			response.sendRedirect(redirect);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Logout
	
	

	//In�cio e Fim da sess�o http
	public void startSession(){
		this.session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.session.setAttribute("sessionLoggedIn",true);
	}

	public void endSession(){
		if(this.session!=null) 
			this.session.invalidate();
		startSession();
		this.sessionLoggedIn=false;
	}
	//In�cio e Fim da sess�o http
}
