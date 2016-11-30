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
        for(ArrayList<String> sourceCode = input.initReader();
                sourceCode != null; sourceCode = input.nextReader()) {
            
            ParserComment parserComment = new ParserCommentImpl();
            sourceCode = parserComment.parserComment(sourceCode);
        }
    }
    
}
