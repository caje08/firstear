package dei.uc.pt.ar.paj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity implementation class for Entity: Utilizador
 *
 */
@Entity
@Table(name = "playlist")
@NamedQueries({
		@NamedQuery(name = "Playlist.orderByNameAscending", query = "SELECT p FROM PlaylistEntity p WHERE p.utilizador = :ownerId ORDER BY p.designacao ASC"),
		@NamedQuery(name = "Playlist.orderByNameDescending", query = "SELECT p FROM PlaylistEntity p WHERE p.utilizador = :ownerId ORDER BY p.designacao DESC"),
		@NamedQuery(name = "Playlist.orderByCreationDateAscending", query = "SELECT p FROM PlaylistEntity p WHERE p.utilizador = :ownerId ORDER BY p.datacriacao ASC"),
		@NamedQuery(name = "Playlist.orderByCreationDateDescending", query = "SELECT p FROM PlaylistEntity p WHERE p.utilizador = :ownerId ORDER BY p.datacriacao DESC"),
// @NamedQuery(name = "Playlist.orderByNoSongsAscending", query =
// "SELECT p FROM PlaylistEntity p WHERE p.utilizador = :ownerId ORDER BY SIZE(p.songs) ASC"),
// @NamedQuery(name = "Playlist.orderByNoSongsDescending", query =
// "SELECT p  FROM PlaylistEntity p WHERE p.utilizador = :ownerId ORDER BY SIZE(p.songs) DESC"),
// @NamedQuery(name = "Playlist.findByOwnerID", query =
// "SELECT p FROM PlaylistEntity p WHERE p.utilizador = :ownerId"),
// @NamedQuery(name = "Playlist.playByOwnerID", query =
// "SELECT p FROM PlaylistEntity p WHERE p.utilizador = :ownerId")
})
public class PlaylistEntity implements Serializable {

	public static final String FIND_BY_DATE_ASCENDING = "Playlist.orderByCreationDateAscending";
	public static final String FIND_BY_DATE_DESCENDING = "Playlist.orderByCreationDateDescending";
	public static final String FIND_BY_NAME_ASCENDING = "Playlist.orderByNameAscending";
	public static final String FIND_BY_NAME_DESCENDING = "Playlist.orderByNameAscending";
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idplaylist")
	private Long idplaylist;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "designacao")
	private String designacao;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "playlist_musica", joinColumns = @JoinColumn(name = "playlist_fk"), inverseJoinColumns = @JoinColumn(name = "musica_fk"))
	private List<MusicEntity> songs;
	
	@NotNull
	// @Temporal(TemporalType.DATE)
	@Column(name = "datacriacao")
	private String datacriacao;

	// @NotNull
	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private UserEntity utilizador;

	public static enum Ordering {
		NAME_ASCEND, NAME_DESCEND, DATE_ASCEND, DATE_DESCEND
	};

	static Logger logger = LoggerFactory.getLogger(PlaylistEntity.class);

	public PlaylistEntity() {
		super();
	}

	public PlaylistEntity(String designacao, ArrayList<MusicEntity> m,
			String datacriacao, UserEntity utilizador) {
		super();
		this.designacao = designacao;
		// this.musicas.add(m);
		this.songs = m;
		this.datacriacao = datacriacao;
		this.utilizador = utilizador;
	}

	public Long getIdPlaylist() {
		return this.idplaylist;
	}

	public void addMusicPlaylist(MusicEntity m) {
		this.songs.add(m);
		logger.info("A musica com o nome= " + m.getNomemusica()
				+ " foi adicionada na playlist = " + designacao);
	}

	public boolean removeMusicPlaylist(MusicEntity m) {
		int nmusic = songs.size();
		boolean saida = false;

		for (int i = 0; i < nmusic; i++) {
			if (songs.get(i).getMusicid() == m.getMusicid()) {
				songs.remove(i);
				logger.info("A musica com o nome= " + m.getNomemusica()
						+ " foi removida da playlist = " + designacao);
				saida = true;
			} else if (i + 1 == nmusic) {
				logger.error("A musica nao existe em Array para ser removida");
				saida = false;
			}
		}
		return saida;
	}

	public String getDesignacao() {
		return designacao;
	}

	public void setDesignacao(String designacao) {
		this.designacao = designacao;
	}

	public List<MusicEntity> getSongs() {
		return songs;
	}

	public void setSongs(List<MusicEntity> musicas) {
		this.songs = musicas;
	}

	public String getDatacriacao() {
		return datacriacao;
	}

	public void setDatacriacao(String datacriacao) {
		this.datacriacao = datacriacao;
	}

	public UserEntity getUtilizador() {
		return utilizador;
	}

	public void setUtilizador(UserEntity utilizador) {
		this.utilizador = utilizador;
	}
	  @Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (idplaylist != null ? idplaylist.hashCode() : 0);
	        return hash;
	    }
	
	 @Override
	    public boolean equals(Object object) {
	        // TODO: Warning - this method won't work in the case the id fields are not set
	        if (!(object instanceof PlaylistEntity)) {
	            return false;
	        }
	        PlaylistEntity other = (PlaylistEntity) object;
	        return (this.idplaylist != null || other.idplaylist == null) && (this.idplaylist == null || this.idplaylist.equals(other.idplaylist));
	    }

	    @Override
	    public String toString() {
	        Observer o = new Observer() {

	            @Override
	            public void update(Observable o, Object arg) {
	                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	            }
	        };
	        return "entities.PlaylistEntity[ id=" + idplaylist + " ]";
	    }
}
