package compilador.lexico;

/**
 * Representa um token genérico.
 */
public class Token {

    public final int tag; // Constante que representa o token

    public Token(int t) {
        tag = t;
    }

    public String toString() {
        return "" + (char)tag;
    }
}
