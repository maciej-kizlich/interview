package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;

import pl.maciejkizlich.interview.persistence.model.Question;

public interface QuestionsRepository {

	Collection<Question> findAll();
	
	Question findById(Long id);

	Question save(Question question);
	
}
