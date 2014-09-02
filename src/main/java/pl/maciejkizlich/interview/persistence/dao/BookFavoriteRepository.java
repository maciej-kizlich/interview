package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;

import pl.maciejkizlich.interview.persistence.model.BookFavorite;

/**
 * Created by Levon_Movsesyan on 8/22/2014.
 */
public interface BookFavoriteRepository extends ModelRepository<BookFavorite> {

    void remove(long userId, long bookId);

    boolean isFavorite(long userId, long bookId);

    Collection<BookFavorite> findUserFavorites(long userId);
}
