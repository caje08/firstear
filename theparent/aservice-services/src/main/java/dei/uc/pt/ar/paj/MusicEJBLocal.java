package dei.uc.pt.ar.paj;

import java.util.List;

import javax.ejb.Local;

import dei.uc.pt.ar.paj.MusicEntity;
import dei.uc.pt.ar.paj.UserEntity;

@Local
public interface MusicEJBLocal {

	public void populateMusic();
	
	public List<MusicEntity> getMusicas();
}