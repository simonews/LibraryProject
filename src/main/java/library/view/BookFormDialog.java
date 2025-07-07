package main.java.library.view;

import main.java.library.model.Book;
import main.java.library.model.BookFactory;

import javax.swing.*;
import java.awt.*;

public class BookFormDialog extends JDialog {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField yearField;
    private JTextField isbnField;
    private JTextArea descriptionArea;
    private Book book;

    public BookFormDialog(Book bookToEdit) {
        setTitle(bookToEdit == null ? "Aggiungi Libro" : "Modifica Libro");
        setModal(true);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6,2,5,5));
        titleField= new JTextField();
        authorField = new JTextField();
        genreField = new JTextField();
        yearField = new JTextField();
        isbnField = new JTextField();
        descriptionArea = new JTextArea();

        inputPanel.add(new JLabel("Titolo: "));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Autore: "));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Genere: "));
        inputPanel.add(genreField);
        inputPanel.add(new JLabel("Anno: "));
        inputPanel.add(yearField);
        inputPanel.add(new JLabel("ISBN: "));
        inputPanel.add(isbnField);
        inputPanel.add(new JLabel("Descrizione: "));
        inputPanel.add(new JScrollPane(descriptionArea));

        add(inputPanel, BorderLayout.CENTER);

        //bottoni
        JButton confirmButton = new JButton("Conferma");
        confirmButton.addActionListener(e -> {
            try {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String genre = genreField.getText().trim();
                int year = Integer.parseInt(yearField.getText().trim());
                String isbn = isbnField.getText().trim();
                String description = descriptionArea.getText().trim();

                book = BookFactory.createBook(title, author, genre, year, isbn, description);
                dispose();

            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Anno non valido");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        add(buttonPanel, BorderLayout.SOUTH);

        //precompilazione in caso di modifica
        if(bookToEdit != null){
            titleField.setText(bookToEdit.getTitle());
            authorField.setText(bookToEdit.getAuthor());
            genreField.setText(bookToEdit.getGenre());
            yearField.setText(String.valueOf(bookToEdit.getYear()));
            isbnField.setText(bookToEdit.getIsbn());
            descriptionArea.setText(bookToEdit.getDescription());
        }

    }

    public Book getBook(){
        return book;
    }
}
