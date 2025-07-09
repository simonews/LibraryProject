package main.java.library.util;

import main.java.library.model.Book;

import java.util.List;

public class SortByRating implements SortStrategy
{
    @Override
    public void sort(List<Book> books){
        for (int i = 0; i < books.size(); i++) {
            for (int j = i+1; j < books.size(); j++) {
                Book a = books.get(i);
                Book b = books.get(j);

                if (a.getRating().getValue() < b.getRating().getValue()){
                    books.set(i, b);
                    books.set(j, a);
                }
            }
        }
    }
}
