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
public interface ParserOutput {
    /**
     * Get the representation in ArrayList of the .h file.
     * @return 
     */
    ArrayList<String> getFileHeader();
    
    /**
     * Get the representation in ArrayList of the .c file.
     * @return 
     */
    ArrayList<String> getFileCode();
}
