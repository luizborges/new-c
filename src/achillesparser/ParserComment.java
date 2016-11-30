/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesparser;

import java.util.ArrayList;

/**
 *
 * @author borges
 */
public interface ParserComment {
    
    /**
     * Função para retirar os comentários do arquivo de código fonte.
     * Function to strip comments from source code file.
     * @param sourceCode: Matrix file that represents the file.
     * @return Matrix that represents the file.
     */
    ArrayList<String> parserComment(final ArrayList<String> sourceCode);
}
