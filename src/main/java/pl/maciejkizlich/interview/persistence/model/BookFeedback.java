package pl.maciejkizlich.interview.persistence.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Levon_Movsesyan on 8/13/2014.
 */
@Entity
public class BookFeedback implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    private Book book;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(message)
                .append(createdDate)
                .append(book.getId())
                .append(user.getId())
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

        BookFeedback feedback = (BookFeedback) o;
        return new EqualsBuilder()
                .append(feedback.id, id)
                .append(feedback.message, message)
                .append(feedback.createdDate, createdDate)
                .append(feedback.book.getId(), book.getId())
                .append(feedback.user.getId(), user.getId())
                .isEquals();
    }
}
