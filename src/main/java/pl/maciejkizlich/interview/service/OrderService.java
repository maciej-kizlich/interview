package pl.maciejkizlich.interview.service;

import java.util.Collection;
import java.util.List;

import pl.maciejkizlich.interview.persistence.model.BookOrder;
import pl.maciejkizlich.interview.persistence.model.BookOrderStatus;
import pl.maciejkizlich.interview.persistence.model.User;

public interface OrderService {
    Collection<BookOrder> findByUser(User user);
    Collection<BookOrder> findByUserId(long id);
    Collection<BookOrder> findByUserAndGivenStatuses(long userId, List<BookOrderStatus> statuses);
    Collection<BookOrder> findAll();
    
    BookOrder cancelOrderById(long id);
}
