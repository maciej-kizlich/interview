package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import pl.maciejkizlich.interview.persistence.model.BookOrder;
import pl.maciejkizlich.interview.persistence.model.BookOrderStatus;


public interface BookOrderRepository extends ModelRepository<BookOrder>{

	Collection<BookOrder> findAllByUserId(long id);
	
	Collection<BookOrder> findByUserIdWithGivenFilters(long id, List<BookOrderStatus> statuses);
	
	List<Object[]> findBooksAndUsersWithOrdersExpiresOn(DateTime expiryDate);
		
}
