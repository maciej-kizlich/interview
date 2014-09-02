package pl.maciejkizlich.interview.persistence.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class BookOrder implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Book book;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reservationDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowedDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expectedReturnDate;

    @Enumerated(EnumType.STRING)
    private BookOrderStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date returnedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getBorrowedDate() {
		return borrowedDate;
	}

	public void setBorrowedDate(Date borrowDate) {
		this.borrowedDate = borrowDate;
	}

	public Date getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(Date expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public BookOrderStatus getStatus() {
        return status;
    }

    public void setStatus(BookOrderStatus status) {
        this.status = status;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(reservationDate)
                .append(expectedReturnDate)
                .append(status)
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

        BookOrder order = (BookOrder) o;
        return new EqualsBuilder()
                .append(order.id, id)
                .append(order.reservationDate, reservationDate)
                .append(order.expectedReturnDate, expectedReturnDate)
                .append(order.status, status)
                .isEquals();
    }

}
