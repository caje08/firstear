package dei.uc.pt.ar.paj;

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

import dei.uc.pt.ar.paj.MusicEntity;
import dei.uc.pt.ar.paj.PlaylistEntity;
import dei.uc.pt.ar.paj.UserEntity;

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

	// private UserEJB userejb;

	public void populate() {
		this.dateFormat = new SimpleDateFormat("HH:mm:ss");
		this.users = new ArrayList<UserEntity>();
		this.musics = new ArrayList<MusicEntity>();
		this.playLists = new ArrayList<PlaylistEntity>();

		generateUsers();
		generateMusics();
		generatePlaylists();
	}

	public UserEntity getUser(int i) {
		return users.get(i);
	}

	// Queria � BD para devolver as PlayLists do User
	public ArrayList<PlaylistEntity> getPlayLists(UserEntity user) {
		ArrayList<PlaylistEntity> userPlayLists = new ArrayList<PlaylistEntity>();

		for (PlaylistEntity p : this.playLists) {
			if (p.getUtilizador() == user) {
				userPlayLists.add(p);
			}
		}

		return userPlayLists;
	}

	// Querie � BD para devolver todas as m�sicas
	public ArrayList<MusicEntity> getMusics() {
		return this.musics;
	}

	// Querie � BD para devolver as m�sicas por User
	public ArrayList<MusicEntity> getMusics(UserEntity user) {
		ArrayList<MusicEntity> userMusics = new ArrayList<MusicEntity>();

		for (MusicEntity m : this.musics) {
			if (m.getOwner() == user) {
				userMusics.add(m);
			}
		}
		return userMusics;
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

	public UserEntity update(UserEntity user) {
		// actualiza os dados do utilizador na BD e devolve o mesmo utilizador
		// j� da BD depois de actualizado
		return user;
	}

	public void remove(UserEntity user) {
		this.users.remove(user);
	}

	// User

	// Music
	public MusicEntity add(MusicEntity music) {
		// adiciona um novo User � BD, devolve o novo User da BD
		this.musics.add(music);
		return music;
	}

	public MusicEntity update(MusicEntity music) {
		// actualiza os dados na BD e devolve j� da BD depois de actualizado
		return music;
	}

	public void remove(MusicEntity music) {
		this.users.remove(music);
	}

	// Music
	// Queries gen�ricas End

	// Geradores de conte�do

	private void generatePlaylists() {
		int i, j;
		int trackIndex;
		Random r = new Random();
		// List<UserEntity> arrusers;

		// arrusers=userejb.getUsers();
		for (j = 0; j < 10; j++) {
			ArrayList<MusicEntity> newMusics = new ArrayList<MusicEntity>();

			for (i = 0; i < 10; i++) {
				trackIndex = r.nextInt(this.musics.size());
				newMusics.add(musics.get(trackIndex));
			}

			PlaylistEntity pl1 = new PlaylistEntity();
			pl1.setDesignacao("Duarte " + j);
			pl1.setSongs(newMusics);
			pl1.setUtilizador(users.get(1));
			// pl1.setUtilizador(arrusers.get(1));

			playLists.add(pl1);
		}

		for (j = 0; j < 10; j++) {
			ArrayList<MusicEntity> newMusics = new ArrayList<MusicEntity>();

			for (i = 0; i < 10; i++) {
				trackIndex = r.nextInt(this.musics.size());
				newMusics.add(musics.get(trackIndex));
			}

			PlaylistEntity pl1 = new PlaylistEntity();
			pl1.setDesignacao("Carlos " + j);
			pl1.setSongs(newMusics);
			// pl1.setUtilizador(arrusers.get(0));
			pl1.setUtilizador(users.get(0));

			playLists.add(pl1);
		}
	}

	private void generateMusics() {
		int i;
		this.date = new Date();
//		List<UserEntity> arrusers = null;
		// arrusers=userejb.getUsers();
		// String nomemusica,
		// String interprete,
		// String album,
		// String anolancamento,
		// UserEntity owner,
		// String path,
		// String datamusica,
		// String tipomusica

		for (i = 0; i < 10; i++) {
			MusicEntity m = new MusicEntity("Track" + i, "The Shins",
					"Port of Morrow", "2015", users.get(0), "path",
					"2015/05/28", "Gen�rico");
			// MusicEntity m1=new MusicEntity("Track"+i, "The Shins",
			// "Port of Morrow", "2015", arrusers.get(0), "path", "2015/05/28",
			// "Gen�rico");
			musics.add(m);
		}

		for (i = 0; i < 10; i++) {
			MusicEntity m = new MusicEntity("Track" + i, "The Shins",
					"Oh The Inverted World", "2015", users.get(0), "path",
					"2015/05/28", "Gen�rico");
			musics.add(m);
		}

		for (i = 0; i < 10; i++) {
			MusicEntity m = new MusicEntity("Track" + i, "Coldplay",
					"Parachutes", "2015", users.get(1), "path",
					"2015/05/28", "Gen�rico");
			musics.add(m);
		}

		for (i = 0; i < 10; i++) {
			MusicEntity m = new MusicEntity("Track" + i, "Coldplay",
					"A Rush Of Blood To The Head", "2015", users.get(1),
					"path", "2015/05/28", "Gen�rico");
			musics.add(m);
		}

	}

	private void generateUsers() {
		this.date = new Date();
		UserEntity user1 = new UserEntity("Carlos", "123", "caje08@gmail.com",
				"1969/09/19");
		UserEntity user2 = new UserEntity("Duarte", "123",
				"duarte.m.a.goncalves@gmail.com", "1985/01/15");

		users.add(user1);
		users.add(user2);
	}

}
