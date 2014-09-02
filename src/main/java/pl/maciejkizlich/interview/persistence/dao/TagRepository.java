package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;

import pl.maciejkizlich.interview.persistence.model.Tag;

/**
 * Created by Denis_Ivanchenko on 7/21/2014.
 */
public interface TagRepository extends ModelRepository<Tag> {

    public Collection<Tag> findByTitle(String title);
    public Collection<Tag> findTitleLike(String regexp);
}
