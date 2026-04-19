package compilador.lexico;

/**
 * Representa um token de literal (string entre aspas).
 */
public class Literal extends Token {

    public final String value;

    public Literal(String value) {
        super(Tag.LITERAL);
        this.value = value;
    }

    public String toString() {
        return "\"" + value + "\"";
    }
}
