/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParser;

import achillesParserUtil.AbstractFactory;
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
public class ParserCommentImpl implements Parser {
    
    ////////////////////////////////////////////////////////////////////////////
    // variables
    ////////////////////////////////////////////////////////////////////////////
    private enum StateFile {
        CommonLine, BlockComment
    }
    
    private final Exit exit = AbstractFactory.newExit(75002);
    private int lineNumber;
    private final ArrayList<String> sourceCodeWithoutComment = new ArrayList<>();
    
    ////////////////////////////////////////////////////////////////////////////
    // constructor
    ////////////////////////////////////////////////////////////////////////////
    
    
    ////////////////////////////////////////////////////////////////////////////
    // interface functions
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * O parser é feito por meio de uma máquina de estados finitos.
     * @param sourceCode 
     */
    /**
     * Função para retirar os comentários do arquivo de código fonte.
     * Function to strip comments from source code file.
     * @param sourceCode: Matrix file that represents the file.
     * @return Matrix that represents the file.
     */
    @Override
    public ArrayList<String> parser(final ArrayList<String> sourceCode, final String fileName) {
        
        StateFile state = StateFile.CommonLine;
        sourceCodeWithoutComment.clear(); // reset the array
        
        for(lineNumber = 0; lineNumber < sourceCode.size(); ++lineNumber) {
            String line = sourceCode.get(lineNumber);
//            System.out.format("ori: \"%s\"\n", line);
            switch(state) {
                case CommonLine:
                    state = findComment(line); break;
                case BlockComment: 
                    state = blockCommentBody(line); break;
                default: break;
            }
        }
        
        return sourceCodeWithoutComment;
        
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////
    
    
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
              exit.errorLine(1, lineNumber, lineStr, "Malformed Line.\n",
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
