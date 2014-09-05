package pl.maciejkizlich.interview.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.maciejkizlich.interview.persistence.dao.BookOrderRepository;
import pl.maciejkizlich.interview.persistence.model.Book;
import pl.maciejkizlich.interview.persistence.model.BookOrder;
import pl.maciejkizlich.interview.persistence.model.BookOrderStatus;
import pl.maciejkizlich.interview.persistence.model.User;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<BookOrder> findByUserAndGivenStatuses(long userId, List<BookOrderStatus> statuses) {
    	return bookOrderRepository.findByUserIdWithGivenFilters(userId, statuses);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Collection<BookOrder> findByUser(User user) {
        return findByUserId(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<BookOrder> findByUserId(long id) {
        return bookOrderRepository.findAllByUserId(id);
    }

    @Override
    public Collection<BookOrder> findAll() {
        return bookOrderRepository.findAll();
    }

	@Override
	public BookOrder cancelOrderById(long id) {
		BookOrder order = bookOrderRepository.findById(id);
		order.setStatus(BookOrderStatus.CANCELED);
		
		Book bookToCancelItsReservation = order.getBook();

		bookToCancelItsReservation.setAvailableQuantity(bookToCancelItsReservation.getAvailableQuantity() + 1);
		
		return bookOrderRepository.save(order);
		
	}
}
