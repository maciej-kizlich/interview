package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;

import pl.maciejkizlich.interview.persistence.model.Answer;

public interface AnswersRepository {

	void saveOrUpdate(Answer answer);
	
	Answer findById(Long id);
	
	Collection<Answer> findAll();

	Collection<Answer> findAllByGivenQuestionId(long questionId);
		
}
