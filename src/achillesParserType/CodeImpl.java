/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParserType;

import achillesParserUtil.Exit;
import achillesParserUtil.ExitImpl;

/**
 *
 * @author borges
 */
public class CodeImpl implements Code {

    Exit exit = new ExitImpl(84001);
    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////
    
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
    public String getCode(TCode code) {
        switch(code) {
            case String: return "$_String_$";
            case Char:   return "$_Char_$";
            default: exit.error(1, "Code does not exists. Code = ", code.toString());
        }
        
        return null;
    }
    
}
