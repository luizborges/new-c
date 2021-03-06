/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParserUtil;

import java.util.ArrayList;

/**
 *
 * @author borges
 */
public interface Input {
    
    
    /**
     * Inicializa o modo de leitura dos arquivos de entrada passados nos
     * argumentos de entrada do programa.
     * @return um BufferedReader para o primeiro arquivo.
     */
    ArrayList<String> initReader();
    
    /**
     * Retorna o proximo arquivo de leitura, dos arquivos de entrada passados pelos
     * argumentos de entrada.
     * @return BufferedReader para o próximo arquivo caso exista.
     *         null para o caso de não haver próximo arquivo.
     */
    ArrayList<String> nextReader();
    
    /**
     * Retorna o nome do arquivo de leitura em questão
     * É retornado o nome com o seu caminho completo, tal como foi passado para
     * o programa.
     * @return String contendo o nome do arquivo
     */
    String getFileName();
}
