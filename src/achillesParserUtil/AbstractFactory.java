/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParserUtil;

import achillesParser.ParserCommentImpl;
import achillesParser.ParserFunctionImpl;
import achillesParser.ParserStringImpl;
import java.util.ArrayList;

/**
 *
 * @author borges
 */
public class AbstractFactory {
    ////////////////////////////////////////////////////////////////////////////
    // variables
    ////////////////////////////////////////////////////////////////////////////
    private static ParserCommentImpl  parserCommentImpl;
    private static ParserStringImpl   parserStringImpl;
    private static ParserFunctionImpl parserFunctionImpl;
    private static Output             output;
    private static TokenImpl          tokenImpl;
    
    ////////////////////////////////////////////////////////////////////////////
    // public functions
    ////////////////////////////////////////////////////////////////////////////
    
    public static Exit newExit(final int errorNumber) {
        return new ExitImpl(errorNumber);
    }
    
    public static Token newToken(final ArrayList<String> sourceCode) {
        if (tokenImpl == null) {
            tokenImpl = new TokenImpl();
        }
        tokenImpl.create(sourceCode);
        return tokenImpl;
    }
    
    public static ParserCommentImpl newParserComment() {
        if (parserCommentImpl == null) {
            parserCommentImpl = new ParserCommentImpl();
        }
        return parserCommentImpl;
    }
    
    public static ParserStringImpl newParserString() {
        if (parserStringImpl == null) {
            parserStringImpl = new ParserStringImpl();
        }
        return parserStringImpl;
    }
    
    public static ParserFunctionImpl newParserFunction() {
        if (parserFunctionImpl == null) {
            parserFunctionImpl = new ParserFunctionImpl();
        }
        return parserFunctionImpl;
    }
    
    public static Output newOutput() {
        if (output == null) {
            output = new OutputImpl();
        }
        return output;
    }
}
