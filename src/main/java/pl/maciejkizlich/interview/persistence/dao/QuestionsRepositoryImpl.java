package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pl.maciejkizlich.interview.persistence.model.Question;

@Repository
public class QuestionsRepositoryImpl extends AbstractModelRepository<Question> implements QuestionsRepository {

	protected QuestionsRepositoryImpl() {
		super(Question.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Question> findAll() {
		Query q = em.createQuery("SELECT q FROM Question q");
		return q.getResultList();
	}

	@Override
	public Question save(Question question) {
		if (question.getId() == null) {
			em.persist(question);
		} else {
			em.merge(question);
		}
		;
		return question;
	}

	@Override
	public Question findById(Long id) {
		return super.findById(id);
	}
}
