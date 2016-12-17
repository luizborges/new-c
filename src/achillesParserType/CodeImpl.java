/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParserType;

import achillesParserUtil.AbstractFactory;
import achillesParserUtil.Exit;
import achillesParserUtil.ExitImpl;

/**
 *
 * @author borges
 */
public class CodeImpl implements Code {

    private static final Exit exit = AbstractFactory.newExit(84001);
    private static CodeImpl codeImpl = null;
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructor and Singleton
    ////////////////////////////////////////////////////////////////////////////
    public static CodeImpl getInstance() {
        if (codeImpl == null) {
            codeImpl = new CodeImpl();
        }
        
        return codeImpl;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Interface methods
    ////////////////////////////////////////////////////////////////////////////
    @Override
    /**
     * 
     */
    public String getCode(Type code) {
        switch(code) {
            case String: return ""; //"$_String_$";
            case Char:   return ""; //"$_Char_$";
            case Macro:  return ""; //"$_Macro_$";
            default: exit.error(1, "Code does not exists. Code = ", code.toString());
        }
        
        return null;
    }
    
}
