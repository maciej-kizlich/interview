package pl.maciejkizlich.interview.web.controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.maciejkizlich.interview.persistence.model.Answer;
import pl.maciejkizlich.interview.persistence.model.Question;
import pl.maciejkizlich.interview.services.AnswersService;
import pl.maciejkizlich.interview.services.QuestionsService;

@Controller
@RequestMapping("answers")
public class AnswersController {

	@Autowired
	private AnswersService answersService;
	
	@Autowired
	private QuestionsService questionsService;

	@RequestMapping(value = "/allAnswers", method = RequestMethod.GET)
	public String showAllAnswers(Map<String, Object> model) {
		Collection<Answer> answers = answersService.findAll();
		model.put("answers", answers);

		return "answers/showAllAnswers"; 
	}

	@RequestMapping(value = "/qAnswers/{questionId}", method = RequestMethod.GET)
	public String showAllAnswersForGivenQuestion(@PathVariable(value = "questionId") long questionId, Map<String, Object> model) {
		Collection<Answer> answers = answersService.findAllForGivenQuestion(questionId);
		Question question = questionsService.findById(questionId);
		model.put("question", question);
		model.put("answers", answers);
		
		return "answers/showAllAnswers"; 
	}
}
