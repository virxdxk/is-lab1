package itmo.is.dao;

import itmo.is.entities.Location;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

@Singleton
public class LocationDAO {
    private final EntityManagerFactory emf;
    
    public LocationDAO() {
        this.emf = Persistence.createEntityManagerFactory("routesPU");
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Location location) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Location find(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Location.class, id);
        } finally {
            em.close();
        }
    }

    public List<Location> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT l FROM Location l", Location.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void update(Location location) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(location);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Location location = em.find(Location.class, id);
            if (location != null) {
                em.remove(location);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
