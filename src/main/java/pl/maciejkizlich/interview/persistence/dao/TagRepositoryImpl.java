package pl.maciejkizlich.interview.persistence.dao;

import org.springframework.stereotype.Repository;

import pl.maciejkizlich.interview.persistence.model.Tag;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Denis_Ivanchenko on 7/21/2014.
 */

@Repository
public class TagRepositoryImpl extends AbstractModelRepository<Tag> implements TagRepository {

    public TagRepositoryImpl() {
        super(Tag.class);
    }

    @Override
    public Collection<Tag> findByTitle(String title) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put("title", title);
        return getResultListByNamedQuery("Tag.findByTitle", params);
    }

    @Override
    public Collection<Tag> findTitleLike(String regexp) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put("regexp",  regexp);
        return getResultListByNamedQuery("Tag.findTitleLike", params);
    }
}
