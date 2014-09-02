package pl.maciejkizlich.interview.persistence.dao;

import org.springframework.stereotype.Repository;

import pl.maciejkizlich.interview.persistence.model.BookFeedback;

import javax.persistence.Query;

import java.util.List;

/**
 * Created by Levon_Movsesyan on 8/13/2014.
 */
@Repository
public class FeedbackRepositoryImpl extends AbstractModelRepository<BookFeedback> implements FeedbackRepository {

    protected FeedbackRepositoryImpl() {
        super(BookFeedback.class);
    }

    @Override
    public List<BookFeedback> getByBookId(long bookId) {
        Query q = em.createQuery("SELECT o FROM BookFeedback o WHERE o.book.id=:id ORDER BY o.createdDate DESC").setParameter("id", bookId);
        return q.getResultList();
    }

    @Override
    public List<BookFeedback> getByUserId(Long userId) {
        Query q = em.createQuery("SELECT o FROM BookFeedback o WHERE o.user.id=:userId ORDER BY o.createdDate DESC").setParameter("userId", userId);
        return q.getResultList();
    }
}
