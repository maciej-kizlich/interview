package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import pl.maciejkizlich.interview.persistence.model.BookFavorite;

/**
 * Created by Levon_Movsesyan on 8/22/2014.
 */
@Component
public class BookFavoriteRepositoryImpl extends AbstractModelRepository<BookFavorite>  implements BookFavoriteRepository {

    protected BookFavoriteRepositoryImpl() {
        super(BookFavorite.class);
    }

    @Override
    public void remove(long userId, long bookId) {
        Query query = em.createQuery("DELETE FROM BookFavorite o WHERE o.book.id = :bookId AND o.user.id = :userId");
        query.setParameter("bookId", bookId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    @Override
    public boolean isFavorite(long userId, long bookId) {
        Query query = em.createQuery("SELECT count(o) FROM BookFavorite o WHERE o.book.id = :bookId AND o.user.id = :userId");
        query.setParameter("bookId", bookId);
        query.setParameter("userId", userId);
        long count = (Long)query.getSingleResult();
        return count > 0;
    }

    @Override
    public Collection<BookFavorite> findUserFavorites(long userId) {
        Query query = em.createQuery("SELECT o FROM BookFavorite o WHERE o.user.id = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
