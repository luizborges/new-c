/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesparser;

import achillesParserUtil.Exit;
import achillesParserUtil.ExitImpl;
import java.util.ArrayList;

/**
 *
 * @author borges
 */
public class AchillesParser {

    private Exit exit = new ExitImpl(75000);
    private int lineNumber;
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
            
            ParserComment pc = new ParserCommentImpl();
            sourceCode = pc.parserComment(sourceCode);
            
            ParserString ps = new ParserStringImpl();
            sourceCode = ps.parserString(sourceCode);
            
            ////////////////////////////////////////////////////////////////////
            // Init the parser to 
            ////////////////////////////////////////////////////////////////////
            for (int i=0; i < sourceCode.size(); ++i) {
                System.out.format("%s\n", sourceCode.get(i));
            }

//            for (lineNumber=0; lineNumber < sourceCode.size(); ++lineNumber) {
//                
//            }
            
        }
    }
    
    
    
    
    
}
