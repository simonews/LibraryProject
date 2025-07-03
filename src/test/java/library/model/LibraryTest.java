package test.java.library.model;

import main.java.library.controller.LibraryController;
import main.java.library.model.Book;
import main.java.library.model.BookFactory;
import main.java.library.model.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private LibraryController controller;

    @BeforeEach
    public void setUp() {
        library = Library.getInstance();
        library.setBooks(new ArrayList<>());
        controller = new LibraryController();
    }

    @Test
    public void testAddBook() {
        Book book = BookFactory.createBook("titolo", "autore", "thriller", 2010, "1234567890", "yes thanks good book");
        boolean res = library.addBook(book);
        assertTrue(res);
        assertEquals(1, library.getBooks().size());
    }

    @Test
    public void testDuplicateIsbnAdded(){
        Book b1 = BookFactory.createBook("tt", "aa", "tt", 2010, "1234567890", "abc");
        Book b2 = BookFactory.createBook("t2", "aa", "tt", 2011, "1234567890", "abc");
        library.addBook(b1);
        boolean res = library.addBook(b2);
        assertFalse(res);
        assertEquals(1, library.getBooks().size());
    }

    @Test
    public void testRemoveBook() {
        Book book = BookFactory.createBook("Titolo", "Autore", "si", 2020, "1234567890", "Descrizione");
        library.addBook(book);
        int id = book.getId();
        boolean removed = library.removeBookById(id);
        assertTrue(removed);
        assertEquals(0, library.getAllBooks().size());
    }

    @Test
    public void testGetAllBooks() {
        Book b1 = BookFactory.createBook("tt", "aa", "tt", 2010, "1234567890", "abc");
        Book b2 = BookFactory.createBook("t2", "aa", "tt", 2011, "2234567890", "abc");
        library.addBook(b1);
        library.addBook(b2);

        List<Book> books = library.getAllBooks();
        assertEquals(2, books.size());
        assertTrue(books.contains(b1));
        assertTrue(books.contains(b2));
    }

    @Test
    void testFilterBooksByAuthor() {
        Book b1 = BookFactory.createBook("tt", "andrea", "tt", 2010, "1234567890", "abc");
        Book b2 = BookFactory.createBook("t2", "michele", "tt", 2011, "2234567890", "abc");
        Book b3 = BookFactory.createBook("t2", "luca", "tt", 2011, "2334567890", "abc");
        controller.addBook(b1);
        controller.addBook(b2);
        controller.addBook(b3);

        List<Book> filtered = controller.filterBooks("autore", "michele");
        assertEquals(1, filtered.size(), "il filtro per autore non ha restituito il numero corretto di libri");
    }
}
