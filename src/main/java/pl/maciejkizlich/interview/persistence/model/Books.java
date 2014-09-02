package pl.maciejkizlich.interview.persistence.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oleksandr_Kalnyi
 */
//@XmlRootElement
public class Books {

    private List<BookData> books;

    //@XmlElement
    public List<BookData> getBookList() {
        if (books == null) {
            books = new ArrayList<>();
        }
        return books;
    }

    public void addBook(BookData bookData) {
        if (this.books == null) {
            this.books = new ArrayList<>();
        }
        this.books.add(bookData);
    }

    public int size() {
        return books == null ? 0 : books.size();
    }

    public BookData get(int i) {
        return books.get(0);
    }

    /**
     * Represents book informal class which contains Book as well
     */
    public static class BookData {
        private Book book;
        private double rating;
        /**
         * 1 - favorite; 0 - none
         */
        private int favorite;
        private int popularity; // not implemented yet
        private List<BookFeedback> feedbackList;

        public Book getBook() {
            return book;
        }

        public void setBook(Book book) {
            this.book = book;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public int getPopularity() {
            return popularity;
        }

        public void setPopularity(int popularity) {
            this.popularity = popularity;
        }

        public int getFavorite() {
            return favorite;
        }

        public void setFavorite(int favorite) {
            this.favorite = favorite;
        }

        public List<BookFeedback> getFeedbackList() {
            return feedbackList;
        }

        public void setFeedbackList(List<BookFeedback> feedbackList) {
            this.feedbackList = feedbackList;
        }
    }
}
