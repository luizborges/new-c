/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParser;

import achillesParserUtil.AbstractFactory;
import achillesParserUtil.Exit;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author borges
 */
public class ParserStringImpl implements Parser, ParserString {
    ////////////////////////////////////////////////////////////////////////////
    // variables
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Class to keep safe the position of the original string and char
     * that exists in a original source code. 
         * code that represents the user string or char
         * code always is represents by: "$@STR" + stringAndCharID + "$@"
         * stringAndCharID is always a positive integer number
         * ex: (original = "Hello world!") + stringAndCharID=1 -> code = "$@STR1$@"
         * ex: (original = ' ') + stringAndCharID=2 -> code = "$@STR2$@"
        */
    private final ArrayList<String> stringAndChar = new ArrayList<>(); // original statement of the user. ex: "hello world!" or 'c'
    private final String keyInit = "$@STR";
    private final String keyEnd  = "$@";
    
    private final Exit exit = AbstractFactory.newExit(75003);
    private int lineNumber;
    private int pos;
    private char line[];
    
    private final ArrayList<String> sourceCodeReverse = new ArrayList<>();
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Interface methods
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public ArrayList<String> reverse(final ArrayList<String> sourceCode) {
        sourceCodeReverse.clear(); // reset the array
        
        // copy sourceCodeReverse to equal sourceCode
        for (int i=0; i < sourceCode.size(); ++i) {
            String line = sourceCode.get(i);
            sourceCodeReverse.add(line);
        }
        
        // change string ocurrence
        int lastLineNumberFound = 0;
        for (int stringAndCharID=0; stringAndCharID < stringAndChar.size();
                ++stringAndCharID) {
            for (int sourceCodeReverseLineNumber= lastLineNumberFound;
                    sourceCodeReverseLineNumber < sourceCode.size();
                    ++sourceCodeReverseLineNumber) {
                String line = sourceCodeReverse.get(sourceCodeReverseLineNumber);
                String code = keyInit + stringAndCharID + keyEnd;
                String newLine = line.replace(code, stringAndChar.get(stringAndCharID));
                
                // check the reference of the string because of the replace method
                if (!newLine.equals(line)) {
                    sourceCodeReverse.set(sourceCodeReverseLineNumber, newLine);
                    lastLineNumberFound = sourceCodeReverseLineNumber;
//                    sourceCodeReverseLineNumber = sourceCode.size(); // jump to end of the loop
                    break; // the breaks sometimes does not work
                }
                
            }
        }
        
        
        return sourceCodeReverse;
    }
    
    @Override
    public ArrayList<String> parser(final ArrayList<String> sourceCode,
            final String fileName) {
        ArrayList<String> sourceCodeWithoutStringAndChar = new ArrayList<>();
        stringAndChar.clear(); // reset the list
        int stringAndCharID = 0; // guarda a posição corrente do ID da última posicao de string
                   // encontrada no arranjo que guarda as strings encontradas
        for (lineNumber=0; lineNumber < sourceCode.size(); ++lineNumber) {
            line = sourceCode.get(lineNumber).toCharArray();
            boolean hasParser = false;
            
            ////////////////////////////////////////////////////////////////////////////
            // identifica todas a string e char da linha
            ////////////////////////////////////////////////////////////////////////////
            for (pos=0; pos < line.length; ++pos) {
                switch (line[pos]) {
                    case '"' : parser('"');  hasParser = true; break;
                    case '\'': parser('\''); hasParser = true; break;
                    default: // do nothing
                }
            }
            
            ////////////////////////////////////////////////////////////////////////////
            // retira todas a string e char da linha e substitiu pelo codigo apropriado
            ////////////////////////////////////////////////////////////////////////////
            if (hasParser == true) {
                String lineStr = sourceCode.get(lineNumber);
                for (; stringAndCharID < stringAndChar.size(); ++stringAndCharID) {
                    String str  = stringAndChar.get(stringAndCharID);
                    String code = keyInit + String.valueOf(stringAndCharID) + keyEnd;
                    lineStr = lineStr.replace(str, code);
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
     */
    private void parser(final char delimiter) {
        int init = pos;
        
        try {
            do {                    
                ++pos;
                if (line[pos] == '\\') {
                    pos += 2;
                }
            } while (line[pos] != delimiter);
        } catch (ArrayIndexOutOfBoundsException e) {
              e.printStackTrace();
              exit.errorLine(2, lineNumber, Arrays.toString(line),
                      "Malformed Line.",
                      "\nCan not be reached the end of  String/Char.",
                      "\n\nException Name: \'ArrayIndexOutOfBoundsException\'");
        }
        
        String str = String.valueOf(line).substring(init, pos+1);
        stringAndChar.add(str);
    }

}
