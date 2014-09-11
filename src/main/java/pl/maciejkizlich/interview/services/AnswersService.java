package pl.maciejkizlich.interview.services;

import java.util.Collection;

import pl.maciejkizlich.interview.persistence.model.Answer;

public interface AnswersService {

	Collection<Answer> findAll();

	Collection<Answer> findAllForGivenQuestion(long questionId); 
	
}
