package dei.uc.pt.ar.paj.EJB;

import java.util.List;

import javax.ejb.Local;

import dei.uc.pt.ar.paj.Entities.MusicEntity;
import dei.uc.pt.ar.paj.Entities.UserEntity;

@Local
public interface UserEJBLocal {
	public void populate();

	public List<UserEntity> getUsers();

}