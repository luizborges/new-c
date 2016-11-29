/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.html.HTML;

/**
 *
 * @author borges
 */
public class AchillesParser {

    
    private Input input;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AchillesParser ap = new AchillesParser();
        ap.parser(args);
    }
    
    /**
     * 
     * @param args 
     */
    public void parser(String[] args) {
        input = new InputImpl(args); // initialize input
        
        // parser files in argument
        for(BufferedReader bufferedReader = input.initReader();
                bufferedReader != null; bufferedReader = input.nextReader()) {
            
            // transforma o arquivo em uma matrix em que cada linha eh uma linha da matrix
            ArrayList<String> sourceCode = getFileString(bufferedReader);
            
            
        }
    }
    
    /**
     * Create a matrix that represents a file.
     * Each line of the file will be a line in the matrix
     * The matrix is a ArrayList<String>
     * @param breader
     * @return ArrayList<String> that represents file.
     */
    private ArrayList<String> getFileString(BufferedReader breader) {
        ArrayList<String> file = new ArrayList<>();
        
        try {
            String line = null;    
            while ((line = breader.readLine()) != null) {
                file.add(line);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(AchillesParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return file;
    }
    
}
