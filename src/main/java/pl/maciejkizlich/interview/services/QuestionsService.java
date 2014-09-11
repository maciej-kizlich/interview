package pl.maciejkizlich.interview.services;

import java.util.Collection;

import pl.maciejkizlich.interview.persistence.model.Question;
import pl.maciejkizlich.interview.web.DTO.QuestionDTO;

public interface QuestionsService {

	Collection<Question> findAll();

	void saveOrUpdateQuestion(QuestionDTO question);

	Question findById(long questionId);
	
}
