package com.example.service;

import com.example.model.MusicalArtist;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 26/11/2012 - 03:59:19 AM
 */
@Service
public class MusicalArtistServiceImpl implements MusicalArtistService{

    @PersistenceContext
    EntityManager em;
    
    @Transactional
    public void addMusicalArtist(MusicalArtist musicalArtist) {
        em.persist(musicalArtist);
    }

    @Transactional
    public List<MusicalArtist> listMusicalArtist() {
        CriteriaQuery<MusicalArtist> c = em.getCriteriaBuilder().createQuery(MusicalArtist.class);
        Root<MusicalArtist> from = c.from(MusicalArtist.class);
        c.orderBy(em.getCriteriaBuilder().asc(from.get("name")));
        return em.createQuery(c).getResultList();
    }

    @Transactional
    public void removeMusicalArtist(String name) {
        MusicalArtist musicalArtist = em.find(MusicalArtist.class, name);
        if (null != musicalArtist){
            em.remove(musicalArtist);
        }
    }

}

