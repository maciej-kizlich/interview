package pl.maciejkizlich.interview.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name="t_user_messages")
public class UserMessage implements Model{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String topic;
	
	private String message;
	
	private boolean read;
	
	@Column(name="message_date")
	private Date messageDate;
	
//	@Transient
//	private Set<UserMessage> replies = new HashSet<UserMessage>();
	
	@JoinColumn(name="to_user_id")
	@ManyToOne(fetch = FetchType.EAGER) //check
	private User toUser;
	
	@JoinColumn(name="from_user_id")
	@OneToOne(fetch = FetchType.EAGER) //check
	private User fromUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

//	public Set<UserMessage> getReplies() {
//		return replies;
//	}
//
//	public void setReplies(Set<UserMessage> replies) {
//		this.replies = replies;
//	}
	
    public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	@Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(message)
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

        UserMessage user = (UserMessage) o;
        return new EqualsBuilder()
                .append(user.id, id)
                .append(user.message, message)
                .isEquals();
    }
}
