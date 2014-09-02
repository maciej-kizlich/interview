package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.Query;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.maciejkizlich.interview.persistence.model.BookOrder;
import pl.maciejkizlich.interview.persistence.model.BookOrderStatus;


@Repository
public class BookOrderRepositoryImpl extends AbstractModelRepository<BookOrder> implements BookOrderRepository {

	@Autowired
	private AdminRepository adminRepository;
	
    public BookOrderRepositoryImpl() {
        super(BookOrder.class);
    }

    @Override
    public Collection<BookOrder> findAllByUserId(long id) {
        Query q = em.createQuery("SELECT o FROM BookOrder o WHERE o.user.id=:id").setParameter("id", id);
        return q.getResultList();
    }
    
    @Override
    public Collection<BookOrder> findByUserIdWithGivenFilters(long id, List<BookOrderStatus> statuses) {
        Query q = em.createQuery("SELECT o FROM BookOrder o WHERE o.user.id=:id and o.status IN (:statuses)").setParameter("id", id).setParameter("statuses", statuses);
        return q.getResultList();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findBooksAndUsersWithOrdersExpiresOn(
			DateTime expiryDate) {

		   DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-d");	
		   String dateOfExpiry = expiryDate.toString(dtf);
		   
		   Query q = em.createQuery("SELECT o.user, o.book, o.expectedReturnDate FROM BookOrder o WHERE to_char(o.expectedReturnDate,'YYYY-MM-DD') = :dateOfExpiry AND o.status = 'BORROWED'")
				   .setParameter("dateOfExpiry", dateOfExpiry);
		   
		   return q.getResultList();
		
	}

}
