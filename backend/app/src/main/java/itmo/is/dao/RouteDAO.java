package itmo.is.dao;

import itmo.is.entities.Route;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import java.util.List;

@Stateless
public class RouteDAO {
    
    @PersistenceContext
    private EntityManager em;

    public void create(Route route) {
        em.persist(route);
    }

    public Route find(Long id) {
        return em.find(Route.class, id);
    }

    public List<Route> findAll() {
        return em.createQuery("SELECT r FROM Route r", Route.class).getResultList();
    }

    public void update(Route route) {
        em.merge(route);
    }

    public void delete(Long id) {
        Route route = em.find(Route.class, id);
        if (route != null) {
            em.remove(route);
        }
    }
}
