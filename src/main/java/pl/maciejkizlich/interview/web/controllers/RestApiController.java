package pl.maciejkizlich.interview.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.maciejkizlich.interview.persistence.model.Book;
import pl.maciejkizlich.interview.persistence.model.BookSearch;
import pl.maciejkizlich.interview.persistence.model.Books;
import pl.maciejkizlich.interview.services.BookService;

import java.util.Map;

@Controller
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.GET, headers="Accept=application/json")
    @ResponseBody
    public Book showBookDetails(@PathVariable(value = "bookId") long bookId, ModelMap model) {
        return  bookService.findBook(bookId);
    }




    @RequestMapping(value = "/books", method = RequestMethod.GET, headers="Accept=application/json")
    @ResponseBody
    public Books showBookList(Map<String, Object> model) {
        return bookService.searchBook(new BookSearch());
    }
}
