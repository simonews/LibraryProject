package main.java.library.model;

import java.util.List;

public interface LibraryObserver {
    void onLibraryChanged(List<Book> updatedBooks);
}
