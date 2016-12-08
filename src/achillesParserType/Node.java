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
public interface Node {
    
    
    
//    /**
//     * 
//     * @param type 
//     */
//    void setType(Type type);
//    
//    /**
//     * 
//     * @return 
//     */
//    Type getType();
    
    /**
     * 
     * @param child 
     */
    void addChild(Node child);
    
    /**
     * Number of child that this node has.
     * @return 
     */
    int numChild();
    
    /**
     * 
     * @param index
     * @return 
     */
    Node getChild(int index);
    
    
    /**
     * Get the parent of the Node.
     * @return 
     */
    Node getParent();
}
