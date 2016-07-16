package android.faccilitacorretor.leoduda.com.br.faccilitacorretor.enums;

/**
 * Created by turbiani on 08/08/15.
 */
public enum EntityStatus {
    ATIVA ("ATIVA"),
    INATIVA ("INATIVA"),
    EXCLUIDA ("EXCLUIDA");

    private final String name;

    private EntityStatus(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
