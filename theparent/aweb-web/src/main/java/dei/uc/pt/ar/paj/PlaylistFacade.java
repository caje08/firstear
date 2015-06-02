/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dei.uc.pt.ar.paj;

import java.util.List;

import dei.uc.pt.ar.paj.PlaylistEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dei.uc.pt.ar.paj.UserEntity;

/**
 *
 * @author 
 */
@Stateless
public class PlaylistFacade extends AbstractFacade<PlaylistEntity> {

	static Logger logger = LoggerFactory.getLogger(PlaylistEJB.class);
	
    @PersistenceContext(unitName = "myPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<PlaylistEntity> findOrdered(PlaylistEntity.Ordering order, UserEntity owner) {        
		logger.info("Entrou no metodo findOrdered()...");
		TypedQuery<PlaylistEntity> q;
	        switch (order) {
	            case NAME_ASCEND:
	                q = em.createNamedQuery(PlaylistEntity.FIND_BY_NAME_ASCENDING, PlaylistEntity.class);
	                break;
	            case NAME_DESCEND:
	                q = em.createNamedQuery(PlaylistEntity.FIND_BY_NAME_DESCENDING, PlaylistEntity.class);
	                break;
	            case DATE_ASCEND:
	                q = em.createNamedQuery(PlaylistEntity.FIND_BY_DATE_ASCENDING, PlaylistEntity.class);
	                break;
	            case DATE_DESCEND:
	                q = em.createNamedQuery(PlaylistEntity.FIND_BY_DATE_DESCENDING, PlaylistEntity.class);
	                break;

	            default:
	                return null;
	        }
	        q.setParameter("ownerId", owner);
	        try {
	            List<PlaylistEntity> list = q.getResultList();
	            return list;
	        } catch (Exception e) {
	            System.err.println("Ordering error " + e);
	            return null;
	        }
	    }


    public PlaylistFacade() {
        super(PlaylistEntity.class);
    }

}
