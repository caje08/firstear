package dei.uc.pt.ar.paj;

import java.util.List;

import javax.ejb.Local;

import dei.uc.pt.ar.paj.MusicEntity;
import dei.uc.pt.ar.paj.PlaylistEntity;
import dei.uc.pt.ar.paj.UserEntity;

@Local
public interface PlaylistEJBLocal {
	
	 public void populatePlaylist();	 
	 public List<PlaylistEntity> getPlaylists();
	 public List<PlaylistEntity> findOrdered();
}