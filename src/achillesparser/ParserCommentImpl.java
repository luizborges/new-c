/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesparser;

import achillesParserUtil.Exit;
import achillesParserUtil.ExitImpl;
import java.util.ArrayList;






////////////////////////////////////////////////////////////////////////////
// Interface methods
////////////////////////////////////////////////////////////////////////////

/**
 *
 * @author borges
 */
public class ParserCommentImpl implements ParserComment {

    ////////////////////////////////////////////////////////////////////////////
    // variables
    ////////////////////////////////////////////////////////////////////////////
    private enum StateFile {
        CommonLine, BlockComment
    }
    
    private final Exit exit = new ExitImpl(75002);
    private int lineNumber;
    private ArrayList<String> sourceCodeWithoutComment = new ArrayList<>();
    
    ////////////////////////////////////////////////////////////////////////////
    // constructor
    ////////////////////////////////////////////////////////////////////////////
    
    
    ////////////////////////////////////////////////////////////////////////////
    // constructor
    ////////////////////////////////////////////////////////////////////////////
    
    
    
    /**
     * O parser é feito por meio de uma máquina de estados finitos.
     * @param sourceCode 
     */
    @Override
    public ArrayList<String> parserComment(ArrayList<String> sourceCode) {
        
        StateFile state = StateFile.CommonLine;
        
        for(lineNumber = 0; lineNumber < sourceCode.size(); ++lineNumber) {
            String line = sourceCode.get(lineNumber);
//            System.out.format("ori: \"%s\"\n", line);
            switch(state) {
                case CommonLine:
                    state = findComment(line); 
//                    System.out.format("fo1: \"%s\"\n", sourceCodeWithoutComment.get(lineNumber));
                    break;
                case BlockComment: 
                    state = blockCommentBody(line); 
//                    System.out.format("fo2: \"%s\"\n", sourceCodeWithoutComment.get(lineNumber));
                    break;
                default: break;
            }
        }
        
        return sourceCodeWithoutComment;
        
    }
  
    
    /**
     * 
     * @param line
     * @return 
     */
    private StateFile findComment(String lineStr) {
        char line[];
        line = lineStr.toCharArray();
        boolean hasBlockComment = false;
        
        
        for (int i=0; i < line.length; ++i) {
          try{
            ///////////////////////////////////////////
            // trata se os caracteres de comentario nao estao dentro de uma string
            ///////////////////////////////////////////
            if (line[i] == '"') {
                do {                    
                    ++i;
                    if (line[i] == '\\') {
                        i += 2;
                    }
                } while (line[i] != '"');
            }
            ///////////////////////////////////////////
            // trata os comentarios
            ///////////////////////////////////////////
            else if (line[i] == '/') {
                if (line[i+1] == '/') {
                    lineStr = lineStr.substring(0, i);
                    break;
                } else if (line[i+1] == '*') {
                    lineStr = lineStr.substring(0, i);
                    hasBlockComment = true;
                    break;
                }
            }
          } catch (ArrayIndexOutOfBoundsException e) {
              e.printStackTrace();
              exit.error(1, "Malformed Line. LineNumber: ", String.valueOf(lineNumber+1),
                      "\n\"", lineStr, "\"\n",
                      "Exception Name: \'ArrayIndexOutOfBoundsException\'");
          }
        }
        
        sourceCodeWithoutComment.add(lineStr);
        
        if (hasBlockComment == true) {
            return StateFile.BlockComment;
        } else {
            return StateFile.CommonLine;
        }
    }
    
    /**
     * 
     * @param line
     * @return 
     */
    private StateFile blockCommentBody(String line) {
        int hasEndBlockComment = line.indexOf("*/");
        
        if (hasEndBlockComment == -1) {
            sourceCodeWithoutComment.add("");
            return StateFile.BlockComment;
        } else {
            if (hasEndBlockComment +2 >= line.length()) {
                line = "";
            } else {
                line = line.substring(hasEndBlockComment+2);
            }
            sourceCodeWithoutComment.add(line);
            return StateFile.CommonLine;
        }
    }
    
}
