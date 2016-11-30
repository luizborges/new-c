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

    @Override
    public ArrayList<String> parserComment(final ArrayList<String> sourceCode) {
        ArrayList<String> psc = new ArrayList<String>();
        
        for(int i = 0; i < sourceCode.size(); ++i) {
            String line = sourceCode.get(i);
            
            int posComment = line.indexOf("/*");
            int posComment2 = line.indexOf("//");
            int posStr = line.indexOf("\"");
        }
        
        return psc;
    }
    
}
