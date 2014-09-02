package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;
import java.util.Map;

import pl.maciejkizlich.interview.persistence.model.Model;

/**
 * Created by Denis_Ivanchenko on 7/18/2014.
 */
public interface ModelRepository<M extends Model> {

    M save(M model);

    void remove(M model);

    M findById(Long id);

    Collection<M> getResultListByNamedQuery(String query, Map<String, Object> params);

    M getSingleResultByNamedQuery(String query, Map<String, Object> params);

    Collection<M> findAll();
}
