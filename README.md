# LibraryProject

LibraryManager è un'applicazione desktop Java progettata per gestire una libreria personale di libri.
Consente di aggiungere, modificare, filtrare, ordinare e rimuovere libri in modo semplice e intuitivo, con salvataggio automatico dei dati in formato JSON.

L'app segue l'architettura MVC e utilizza diversi design pattern (Factory Method, Singleton, Strategy, Observer) per garantire modularità, riusabilità e facilità di manutenzione.

## Funzionalità previste
- Aggiunta e modifica dei libri tramite interfaccia grafica
- Ordinamento personalizzabile per titolo, autore, anno, ecc.
- Filtraggio per autore, genere, stato di lettura, ecc.
- Salvataggio automatico su file dopo ogni modifica
- Gestione dei dati in formato JSON

## Tecnologie e strumenti
Java 21
Java Swing
JUnit 5 per i test unitari
JSON per la persistenza dei dati (Libreria Gson)

## Flusso per avvio applicazione
src >> main >> java >> library >> MainApp.java 
