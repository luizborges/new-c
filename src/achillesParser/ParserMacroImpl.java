/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParser;

import achillesParserUtil.Exit;
import achillesParserUtil.ExitImpl;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author borges
 */
public class ParserMacroImpl implements Parser {

    ////////////////////////////////////////////////////////////////////////////
    // Job variables
    ////////////////////////////////////////////////////////////////////////////
    private enum Macro {
        define,
        conditional,
        others // this macros suppose that requires just one line
    }
    
    private final ArrayList<String> sourceCodeWithoutMacro = new ArrayList<>();
    private int lineNumber;
    private final Exit exit = new ExitImpl(75004);
    private String lineStr;
    private Macro macroType;
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Interface methods
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public ArrayList<String> parser(final ArrayList<String> sourceCode) {
        for (lineNumber = 0; lineNumber < sourceCode.size(); ++lineNumber) {
            lineStr = sourceCode.get(lineNumber);
            if (isLineMacroStatment() == true) {
                switch (macroType) {
                    case define: break;
                    case conditional: break;
                    case others: break;
                    default: exit.error(4, "Invalid Macro Type. Macro Type: ", macroType.toString());
                }
            } else {
                sourceCodeWithoutMacro.add(lineStr);
            }
        }
        return sourceCodeWithoutMacro; 
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Check if the current line is a macro.
     * @return 
     */
    private boolean isLineMacroStatment() {
        boolean isMacro = false;
        /**
         * \\s++ -> retira os caracteres em branco, tab, new line, etc
         * | -> significa o operador or, ou
         * (?!^|$)(?:(?<=#)(?!#)|(?<!#)(?=#)) -> torna o caractere # um delimitador
         * mas o mantem na saída, porém como separado dos demais caracteres.
         * ex: "# include <stdio.h>" -> "[#] [include] [<stdio.h>]"
         * "#include <stdio.h>" -> "[#] [include] [<stdio.h>]"
         * "	xupeta#sergio# ###  " -> [xupeta] [#] [sergio] [#] [] [###]
         */
        String tokens[] = lineStr.trim().split("\\s++|(?!^|$)(?:(?<=#)(?!#)|(?<!#)(?=#))");
        // code to print tokens in terminal
//        System.out.format("%d:: ", lineNumber+1);
//        for (int i=0; i < tokens.length; ++i) {
//            System.out.format("[%s] ", tokens[i]);
//        }
//        System.out.println();
        
        if (tokens[0].compareTo("#") == 0) {
            if (tokens.length == 1) {
                exit.error(1, "Incorrect Macro Statement. Initial macros must to have more words. ",
                        "Line number: %d\n", "Line: \"", lineStr, "\"");
            } else {
                if (tokens[1].compareTo("define") == 0) {
                    macroType = Macro.define;
                } else if (tokens[1].compareTo("if")     == 0
                        || tokens[1].compareTo("ifdef")  == 0
                        || tokens[1].compareTo("ifndef") == 0
                        || tokens[1].compareTo("else")   == 0   
                        || tokens[1].compareTo("elif")   == 0 ) {
                    macroType = Macro.conditional;
                    isMacro   = true;
                } else if (tokens[1].compareTo("undef")   == 0
                        || tokens[1].compareTo("include") == 0
                        || tokens[1].compareTo("error")   == 0
                        || tokens[1].compareTo("pragma")  == 0
                        || tokens[1].compareTo("warning") == 0) {
                    macroType = Macro.others;
                    isMacro   = true;
                } else if (tokens[1].compareTo("endif") == 0) {
                    exit.error(2, "Incorrect Macro Statement. End macro statment can not be a inital macro. ",
                            "Line number: %d\n", "Line: \"", lineStr, "\"");
                } else {
                    exit.error(3, "Incorrect Macro Statement. Not a macro initial word: \"",
                            tokens[1], "\" - Line number: %d\n", "Line: \"", lineStr, "\"");
                }
            }
        }
        
        return isMacro;
    }
}
