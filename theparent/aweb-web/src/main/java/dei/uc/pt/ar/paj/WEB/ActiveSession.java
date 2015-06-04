package dei.uc.pt.ar.paj.WEB;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dei.uc.pt.ar.paj.EJB.MusicEJBLocal;
import dei.uc.pt.ar.paj.EJB.PlaylistEJBLocal;
import dei.uc.pt.ar.paj.EJB.UserEJBLocal;
import dei.uc.pt.ar.paj.EJB.VirtualEJB;
import dei.uc.pt.ar.paj.Entities.MusicEntity;
import dei.uc.pt.ar.paj.Entities.PlaylistEntity;
import dei.uc.pt.ar.paj.Entities.UserEntity;
import dei.uc.pt.ar.paj.Facade.PlaylistFacade;
import dei.uc.pt.ar.paj.Facade.UserFacade;
import dei.uc.pt.ar.paj.login.UserSession;
import dei.uc.pt.ar.paj.render.Render;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	UserSession usersession;

	@Inject
	EditUser editUser;

	@Inject
	EditMusic editMusic;

	@Inject
	NewMusic newMusic;
	@EJB
	private UserEJBLocal userEJB;
	@EJB
	private MusicEJBLocal musicEJB;
	@EJB
	private PlaylistEJBLocal playlistEJB;
	@EJB
	private UserFacade userFacade;
	@EJB
	private PlaylistFacade playlistFacade;

	private HttpSession session;

	private UserEntity activeUser;

	private PlaylistEntity activePlayList;

	// private ArrayList <PlaylistEntity> userPlayLists;

	private MusicEntity activeMusic;

	private ArrayList<MusicEntity> musicSearch;

	private String newPlayListName;
	private String search;

	private Date date;

	private PlaylistEntity.Ordering order;

	private List<PlaylistEntity> userplaylistsdisplay;
	private boolean getMusicsByUser;
	private boolean sessionLoggedIn;
	private boolean isSearch;
	private String mensagem;

	public void init(UserEntity user) {
		this.isSearch = false;

		this.newPlayListName = "";

		this.render.init();

		this.activeUser = user;

		// this.userPlayLists=this.ejb.getPlayLists(activeUser);

		// Vai fora com a DB
		//this.activeUser.setUserplaylists(this.ejb.getPlayLists(activeUser));
		this.activeUser.setUserplaylists(userplaylistsdisplay);
		this.sessionLoggedIn = true;
		this.mensagem = "";
		startSession();
	}

	@PostConstruct
	public void init() {
		order = PlaylistEntity.Ordering.NAME_ASCEND;
		reOrder();
	}

	public PlaylistEntity.Ordering getOrder() {
		return order;
	}

	public void setOrder(PlaylistEntity.Ordering order) {
		this.order = order;
	}



	public List<PlaylistEntity> getUserplaylistsdisplay() {
		return this.playlistEJB.findOrdered(order, usersession.getLoggedUser());
	}

	public void setUserplaylistsdisplay(List<PlaylistEntity> userplaylistsdisplay) {
		this.userplaylistsdisplay = userplaylistsdisplay;
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

	private void reOrder() {
		this.activeUser=editUser.getActiveUser();
		try {
			userplaylistsdisplay=(playlistFacade.findOrdered(order, usersession.getLoggedUser()));
		} catch (Exception e) {
			System.err.println("Reordering exception: " + e);
		}
	}

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



	// Music Browser
	public void browseMusics() {
		this.render.browseMusics();
		this.getMusicsByUser = false;
		this.isSearch = false;
		getMusicLibrary();
	}

	public void browseMusicsFromUser() {
		this.render.browseMusics();
		this.getMusicsByUser = true;
		this.isSearch = false;
		getMusicLibrary();
	}

	public ArrayList<MusicEntity> getMusicLibrary() {
		if (isSearch)
			return this.musicSearch;

		// BD
		if (this.getMusicsByUser)
			return this.ejb.getMusics(this.activeUser);

		// BD
		return this.ejb.getMusics();		
	}

	public void sortPlayList(int index) {
		// queries � EJB
	}


	// Search
	public void searchMusic() {
		this.render.browseMusics();
		this.isSearch = true;
		// BD
		this.musicSearch = (ArrayList<MusicEntity>) this.ejb.searchMusic(this.search);
	}

	public void searchMusicByTrack() {
		this.render.browseMusics();
		this.isSearch = true;
		// BD
		this.musicSearch = (ArrayList<MusicEntity>) this.ejb.searchMusicByTrack(this.search);
	}

	public void searchMusicByArtist() {
		this.render.browseMusics();
		this.isSearch = true;
		// BD
		this.musicSearch = (ArrayList<MusicEntity>) this.ejb.searchMusicByArtist(this.search);
	}
	// Music Browser




	// User Edits
	public void editUser() {
		this.editUser.init(this.activeUser);
		this.render.setEditUser(true);
	}

	public void editUserCancel() {
		render.cancelEditUser();
	}

	public void saveUserChanges() {
		this.render.cancelEditUser();

		this.activeUser = this.editUser.saveUserChanges();

		this.ejb.update(this.activeUser);
	}

	public void saveUserPasswordChanges() {
		if(editUser.checkPw()){
			this.render.cancelEditUser();
			// BD
			this.ejb.update(this.activeUser);
		}
	}

	public void deleteUser(){
		this.activePlayList=null;
		this.editPlayList.setPlayListToEdit(null);
		
		this.userplaylistsdisplay=getUserplaylistsdisplay();

		//Remove as Playlists do User
		for(PlaylistEntity pl:this.userplaylistsdisplay){
			removePlayList(pl);
		}
		

		this.userplaylistsdisplay=new ArrayList <PlaylistEntity>();
		
		this.activeUser.setUserplaylists(getUserplaylistsdisplay());

		this.getMusicsByUser = true;
		this.isSearch = false;
		

		ArrayList<MusicEntity>musics=getMusicLibrary();
		UserEntity admin=userFacade.findByEmailPass("admin@admin", "d033e22ae348aeb5660fc2140aec35850c4da997");

		//Desassocia as Músicas
		for(MusicEntity m:musics){
			m.setUtilizador(admin);
			this.ejb.update(m);
		}
		
		this.ejb.remove(this.activeUser);
		logout();
	}
	// User Edits



	// Music Edits
	public void editThisMusic(MusicEntity music) {
		if (this.editMusic.editThisMusic(music, this.activeUser)) {
			this.render.setEditMusic(true);
		} else
			this.render.setEditMusic(false);
	}

	public String userPresentationMusic(MusicEntity music) {
		if (music.getOwner().getUserId() == this.activeUser.getUserId())
			return "Yours! Edit this Music";
		return music.getOwner().getName();
	}

	public void saveMusicChanges() {
		this.render.setEditMusic(false);

		// BD
		this.ejb.update(this.editMusic.saveChanges());
	}
	// Music Edits



	// New Music
	public void newMusicStart() {
		this.newMusic.init();
		this.render.setNewMusic(true);
	}

	public void newMusicEnd() throws IOException {
		this.date = new Date();

		if (this.newMusic.verify()) {
			MusicEntity music = new MusicEntity(this.newMusic.getNewName(),
					this.newMusic.getNewArtist(), this.newMusic.getNewAlbum(),
					"2015", this.activeUser, "path", "2015/05/28", "Gen�rico");
			//BD
			this.ejb.add(music);
			this.render.setNewMusic(false);
		}
	}

	public void newMusicCancel() {
		this.render.setNewMusic(false);
	}
	// New Music

	//Disassociate Music
	public void disassociateMusic() {
		UserEntity admin=userFacade.findByEmailPass("admin@admin", "d033e22ae348aeb5660fc2140aec35850c4da997");
		MusicEntity music=this.editMusic.getMusic();
		music.setUtilizador(admin);
		this.ejb.update(music);
		this.render.setEditMusic(false);
	}
	//Disassociate Music




	// New PlayList
	public void createPlayList() {
		if (!this.newPlayListName.equals("")) {
			PlaylistEntity newPlayList = new PlaylistEntity();
			newPlayList.setDesignacao(this.newPlayListName);
			newPlayList.setUtilizador(this.activeUser);
			newPlayList.setDatacriacao("data");

			this.newPlayListName="";

			// BD
			this.ejb.add(newPlayList);
		}
	}
	// New PlayList

	// Add Music To PlayList
	public void addMusicToPlayListStart(MusicEntity musicToAddToPlayList){
		this.editPlayList.addMusicToPlayListStart(musicToAddToPlayList);
		//		this.
	}

	public void addMusicToPlayListEnd(PlaylistEntity playList) {
		this.activePlayList = this.editPlayList.addMusicToPlayListEnd(playList);

		// BD
		this.ejb.update(this.editPlayList.getMusicToAddToPlayList());
		this.ejb.update(this.activePlayList);

		this.render.setEditPlayList(true);
		this.render.setAddMusicToPlayList(false);
	}
	// Add Music To PlayList

	//Remove music from Playlist
	public void removeMusicFromPlayList(MusicEntity music){
		this.editPlayList.removeMusicFromPlayList(music);
		this.ejb.update(this.activePlayList);
	}
	//Remove music from Playlist



	// Remove PlayList
	public void removePlayList(PlaylistEntity playList) {
		this.render.init();

		List <MusicEntity>emptyList=new ArrayList <MusicEntity>();
		playList.setSongs(emptyList);
		playList.setUtilizador(null);

		// BD
		this.ejb.remove(playList);
	}
	// Remove PlayList




	// Logout
	public void logout() {
		redirect();
		this.activeUser = null;
		this.activePlayList = null;
		// this.userPlayLists.clear();
		this.activeMusic = null;
		endSession();
	}

	private void redirect() {
		String redirect = "login.xhtml";
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();
		try {
			response.sendRedirect(redirect);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Logout





	// In�cio e Fim da sess�o http
	public void startSession() {
		this.session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		this.session.setAttribute("sessionLoggedIn", true);
	}

	public void endSession() {
		if (this.session != null)
			this.session.invalidate();
		startSession();
		this.sessionLoggedIn = false;
	}
	// In�cio e Fim da sess�o http
}
