package main.java.library.model;

import main.java.library.model.enums.Rating;
import main.java.library.model.enums.ReadingStatus;

public class BookFactory {

    private BookFactory() {}

    public static Book createBook(String title, String author, String genre, int year, String isbn, Rating rating, ReadingStatus status, String description){
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Titolo non valido");
        }
        if(author == null || author.isBlank()) {
            throw new IllegalArgumentException(("Autore non valido"));
        }
        if(genre == null || genre.isBlank()){
            throw new IllegalArgumentException("Genere non valido");
        }
        return new Book(title,author,genre,year,isbn,rating,status,description);
    }
}
