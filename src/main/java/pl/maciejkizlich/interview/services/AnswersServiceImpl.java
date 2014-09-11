package pl.maciejkizlich.interview.services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.maciejkizlich.interview.persistence.dao.AnswersRepository;
import pl.maciejkizlich.interview.persistence.model.Answer;

@Service
@Transactional
public class AnswersServiceImpl implements AnswersService {

	@Autowired
	private AnswersRepository answersRepository;
	
	@Override
	public Collection<Answer> findAll() {
		return answersRepository.findAll();
	}

	@Override
	public Collection<Answer> findAllForGivenQuestion(long questionId) {
		return answersRepository.findAllByGivenQuestionId(questionId);
	}

}
