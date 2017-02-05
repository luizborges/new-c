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
public class AbstractFactory {
    
    public static Exit newExit(final int errorNumber) {
        return new ExitImpl(errorNumber);
    }
    
    public static Token newToken(final ArrayList<String> sourceCode) {
        return new TokenImpl(sourceCode);
    }
}
