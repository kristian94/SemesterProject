/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import deployment.ServerDeployment;
import entity.Airline;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Kristian Nielsen
 */
public class AirlineFacade {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(ServerDeployment.PU_NAME);

    public Airline addAirline(Airline a) {
        EntityManager em = emf.createEntityManager();
        Airline res = null;
        try {
            em.getTransaction().begin();
            em.merge(a);
            em.getTransaction().commit();
            res = em.find(Airline.class, a.getUrl());
        } finally {
            em.close();
        }
        return res;
    }

    public Airline addAirlineNoOverwrite(Airline a) {
        EntityManager em = emf.createEntityManager();
        Airline res = null;
        if (em.find(Airline.class, a.getUrl()) == null) {
            try {
                em.getTransaction().begin();
                em.persist(a);
                em.getTransaction().commit();
                res = em.find(Airline.class, a.getUrl());
            } finally {
                em.close();
            }
        }

        return res;
    }

    public List<Airline> getAirlines() {
        EntityManager em = emf.createEntityManager();
        List<Airline> res = null;
        try {
            res = em.createNamedQuery("Airline.FindAll").getResultList();
        } finally {
            em.close();
        }
        return res;
    }

    public Airline getAirlineByName(String name) {
        EntityManager em = emf.createEntityManager();
        Airline a = null;
        try {
            a = (Airline) em.createNamedQuery("Airline.FindByName").setParameter("name", name).getSingleResult();
        } finally {
            em.close();
        }
        return a;
    }

    public void updateAirline(String name, String url) {
        EntityManager em = emf.createEntityManager();
        try {
            Airline a = (Airline) em.createNamedQuery("Airline.FindByUrl").setParameter("url", url).getSingleResult();
            a.setName(name);
            em.getTransaction().begin();
            em.merge(a);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
