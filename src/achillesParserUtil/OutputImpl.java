/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParserUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author borges
 */
public class OutputImpl implements Output {

    
    ////////////////////////////////////////////////////////////////////////////
    // variables
    ////////////////////////////////////////////////////////////////////////////
    

    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Interface methods
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public void save(final ArrayList<String> fileHeader,
            final ArrayList<String> fileCode, final String fileName) {
        // get the full name of the file without extension
        String name[] = fileName.split("\\s++" // retira a extensão do nome do arquivo
                + "|(?!^|$)(?:(?<=\\.)(?!\\.)|(?<!\\.)(?=\\.))");
        
        save(fileHeader, name[0] + ".h");
        save(fileCode, name[0] + ".c");
    }

    ////////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////

    private void save(final ArrayList<String> fileArray, final String fileName) {
        PrintWriter file = null;
        try {
            file = new PrintWriter(fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OutputImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        for (int i = 0; i < fileArray.size(); ++i) {
            file.println(fileArray.get(i));
        }
        
        file.println(""); // empty line in the end of the file
        
        file.close();
    }
}
