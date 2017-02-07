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
     * Save a fileArray with the name and path specified on fileName
     * @param fileArray ArrayList<String> representation, each line on the list
     * represents a line in output file
     * @param fileName path and file name of the output file, the file name
     * will be treated to receive the correct extension
     * @param extension extension that will receive the file.
     * ex: ".h", ".c", ".cpp", etc
     */
    void save(final ArrayList<String> fileArray,
            final String fileName, final String extension);
}
