/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import deployment.ServerDeployment;
import entity.SearchEntity;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Kristian Nielsen
 */
public class SearchFacade  {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(ServerDeployment.PU_NAME);
    
    
    public SearchEntity addSearch(SearchEntity search) {
        EntityManager em = emf.createEntityManager();
        SearchEntity res = null;
        try{
            em.getTransaction().begin();
            em.persist(search);
            em.getTransaction().commit();
            res = em.find(SearchEntity.class, search.getSearchID());
        }finally{
            em.close();
        }
        return res;
    }

    public SearchEntity getSearch(int searchID) {
        EntityManager em = emf.createEntityManager();
        SearchEntity res = null;
        try{
            res = em.find(SearchEntity.class, searchID);
        }finally{
            em.close();
        }
        return res;
    }

    public List<SearchEntity> getSearches() {
        EntityManager em = emf.createEntityManager();
        List<SearchEntity> res = null;
        try{
            res = em.createNamedQuery("Search.FindAll").getResultList();
        }finally{
            em.close();
        }
        return res;
    }

    public List<SearchEntity> getSearchesByDate(Date date) {
        EntityManager em = emf.createEntityManager();
        List<SearchEntity> res = null;
        try{
            res = em.createNamedQuery("Search.FindByDate").setParameter("travelDate", date).getResultList();
        }finally{
            em.close();
        }
        return res;
    }

    public List<SearchEntity> getSearchesByOrigin(String IATA) {
        EntityManager em = emf.createEntityManager();
        List<SearchEntity> res = null;
        try{
            res = em.createNamedQuery("Search.FindByOrigin").setParameter("origin", IATA).getResultList();
        }finally{
            em.close();
        }
        return res;
    }

    public List<SearchEntity> getSearchesByDesination(String IATA) {
        EntityManager em = emf.createEntityManager();
        List<SearchEntity> res = null;
        try{
            res = em.createNamedQuery("Search.FindByDestination").setParameter("destination", IATA).getResultList();
        }finally{
            em.close();
        }
        return res;
    }    
}
