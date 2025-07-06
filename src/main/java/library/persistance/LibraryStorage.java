package main.java.library.persistance;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.library.model.Book;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LibraryStorage {

    private static LibraryStorage instance;
    private static final Gson gson = new Gson();

    private LibraryStorage() {}

    public static LibraryStorage getInstance() {
        if (instance == null) {
            instance = new LibraryStorage();
        }
        return instance;
    }

    public static boolean saveToFile(List<Book> books, String filepath) throws IOException {
        try (Writer writer = new FileWriter(filepath)) {
            gson.toJson(books, writer);
            return true;
        }
        catch (IOException e){
            System.err.println("Errore durante il salvataggio: " + e.getMessage());
            return false;
        }
    }

    public static List<Book> loadFromFile(String filepath) throws IOException {
        File file = new File(filepath);
        if(!file.exists()){
            System.out.println("File non trovato. Creazione di un nuovo file vuoto");
            List<Book> emptyList = new ArrayList<>();
            saveToFile(emptyList, filepath);
            return emptyList;
        }

        try (Reader reader =new FileReader(filepath)) {
            Type bookListType = new TypeToken<List<Book>>(){}.getType();
            return gson.fromJson(reader, bookListType);
        }
        catch (IOException e){
            System.err.println("Errore durante il caricamento: " + e.getMessage());
            return null;
        }
    }
}
