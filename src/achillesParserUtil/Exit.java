/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParserUtil;

/**
 *
 * @author borges
 */
public interface Exit {
    
    /**
     * Send error message and exit program.
     * @param msg 
     */
    void error(final String... msg);
    
    void error(final int errorCode, final String... msg);
}
