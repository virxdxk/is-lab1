package itmo.is.dao;

import itmo.is.entities.Route;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

@Singleton
public class RouteDAO {
    
    private final EntityManagerFactory emf;
    
    public RouteDAO() {
        this.emf = Persistence.createEntityManagerFactory("routesPU");
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Route route) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(route);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Route find(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Route.class, id);
        } finally {
            em.close();
        }
    }

    public List<Route> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT r FROM Route r", Route.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void update(Route route) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(route);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Route route = em.find(Route.class, id);
            if (route != null) {
                em.remove(route);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
