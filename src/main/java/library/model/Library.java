package main.java.library.model;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private final List<Book> books = new ArrayList<>();
    private int nextIndex = 0;

    public boolean addBook(String title, String author, String genre, int year, String isbn, String description){
        if(!isValidIsbn(isbn)){
            throw new IllegalArgumentException("ISBN non valido");
        }

        for (Book book : books) {
            if(book.getIsbn().equalsIgnoreCase(isbn))
                throw new IllegalArgumentException("Esiste già un libro con questo ISBN");
        }

        Book book = new Book(nextIndex++, title, author, genre, year, isbn, description);
        books.add(book);
        return true;
    }

    public boolean removeBookById(int id) {
        for (int i = 0; i < books.size(); i++) {
            if(books.get(i).getId() == id) {
                books.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean modifyBook(int id, String newTitle, String newAuthor, String newIsbn, String newDescription, int year, String newGenre){
        for (Book book: books){
            if(book.getId()==id){
                if(!book.getIsbn().equalsIgnoreCase(newIsbn)){
                    if(!isValidIsbn(newIsbn)) throw new IllegalArgumentException("Nuovo ISBN non valido");

                    for (Book other: books){
                        if(other.getIsbn().equalsIgnoreCase(newIsbn)) throw new IllegalArgumentException("Nuovo ISBN già presente");
                    }
                }

                book.setTitle(newTitle);
                book.setAuthor(newAuthor);
                book.setDescription(newDescription);
                book.setIsbn(newIsbn);
                book.setGenre(newGenre);
                book.setYear(year);
                return true;

            }
        }
        return false;
    }

    public List<Book> searchByTitle(String title){
        List<Book> result = new ArrayList<>();
        for(Book book: books){
            if(book.getTitle().toLowerCase().contains(title.toLowerCase())) result.add(book);
        }

        return result;
    }

    public List<Book> searchByAuthor(String author){
        List<Book> result = new ArrayList<>();
        for(Book book: books){
            if(book.getAuthor().toLowerCase().contains(author.toLowerCase())) result.add(book);
        }

        return result;
    }

    public List<Book> searchByISBN(String isbn){
        List<Book> result =  new ArrayList<>();
        for(Book book: books){
            if(book.getIsbn().equalsIgnoreCase(isbn)) result.add(book);
        }

        return result;
    }

    public List<Book> filterByYear(int year){
        List<Book> result =  new ArrayList<>();
        for(Book book: books){
            if(book.getYear() == year) result.add(book);
        }

        return result;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public Book getBookById(int id) {
        for (Book book: books){
            if (book.getId() == id) return book;
        }
        return null;
    }

    private boolean isValidIsbn(String isbn){
        if (isbn == null) return false;
        String cleanIsbn = isbn.replaceAll("[\\s-]","");

        return cleanIsbn.matches("^(?:\\d{10}|\\d{13})$\n");
    }

}
