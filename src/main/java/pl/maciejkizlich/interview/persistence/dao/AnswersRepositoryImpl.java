package pl.maciejkizlich.interview.persistence.dao;

import java.util.Collection;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pl.maciejkizlich.interview.persistence.model.Answer;

@Repository
public class AnswersRepositoryImpl extends AbstractModelRepository<Answer> implements AnswersRepository {

	protected AnswersRepositoryImpl() {
		super(Answer.class);
	}
	
	@Override
	public void saveOrUpdate(Answer answer) {
		if(null == answer.getId()){
			em.persist(answer);
		} else {
			em.merge(answer);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Answer> findAll() {
        Query q = em.createQuery("SELECT q FROM Answer q");
        return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Answer> findAllByGivenQuestionId(long questionId) {
		Query q = em.createQuery("SELECT q FROM Answer q WHERE q.question.id = :questionId")
				.setParameter("questionId", questionId);
		
		return q.getResultList();
	}
}
