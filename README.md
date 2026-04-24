# Analisador Léxico (Scanner) - Compiladores

Este repositório contém a implementação da primeira etapa do "Projeto de Compiladores": um Analisador Léxico completo desenvolvido em Java, em conjunto com uma Tabela de Símbolos (`SymbolTable`).

## Funcionalidades
- **Scanner**: Varredura de arquivos fonte, eliminando espaços e comentários (tanto de uma linha `//` quanto em bloco `/* */`).
- **Análise Numérica e Literal**: Identificação e extração de identificadores, literais string (`"texto"`), números inteiros e números de ponto flutuante (`Real`).
- **Tokens**: Reconhecimento de todos os operadores relacionais, aritméticos e lógicos da gramática específica do trabalho.
- **Tabela de Símbolos**: Armazenamento automático de palavras reservadas e inicialização de variáveis, exibindo o dicionário final de constantes e variáveis mapeadas durante a compilação.

## Estrutura do Projeto
- `src/compilador/lexico/`: Contém todo o código fonte (Java).
- `testes/`: Diretório contendo os casos de teste providos (ex: `teste_01.txt`).

## Como Compilar e Executar

1. **Compilação**:
Certifique-se de ter o `javac` disponível na linha de comando e execute a partir da raiz do projeto:
```bash
javac src/compilador/lexico/*.java
```

2. **Execução**:
Após compilar as classes, ainda na raiz do projeto, executar::
```bash
java -cp src compilador.lexico.Main testes/teste_01.txt
```
*(O analisador irá imprimir todos os `<Tokens>` e, em seguida, despejar o conteúdo da `Tabela de Símbolos`).*
O argumento final é o arquivo de entrada a ser analisado. Para testar outros casos, basta trocar o nome do arquivo (por exemplo, teste_02.txt, teste_03.txt etc.).

## Boas Práticas
- Foi utilizada a interface `Closeable` e blocos `try-with-resources` para evitar Resource Leaks.
- A tabela de símbolos substitui o antigo formato `Hashtable` por instâncias genéricas da Collections API baseadas em `HashMap`, melhorando performance local.
