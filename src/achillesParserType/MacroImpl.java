/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParserType;

import achillesParserUtil.Exit;
import achillesParserUtil.ExitImpl;
import java.util.ArrayList;

/**
 *
 * @author borges
 */
public class MacroImpl implements Macro {

    ////////////////////////////////////////////////////////////////////
    // Variables
    ////////////////////////////////////////////////////////////////////
    Exit exit = new ExitImpl(84011);
    ArrayList<String> sourceCodeWithoutMacro = new ArrayList<>();
    Node sourceCodeTree;
    int lineNumber;
    
    ////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////
    public MacroImpl(Node sourceCodeTree) {
        if (sourceCodeTree == null) {
            exit.error(1, "Source Code Tree is NULL");
        } else {
            this.sourceCodeTree = sourceCodeTree;
        }
    }

    ////////////////////////////////////////////////////////////////////
    // Macro Interface
    ////////////////////////////////////////////////////////////////////
    
    
    ////////////////////////////////////////////////////////////////////
    // private functions
    ////////////////////////////////////////////////////////////////////

    @Override
    public ArrayList<String> parser(ArrayList<String> sourceCode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
