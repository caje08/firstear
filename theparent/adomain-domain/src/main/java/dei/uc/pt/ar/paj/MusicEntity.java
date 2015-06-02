package dei.uc.pt.ar.paj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity implementation class for Entity: Utilizador
 *
 */
@Entity
@Table(name="musica")
@NamedQueries({
    @NamedQuery(name = "Music.findByOwner", query = "SELECT m FROM MusicEntity m WHERE m.utilizador = :owner"),
    @NamedQuery(name = "Music.findBySearch", query = "SELECT m FROM MusicEntity m WHERE m.nomemusica LIKE :searchTerm OR m.interprete LIKE :searchTerm"),
    @NamedQuery(name = "Music.findAll", query="SELECT m FROM MusicEntity m"),
	@NamedQuery(name = "Music.findByInterprete", query="SELECT m FROM MusicEntity m WHERE m.interprete like :interprete"),
	@NamedQuery(name = "Music.findByNomeMusica", query="SELECT m FROM MusicEntity m WHERE m.nomemusica like :title"),
	@NamedQuery(name = "Music.findByAlbum", query="SELECT m FROM MusicEntity m WHERE m.album like :album"),
	@NamedQuery(name = "Music.findByAno", query="SELECT m FROM MusicEntity m WHERE m.anolancamento like :year"),
//	@NamedQuery(name = "Music.findByUser", query="SELECT m FROM MusicEntity m WHERE m.utilizador like :user"),
    })
public class MusicEntity implements Serializable {

	public static final String FIND_ALL = "Music.findAll";
	public static final String FIND_BY_INTERPRETE = "Music.findByInterprete";
	public static final String FIND_BY_NOMEMUSICA = "Music.findByNomeMusica";
	public static final String FIND_BY_ALBUM = "Music.findByAlbum";
	public static final String FIND_BY_ANO = "Music.findByAno";
	public static final String FIND_BY_OWNER = "Music.findByOwner";

	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "musicid", nullable = false, unique = true)
    private Long musicid;
//	private int musicid;
	@NotNull
    @Column(name = "nomemusica", nullable = false)
	private String nomemusica;
	@NotNull
    @Column(name = "interprete", nullable = false)
	private String interprete;
	@NotNull
    @Column(name = "album", nullable = false)
	private String album;
	@NotNull
    @Column(name = "anolancamento", nullable = false)
	private String anolancamento;
	
	@NotNull
    @Column(name = "time")
	private double length;
//
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
	private UserEntity utilizador;
    
    @NotNull
    @Column(name = "path", nullable = false)
	private String path;
    
    @ManyToMany(mappedBy = "songs")
    private List<PlaylistEntity> playlists;
    
    @Column(name = "datamusica", nullable = false)
	private String datamusica;
    
    @Column(name = "tipomusica", nullable = false)
	private String tipomusica;  
    
    public static enum Ordering {
		FIND_ALL, FIND_BY_INTERPRETE, FIND_BY_NOMEMUSICA, FIND_BY_ALBUM, FIND_BY_ANO, FIND_BY_OWNER
	};
	static Logger logger = LoggerFactory.getLogger(MusicEntity.class); 
	
	public MusicEntity() {
		super();
	}

	public MusicEntity(String nomemusica, String interprete, String album,
			String anolancamento, UserEntity owner, String path,
			String datamusica, String tipomusica) {
		super();
		this.nomemusica = nomemusica;
		this.interprete = interprete;
		this.album = album;
		this.anolancamento = anolancamento;
		this.utilizador = owner;
		this.path = path;
		this.datamusica = datamusica;
		this.tipomusica = tipomusica;
		this.length=0;
	}

	public Long getMusicid(){
		return this.musicid;
	}
	
	public UserEntity getOwner() {
		return utilizador;
	}

	public void setOwner(UserEntity owner) {
		this.utilizador = owner;
	}

	public void setMusicid(Long musicid) {
		this.musicid = musicid;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public List<PlaylistEntity> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<PlaylistEntity> playlists) {
		this.playlists = playlists;
	}

	public String getNomemusica() {
		return nomemusica;
	}

	public void setNomemusica(String nomemusica) {
		this.nomemusica = nomemusica;
	}

	public String getInterprete() {
		return interprete;
	}

	public void setInterprete(String interprete) {
		this.interprete = interprete;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAnolancamento() {
		return anolancamento;
	}

	public void setAnolancamento(String anolancamento) {
		this.anolancamento = anolancamento;
	}

	public UserEntity getUtilizador() {
		return utilizador;
	}

	public void setUtilizador(UserEntity utilizador) {
		this.utilizador = utilizador;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDatamusica() {
		return datamusica;
	}

	public void setDatamusica(String datamusica) {
		this.datamusica = datamusica;
	}

	public String getTipomusica() {
		return tipomusica;
	}

	public void setTipomusica(String tipomusica) {
		this.tipomusica = tipomusica;
	}
	 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (musicid != null ? musicid.hashCode() : 0);
        return hash;
    }
	
	 @Override
	    public boolean equals(Object object) {
	        // TODO: Warning - this method won't work in the case the id fields are not set
	        if (!(object instanceof MusicEntity)) {
	            return false;
	        }
	        MusicEntity other = (MusicEntity) object;
	        return (this.musicid != null || other.musicid == null) && (this.musicid == null || this.musicid.equals(other.musicid));
	    }

	    @Override
	    public String toString() {
	        return "musicEntity[ id=" + musicid + " ]";
	    }
}
