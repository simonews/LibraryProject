package main.java.library.util;

import main.java.library.model.Book;
import java.util.List;

public interface SortStrategy {
    void sort(List<Book> books);
}
