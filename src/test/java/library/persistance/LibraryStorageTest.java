package test.java.library.persistance;

import main.java.library.model.Book;
import main.java.library.model.BookFactory;
import main.java.library.model.enums.Rating;
import main.java.library.model.enums.ReadingStatus;
import main.java.library.persistance.LibraryStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryStorageTest {

    private static final String TEST_FILE_PATH = "testbooks.json";
    private LibraryStorage storage;

    @BeforeEach
    void setUp() {
        storage = LibraryStorage.getInstance();
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE_PATH);
        if(file.exists()) file.delete();
    }

    @Test
    void testSaveAndLoadBooks() throws IOException {
        List<Book> bookToSave = new ArrayList<>();
        Book b1 = BookFactory.createBook("titolo1", "autore", "thriller", 2010, "1234567890", Rating.DUE, ReadingStatus.DA_LEGGERE, "aaa" );
        Book b2 = BookFactory.createBook("titolo2", "autore", "thriller", 2010, "1234567891", Rating.DUE, ReadingStatus.DA_LEGGERE, "aaa" );
        bookToSave.add(b1);
        bookToSave.add(b2);

        storage.saveToFile(bookToSave, TEST_FILE_PATH);
        List<Book> loadedBooks = storage.loadFromFile(TEST_FILE_PATH);

        assertNotNull(loadedBooks);
        assertEquals(2, loadedBooks.size());
        assertTrue(loadedBooks.stream().anyMatch(b -> b.getIsbn().equals("1234567890")));
        assertTrue(loadedBooks.stream().anyMatch(b -> b.getIsbn().equals("1234567891")));

    }

}
