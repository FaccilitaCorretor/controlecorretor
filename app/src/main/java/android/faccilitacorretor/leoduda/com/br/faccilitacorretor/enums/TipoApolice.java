package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.enums;

/**
 * Created by turbiani on 08/08/15.
 */
public enum TipoApolice {
    Auto ("Auto"),
    Vida ("Vida"),
    RamosElementares ("RamosElementares");

    private final String name;

    private TipoApolice(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
