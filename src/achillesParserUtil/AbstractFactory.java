/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParserUtil;

import achillesParserType.Code;
import achillesParserType.CodeImpl;
import achillesParserType.Node;
import achillesParserType.NodeImpl;
import achillesParserType.NodeImpl2;
import java.util.ArrayList;

/**
 *
 * @author borges
 */
public class AbstractFactory {
    public static Code newCode() {
        return CodeImpl.getInstance();
    }
    
    public static Node newNode() {
        return NodeImpl2.getRootInstance();
    }
    
    public static Node newNode(final int lineNumberEnd) {
        return NodeImpl2.init(lineNumberEnd);
    }
    
    public static Exit newExit(final int errorNumber) {
        return new ExitImpl(errorNumber);
    }
    
    public static Token newToken(final ArrayList<String> sourceCode) {
        return new TokenImpl(sourceCode);
    }
}
