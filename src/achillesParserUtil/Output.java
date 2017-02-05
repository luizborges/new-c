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
public interface Output {
    /**
     * Save the file header, file.h, and file code, file.c, with 
     * the name and path specified on fileName.
     * @param fileHeader ArrayList<String> representation, each line on the list
     * represents a line in output file
     * @param fileCode ArrayList<String> representation, each line on the list
     * represents a line in output file
     * @param fileName path and file name of the output file, the file name
     * will be treated to receive the correct extension
     */
    void save(final ArrayList<String> fileHeader,
            final ArrayList<String> fileCode, final String fileName);
}
