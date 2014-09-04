package pl.maciejkizlich.interview.web.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.maciejkizlich.interview.persistence.model.Book;
import pl.maciejkizlich.interview.persistence.model.BookFeedback;
import pl.maciejkizlich.interview.persistence.model.BookOrder;
import pl.maciejkizlich.interview.persistence.model.BookPopularity;
import pl.maciejkizlich.interview.persistence.model.BookSearch;
import pl.maciejkizlich.interview.persistence.model.Books;
import pl.maciejkizlich.interview.security.UserPrincipal;
import pl.maciejkizlich.interview.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/get/{bookId}", method = RequestMethod.GET)
    public String showBookDetails(@PathVariable(value = "bookId") long bookId, ModelMap model) {
        final long userId = UserPrincipal.getLoggedUserId();
        prepareBookDetail(userId, bookId, model);
        return "books/bookDetails";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showBookList(ModelMap model) {
        
        Books books = new Books();
        Collection<Books.BookData> notFiltered = bookService.searchBook(new BookSearch()).getBookList();
        List<Books.BookData> filtered = notFiltered.stream().filter(bookData -> !bookData.getBook().isRemoved()).collect(Collectors.toList());
        books.getBookList().addAll(filtered);
        
        model.put("books", books);

        prepareTopBooks(model);

        return "books/bookList";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String search(@ModelAttribute("bookSearch") BookSearch bookSearch, ModelMap model) {
        Books books = bookService.searchBook(bookSearch);
        model.put("books", books);

        prepareTopBooks(model);

        return "books/bookList";
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String removeBook(@RequestParam Long bookId, ModelMap model) {

        bookService.removeBook(bookId);

        return "redirect:/books/list";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String loadBookForEditing(@PathVariable(value = "id") Long id, Map<String, Object> model) {
        final Book book = bookService.findBook(id);
        if (book == null) {
            //TODO: add no book found page
            return "redirect:/books";
        }
        model.put("book", book);
        return "books/bookEdit";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createBook(Map<String, Object> model) {
        Book book = new Book();
        model.put("book", book);
        return "books/bookEdit";
    }

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String editBook(@ModelAttribute("book") Book book) {
		bookService.saveOrUpdateBook(book);
		return "redirect:/books/list";
	}

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String reserveBook(@RequestParam long bookId, ModelMap model) {

        long callerId = UserPrincipal.getLoggedUserId();
        BookOrder bookOrder = bookService.reserveBook(bookId, callerId);
        model.put("book", bookOrder.getBook());

        return "books/bookReserved";
    }
    
    @RequestMapping(value = "/borrow", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String borrowBook(@RequestParam long borrowingUserId, @RequestParam long orderId, ModelMap model) {

        BookOrder bookOrder = bookService.borrowBook(borrowingUserId, orderId);
        model.put("book", bookOrder.getBook());

        return "books/bookBorrowed";
    }
    
    @RequestMapping(value = "/return", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String returnBook(@RequestParam long bookId, @RequestParam long orderId, ModelMap model) {

        BookOrder bookOrder = bookService.returnBook(bookId, UserPrincipal.getLoggedUserId(), orderId);
        model.put("book", bookOrder.getBook());

        return "books/bookReturned";
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String feedback(@RequestParam long bookId, @RequestParam String message, ModelMap model) {
        final long userId = UserPrincipal.getLoggedUserId();
        bookService.feedback(userId, bookId, message);
        prepareBookDetail(userId, bookId, model);
        return "books/bookDetails";
    }

    @RequestMapping(value = "/evaluate", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void evaluate(@RequestParam int rateValue, @RequestParam int bookId, ModelMap model) {
        final long userId = UserPrincipal.getLoggedUserId();
        bookService.evaluateBook(userId, bookId, rateValue);
    }

    @RequestMapping(value = "/addFavorite", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void addFavorite(@RequestParam int bookId, ModelMap model) {
        final long userId = UserPrincipal.getLoggedUserId();
        bookService.addToFavorites(userId, bookId);
    }

    @RequestMapping(value = "/removeFavorite", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void removeFavorite(@RequestParam int bookId, ModelMap model) {
        final long userId = UserPrincipal.getLoggedUserId();
        bookService.removeFromFavorites(userId, bookId);
    }

    @RequestMapping(value = "/getFavorites", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public void getFavorites(ModelMap model) {
        final long userId = UserPrincipal.getLoggedUserId();
        bookService.getFavorites(userId);
    }

    private void prepareBookDetail(long userId, long bookId, ModelMap model) {
        assert model != null;

        Books books = bookService.searchBook(new BookSearch(bookId));
        Books.BookData bookData = books.get(0);

        boolean isFavorite = bookService.isFavoriteBook(userId, bookId);
        bookData.setFavorite(isFavorite ? 1 : 0);

        List<BookFeedback> feedbackList = bookService.findBookFeedback(bookId);
        bookData.setFeedbackList(feedbackList);

        model.put("data", bookData);
    }

    private void prepareTopBooks(ModelMap model) {
        assert model != null;

        Collection<BookPopularity> mostReadBooks = bookService.findMostReadBooks(5);
        model.put("mostReadBooks", mostReadBooks);

        Collection<BookPopularity> mostRatedBooks = bookService.findMostRatedBooks(5);
        model.put("mostRatedBooks", mostRatedBooks);
    }
}
