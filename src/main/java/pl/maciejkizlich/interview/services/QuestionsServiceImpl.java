package pl.maciejkizlich.interview.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.maciejkizlich.interview.persistence.dao.AnswersRepository;
import pl.maciejkizlich.interview.persistence.dao.CompaniesRepository;
import pl.maciejkizlich.interview.persistence.dao.QuestionsRepository;
import pl.maciejkizlich.interview.persistence.dao.UserRepository;
import pl.maciejkizlich.interview.persistence.model.Answer;
import pl.maciejkizlich.interview.persistence.model.Company;
import pl.maciejkizlich.interview.persistence.model.Question;
import pl.maciejkizlich.interview.persistence.model.User;
import pl.maciejkizlich.interview.utils.TimeProvider;
import pl.maciejkizlich.interview.web.DTO.QuestionDTO;

@Service
@Transactional
public class QuestionsServiceImpl implements QuestionsService{

	@Autowired
	private QuestionsRepository questionsRepository;
	
	@Autowired
	private AnswersRepository answersRepository;
	
	@Autowired
	private CompaniesRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TimeProvider timeProvider;
	
    @Override
    public Collection<Question> findAll() {
        return questionsRepository.findAll();
    }

	@Override
	public void saveOrUpdateQuestion(QuestionDTO questionDTO) {

		String companyName = questionDTO.getCompanyName();
		
		Company company = companyRepository.getCompanyByName(companyName);
		
		if(null == company){
			company = new Company();
			company.setName(companyName);
			companyRepository.saveOrUpdate(company);
		}
		
		Question question = new Question();
		
		question.setCompany(company);
		question.setAskDate(timeProvider.getCurrentTime().toDate());
		question.setPosition(questionDTO.getPosition());
		question.setQuestion(questionDTO.getQuestion());
		
		Answer answer = new Answer();
		answer.setAnswer(questionDTO.getAnswer());
		answer.setAnswerDate(timeProvider.getCurrentTime().toDate());
		
		User user = userRepository.findById(questionDTO.getUserId());
		answer.setUser(user);
		
		Set<Answer> answers = new HashSet<Answer>();
		answers.add(answer);
		
		answersRepository.saveOrUpdate(answer);
		
		question.setAnswers(answers);
		question.setUser(user);
		
		questionsRepository.save(question);
		
		Answer a = answersRepository.findById(answer.getId()); //fuckup
		a.setQuestion(question);
		answersRepository.saveOrUpdate(answer);
		
	}

	@Override
	public Question findById(long questionId) {
		return questionsRepository.findById(questionId);
	}
}
