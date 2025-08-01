package main.java.library.model;

import main.java.library.model.enums.Rating;
import main.java.library.model.enums.ReadingStatus;

import java.util.Objects;

public class Book {
    private int id = -1;
    private String title;
    private String author;
    private String genre;
    private int year;
    private String isbn;
    private String description;
    private Rating rating;
    private ReadingStatus status;

     Book(String title, String author, String genre, int year, String isbn, Rating rating, ReadingStatus status, String description){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.isbn = isbn;
        this.rating = rating;
        this.status = status;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int x) {
        this.id = x;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Rating getRating() {return rating;}

    public void setRating(Rating rating) {this.rating = rating;}

    public ReadingStatus getStatus() {return status;}

    public void setStatus(ReadingStatus s) {this.status = s;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }

    @Override
    public String toString() {
        return String.format("%s (%d) di %s [%s]", title, year, author, genre);
    }
}
