/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParserUtil;

import java.util.ArrayList;
import java.util.Stack;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;

/**
 *
 * @author borges
 */
public class TokenImpl implements Token {
    ////////////////////////////////////////////////////////////////////////////
    // variables
    ////////////////////////////////////////////////////////////////////////////
    
    
    private ArrayList<String> sourceCode;
    private String lineToken[];
    private int lineNumber;
    private int linePos;
    private String tokenStr;
    private boolean isInit;
    private Exit exit = AbstractFactory.newExit(23002);

    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////
    public TokenImpl(final ArrayList<String> sourceCode) {
        if (sourceCode != null) {
            this.sourceCode = sourceCode;
            isInit = false;
        } else {
            exit.error(1, "Source Code passed to Token is null.");
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Interface methods
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public String init() {
        isInit = true;
        tokenStr = null;
        ////////////////////////////////////////////////////////////////////////////
        // Get the first token
        ////////////////////////////////////////////////////////////////////////////
        for (lineNumber = 0; lineNumber < sourceCode.size(); ++lineNumber) {
            lineToTokens();
            for (linePos = 0; linePos < lineToken.length; ++linePos) {
                if (lineToken[linePos].isEmpty() == false) {
                    tokenStr = lineToken[linePos];
                    break;
                }
            }
        }
        return tokenStr;
    }
    
    @Override
    public String nextToken() {
        tokenStr = null;
        for (; lineNumber < sourceCode.size(); ++lineNumber) {
            lineToTokens();
            for (; linePos < lineToken.length; ++linePos) {
                if (lineToken[linePos].isEmpty() == false) {
                    tokenStr = lineToken[linePos];
                    break;
                }
            }
        }
        return tokenStr;
    }
    
    @Override
    public String getToken(final int lineNumberInit, final int linePosInit) {
        tokenStr = null;
        for (lineNumber = lineNumberInit; lineNumber < sourceCode.size(); ++lineNumber) {
            lineToTokens();
            linePos = lineNumber == lineNumberInit ? linePosInit : 0;
            for (; linePos < lineToken.length; ++linePos) {
                if (lineToken[linePos].isEmpty() == false) {
                    tokenStr = lineToken[linePos];
                    break;
                }
            }
        }
        return tokenStr;
    }

    @Override
    public String getString() {
        if (isInit) {
            return tokenStr;
        } else {
            exit.error(2, "Not Initialize Token and try to use it.");
            return null;
        }
    }

    @Override
    public int getLineNumber() {
        if (isInit) {
            return lineNumber;
        } else {
            exit.error(2, "Not Initialize Token and try to use it.");
            return -2;
        }
    }

    @Override
    public int getPositionInLine() {
        if (isInit) {
            return linePos;
        } else {
            exit.error(2, "Not Initialize Token and try to use it.");
            return -2;
        }
    }

    @Override
    public boolean isEnd() {
        if (isInit) {
            if (tokenStr != null) {
                return false;
            } else {
                return true;
            }
        } else {
            exit.error(2, "Not Initialize Token and try to use it.");
            return true;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Parser a line in a multiple string
     */
    private void lineToTokens() {
        String lineStr = sourceCode.get(lineNumber).trim();
        lineToken = lineStr.split("\\s++"
                            + "|(?!^|$)(?:(?<=;)(?!;)|(?<!;)(?=;))"
                            + "|(?!^|$)(?:(?<=#)(?!#)|(?<!#)(?=#))"
                            + "|(?!^|$)(?:(?<=\\{)(?!\\{)|(?<!\\{)(?=\\{))"
                            + "|(?!^|$)(?:(?<=\\})(?!\\})|(?<!\\})(?=\\}))"
                            + "|(?!^|$)(?:(?<=\\()(?!\\()|(?<!\\()(?=\\())"
                            + "|(?!^|$)(?:(?<=\\))(?!\\))|(?<!\\))(?=\\)))"
                            + "|(?!^|$)(?:(?<=\\,)(?!\\,)|(?<!\\,)(?=\\,))"
                            + "|(?!^|$)(?:(?<=\\:)(?!\\:)|(?<!\\:)(?=\\:))"
                            + "|(?!^|$)(?:(?<=\\::)(?!\\::)|(?<!\\::)(?=\\::))"
                            + "|(?!^|$)(?:(?<=\\<)(?!\\<)|(?<!\\<)(?=\\<))"
                            + "|(?!^|$)(?:(?<=\\>)(?!\\>)|(?<!\\>)(?=\\>))"
                            + "|(?!^|$)(?:(?<=\\=)(?!\\=)|(?<!\\=)(?=\\=))");
        
        // code to print tokens in terminal
//        System.out.format("%d:: ", lineNumber+1);
//        for (int i=0; i < token.length; ++i) {
//            System.out.format("[%s] ", token[i]);
//        }
//        System.out.println();
    }

    

    
    
    
}
