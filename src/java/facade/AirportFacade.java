/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import deployment.ServerDeployment;
import entity.Airport;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author matskruger
 */
public class AirportFacade {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(ServerDeployment.PU_NAME);

    public Airport getAirport(Integer ID) {
        EntityManager em = emf.createEntityManager();
        return em.find(Airport.class, ID);
    }
    
    public List<Airport> getAirports() {
        EntityManager em = emf.createEntityManager();
        List<Airport> res = null;
        try {
            res = em.createNamedQuery("Airport.findAll").getResultList();
        } finally {
            em.close();
        }
        return res;
    }
    
    public List<Airport> getAirportsByQuery(String query) {
        EntityManager em = emf.createEntityManager();
        List<Airport> res = null;
        try {
            res = em.createNamedQuery("Airport.findAllByQuery").setParameter("query", query + '%').setMaxResults(5).getResultList();
        } finally {
            em.close();
        }
        return res;
    }

    public Airport getAirportByName(String name) {
        EntityManager em = emf.createEntityManager();
        Airport a = null;
        try {
            a = (Airport) em.createNamedQuery("Airport.FindByName").setParameter("name", name).getSingleResult();
        } finally {
            em.close();
        }
        return a;
    }

    public void updateAirport(String name, String url) {
        EntityManager em = emf.createEntityManager();
        try {
            Airport a = (Airport) em.createNamedQuery("Airport.FindByUrl").setParameter("url", url).getSingleResult();
            a.setName(name);
            em.getTransaction().begin();
            em.merge(a);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
