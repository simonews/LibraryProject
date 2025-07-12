package main.java.library;

import main.java.library.controller.LibraryController;
import main.java.library.model.Book;
import main.java.library.model.Library;
import main.java.library.persistance.LibraryStorage;
import main.java.library.view.LibraryPanel;

import javax.swing.*;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}


        SwingUtilities.invokeLater(() -> {
            try {
                //caricamento libri
                List<Book> books =LibraryStorage.loadFromFile("books.json");

                //modello
                Library library = Library.getInstance();
                library.setBooks(books);

                //controller
                LibraryController controller = new LibraryController();

                //gui
                LibraryPanel panel = new LibraryPanel(controller);
                controller.addObserver(panel);

                //visualizzazione
                JFrame frame = new JFrame("Library Manager");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(panel);
                frame.setSize(900, 600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Errore durante l'avvio: "+e.getMessage());
            }
        });
    }
}
