package main.java.library.model;

import main.java.library.model.enums.Rating;
import main.java.library.model.enums.ReadingStatus;
import main.java.library.util.SortStrategy;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private static Library instance;
    private List<Book> books;
    private int nextIndex = -1;
    private List<LibraryObserver> observers;

    private Library() {
        books = new ArrayList<>();
        observers = new ArrayList<>();
        nextIndex = 0;
    }

    public static Library getInstance(){
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    public void addObserver(LibraryObserver observer){
        observers.add(observer);
    }

    public void removeObserver(LibraryObserver observer){
        observers.remove(observer);
    }

    public void notifyObservers() {
        for(LibraryObserver o:observers){
            o.onLibraryChanged(new ArrayList<>(books));
        }
    }

    public boolean addBook(Book book){
        if(!isValidIsbn(book.getIsbn())){
            throw new IllegalArgumentException("ISBN non valido");
        }

        for (Book b : books) {
            if((b.getId() != book.getId()) && (book.getIsbn().equalsIgnoreCase(b.getIsbn())))
                throw new IllegalArgumentException("Esiste già un libro con questo ISBN");
        }

        book.setId(nextIndex++);
        books.add(book);
        notifyObservers();
        return true;
    }

    public boolean removeBookById(int id) {
        for (int i = 0; i < books.size(); i++) {
            if(books.get(i).getId() == id) {
                books.remove(i);
                notifyObservers();
                return true;
            }
        }
        notifyObservers();
        return false;
    }

    public boolean removeBookByISBN(String isbn){
        for(Book book: books){
            if(book.getIsbn().equalsIgnoreCase(isbn)) {
                books.remove(book);
                notifyObservers();
                return true;
            }
            notifyObservers();
        }return false;
    }

    public boolean modifyBook(int id, String newTitle, String newAuthor, String newIsbn, String newDescription, int year, String newGenre, Rating newRating, ReadingStatus newStatus){
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
                book.setRating(newRating);
                book.setStatus(newStatus);
                notifyObservers();
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

    public List<Book> filterBooksBy(String crit, String valore){
        List<Book> filtered = new ArrayList<>();
        for (Book b : books) {
            switch (crit.toLowerCase()) {
                case "autore":
                    if (b.getAuthor().equalsIgnoreCase(valore)) {
                        filtered.add(b);
                    }
                    break;
                case "anno":
                    try {
                        int year = Integer.parseInt(valore);
                        if (b.getYear() == year) {
                            filtered.add(b);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Anno non valido");
                    }
                    break;
                case "titolo":
                    if (b.getTitle().equalsIgnoreCase(valore)) {
                        filtered.add(b);
                    }
                    break;
                case "genere":
                    if(b.getGenre().equalsIgnoreCase(valore)) {
                        filtered.add(b);
                    }
                case "stato":
                    if(b.getStatus().getName().equalsIgnoreCase(valore)) {
                        filtered.add(b);
                    }
                case "rating":
                    if(b.getRating().getValue() == Integer.parseInt(valore)){
                        filtered.add(b);
                    }
                default:
                    System.out.println("Criterio di filtro non riconosciuto");
            }
        }
        return filtered;
    }

    public void getBooksSortBy(SortStrategy strategy){
        strategy.sort(books);
        System.out.println("Libri ordinati con strategia: " + strategy.getClass().getSimpleName());
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

    public Book getBookByIsbn(String isbn){
        for (Book book: books){
            if (book.getIsbn().equals(isbn)) return book;
        }
        return null;
    }

    private boolean isValidIsbn(String isbn){
        if (isbn == null) return false;
        String cleanIsbn = isbn.replaceAll("[\\s-]","");

        return cleanIsbn.matches("^(?:\\d{10}|\\d{13})$");

    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyObservers();
    }

}
