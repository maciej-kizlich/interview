package pl.maciejkizlich.interview.persistence.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name="t_questions")
public class Question implements Model {

	private static final long serialVersionUID = 2434710080744151658L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	private Company company;

	private String question;
	
	private String position;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	private Set<Answer> answers = new HashSet<Answer>();
	
	@Column(name = "ask_date")
	private Date askDate;
	
	public Question(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}
	
    public Date getAskDate() {
		return askDate;
	}

	public void setAskDate(Date askDate) {
		this.askDate = askDate;
	}

	@Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(question)
                .append(position)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Question question = (Question) o;
        return new EqualsBuilder()
                .append(question.id, id)
                .append(question.question, this.question)
                .append(question.position, position)
                .isEquals();
    }
}
