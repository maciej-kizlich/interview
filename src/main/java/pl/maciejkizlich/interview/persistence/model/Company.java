package pl.maciejkizlich.interview.persistence.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@SuppressWarnings("serial")
@Entity
@Table(name="t_companies")
public class Company implements Model{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "company")
    Set<Question> questions = new HashSet<>();

    public Company(){
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}
	
	  @Override
	    public int hashCode() {
	        return new HashCodeBuilder()
	                .append(id)
	                .append(name)
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

	        Company company = (Company) o;
	        return new EqualsBuilder()
	                .append(company.id, id)
	                .append(company.name, name)
	                .isEquals();
	    }
}
