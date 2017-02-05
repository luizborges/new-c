/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParser;

import achillesParserUtil.Input;
import achillesParserUtil.InputImpl;
import achillesParserUtil.Exit;
import achillesParserUtil.ExitImpl;
import java.util.ArrayList;

/**
 *
 * @author borges
 */
public class AchillesParser {

    private Exit exit = new ExitImpl(75000);
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
            
            Parser pc = new ParserCommentImpl();
            sourceCode = pc.parser(sourceCode, input.getFileName());
            
            System.out.format("File name: \"%s\"\n", input.getFileName());
            
            pc = new ParserFunctionImpl3();
            pc.parser(sourceCode, input.getFileName());
            
//            Output out = new OuputImpl();
//            out.save(input.getFile(), pc.getFileHeader(), pc.getFileCode());
        }
    }
    
    
    
    
    
}
