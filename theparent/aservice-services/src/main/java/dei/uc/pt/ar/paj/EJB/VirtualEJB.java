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
import dei.uc.pt.ar.paj.Facade.UserFacade;

@SessionScoped
public class VirtualEJB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<UserEntity> users;
	private ArrayList<MusicEntity> musics;
	private ArrayList<PlaylistEntity> playLists;
	private SimpleDateFormat dateFormat;
	private Date date;

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
	public ArrayList<MusicEntity> searchMusic(String search) {
		ArrayList<MusicEntity> searchResult = new ArrayList<MusicEntity>();
		ArrayList<MusicEntity> searchResultByArtist = new ArrayList<MusicEntity>();

		searchResult = searchMusicByTrack(search);
		searchResultByArtist = searchMusicByArtist(search);

		for (MusicEntity m : searchResultByArtist) {
			if (!searchResult.contains(m))
				searchResult.add(m);
		}

		return searchResult;
	}

	// Querie � BD para devolver a pesquisa por m�sica
	public ArrayList<MusicEntity> searchMusicByTrack(String search) {
		ArrayList<MusicEntity> searchResultByTrack = new ArrayList<MusicEntity>();

		for (MusicEntity m : musics) {
			if (m.getNomemusica().toLowerCase().contains(search.toLowerCase()))
				searchResultByTrack.add(m);
		}

		return searchResultByTrack;
	}

	// Querie � BD para devolver a pesquisa por artista
	public ArrayList<MusicEntity> searchMusicByArtist(String search) {
		ArrayList<MusicEntity> searchResultByArtist = new ArrayList<MusicEntity>();
		for (MusicEntity m : musics) {
			if (m.getInterprete().toLowerCase().contains(search.toLowerCase()))
				searchResultByArtist.add(m);
		}
		return searchResultByArtist;
	}

	// Search

	// Queries gen�ricas Start
	// PlayList
	public PlaylistEntity add(PlaylistEntity playList) {
		// adiciona a nova PlayList � BD, devolve a nova PlayList da BD
		this.playLists.add(playList);
		return playList;
	}

	public PlaylistEntity update(PlaylistEntity playList) {
		// actualiza os dados da PlayList na BD e devolve a mesma PlayList j� da
		// BD depois de actualizada
		return playList;
	}

	public void remove(PlaylistEntity playList) {
		this.playLists.remove(playList);
	}

	// PlayList

	// User
	public UserEntity add(UserEntity user) {
		// adiciona um novo User � BD, devolve o novo User da BD
		return user;
	}

	public void update(UserEntity user) {
		// actualiza os dados do utilizador na BD
		this.userFacade.updateUser(user);
	}

	public void remove(UserEntity user) {
		this.users.remove(user);
	}

	// User

	// Music
	public void add(MusicEntity music) {
		// adiciona uma nova Musica � BD
		this.musicFacade.merge(music);
	}

	public void update(MusicEntity music) {
		// actualiza os dados na BD
		this.musicFacade.updateSong(music);
	}

	public void remove(MusicEntity music) {
		this.users.remove(music);
	}

	// Music
	// Queries gen�ricas End
}