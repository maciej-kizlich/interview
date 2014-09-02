package pl.maciejkizlich.interview.persistence.model;

/**
 * Created by Levon_Movsesyan on 8/20/2014.
 * Represents search criterion fields of books
 */
public class BookSearch {

    public BookSearch() {
        this.id = 0;
    }

    public BookSearch(long id) {
        this.id = id;
    }

    /**
     * ID of book which going to search. If this member is greater than 0 then other search members not considered
     */
    private final long id;
    /**
     * partially matches with author name, if it's null or empty then consider all authors
     */
    private String author;
    /**
     * partially matches with book title, if it's null or empty then consider all authors
     */
    private String title;
    /**
     * minimum rating of books to search
     */
    private int rating;

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author == null ? "" : author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
