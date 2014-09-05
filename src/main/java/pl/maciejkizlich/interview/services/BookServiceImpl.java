package pl.maciejkizlich.interview.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.maciejkizlich.interview.persistence.dao.AdminRepository;
import pl.maciejkizlich.interview.persistence.dao.BookFavoriteRepository;
import pl.maciejkizlich.interview.persistence.dao.BookOrderRepository;
import pl.maciejkizlich.interview.persistence.dao.BookRatingRepository;
import pl.maciejkizlich.interview.persistence.dao.BookRepository;
import pl.maciejkizlich.interview.persistence.dao.FeedbackRepository;
import pl.maciejkizlich.interview.persistence.dao.UserRepository;
import pl.maciejkizlich.interview.persistence.model.Book;
import pl.maciejkizlich.interview.persistence.model.BookFavorite;
import pl.maciejkizlich.interview.persistence.model.BookFeedback;
import pl.maciejkizlich.interview.persistence.model.BookOrder;
import pl.maciejkizlich.interview.persistence.model.BookOrderStatus;
import pl.maciejkizlich.interview.persistence.model.BookPopularity;
import pl.maciejkizlich.interview.persistence.model.BookRating;
import pl.maciejkizlich.interview.persistence.model.BookSearch;
import pl.maciejkizlich.interview.persistence.model.Books;
import pl.maciejkizlich.interview.persistence.model.User;
import pl.maciejkizlich.interview.utils.TimeProvider;


//TODO this does not extends AbstractModelRepository as User/Tag repositories - should it? //no, it should not, because it is a Service, not a Repository :) 

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final static Logger LOG = LoggerFactory.getLogger(BookService.class);
	
	public static final String BOOK_UNAVAILABLE_ERROR = "Book not available because of available quantity";
	
	public static final String BOOK_DELETED_ERROR = "Book not available because it is deleted";
	
	public static final String USER_RESERVATIONS_BLOCKED = "User reservations blocked due to long overtime borrowing";
	
	
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private BookRatingRepository bookRatingRepository;

    @Autowired
    private BookFavoriteRepository bookFavoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookOrderRepository bookOrderRepository;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private TimeProvider timeProvider;

    @Override
    public void removeBook(Long id) {
        final Book book = bookRepository.findById(id);
        book.setRemoved(true);
    }

    @Override
    public void saveOrUpdateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public BookOrder reserveBook(long bookId, long userId) {

        User reservingUser = userRepository.findById(userId);
        Book bookBeingReserved = bookRepository.findById(bookId);

        checkIfReservationIsPossible(reservingUser, bookBeingReserved);
        
        BookOrder bookOrder = new BookOrder();
        bookOrder.setBook(bookBeingReserved);

        bookOrder.setReservationDate(timeProvider.getCurrentTime().toDate());

        bookOrder.setStatus(BookOrderStatus.RESERVED);
        bookOrder.setUser(reservingUser);

        bookOrder = bookOrderRepository.save(bookOrder);

        bookBeingReserved.setAvailableQuantity(bookBeingReserved.getAvailableQuantity()-1);

        return bookOrder;

    }

	private void checkIfReservationIsPossible(User reservingUser, Book bookBeingReserved) {
		
		checkBorrowAvailable(bookBeingReserved);
		
		if(null != reservingUser){
        	if(reservingUser.isReservationBlocked()){
        		throw new RuntimeException(USER_RESERVATIONS_BLOCKED);
        	}
        }
	}
    
    @Override
	public BookOrder borrowBook(long userId, long orderId){

        BookOrder bookOrder = bookOrderRepository.findById(orderId);
        
        bookOrder.setStatus(BookOrderStatus.BORROWED);
        bookOrder.setBorrowedDate(timeProvider.getCurrentTime().toDate());
        bookOrder.setExpectedReturnDate(timeProvider.getCurrentDate().plusDays(Integer.valueOf(adminRepository.getConfigByName("max.loan.period").getValue())).toDate());
        
        bookOrder = bookOrderRepository.save(bookOrder);

        return bookOrder;
    }
    
    @Override
  	public BookOrder returnBook(long bookId, long userId, long orderId){
          Book bookBorrowed = bookRepository.findById(bookId);
          User user = userRepository.findById(userId);
          
          BookOrder bookOrder = bookOrderRepository.findById(orderId);
          
          bookOrder.setStatus(BookOrderStatus.RETURNED);
          bookOrder.setReturnedDate(timeProvider.getCurrentTime().toDate());
          
          bookBorrowed.setAvailableQuantity(bookBorrowed.getAvailableQuantity() + 1);
          
          bookOrderRepository.save(bookOrder);
          bookRepository.save(bookBorrowed);
          
          if(user.isReservationBlocked()){
        	  checkIfUnlockIsPossible(user);
          }
          
          
    	return bookOrder;
      }

	private void checkIfUnlockIsPossible(User user) {
		Collection<User> results = userRepository.findUsersWithOvertimeOrders();

		if (results.contains(user)) {
			return;
		}

		user.setReservationBlocked(false);
		userRepository.save(user);
	}

	@Override
    public BookFeedback feedback(long userId, long bookId, String message) {
        BookFeedback feedback = new BookFeedback();
        final User user = userRepository.findById(userId);
        final Book book = bookRepository.findById(bookId);
        feedback.setBook(book);
        feedback.setUser(user);
        final Date currentDate = timeProvider.getCurrentTime().toDate();
        feedback.setCreatedDate(currentDate);
        feedback.setMessage(message);
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<BookFeedback> findBookFeedback(long bookId) {
        return feedbackRepository.getByBookId(bookId);
    }

    @Override
    public void evaluateBook(long userId, long bookId, int rateValue) {
        if (rateValue < 0 || rateValue > 5) {
            LOG.error("Wrong rating ({}) for book id={}" , rateValue, bookId);
            throw new RuntimeException("Wrong rating");
        }
        BookRating bookRating = bookRatingRepository.getRating(userId, bookId);
        if (bookRating == null) {
            assert rateValue != 0;
            bookRating = new BookRating();
            final User user = userRepository.findById(userId);
            final Book book = bookRepository.findById(bookId);
            bookRating.setUser(user);
            bookRating.setBook(book);
        } else if (rateValue == 0) {
            bookRatingRepository.remove(bookRating);
            return;
        }
        bookRating.setRating(rateValue);
        final Date currentDate = timeProvider.getCurrentTime().toDate();
        bookRating.setCreatedDate(currentDate);
        bookRatingRepository.save(bookRating);
    }

    @Override
    public int findBookRating(long userId, long bookId) {
        BookRating bookRating = bookRatingRepository.getRating(userId, bookId);
        int rating = 0;
        if (bookRating != null) {
            rating = bookRating.getRating();
        }
        return rating;
    }

    private void checkBorrowAvailable(Book book) {

        if(book.isRemoved()) {
            throw new RuntimeException(BOOK_DELETED_ERROR);
        }

        if(book.getAvailableQuantity() < 1){
            throw new RuntimeException(BOOK_UNAVAILABLE_ERROR);
        }
    }

    @Override
    public Book findBook(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Collection<BookPopularity> findMostReadBooks(int numberOfBooks) {
        return bookRepository.findMostReadBooks(numberOfBooks);
    }

    @Override
    public Collection<BookPopularity> findMostRatedBooks(int numberOfBooks) {
        return bookRatingRepository.findMostRatedBooks(numberOfBooks);
    }

    @Override
    public Books searchBook(BookSearch bookSearch) {
        return bookRepository.searchBooks(bookSearch);
    }

    @Override
    public void addToFavorites(long userId, long bookId) {
        BookFavorite favoriteBook = new BookFavorite();
        final Book book = bookRepository.findById(bookId);
        favoriteBook.setBook(book);
        final User user = userRepository.findById(userId);
        favoriteBook.setUser(user);
        final Date currentDate = timeProvider.getCurrentTime().toDate();
        favoriteBook.setCreatedDate(currentDate);
        bookFavoriteRepository.save(favoriteBook);
        LOG.info("user({}) added book({}) into favorites", userId, bookId); //TODO change to debug level
    }

    @Override
    public void removeFromFavorites(long userId, long bookId) {
        bookFavoriteRepository.remove(userId, bookId);
        LOG.info("user({}) removed book({}) from favorites", userId, bookId);
    }

    @Override
    public Collection<BookFavorite> getFavorites(long userId) {
        return bookFavoriteRepository.findUserFavorites(userId);
    }

    @Override
    public boolean isFavoriteBook(long userId, long bookId) {
        return bookFavoriteRepository.isFavorite(userId, bookId);
    }
}
