package main.java.library.model;

public class BookFactory {

    private BookFactory() {}

    public static Book createBook(String title, String author, String genre, int year, String isbn, String description){
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Titolo non valido");
        }
        if(author == null || author.isBlank()) {
            throw new IllegalArgumentException(("Autore non valido"));
        }
        if(genre == null || genre.isBlank()){
            throw new IllegalArgumentException("Genere non valido");
        }
        return new Book(title,author,genre,year,isbn,description);
    }
}
