package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import pl.maciejkizlich.interview.persistence.model.Model;
import pl.maciejkizlich.interview.utils.TimeProvider;

/**
 * Created by Denis_Ivanchenko on 7/18/2014.
 */

public abstract class AbstractModelRepository<M extends Model> implements ModelRepository<M> {

	@Autowired
	protected TimeProvider timeProvider;
	
    @PersistenceContext
    protected EntityManager em;

    Class<M> entityClass;

    protected AbstractModelRepository(Class<M> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
	public M save(M model) {
        return em.merge(model);
    }

    @Override
    public void remove(M model) {
        em.remove(model);
    }

    @Override
    public M findById(Long id) {
        return em.find(entityClass, id);
    }


    @Override
    public Collection<M> getResultListByNamedQuery(String queryName, Map<String, Object> params) {
        Query query = em.createNamedQuery(queryName);
        for(Map.Entry<String,Object> param: params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        return query.getResultList();
    }

    @Override
    public M getSingleResultByNamedQuery(String queryName, Map<String, Object> params) {
        Query query = em.createNamedQuery(queryName);
        for(Map.Entry<String,Object> param: params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        return (M) query.getSingleResult();
    }

    @Override
    public Collection<M> findAll() {
        return em.createQuery("select distinct m from "+ entityClass.getName() + " m").getResultList();
    }

}
