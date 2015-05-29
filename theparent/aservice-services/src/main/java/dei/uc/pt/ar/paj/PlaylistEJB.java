package dei.uc.pt.ar.paj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import dei.uc.pt.ar.paj.MusicEntity;
import dei.uc.pt.ar.paj.PlaylistEntity;
import dei.uc.pt.ar.paj.UserEntity;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
public class PlaylistEJB implements PlaylistEJBLocal {

	@PersistenceContext(name = "myPU")
	private EntityManager em;

	private UserEJB teste;
	private PlaylistEntity.Ordering order; 
	
	private UserEntity usertmp1;
	private UserEntity usertmp2;
	private String dataddmusic;
	private MusicEntity musica1, musica2, musica3, musica4, musica5, musica6,
			musica7, musica8, musica9, musica10, musica11, musica12;
	// SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");

	static Logger logger = LoggerFactory.getLogger(PlaylistEJB.class);

	public PlaylistEJB() {
	}

	@PostConstruct
    public void init() {
        order = PlaylistEntity.Ordering.DATE_ASCEND;
    }
	
	public void populatePlaylist() {
		
		List<UserEntity> lstusers=null;
		ArrayList<MusicEntity> musics = new ArrayList<MusicEntity>();
		dataddmusic = "2015/01/10";
		System.out.println("Users.size="+lstusers.size());
		lstusers=teste.getUsers();
		System.out.println("Users.size="+lstusers.size());
		usertmp1=lstusers.get(1);
		System.out.println("User1="+usertmp1.getEmail());
		usertmp2=lstusers.get(2);
		System.out.println("User1="+usertmp2.getEmail());
		// usertmp1 = new UserEntity("Carlos", "123", "carlosantos3@gmail.com",
		// "1970/06/13");
//		usertmp2 = new UserEntity("Duarte", "456", "duarte3@gmail.com",
//				"1985/10/21");

		musica1 = new MusicEntity("nomemusic1", "interpret1", "album1", "2013",
				usertmp1, "c:\\path1", "2015/02/20", "tipo1");
		musica2 = new MusicEntity("nomemusic2", "interpret2", "album2", "2014",
				usertmp2, "c:\\path2", "2014/01/10", "tipo2");
		musica3 = new MusicEntity("nomemusic3", "interpret3", "album3", "2013",
				usertmp1, "c:\\path3", "2015/02/10", "tipo3");
		musica4 = new MusicEntity("nomemusic4", "interpret4", "album4", "2012",
				usertmp2, "c:\\path4", "2015/03/10", "tipo4");
		musica5 = new MusicEntity("nomemusic5", "interpret5", "album5", "2011",
				usertmp2, "c:\\path5", "2015/04/10", "tipo5");
		musica6 = new MusicEntity("nomemusic6", "interpret6", "album6", "2014",
				usertmp2, "c:\\path6", "2015/05/10", "tipo6");
		musica7 = new MusicEntity("nomemusic7", "interpret7", "album7", "2013",
				usertmp1, "c:\\path1", "2015/02/20", "tipo1");
		musica8 = new MusicEntity("nomemusic8", "interpret8", "album8", "2014",
				usertmp2, "c:\\path2", "2014/01/10", "tipo2");
		musica9 = new MusicEntity("nomemusic9", "interpret9", "album9", "2013",
				usertmp1, "c:\\path3", "2015/02/10", "tipo3");
		musica10 = new MusicEntity("nomemusic10", "interpret10", "album10",
				"2012", usertmp1, "c:\\path4", "2015/03/10", "tipo4");
		musica11 = new MusicEntity("nomemusic11", "interpret11", "album11",
				"2011", usertmp2, "c:\\path5", "2015/04/10", "tipo5");
		musica12 = new MusicEntity("nomemusic12", "interpret12", "album12",
				"2014", usertmp2, "c:\\path6", "2015/05/10", "tipo6");
		// new Playlist(String designacao, Musica m, String datacriacao,User
		// utilizador)
		musics.add(musica1);
		musics.add(musica2);
		musics.add(musica4);
		musics.add(musica3);
		em.persist(new PlaylistEntity("playlist1", musics, "2015/04/14",
				usertmp1));
		musics.removeAll(musics);
		musics.add(musica3);
		musics.add(musica4);
		musics.add(musica5);
		em.persist(new PlaylistEntity("playlist2", musics, "2015/04/14",
				usertmp2));
		musics.removeAll(musics);
		musics.add(musica3);
		musics.add(musica4);
		musics.add(musica8);
		musics.add(musica10);
		em.persist(new PlaylistEntity("playlist3", musics, "2015/05/14",
				usertmp1));
		musics.removeAll(musics);
		musics.add(musica2);
		musics.add(musica6);
		musics.add(musica1);
		musics.add(musica7);
		musics.add(musica12);
		em.persist(new PlaylistEntity("playlist4", musics, "2015/05/14",
				usertmp2));
		musics.removeAll(musics);
		musics.add(musica1);
		musics.add(musica6);
		em.persist(new PlaylistEntity("playlist5", musics, "2015/05/10",
				usertmp1));
		musics.add(musica9);
		musics.add(musica7);
		musics.add(musica11);
		em.persist(new PlaylistEntity("playlist6", musics, "2015/05/10",
				usertmp1));
		musics.removeAll(musics);
		musics.add(musica10);
		em.persist(new PlaylistEntity("playlist7", musics, "2015/05/11",
				usertmp2));
		musics.add(musica3);
		musics.add(musica5);
		musics.add(musica7);
		em.persist(new PlaylistEntity("playlist8", musics, "2015/04/11",
				usertmp2));
		musics.removeAll(musics);
		musics.add(musica3);
		musics.add(musica4);
		musics.add(musica7);
		em.persist(new PlaylistEntity("playlist9", musics, "2015/04/16",
				usertmp1));
		musics.add(musica9);
		musics.add(musica1);
		musics.add(musica11);
		em.persist(new PlaylistEntity("playlist10", musics, "2015/05/16",
				usertmp2));
		musics.removeAll(musics);
		musics.add(musica3);
		musics.add(musica6);
		musics.add(musica7);
		musics.add(musica10);
		em.persist(new PlaylistEntity("playlist11", musics, "2015/05/18",
				usertmp1));
		musics.removeAll(musics);
		musics.add(musica3);
		em.persist(new PlaylistEntity("playlist12", musics, "2015/05/19",
				usertmp1));
	}

	@Override
	public List<PlaylistEntity> getPlaylists() {
		// List<String> usernames = new LinkedList<>();
		logger.trace("Trace Antes getPlaylists()");
		logger.debug("Sample Antes debug message");
		logger.info("Sample Antes info message");
		logger.warn("Sample Antes warn message");
		logger.error("Sample Antes error message");
		System.out.println("Antes de criar a query");

		Query q = em.createQuery("from PlaylistEntity p");
		List<PlaylistEntity> playlists = q.getResultList();

		System.out.println("Depois de apresentar os resultados");
		logger.trace("Trace Depois getPlaylists()");
		logger.debug("Sample Depois debug message");
		logger.info("Sample Depois info message");
		logger.warn("Sample Depois warn message");
		logger.error("Sample Depois error message");

		// for (User u : users) {
		// usernames.add(u.toString());
		// }

		return playlists;
	}
	
//	public List<PlaylistEntity> findOrdered(PlaylistEntity.Ordering order, UserEntity owner) {
	public List<PlaylistEntity> findOrdered() {        
		logger.info("Entrou no metodo...");
		TypedQuery<PlaylistEntity> q;
	        switch (order) {
	            case NAME_ASCEND:
	                q = em.createNamedQuery("Playlist.orderByNameAscending", PlaylistEntity.class);
	                break;
	            case NAME_DESCEND:
	                q = em.createNamedQuery("PlaylistByNameDescending", PlaylistEntity.class);
	                break;
	            case DATE_ASCEND:
	                q = em.createNamedQuery(PlaylistEntity.FIND_BY_ID_ASCENDING, PlaylistEntity.class);
	                break;
	            case DATE_DESCEND:
	                q = em.createNamedQuery("Playlist.orderByCreationDateDescending", PlaylistEntity.class);
	                break;

	            default:
	                return null;
	        }
	        q.setParameter("ownerId", usertmp1);
	        try {
	            List<PlaylistEntity> list = q.getResultList();
	            return list;
	        } catch (Exception e) {
	            System.err.println("Ordering error " + e);
	            return null;
	        }
	    }


//	public List<PlaylistEntity> getPlayByOwner() {
//		String owner = "carlosantos3@gmail.com";
		// if (usertmp1 != null) {
		// TypedQuery<PlaylistEntity> q = em.createNamedQuery(
		// "Playlist.playByOwnerID", PlaylistEntity.class);
		// q.setParameter("utilizador", owner);
		// try {
		// return q.getResultList();
		// } catch (Exception e) {
		// System.err.println("Single result not found: " + e);
		// return null;
		// }
		// }else
		// return null;
//		System.out.println("Owner=" + owner);
//		Query q = em.createQuery("from PlaylistEntity p where p.utilizador.email='"
//				+ owner+"'");
//		@SuppressWarnings("unchecked")
//		List<PlaylistEntity> playlists = q.getResultList();
//		System.out.println("Playlist size=" + playlists.size());
//		return playlists;
//	}
}
