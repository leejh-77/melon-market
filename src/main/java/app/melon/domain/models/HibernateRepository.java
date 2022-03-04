package app.melon.domain.models;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.persistence.EntityManager;

public abstract class HibernateRepository<T> {

    private EntityManager entityManager;

    public HibernateRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void save(T obj) {
        this.entityManager.persist(obj);
        this.entityManager.flush();
    }
}
