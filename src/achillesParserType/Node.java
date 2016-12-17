/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParserType;

import achillesParserType.Code.Type;

/**
 *
 * @author borges
 */
public interface Node {
    
    /**
     * Add a new node in a node structure,
     * A node has to be unique in lineNumberInit and lineNumberEnd.
     * @param type  type of node
     * @param lineNumberInit  line number init
     * @param lineNumberEnd  line number end
     */
    void add(final Type type, final int lineNumberInit, final int lineNumberEnd);
    
}
