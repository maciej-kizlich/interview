package pl.maciejkizlich.interview.web.controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.maciejkizlich.interview.persistence.model.BookOrder;
import pl.maciejkizlich.interview.services.OrderService;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    @Autowired
    private QuestionsService questionsService;
	
    @RequestMapping(value="/allQuestions", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String showAllQuestions(Map<String, Object> model) {
        Collection<BookOrder> orders = orderService.findAll();
        model.put("orders", orders);

        return "orders/manage";
    }
}
