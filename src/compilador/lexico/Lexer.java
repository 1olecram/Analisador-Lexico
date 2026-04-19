package compilador.lexico;

import java.io.*;

/**
 * Analisador Léxico (Scanner) para a linguagem do compilador.
 */
public class Lexer implements Closeable {
    private int line = 1; // contador de linhas
    private char ch = ' '; // caractere lido do arquivo
    private FileReader file;

    private SymbolTable words = new SymbolTable();

    /* Método para inserir palavras reservadas na SymbolTable */
    private void reserve(Word w) {
        words.put(w.getLexeme(), w);
    }

    /* Método construtor */
    public Lexer(String fileName) throws FileNotFoundException {
        try {
            file = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
            throw e;
        }

        // Insere palavras reservadas na Tabela de Símbolos
        reserve(new Word("class", Tag.CLASS));
        reserve(new Word("int", Tag.INT));
        reserve(new Word("string", Tag.STRING));
        reserve(new Word("float", Tag.FLOAT));
        reserve(new Word("if", Tag.IF));
        reserve(new Word("else", Tag.ELSE));
        reserve(new Word("do", Tag.DO));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("repeat", Tag.REPEAT));
        reserve(new Word("until", Tag.UNTIL));
        reserve(new Word("read", Tag.READ));
        reserve(new Word("write", Tag.WRITE));
        
        // Operadores lógicos como palavras
        reserve(new Word("and", Tag.AND));
        reserve(new Word("or", Tag.OR));
        reserve(new Word("not", Tag.NOT));
    }

    /**
     * Retorna a tabela de símbolos construída pelo analisador.
     */
    public SymbolTable getSymbolTable() {
        return words;
    }

    /**
     * Retorna a linha atual onde o analisador está processando.
     */
    public int getLine() {
        return line;
    }

    /**
     * Fecha o leitor de arquivo de forma segura.
     */
    @Override
    public void close() throws IOException {
        if (file != null) {
            file.close();
        }
    }

    /* Lê o próximo caractere do arquivo */
    private void readch() throws IOException {
        int c = file.read();
        if (c == -1) {
            ch = (char) 65535; // representa EOF
        } else {
            ch = (char) c;
        }
    }

    /* Lê o próximo caractere do arquivo e verifica se é igual a c */
    private boolean readch(char c) throws IOException {
        readch();
        if (ch != c)
            return false;
        ch = ' ';
        return true;
    }

    public Token scan() throws IOException {
        // Desconsidera delimitadores e processa comentários na entrada
        for (;; readch()) {
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') {
                continue;
            } else if (ch == '\n') {
                line++; // conta linhas
            } else if (ch == '/') {
                readch();
                if (ch == '/') {
                    // Comentário de uma linha
                    while (ch != '\n' && ch != (char) 65535) {
                        readch();
                    }
                    if (ch == '\n') {
                        line++;
                    }
                } else if (ch == '*') {
                    // Comentário de múltiplas linhas
                    readch();
                    boolean commentEnded = false;
                    while (!commentEnded && ch != (char) 65535) {
                        if (ch == '\n') {
                            line++;
                        }
                        if (ch == '*') {
                            readch();
                            if (ch == '/') {
                                commentEnded = true;
                            } else {
                                continue; // evita avançar duplo ao encontrar sequência de *
                            }
                        } else {
                            readch();
                        }
                    }
                } else {
                    // Se não era '//' nem '/*', era o operador de divisão
                    return new Token('/');
                }
            } else {
                break; // Se não for espaço ou comentário, sai do loop
            }
        }

        // Fim de arquivo
        if (ch == (char) 65535) {
            return null; // Retorna null para sinalizar EOF
        }

        // Operadores compostos, relacionais e lógicos
        switch (ch) {
            case '&':
                if (readch('&')) return Word.and;
                else return new Token('&');
            case '|':
                if (readch('|')) return Word.or;
                else return new Token('|');
            case ':':
                if (readch('=')) return Word.assign;
                else return new Token(':');
            case '=':
                readch();
                return Word.eq;
            case '<':
                readch();
                if (ch == '=') { ch = ' '; return Word.le; }
                if (ch == '>') { ch = ' '; return Word.ne; }
                return new Token('<');
            case '>':
                if (readch('=')) return Word.ge;
                else return new Token('>');
            case '"':
                // Literais de string
                StringBuffer sbStr = new StringBuffer();
                readch();
                while (ch != '"' && ch != '\n' && ch != (char) 65535) {
                    sbStr.append(ch);
                    readch();
                }
                if (ch == '"') {
                    readch(); // consome aspas finais
                }
                return new Literal(sbStr.toString());
        }

        // Números
        if (Character.isDigit(ch)) {
            int value = 0;
            if (ch == '0') {
                value = 0;
                readch();
            } else {
                do {
                    value = 10 * value + Character.digit(ch, 10);
                    readch();
                } while (Character.isDigit(ch));
            }

            if (ch == '.') {
                readch();
                float f_value = value;
                float d = 10;
                while (Character.isDigit(ch)) {
                    f_value = f_value + Character.digit(ch, 10) / d;
                    d = d * 10;
                    readch();
                }
                // Criação do real
                return new Real(f_value);
            }
            return new Num(value);
        }

        // Identificadores
        if (Character.isLetter(ch)) {
            StringBuffer sb = new StringBuffer();
            do {
                sb.append(ch);
                readch();
            } while (Character.isLetterOrDigit(ch));
            
            String s = sb.toString();
            Word w = words.get(s);
            if (w != null)
                return w; // palavra já existe na SymbolTable
            w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }

        // Caracteres não especificados (operadores matemáticos simples, delimitadores)
        Token t = new Token(ch);
        ch = ' ';
        return t;
    }
}
