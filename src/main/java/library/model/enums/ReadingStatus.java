package main.java.library.model.enums;

public enum ReadingStatus {
    DA_LEGGERE("Da leggere"),
    IN_LETTURA("In lettura"),
    LETTO("Letto");

    private final String status;

    ReadingStatus(String status){
        this.status = status;
    }

    public String getName(){
        return status;
    }

    @Override
    public String toString() {
        return status;
    }

    public static ReadingStatus fromStatus(String stato){
        for (ReadingStatus s : values()){
            if(s.status.equalsIgnoreCase(stato)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Stato non valido: "+stato);
    }
}
