package pl.maciejkizlich.interview.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@SuppressWarnings("serial")
@Entity
@Table(name="t_answers")
public class Answer implements Model {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String answer;
	
	private int rating;
	
	@Column(name="answer_date")
	private Date answerDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Question question;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	  public Date getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}

	@Override
	    public int hashCode() {
	        return new HashCodeBuilder()
	                .append(id)
	                .append(answer)
	                .append(rating)
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

	        Answer answer = (Answer) o;
	        return new EqualsBuilder()
	                .append(answer.id, id)
	                .append(answer.answer, this.answer)
	                .isEquals();
	    }
	
}