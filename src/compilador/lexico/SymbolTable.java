package compilador.lexico;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsula a Tabela de Símbolos, mapeando strings (lexemas) para objetos Word (tokens reservados ou identificadores).
 */
public class SymbolTable {
    private Map<String, Word> table;

    public SymbolTable() {
        table = new HashMap<>();
    }

    /**
     * Insere uma palavra reservada ou identificador na tabela.
     * @param s O lexema a ser inserido.
     * @param w O token associado.
     */
    public void put(String s, Word w) {
        table.put(s, w);
    }

    /**
     * Recupera um símbolo da tabela pelo seu lexema.
     * @param s O lexema a ser buscado.
     * @return O objeto Word associado ou null se não existir.
     */
    public Word get(String s) {
        return table.get(s);
    }

    /**
     * Imprime o conteúdo da tabela de símbolos no console.
     */
    public void printTable() {
        System.out.println("\n--- Tabela de Símbolos ---");
        for (Map.Entry<String, Word> entry : table.entrySet()) {
            System.out.println("Lexema: " + entry.getKey() + " \t| Tag: " + entry.getValue().tag);
        }
        System.out.println("--------------------------");
    }
}
