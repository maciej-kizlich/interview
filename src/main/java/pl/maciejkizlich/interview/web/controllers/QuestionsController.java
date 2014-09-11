package pl.maciejkizlich.interview.web.controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.maciejkizlich.interview.persistence.model.Question;
import pl.maciejkizlich.interview.security.UserPrincipal;
import pl.maciejkizlich.interview.services.QuestionsService;
import pl.maciejkizlich.interview.web.DTO.QuestionDTO;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    @Autowired
    private QuestionsService questionsService;
	
    @RequestMapping(value="/allQuestions", method = RequestMethod.GET)
    public String showAllQuestions(Map<String, Object> model) {
        Collection<Question> questions = questionsService.findAll();
        model.put("questions", questions);

        return "questions/showAllQuestions"; 
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addQuestion(Map<String, Object> model) {
    	QuestionDTO questionDTO = new QuestionDTO();
        model.put("question", questionDTO);
        return "questions/questionEdit";
    }
    
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String editQuestion(@ModelAttribute("question") QuestionDTO questionDTO) {

		long callerId = UserPrincipal.getLoggedUserId();

		questionDTO.setUserId(callerId);

		questionsService.saveOrUpdateQuestion(questionDTO);
		return "redirect:/questions/allQuestions";
	}
}
