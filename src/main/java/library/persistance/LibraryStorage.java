package main.java.library.persistance;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.library.model.Book;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class LibraryStorage {

    private static final Gson gson = new Gson();

    public static void saveToFile(List<Book> books, String filepath) throws IOException {
        try (Writer writer = new FileWriter(filepath)) {
            gson.toJson(books, writer);
        }
    }

    public static List<Book> loadFromFile(String filepath) throws IOException {
        try (Reader reader =new FileReader(filepath)) {
            Type bookListType = new TypeToken<List<Book>>(){}.getType();
            return gson.fromJson(reader, bookListType);
        }
    }
}
