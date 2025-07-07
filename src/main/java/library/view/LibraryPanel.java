package main.java.library.view;

import main.java.library.controller.LibraryController;
import main.java.library.model.Book;
import main.java.library.model.LibraryObserver;
import main.java.library.util.SortByAuthor;
import main.java.library.util.SortByTitle;
import main.java.library.util.SortByYear;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryPanel extends JPanel implements LibraryObserver {
    private JTable table;
    private DefaultTableModel tableModel;
    private LibraryController controller;
    private JTextField searchField;
    private JComboBox<String> genreFilter;
    private JComboBox<String> sortComboBox;

    public LibraryPanel(LibraryController controller){
        this.controller = controller;
        setLayout(new BorderLayout());

        //colonne
        String[] columnNames = {"Titolo", "Autore", "Genere", "Anno", "ISBN", "Descrizione"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        //filtri, ricerca e ordinamento
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchField = new JTextField(20);
        genreFilter = new JComboBox<>();
        genreFilter.addItem("Tutti");
        sortComboBox = new JComboBox<>(new String[]{"Nessuno", "Titolo", "Autore", "Anno"});
        controller.getAllBooks().stream()
                .map(Book::getGenre)
                .distinct()
                .forEach(genreFilter::addItem);

        topPanel.add(new JLabel("Cerca:"));
        topPanel.add(searchField);
        topPanel.add(new JLabel("Genere:"));
        topPanel.add(genreFilter);
        topPanel.add(new JLabel("Ordina per:"));
        topPanel.add(sortComboBox);
        add(topPanel, BorderLayout.NORTH);

        //pannello pulsanti add/edit/delete
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Aggiungi");
        JButton editButton = new JButton("Modifica");
        JButton deleteButton = new JButton("Rimuovi");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable(controller.getAllBooks());

        //listener filtro/ricerca
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filtra();}
            public void removeUpdate(DocumentEvent e) { filtra();}
            public void changedUpdate(DocumentEvent e) { filtra();}
        });

        //aggiunta
        addButton.addActionListener(e -> {
            BookFormDialog dialog = new BookFormDialog(null);
            dialog.setVisible(true);
            Book newBook = dialog.getBook();
            if (newBook != null) {
                controller.addBook(newBook);
            }
        });

        //modifica
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >=0 ){
                String isbn = (String) tableModel.getValueAt(selectedRow, 4);
                Book existingBook = controller.getBookByISBN(isbn);
                BookFormDialog dialog = new BookFormDialog(existingBook);
                dialog.setVisible(true);
                Book updatedBook = dialog.getBook();
                if (updatedBook != null){
                    controller.updateBook(existingBook.getId(), updatedBook);
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Seleziona un libro da modificare");
            }
        });

        //Rimozione
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String isbn = (String) tableModel.getValueAt(selectedRow, 4);
                int result = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler rimuovere il libro?", "Conferma", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    controller.removeBookByIsbn(isbn);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un libro da rimuovere.");
            }
        });

        sortComboBox.addActionListener(e -> {
            String criterio = (String) sortComboBox.getSelectedItem();

            switch (criterio) {
                case "Titolo":
                    controller.sortBooks(new SortByTitle());
                    break;
                case "Autore":
                    controller.sortBooks(new SortByAuthor());
                    break;
                case "Anno":
                    controller.sortBooks(new SortByYear());
                    break;
                default:
                    break;
            }

            filtra();
        });
    }

    public void refreshTable(List<Book> books){
        tableModel.setRowCount(0);
        for (Book b:books){
            tableModel.addRow(new Object[]{
                b.getTitle(),
                b.getAuthor(),
                b.getGenre(),
                b.getYear(),
                b.getIsbn(),
                b.getDescription()
            });
        }
    }

    private void filtra() {
        String testo = searchField.getText().trim().toLowerCase();
        String genere = (String) genreFilter.getSelectedItem();
        String criterioOrdine = (String) sortComboBox.getSelectedItem();

        List<Book> filtrati = controller.getAllBooks();

        if (!testo.isEmpty()) {
            filtrati = filtrati.stream()
                    .filter(b -> b.getTitle().toLowerCase().contains(testo) || b.getAuthor().toLowerCase().contains(testo)).collect(Collectors.toList());
        }

        if (!genere.equalsIgnoreCase("Tutti")) {
            filtrati = filtrati.stream().filter(b -> b.getGenre().equalsIgnoreCase(genere)).collect(Collectors.toList());
        }

        switch (criterioOrdine) {
            case "Titolo":
                controller.sortBooks(new SortByTitle());
                break;
            case "Autore":
                controller.sortBooks(new SortByAuthor());
                break;
            case "Anno":
                controller.sortBooks(new SortByYear());
                break;
            default:
                break;
        }

        refreshTable(filtrati);
    }

    private void aggiornaGeneri(List<Book> books) {
        genreFilter.removeAllItems();
        genreFilter.addItem("Tutti");
        books.stream().map(Book::getGenre).distinct().sorted().forEach(genreFilter::addItem);
    }

    @Override
    public void onLibraryChanged(List<Book> updatedBooks) {
        aggiornaGeneri(updatedBooks);
        refreshTable(updatedBooks);
    }
}
