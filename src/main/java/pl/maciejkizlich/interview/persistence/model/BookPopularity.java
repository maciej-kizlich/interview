package pl.maciejkizlich.interview.persistence.model;

import java.util.Comparator;

/**
 * Created by Levon_Movsesyan on 8/19/2014.
 */
public class BookPopularity {

    private final Book book;
    private double value;

    public BookPopularity(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static class FamousComparator implements Comparator<BookPopularity> {

        @Override
        public int compare(BookPopularity o1, BookPopularity o2) {
            double diff = o1.value - o2.value;
            if (diff < 0) {
                return 1;
            } else if (diff > 0) {
                return -1;
            }
            return 0;
        }
    }
}
