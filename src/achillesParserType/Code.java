/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParserType;

/**
 *
 * @author borges
 */
public interface Code {
    
    enum TCode {
        String,
        Char
    }
    
    String getCode(final TCode code);
}
