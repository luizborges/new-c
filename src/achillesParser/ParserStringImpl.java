/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParser;

import achillesParserType.Code;
import achillesParserType.CodeImpl;
import achillesParserUtil.AbstractFactory;
import achillesParserUtil.Exit;
import achillesParserUtil.ExitImpl;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author borges
 */
public class ParserStringImpl implements Parser {
    /**
     * Class to keep safe the position of the original string and char
     * that exists in a original source code.
     */
    private class CodeStrPosition {
        public int init; // init position in line
        public int end; // end position in line
        public Code.Type tcode; // type of the code
        public String originalCode; // original statement of the user. ex: "hello world!" or 'c' 
        public int lineNumber; // line number of the source code
    }
    
    private final Exit exit = AbstractFactory.newExit(75003);
    private final ArrayList<String> sourceCodeWithoutStringAndChar = new ArrayList<>();
    private int lineNumber;
    private int pos;
    private char line[];
    private final Code code = AbstractFactory.newCode();
    private final ArrayList<CodeStrPosition> codePosition = new ArrayList<>();
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Interface methods
    ////////////////////////////////////////////////////////////////////////////
    /**
     *
     * @param sourceCode
     * @return
     */
    public ArrayList<String> parser(final ArrayList<String> sourceCode) {
        int i = 0; // guarda a posição corrente do ID da última posicao de string
                   // encontrada no arranjo que guarda as strings encontradas
        for (lineNumber=0; lineNumber < sourceCode.size(); ++lineNumber) {
            line = sourceCode.get(lineNumber).toCharArray();
            boolean hasParser = false;
            
            ////////////////////////////////////////////////////////////////////////////
            // identifica todas a string e char da linha
            ////////////////////////////////////////////////////////////////////////////
            for (pos=0; pos < line.length; ++pos) {
                if (line[pos] == '"') {
                    parser(Code.Type.String);
                    hasParser = true;
                } else if (line[pos] == '\'') {
                    parser(Code.Type.Char);
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

    ////////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Descobre uma ocorrencia simples de string e char no código e guarda a mesma.
     * @param type: tipo de ocorrencia, se é string ou char 
     */
    private void parser(Code.Type type) {
        char c = 0;
        switch (type) {
            case String: c = '"'; break;
            case Char: c = '\''; break;
            default: exit.error(1, "Code does not exists. Code = ", type.toString());
        }
        
        CodeStrPosition positionStr = new CodeStrPosition();
        positionStr.tcode = type;
        positionStr.init = pos;
        positionStr.lineNumber = lineNumber;
        
        try {
            do {                    
                ++pos;
                if (line[pos] == '\\') {
                    pos += 2;
                }
            } while (line[pos] != c);
        } catch (ArrayIndexOutOfBoundsException e) {
              e.printStackTrace();
              exit.errorLine(2, lineNumber, Arrays.toString(line),
                      "Malformed Line.",
                      "\nCan not be reached the end of  ", type.toString(),
                      "\n\nException Name: \'ArrayIndexOutOfBoundsException\'");
        }
        
        positionStr.end = pos;
        positionStr.originalCode = String.valueOf(line).substring(positionStr.init, positionStr.end+1);
        codePosition.add(positionStr);
    }
}
