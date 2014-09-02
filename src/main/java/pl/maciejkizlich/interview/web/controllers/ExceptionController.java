package pl.maciejkizlich.interview.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception) {


        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("uri", req.getRequestURI());

        mav.setViewName("exception");
        return mav;
    }


}