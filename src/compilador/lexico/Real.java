package compilador.lexico;

/**
 * Representa um token de número real (ponto flutuante).
 */
public class Real extends Token {

    public final float value;

    public Real(float value) {
        super(Tag.REAL);
        this.value = value;
    }

    public String toString() {
        return "" + value;
    }
}
