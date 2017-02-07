/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParser;

import java.util.ArrayList;

/**
 *
 * @author borges
 */
public interface ParserString {
    /**
     * Recebe um arquivo contendo códigos de string e char e substitui para as strings
     * que o arquivo continha originalmente, ou seja as que realmente foram inseridas
     * pelo usuário, que foram traduzidas para o código correspondente
     * Exemplo:
     * Em um primeiro momento: string literal = "hello world!" foi traduzida
     * para o código "$@STR456$@"
     * Esta função reverse, irá pegar o código "$@STR456$@" e substituir pela
     * string que o usuário realmente inseriu, que é "hello world!" neste exemplo.
     * @param sourceCode representação do arquivo que contém os códigos das strings
     * @return representação de um arquivo que tem os códigos revertidos nas 
     * strings originais inseridas pelo usuário
     */
    ArrayList<String> reverse(final ArrayList<String> sourceCode);
}
