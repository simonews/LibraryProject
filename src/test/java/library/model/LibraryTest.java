package test.java.library.model;

import main.java.library.controller.LibraryController;
import main.java.library.model.Book;
import main.java.library.model.BookFactory;
import main.java.library.model.Library;
import main.java.library.model.enums.Rating;
import main.java.library.model.enums.ReadingStatus;
import main.java.library.util.SortByYear;
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
        Book book = BookFactory.createBook("titolo", "autore", "thriller", 2010, "1234567890", Rating.DUE, ReadingStatus.DA_LEGGERE, "aaa" );
        boolean res = library.addBook(book);
        assertTrue(res);
        assertEquals(1, library.getBooks().size());
    }

    @Test
    public void testDuplicateIsbnAdded(){
        Book b1 = BookFactory.createBook("titolo1", "autore", "thriller", 2010, "1234567890", Rating.DUE, ReadingStatus.DA_LEGGERE, "aaa" );
        Book b2 = BookFactory.createBook("titolo2", "autore", "thriller", 2010, "1234567890", Rating.DUE, ReadingStatus.DA_LEGGERE, "aaa" );
        library.addBook(b1);
        boolean res = library.addBook(b2);
        assertFalse(res);
        assertEquals(1, library.getBooks().size());
    }

    @Test
    public void testRemoveBook() {
        Book book = BookFactory.createBook("titolo", "autore", "thriller", 2010, "1234567890", Rating.DUE, ReadingStatus.DA_LEGGERE, "aaa" );
        library.addBook(book);
        int id = book.getId();
        boolean removed = library.removeBookById(id);
        assertTrue(removed);
        assertEquals(0, library.getAllBooks().size());
    }

    @Test
    public void testGetAllBooks() {
        Book b1 = BookFactory.createBook("titolo1", "autore", "thriller", 2010, "1234567890", Rating.DUE, ReadingStatus.DA_LEGGERE, "aaa" );
        Book b2 = BookFactory.createBook("titolo2", "autore", "thriller", 2010, "1234567891", Rating.DUE, ReadingStatus.DA_LEGGERE, "aaa" );
        library.addBook(b1);
        library.addBook(b2);

        List<Book> books = library.getAllBooks();
        assertEquals(2, books.size());
        assertTrue(books.contains(b1));
        assertTrue(books.contains(b2));
    }

    @Test
    void testFilterBooksByAuthor() {
        Book b1 = BookFactory.createBook("titolo1", "a", "thriller", 2010, "1234567891", Rating.DUE, ReadingStatus.DA_LEGGERE, "aaa" );
        Book b2 = BookFactory.createBook("titolo2", "b", "thriller", 2010, "1234567892", Rating.DUE, ReadingStatus.DA_LEGGERE, "aaa" );
        Book b3 = BookFactory.createBook("titolo3", "c", "thriller", 2010, "1234567893", Rating.DUE, ReadingStatus.DA_LEGGERE, "aaa" );
        controller.addBook(b1);
        controller.addBook(b2);
        controller.addBook(b3);

        List<Book> filtered = controller.filterBooks("autore", "a");
        assertEquals(1, filtered.size(), "il filtro per autore non ha restituito il numero corretto di libri");
    }

    @Test
    void testUpdateBook() {
        Book book = BookFactory.createBook("titolo1", "autore", "genere", 2005, "1224567890", Rating.DUE, ReadingStatus.DA_LEGGERE, "" );
        controller.addBook(book);
        int id = book.getId();

        Book updatedBook = BookFactory.createBook("titoloAggiornato", "autore", "genere", 2005, "1224567890", Rating.DUE, ReadingStatus.DA_LEGGERE, "desc" );
        boolean result = controller.updateBook(id, updatedBook);

        assertTrue(result);
        assertEquals("titoloAggiornato", library.getBookById(id).getTitle() );
    }

    @Test
    void testSortByYear() {
        Book b1 = BookFactory.createBook("titolo1", "a", "thriller", 2000, "1234567891", Rating.DUE, ReadingStatus.DA_LEGGERE, "aaa" );
        Book b2 = BookFactory.createBook("titolo2", "b", "thriller", 2010, "1234567892", Rating.DUE, ReadingStatus.DA_LEGGERE, "aaa" );

        controller.addBook(b1);
        controller.addBook(b2);

        controller.sortBooks(new SortByYear());
        List<Book> sorted = controller.getAllBooks();

        assertEquals("titolo1", sorted.get(0).getTitle());
        assertEquals("titolo2", sorted.get(1).getTitle());
    }

}
