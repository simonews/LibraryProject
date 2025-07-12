package main.java.library.view;

import main.java.library.controller.LibraryController;
import main.java.library.model.Book;
import main.java.library.model.LibraryObserver;
import main.java.library.util.SortByAuthor;
import main.java.library.util.SortByRating;
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
    private JComboBox<String> filterCritComboBox;
    private JComboBox<String> sortComboBox;

    public LibraryPanel(LibraryController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        //colonne
        String[] columnNames = {"Titolo", "Autore", "Genere", "Anno", "ISBN", "Rating", "Stato", "Descrizione"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("SanSerif", Font.PLAIN, 14));
        table.setGridColor(Color.GRAY);
        table.setBackground(new Color(244,240,240));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        //filtri, ricerca e ordinamento
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.DARK_GRAY);

        searchField = new JTextField(20);
        filterCritComboBox = new JComboBox<>((new String[]{"Tutti", "Titolo", "Autore", "Genere", "Rating", "Stato", "Anno"}));
        sortComboBox = new JComboBox<>(new String[]{"Nessuno", "Titolo", "Autore", "Anno", "Rating"});

        topPanel.add(new JLabel("Cerca:")).setForeground(Color.WHITE);
        topPanel.add(searchField);
        topPanel.add(new JLabel("Filtra per:")).setForeground(Color.WHITE);
        topPanel.add(filterCritComboBox);
        topPanel.add(new JLabel("Ordina per:")).setForeground(Color.WHITE);
        topPanel.add(sortComboBox);



        add(topPanel, BorderLayout.NORTH);


        //pannello pulsanti add/edit/delete
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.DARK_GRAY);

        JButton addButton = new JButton("Aggiungi");
        addButton.setBackground(new Color(46,204,113));
        addButton.setForeground(Color.WHITE);

        JButton editButton = new JButton("Modifica");
        editButton.setBackground(new Color(52,152,219));
        editButton.setForeground(Color.WHITE);

        JButton deleteButton = new JButton("Rimuovi");
        deleteButton.setBackground(new Color(231,76,60));
        deleteButton.setForeground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable(controller.getAllBooks());

        //listener filtro/ricerca
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filtra();
            }

            public void removeUpdate(DocumentEvent e) {
                filtra();
            }

            public void changedUpdate(DocumentEvent e) {
                filtra();
            }
        });

        filterCritComboBox.addActionListener(e -> filtra());

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
                case "Rating":
                    controller.sortBooks(new SortByRating());
                default:
                    break;
            }

            filtra();
        });

        //aggiunta
        addButton.addActionListener(e -> {
            BookFormDialog dialog = new BookFormDialog(null);
            dialog.setVisible(true);
            Book newBook = dialog.getBook();
            if (newBook != null) {
                try{
                    controller.addBook(newBook);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this,
                            "ISBN già presente in libreria: impossibile aggiungere il libro.",
                            "ISBN duplicato",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        //modifica
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String isbn = (String) tableModel.getValueAt(selectedRow, 4);
                Book existingBook = controller.getBookByISBN(isbn);
                BookFormDialog dialog = new BookFormDialog(existingBook);
                dialog.setVisible(true);
                Book updatedBook = dialog.getBook();
                if (updatedBook != null) {
                    controller.updateBook(existingBook.getId(), updatedBook);
                }
            } else {
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

    }

    public void refreshTable(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                    b.getTitle(),
                    b.getAuthor(),
                    b.getGenre(),
                    b.getYear(),
                    b.getIsbn(),
                    b.getRating().getValue() + " " + "★".repeat(b.getRating().getValue()),
                    b.getStatus().getName(),
                    b.getDescription()
            });
        }
    }

    private void filtra() {
        String testo = searchField.getText().trim().toLowerCase();
        String criterioFiltro = (String) filterCritComboBox.getSelectedItem();
        String criterioOrdine = (String) sortComboBox.getSelectedItem();

        List<Book> filtrati = controller.getAllBooks();

        if (!testo.isEmpty()) {
            filtrati = filtrati.stream().filter(book -> {
                switch (criterioFiltro) {
                    case "Titolo":
                        return book.getTitle().toLowerCase().contains(testo);
                    case "Autore":
                        return book.getAuthor().toLowerCase().contains(testo);
                    case "Genere":
                        return book.getGenre().toLowerCase().contains(testo);
                    case "Anno":
                        return String.valueOf(book.getYear()).contains(testo);
                    case "Rating":
                        return String.valueOf(book.getRating().getValue()).contains(testo);
                    case "Stato":
                        return book.getStatus().getName().toLowerCase().contains(testo);
                    default:
                        return book.getTitle().toLowerCase().contains(testo)
                                || book.getAuthor().toLowerCase().contains(testo)
                                || book.getGenre().toLowerCase().contains(testo)
                                || String.valueOf(book.getYear()).contains(testo);
                }
            }).collect(Collectors.toList());
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
            case "Rating":
                controller.sortBooks(new SortByRating());
                break;
            default:
                break;
        }

        refreshTable(filtrati);
    }

    @Override
    public void onLibraryChanged(List<Book> updatedBooks) {
        refreshTable(updatedBooks);
    }
}