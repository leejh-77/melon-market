package app.melon.domain.models;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class HibernateRepository<T> {

    private final EntityManager entityManager;

    private String tableName;

    public HibernateRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        init();
    }

    private void init() {
        Class<T> c = (Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.tableName = c.getSimpleName();
    }

    public String getTableName() {
        return this.tableName;
    }

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    public void save(T obj) {
        this.entityManager.persist(obj);
        this.entityManager.flush();
    }

    protected T find(String query, Object... params) {
        return this.createQuery(query, params).uniqueResult();
    }

    protected List<T> findAll(String query, Object... params) {
        return this.createQuery(query, params).getResultList();
    }

    private Query<T> createQuery(String queryString, Object... params) {
        queryString = "FROM " + this.tableName + " WHERE " + queryString;
        Query<T> query = this.getSession().createQuery(queryString);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
        return query;
    }
}
