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



/**
 *
 * @author borges
 */
public class ParserMacroImpl implements Parser {

    ////////////////////////////////////////////////////////////////////////////
    // Job variables
    ////////////////////////////////////////////////////////////////////////////

    
    private final ArrayList<String> sourceCodeWithoutMacro = new ArrayList<>();
    private int lineNumber;
    private final Exit exit = AbstractFactory.newExit(75004);
    private String lineStr;
//    private final Node root = AbstractFactory.newNode();
//    private final Code code = AbstractFactory.newCode();
    private ArrayList<String> sourceCode;
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Interface methods
    ////////////////////////////////////////////////////////////////////////////
        @Override
    public ArrayList<String> parser(final ArrayList<String> sourceCode, final String fileName) {
        this.sourceCode = sourceCode;
        for (lineNumber = 0; lineNumber < sourceCode.size(); ++lineNumber) {
            lineStr = sourceCode.get(lineNumber);
            if (isLineMacroStatement() == true) {
                parserMacro();
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
     * @return true if is a macro,
     *  false otherwise.
     */
    private boolean isLineMacroStatement() {
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
                exit.errorLine(1, lineNumber, lineStr,
                        "Incorrect Macro Statement. Initial macros must to have more words. ");
            } else {
                if (tokens[1].compareTo("define")     == 0
                    || tokens[1].compareTo("if")      == 0
                    || tokens[1].compareTo("ifdef")   == 0
                    || tokens[1].compareTo("ifndef")  == 0
                    || tokens[1].compareTo("else")    == 0   
                    || tokens[1].compareTo("elif")    == 0 
                    || tokens[1].compareTo("undef")   == 0
                    || tokens[1].compareTo("include") == 0
                    || tokens[1].compareTo("error")   == 0
                    || tokens[1].compareTo("pragma")  == 0
                    || tokens[1].compareTo("warning") == 0
                    || tokens[1].compareTo("endif")   == 0) {
                    
                    isMacro = true;
                } else {
                    exit.errorLine(2, lineNumber, lineStr,
                            "Incorrect Macro Statement. Not a macro initial word: \"", tokens[1], "\"");
                }
            }
        }
        
        return isMacro;
    }

    /**
     * Parser macro define.
     */
    private void parserMacro() {
        boolean macroEnd;
        int lineInit = lineNumber;
        do {
            ////////////////////////////////////////////////////////////////////
            // check define ends here
            ////////////////////////////////////////////////////////////////////
            String line = sourceCode.get(lineNumber).trim();
            int pos = line.lastIndexOf('\\');
            macroEnd = pos == line.length() -1 ? false : true;
            
            ////////////////////////////////////////////////////////////////////
            // set line Macro in source File without macro
            ////////////////////////////////////////////////////////////////////
//            sourceCodeWithoutMacro.add(code.getCode(Code.Type.Macro));
            if (macroEnd == false) {
                ++lineNumber;
            }
        } while(macroEnd == false);
        
        int lineEnd = lineNumber;
//        root.add(Code.Type.Macro, lineInit, lineEnd);
    }

    @Override
    public ArrayList<String> getFileHeader() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getFileCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}








/**
 * Como era os métodos, antes da nova abordagem.
 * Nesta abordagem antiga, se fazia diferença, entre Macro: define, conditional e others.
 */
//    private Macro macroType;
//    private enum Macro {
//        define,
//        conditional,
//        others // this macros suppose that requires just one line
//    }
//    @Override
//    public ArrayList<String> parser(final ArrayList<String> sourceCode) {
//        this.sourceCode = sourceCode;
//        for (lineNumber = 0; lineNumber < sourceCode.size(); ++lineNumber) {
//            lineStr = sourceCode.get(lineNumber);
//            if (isLineMacroStatement() == true) {
//                switch (macroType) {
//                    case define: parserMacroDefine(); break;
//                    case conditional: sourceCodeWithoutMacro.add(lineStr); break;
//                    case others: parserMacroOthers(); break;
//                    default: exit.errorLine(4, lineNumber, lineStr,
//                            "Invalid Macro Type. Macro Type: ", macroType.toString());
//                }
//            } else {
//                sourceCodeWithoutMacro.add(lineStr);
//            }
//        }
//        return sourceCodeWithoutMacro; 
//    }
//    /**
//     * Check if the current line is a macro.
//     * @return 
//     */
//    private boolean isLineMacroStatement() {
//        boolean isMacro = false;
//        /**
//         * \\s++ -> retira os caracteres em branco, tab, new line, etc
//         * | -> significa o operador or, ou
//         * (?!^|$)(?:(?<=#)(?!#)|(?<!#)(?=#)) -> torna o caractere # um delimitador
//         * mas o mantem na saída, porém como separado dos demais caracteres.
//         * ex: "# include <stdio.h>" -> "[#] [include] [<stdio.h>]"
//         * "#include <stdio.h>" -> "[#] [include] [<stdio.h>]"
//         * "	xupeta#sergio# ###  " -> [xupeta] [#] [sergio] [#] [] [###]
//         */
//        String tokens[] = lineStr.trim().split("\\s++|(?!^|$)(?:(?<=#)(?!#)|(?<!#)(?=#))");
//        // code to print tokens in terminal
////        System.out.format("%d:: ", lineNumber+1);
////        for (int i=0; i < tokens.length; ++i) {
////            System.out.format("[%s] ", tokens[i]);
////        }
////        System.out.println();
//        
//        if (tokens[0].compareTo("#") == 0) {
//            if (tokens.length == 1) {
//                exit.errorLine(1, lineNumber, lineStr,
//                        "Incorrect Macro Statement. Initial macros must to have more words. ");
//            } else {
//                if (tokens[1].compareTo("define") == 0) {
//                    macroType = Macro.define;
//                    isMacro   = true;
//                } else if (tokens[1].compareTo("if")     == 0
//                        || tokens[1].compareTo("ifdef")  == 0
//                        || tokens[1].compareTo("ifndef") == 0
//                        || tokens[1].compareTo("else")   == 0   
//                        || tokens[1].compareTo("elif")   == 0 ) {
//                    macroType = Macro.conditional;
//                    isMacro   = true;
//                    
//                } else if (tokens[1].compareTo("undef")   == 0
//                        || tokens[1].compareTo("include") == 0
//                        || tokens[1].compareTo("error")   == 0
//                        || tokens[1].compareTo("pragma")  == 0
//                        || tokens[1].compareTo("warning") == 0) {
//                    macroType = Macro.others;
//                    isMacro   = true;
//                    
//                } else if (tokens[1].compareTo("endif") == 0) {
////                    exit.errorLine(2, lineNumber, lineStr, 
////                            "Incorrect Macro Statement. End macro statment can not be a inital macro. ");
//                } else {
//                    exit.errorLine(3, lineNumber, lineStr,
//                            "Incorrect Macro Statement. Not a macro initial word: \"", tokens[1], "\"");
//                }
//            }
//        }
//        
//        return isMacro;
//    }
    
//    /**
//     * Parser macro define.
//     */
//    private void parserMacroDefine() {
//        boolean defineEnd;
//        int lineInit = lineNumber;
//        do {
//            ////////////////////////////////////////////////////////////////////
//            // check define ends here
//            ////////////////////////////////////////////////////////////////////
//            String line = sourceCode.get(lineNumber).trim();
//            int pos = line.lastIndexOf('\\');
//            defineEnd = pos == line.length() -1 ? false : true;
//            
//            ////////////////////////////////////////////////////////////////////
//            // set line Macro in source File without macro
//            ////////////////////////////////////////////////////////////////////
//            sourceCodeWithoutMacro.add(code.getCode(Code.Type.Macro_Define));
//            if (defineEnd == false) {
//                ++lineNumber;
//            }
//        } while(defineEnd == false);
//        
//        int lineEnd = lineNumber;
//        root.add(Code.Type.Macro_Define, lineInit, lineEnd);
//    }
//    
//    private void parserMacroOthers() {
//        sourceCodeWithoutMacro.add(code.getCode(Code.Type.Macro_Other));
//        root.add(Code.Type.Macro_Other, lineNumber, lineNumber);
//    }