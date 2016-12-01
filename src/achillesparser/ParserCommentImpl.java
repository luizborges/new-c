/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesparser;

import java.util.ArrayList;






////////////////////////////////////////////////////////////////////////////
// Interface methods
////////////////////////////////////////////////////////////////////////////

/**
 *
 * @author borges
 */
public class ParserCommentImpl implements ParserComment {

    /**
     * O parser é feito por meio de uma máquina de estados finitos.
     * @param sourceCode 
     */
    @Override
    public void parserComment(ArrayList<String> sourceCode) {
        
        int state = 0;
        for(int i = 0; i < sourceCode.size(); ++i) {
            String line = sourceCode.get(i);
            
            switch(state) {
                case 0: findComment(line); break;
                case 1: break;
                case 2: break;
                case 3: break;
                default: break;
            }
        }
        
    }

    /**
     * 
     * @param line 
     */
    private void findComment(final String line) {
        int commentLine  = line.indexOf("//");
        int commentBlock = line.indexOf("/*");
        int initString   = line.indexOf("\"");
        int initString2  = line.indexOf("'");
        
        ///////////////////////////////////////////
        // checa se nao tem comentario nem string
        ///////////////////////////////////////////
        if (commentLine == -1 && commentBlock == -1
            && initString == -1 && initString2 == -1) return;
        
    }
    
}
