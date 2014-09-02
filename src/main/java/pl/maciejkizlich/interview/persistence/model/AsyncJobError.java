package pl.maciejkizlich.interview.persistence.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="async_jobs_errors")
public class AsyncJobError {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private Date dateOfOccuring;
	
	private String lastException;
	
	private String initialMessage;
	
	private String jobName;
	
	private String initialException;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOfOccuring() {
		return dateOfOccuring;
	}

	public void setDateOfOccuring(Date dateOfOccuring) {
		this.dateOfOccuring = dateOfOccuring;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getLastException() {
		return lastException;
	}

	public void setLastException(String lastException) {
		this.lastException = lastException;
	}

	public String getInitialMessage() {
		return initialMessage;
	}

	public void setInitialMessage(String initialMessage) {
		this.initialMessage = initialMessage;
	}

	public String getInitialException() {
		return initialException;
	}

	public void setInitialException(String exception) {
		this.initialException = exception;
	}

}
