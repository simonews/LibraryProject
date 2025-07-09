package main.java.library.model.enums;

public enum Rating {
    UNA(1),
    DUE(2),
    TRE(3),
    QUATTRO(4),
    CINQUE(5);

    private final int value;

    Rating(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }

    public static Rating fromValue(int valore){
        for (Rating r:values()){
            if (r.value==valore){
                return r;
            }
        }
        throw new IllegalArgumentException("Valore rating non valido: "+valore);
    }
}
