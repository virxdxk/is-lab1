package itmo.is.dao;

import itmo.is.entities.Location;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Singleton
public class LocationDAO {
    @PersistenceContext
    private EntityManager em;

    public void create(Location location) {
        em.persist(location);
    }

    public Location find(Long id) {
        return em.find(Location.class, id);
    }

    public List<Location> findAll() {
        return em.createQuery("SELECT l FROM Location l", Location.class).getResultList();
    }

    public void update(Location location) {
        em.merge(location);
    }

    public void delete(Long id) {
        Location location = em.find(Location.class, id);
        if (location != null) {
            em.remove(location);
        }
    }
}
