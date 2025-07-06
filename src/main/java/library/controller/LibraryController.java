package main.java.library.controller;

import main.java.library.model.Book;
import main.java.library.model.Library;
import main.java.library.model.LibraryObserver;
import main.java.library.persistance.LibraryStorage;
import main.java.library.util.SortStrategy;

import java.io.IOException;
import java.util.List;

public class LibraryController {

    private final Library library;
    private final LibraryStorage storage;

    public LibraryController(){
        this.library = Library.getInstance();
        this.storage = LibraryStorage.getInstance();
    }

    public boolean addBook(Book book){
        return library.addBook(book);
    }

    public boolean removeBook(int id){
        return library.removeBookById(id);
    }

    public boolean removeBookByIsbn(String isbn){
        return library.removeBookByISBN(isbn);
    }

    public boolean updateBook(int id, Book updatedBook) {
        return library.modifyBook(
                id,
                updatedBook.getTitle(),
                updatedBook.getAuthor(),
                updatedBook.getIsbn(),
                updatedBook.getDescription(),
                updatedBook.getYear(),
                updatedBook.getGenre()
        );
    }

    public Book getBookByISBN(String isbn){
        return library.getBookByIsbn(isbn);
    }

    public void sortBooks(SortStrategy str){
        library.getBooksSortBy(str);
    }

    public List<Book> filterBooks(String crit, String value){
        return library.filterBooksBy(crit, value);
    }

    public boolean saveLibrary(String path) throws IOException {
        return storage.saveToFile(library.getAllBooks(), path);
    }

    public List<Book> loadLibrary(String path) throws IOException {
        List<Book> loaded = storage.loadFromFile(path);
        library.setBooks(loaded);
        return loaded;
    }

    public List<Book> getAllBooks(){
        return library.getBooks();
    }

    public void addObserver(LibraryObserver observer){
        library.addObserver(observer);
    }
}
