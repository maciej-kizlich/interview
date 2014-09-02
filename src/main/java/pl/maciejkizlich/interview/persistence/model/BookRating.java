package pl.maciejkizlich.interview.persistence.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Levon_Movsesyan on 8/20/2014.
 */
@Entity
public class BookRating implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Book book;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private int rating;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(rating)
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

        BookRating bookRating = (BookRating) o;
        return new EqualsBuilder()
                .append(bookRating.id, id)
                .append(bookRating.rating, rating)
                .append(bookRating.createdDate, createdDate)
                .append(bookRating.book.getId(), book.getId())
                .append(bookRating.user.getId(), user.getId())
                .isEquals();
    }
}


