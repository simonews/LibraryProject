package test.java.library.model;

import main.java.library.model.Book;
import main.java.library.model.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;

    @BeforeEach
    public void setUp() {
        library = Library.getInstance();
    }

    @Test
    public void testAddBook() {
        Book book = new Book("titolo", "autore", "thriller", 2010, "1234567890", "yes thanks good book");
        boolean res = library.addBook(book);
        assertTrue(res);
        assertEquals(1, library.getBooks().size());
    }

    @Test
    public void testDuplicateIsbnAdded(){
        Book b1 = new Book("tt", "aa", "tt", 2010, "1234567890", "abc");
        Book b2 = new Book("t2", "aa", "tt", 2011, "1234567890", "abc");
        library.addBook(b1);
        boolean res = library.addBook(b2);
        assertFalse(res);
        assertEquals(1, library.getBooks().size());
    }

}
