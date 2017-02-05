/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParserUtil;

import java.util.ArrayList;

/**
 *
 * @author borges
 */
public class TokenImpl implements Token {
    ////////////////////////////////////////////////////////////////////////////
    // variables
    ////////////////////////////////////////////////////////////////////////////
    
    
    private ArrayList<String> sourceCode;
    private ArrayList<String> lineToken = new ArrayList<>();
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
            for (linePos = 0; linePos < lineToken.size(); ++linePos) {
                if (lineToken.get(linePos).isEmpty() == false) {
                    tokenStr = lineToken.get(linePos);
                    return tokenStr;
                }
            }
        }
        return tokenStr;
    }
    
    @Override
    public String nextToken() {
        tokenStr = null;
        ++linePos;
        if (lineNumber < sourceCode.size()) {
            if (linePos == 0) {
                lineToTokens();
            }
            
            if (linePos < lineToken.size()) {
                tokenStr = lineToken.get(linePos);
                return tokenStr;
            } else {
                // realiza o incremento da linha e reseta o valor da coluna
                // chama a proxima interação para não retornar um valor null
                // e sempre retornar um valor e token válido
                ++lineNumber;
                linePos = -1;
                return nextToken();
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
            for (; linePos < lineToken.size(); ++linePos) {
                tokenStr = lineToken.get(linePos);
                return tokenStr;
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
    public String getLine() {
        if (isInit) {
            return sourceCode.get(lineNumber);
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
            if (lineNumber < sourceCode.size()) {
                return false;
            } else {
                return true;
            }
        } else {
            exit.error(2, "Not Initialize Token and try to use it.");
            return true;
        }
    }
    
    @Override
    public boolean isNewLine() {
        if (isInit) {
            if (linePos == 0) {
                return true;
            } else if (linePos < 0) {
                exit.error(3, "Token ID is less than Token_Line_Min_ID = 0. linePos = ", String.valueOf(linePos));
                return false; // never gets here
            } else if (linePos >=  lineToken.size()) {
                exit.error(4, "Token ID is greater than Token_Line_Max_ID. linePos = ", String.valueOf(linePos), 
                        "\nToken_Line_Max_ID = ", String.valueOf(lineToken.size() -1));
                return false; // never gets here
            } else {
                return false;
            }
        } else {
            exit.error(5, "Not Initialize Token and try to use it.");
            return false; // never gets here
        }
    }

    @Override
    public boolean isLastToken() {
        if (isInit) {
            if (linePos == lineToken.size() -1) {
                return true;
            } else if (linePos < 0) {
                exit.error(6, "Token ID is less than Token_Line_Min_ID = 0. linePos = ", String.valueOf(linePos));
                return false; // never gets here
            } else if (linePos >=  lineToken.size()) {
                exit.error(7, "Token ID is greater than Token_Line_Max_ID. linePos = ", String.valueOf(linePos), 
                        "\nToken_Line_Max_ID = ", String.valueOf(lineToken.size() -1));
                return false; // never gets here
            } else {
                return false;
            }
        } else {
            exit.error(8, "Not Initialize Token and try to use it.");
            return false; // never gets here
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
        String[] lineT;
        
        lineT = lineStr.split("\\s++"
                + "|(?!^|$)(?:(?<=\\;)(?!\\;)|(?<!\\;)(?=\\;))"
                + "|(?!^|$)(?:(?<=\\#)(?!\\#)|(?<!\\#)(?=\\#))"
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
        
        lineToken.clear(); // reseta a lista
        
        for (int i = 0; i < lineT.length; ++i) {
            if (lineT[i].isEmpty() == false) {
                lineToken.add(lineT[i]);
            }
        }
    }

    

    

    
    
    
}
