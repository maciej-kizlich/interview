package pl.maciejkizlich.interview.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.maciejkizlich.interview.persistence.model.BookOrder;
import pl.maciejkizlich.interview.persistence.model.BookOrderStatus;
import pl.maciejkizlich.interview.security.UserPrincipal;
import pl.maciejkizlich.interview.service.OrderService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/myOrders", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String showUserOrders(Map<String, Object> model) {
    	
    	List<BookOrderStatus> allowedStatuses = new ArrayList<BookOrderStatus>();
    	allowedStatuses.add(BookOrderStatus.RESERVED);
    	allowedStatuses.add(BookOrderStatus.BORROWED);
    	
    	Collection<BookOrder> orders = orderService.findByUserAndGivenStatuses(UserPrincipal.getLoggedUserId(), allowedStatuses);
        model.put("orders", orders);

        return "orders/manage";
    }

    @RequestMapping(value="/allOrders", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showAllOrders(Map<String, Object> model) {
        Collection<BookOrder> orders = orderService.findAll();
        model.put("orders", orders);

        return "orders/manage";
    }
    
    @RequestMapping(value="/cancelOrder", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String cancelOrder(@RequestParam Long orderId, Map<String, Object> model) {
        BookOrder canceledOrder = orderService.cancelOrderById(orderId);
        model.put("order", canceledOrder);

        return "orders/canceledOrder";
    }
    
}
