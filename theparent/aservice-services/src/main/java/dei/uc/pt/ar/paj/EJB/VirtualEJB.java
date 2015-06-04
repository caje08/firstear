package dei.uc.pt.ar.paj.EJB;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dei.uc.pt.ar.paj.Entities.MusicEntity;
import dei.uc.pt.ar.paj.Entities.PlaylistEntity;
import dei.uc.pt.ar.paj.Entities.UserEntity;
import dei.uc.pt.ar.paj.Entities.MusicEntity.Ordering;
import dei.uc.pt.ar.paj.Facade.MusicFacade;
import dei.uc.pt.ar.paj.Facade.PlaylistFacade;
import dei.uc.pt.ar.paj.Facade.UserFacade;

@SessionScoped
public class VirtualEJB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<UserEntity> users;

	@EJB
	private UserEJBLocal userEJB;
	@EJB
	private UserFacade userFacade;



	@EJB
	private MusicEJBLocal musicEJB;
	@EJB
	private MusicFacade musicFacade;

	private MusicEntity.Ordering orderMusic;


	@EJB
	private PlaylistEJBLocal playlistEJB;
	@EJB
	private PlaylistFacade playlistFacade;
	
	private PlaylistEntity.Ordering orderPlaylist;

	public void populate() {
		//		this.dateFormat = new SimpleDateFormat("HH:mm:ss");
		//		this.users = new ArrayList<UserEntity>();
		//		this.musics = new ArrayList<MusicEntity>();
		//		this.playLists = new ArrayList<PlaylistEntity>();

		//		generateUsers();
		//		generateMusics();
		//		generatePlaylists();
	}

	public UserEntity getUser(int i) {
		return users.get(i);
	}

	// Queria � BD para devolver as PlayLists do User
	public ArrayList<PlaylistEntity> getPlayLists(UserEntity user) {
		return null;
	}


	// Querie � BD para devolver todas as m�sicas
	public ArrayList<MusicEntity> getMusics() {
		return (ArrayList<MusicEntity>) musicEJB.getMusicas();
	}

	// Querie � BD para devolver as m�sicas por User
	public ArrayList<MusicEntity> getMusics(UserEntity user) {
		return (ArrayList<MusicEntity>) musicEJB.findOrdered(Ordering.FIND_BY_OWNER, user);
	}


	// Search
	public List<MusicEntity> searchMusic(String search) {
		return this.musicEJB.search(search);
	}

	// Querie � BD para devolver a pesquisa por m�sica
	public List<MusicEntity> searchMusicByTrack(String search) {
		return this.musicEJB.searchByTrack(search);
	}

	// Querie � BD para devolver a pesquisa por artista
	public List<MusicEntity> searchMusicByArtist(String search) {
		return this.musicEJB.searchByArtist(search);
	}

	// Search


	// Queries gen�ricas Start
	// PlayList
	public void add(PlaylistEntity playList) {
		// adiciona a nova PlayList � BD
		this.playlistFacade.merge(playList);
	}

	public void update(PlaylistEntity playList) {
		this.playlistFacade.edit(playList);
	}

	public void remove(PlaylistEntity playList) {
		this.playlistFacade.remove(playList);
	}
	// PlayList

	// User
	public void add(UserEntity user) {
		// adiciona um novo User � BD, devolve o novo User da BD
		this.userFacade.merge(user);
	}

	public void update(UserEntity user) {
		// actualiza os dados do utilizador na BD
		this.userFacade.edit(user);
	}

	public void remove(UserEntity user) {
		this.userFacade.remove(user);
	}
	// User

	// Music
	public void add(MusicEntity music) {
		// adiciona uma nova Musica � BD
		this.musicFacade.merge(music);
	}

	public void update(MusicEntity music) {
		// actualiza os dados na BD
		this.musicFacade.edit(music);
	}

	public void remove(MusicEntity music) {
		this.musicFacade.remove(music);
	}
	// Music
	// Queries gen�ricas End
}