package compilador.lexico;

/**
 * Representa tokens compostos por palavras (palavras reservadas, identificadores e operadores de múltiplos caracteres).
 */
public class Word extends Token {
    private String lexeme = "";

    // Instâncias pré-definidas para operadores relacionais, lógicos e de atribuição
    public static final Word and = new Word("and", Tag.AND);
    public static final Word or = new Word("or", Tag.OR);
    public static final Word not = new Word("not", Tag.NOT);
    
    public static final Word eq = new Word("=", Tag.EQ);
    public static final Word ne = new Word("<>", Tag.NE);
    public static final Word le = new Word("<=", Tag.LE);
    public static final Word ge = new Word(">=", Tag.GE);
    public static final Word assign = new Word(":=", Tag.ASSIGN);

    public Word(String s, int tag) {
        super(tag);
        lexeme = s;
    }

    public String getLexeme() {
        return lexeme;
    }

    public String toString() {
        return lexeme;
    }
}
