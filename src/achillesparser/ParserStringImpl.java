/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesparser;

import achillesParserType.Code;
import achillesParserType.CodeImpl;
import achillesParserUtil.Exit;
import achillesParserUtil.ExitImpl;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author borges
 */
public class ParserStringImpl implements ParserString {
    /**
     * Class to keep safe the position of the original string and char
     * that exists in a original source code.
     */
    private class CodeStrPosition {
        public int init; // init position in line
        public int end; // end position in line
        public Code.TCode tcode; // type of the code
        public String originalCode; // original statement of the user. ex: "hello world!" or 'c' 
        public int lineNumber; // line number of the source code
    }
    
    private Exit exit = new ExitImpl(75003);
    private ArrayList<String> sourceCodeWithoutStringAndChar = new ArrayList<>();
    private int lineNumber;
    private int pos;
    private char line[];
    private Code code = new CodeImpl();
    private ArrayList<CodeStrPosition> codePosition = new ArrayList<>();
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Interface methods
    ////////////////////////////////////////////////////////////////////////////
    @Override

    /**
     *
     * @param sourceCode
     * @return
     */
    public ArrayList<String> parserString(final ArrayList<String> sourceCode) {
        int i = 0;
        for (lineNumber=0; lineNumber < sourceCode.size(); ++lineNumber) {
            line = sourceCode.get(lineNumber).toCharArray();
            boolean hasParser = false;
            
            ////////////////////////////////////////////////////////////////////////////
            // identifica todas a string e char da linha
            ////////////////////////////////////////////////////////////////////////////
            for (pos=0; pos < line.length; ++pos) {
                if (line[pos] == '"') {
                    parser(Code.TCode.String);
                    hasParser = true;
                } else if (line[pos] == '\'') {
                    parser(Code.TCode.Char);
                    hasParser = true;
                }
            }
            
            ////////////////////////////////////////////////////////////////////////////
            // retira todas a string e char da linha e substitiu pelo codigo apropriado
            ////////////////////////////////////////////////////////////////////////////
            if (hasParser == true) {
                String lineStr = sourceCode.get(lineNumber);
                for (; i < codePosition.size(); ++i) {
                    CodeStrPosition csp = codePosition.get(i);
                    lineStr = lineStr.replace(csp.originalCode, code.getCode(csp.tcode));
                }
                sourceCodeWithoutStringAndChar.add(lineStr);
            } else {
                sourceCodeWithoutStringAndChar.add(sourceCode.get(lineNumber));
            }
        }
        
        return sourceCodeWithoutStringAndChar;
    }

    /**
     * Descobre uma ocorrencia simples do código e guarda a mesma.
     * @param tCode 
     */
    private void parser(Code.TCode tCode) {
        char c = 0;
        switch (tCode) {
            case String: c = '"'; break;
            case Char: c = '\''; break;
            default: exit.error(1, "Code does not exists. Code = ", tCode.toString());
        }
        
        CodeStrPosition positionStr = new CodeStrPosition();
        positionStr.tcode = tCode;
        positionStr.init = pos;
        positionStr.lineNumber = lineNumber+1;
        
        try {
            do {                    
                ++pos;
                if (line[pos] == '\\') {
                    pos += 2;
                }
            } while (line[pos] != c);
        } catch (ArrayIndexOutOfBoundsException e) {
              e.printStackTrace();
              exit.error(2, "Malformed Line. LineNumber: ", String.valueOf(lineNumber+1),
                      "\n\"", Arrays.toString(line), "\"\n",
                      "Exception Name: \'ArrayIndexOutOfBoundsException\'");
        }
        
        positionStr.end = pos;
        positionStr.originalCode = String.valueOf(line).substring(positionStr.init, positionStr.end+1);
        codePosition.add(positionStr);
    }
}
