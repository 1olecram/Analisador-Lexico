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
     */
    public void put(String s, Word w) {
        table.put(s, w);
    }

    /**
     * Recupera um símbolo da tabela pelo seu lexema.
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
