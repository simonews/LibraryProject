package main.java.library.view;

import main.java.library.model.Book;
import main.java.library.model.BookFactory;
import main.java.library.model.enums.Rating;
import main.java.library.model.enums.ReadingStatus;

import javax.swing.*;
import java.awt.*;

public class BookFormDialog extends JDialog {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField yearField;
    private JTextField isbnField;
    private JComboBox<Rating> ratingField;
    private JComboBox<ReadingStatus> statusField;
    private JTextArea descriptionArea;
    private Book book;

    public BookFormDialog(Book bookToEdit) {
        setTitle(bookToEdit == null ? "Aggiungi Libro" : "Modifica Libro");
        setModal(true);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.DARK_GRAY);

        JPanel inputPanel = new JPanel(new GridLayout(8,2,5,5));
        inputPanel.setBackground(Color.DARK_GRAY);

        titleField= new JTextField();
        titleField.setBackground(Color.LIGHT_GRAY);
        titleField.setForeground(Color.BLACK);

        authorField = new JTextField();
        authorField.setBackground(Color.LIGHT_GRAY);
        authorField.setForeground(Color.BLACK);

        genreField = new JTextField();
        genreField.setBackground(Color.LIGHT_GRAY);
        genreField.setForeground(Color.BLACK);

        yearField = new JTextField();
        yearField.setBackground(Color.LIGHT_GRAY);
        yearField.setForeground(Color.BLACK);

        isbnField = new JTextField();
        isbnField.setBackground(Color.LIGHT_GRAY);
        isbnField.setForeground(Color.BLACK);

        ratingField = new JComboBox<>(Rating.values());
        statusField = new JComboBox<>(ReadingStatus.values());

        descriptionArea = new JTextArea();
        descriptionArea.setBackground(Color.LIGHT_GRAY);
        descriptionArea.setForeground(Color.BLACK);

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
        inputPanel.add(new JLabel("Rating: "));
        inputPanel.add(ratingField);
        inputPanel.add(new JLabel("Stato: "));
        inputPanel.add(statusField);
        inputPanel.add(new JLabel("Descrizione: "));
        inputPanel.add(new JScrollPane(descriptionArea));

        add(inputPanel, BorderLayout.CENTER);

        //bottoni
        JButton confirmButton = new JButton("Conferma");
        confirmButton.setBackground(new Color(52, 152, 219));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.addActionListener(e -> {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String genre = genreField.getText().trim();
                String year = yearField.getText().trim();
                String isbn = isbnField.getText().trim();
                Rating rating = (Rating) ratingField.getSelectedItem();
                ReadingStatus status = (ReadingStatus) statusField.getSelectedItem();
                String description = descriptionArea.getText().trim();

                if(title.isEmpty() || author.isEmpty() || genre.isEmpty() || year.isEmpty() || isbn.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Tutti i campi (tranne la descrizione) devono essere compilati. ", "Errore.", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                int yearInt;
                try{
                    yearInt = Integer.parseInt(year);
                    if (yearInt < 1 || yearInt > 2100) {
                        throw new NumberFormatException("Anno fuori range.");
                    }
                    }catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Anno non valido. Inserire un numero tra 1 e 2100", "Errore." ,JOptionPane.ERROR_MESSAGE);
                    return;
                }

                book = BookFactory.createBook(title, author, genre, yearInt, isbn,rating,status, description);
                dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.add(confirmButton);
        add(buttonPanel, BorderLayout.SOUTH);

        //precompilazione in caso di modifica
        if(bookToEdit != null){
            titleField.setText(bookToEdit.getTitle());
            authorField.setText(bookToEdit.getAuthor());
            genreField.setText(bookToEdit.getGenre());
            yearField.setText(String.valueOf(bookToEdit.getYear()));
            isbnField.setText(bookToEdit.getIsbn());
            ratingField.setSelectedItem(bookToEdit.getRating());
            statusField.setSelectedItem(bookToEdit.getStatus());
            descriptionArea.setText(bookToEdit.getDescription());
        }

    }

    public Book getBook(){
        return book;
    }
}
