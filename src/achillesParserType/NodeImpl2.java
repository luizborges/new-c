/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParserType;

import achillesParserUtil.AbstractFactory;
import achillesParserUtil.Exit;
import java.util.ArrayList;

/**
 *
 * @author borges
 */
public class NodeImpl2 implements Node {
    ////////////////////////////////////////////////////////////////////////////
    // Singleton variable
    ////////////////////////////////////////////////////////////////////////////
    private static NodeImpl2 root = null;
    
    ////////////////////////////////////////////////////////////////////////////
    // State Variables that define and keep a node state
    ////////////////////////////////////////////////////////////////////////////
    private NodeImpl2 parent;
    private int lineNumberInit;
    private int lineNumberEnd;
    private ArrayList<NodeImpl2> child;
    private Code.Type type;
    private static final Exit exit = AbstractFactory.newExit(84002);
    
    ////////////////////////////////////////////////////////////////////////////
    // Job variables
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructor and Singleton Pattern
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Return a root instance by Singleton Pattern
     * If root do not exists, a error is throw.
     * @return a root instance.
     */
    public static Node getRootInstance() {
        if (root == null) {
            exit.error(5, "Root Node do not proper initialized.");
        }
        
        return root;
    }
    
    public static Node init(final int lineNumberEnd) {
        if (lineNumberEnd < 1) {
            exit.error(6, "Root Node must be a Line Number End greater of equal 1.\n",
                    "Line Number End passed is: ", String.valueOf(lineNumberEnd));
        }
        
        ////////////////////////////////////////////////////////////////////////////
        // initialize root parameters
        ////////////////////////////////////////////////////////////////////////////
        root = new NodeImpl2();
        root.lineNumberInit = 0;
        root.lineNumberEnd  = lineNumberEnd;
        root.type = Code.Type.Root;
        
        return root;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////
    private void checkLineNumber(final int lineNumberInit,
                                 final int lineNumberEnd) {
        if (lineNumberInit > lineNumberEnd) {
            exit.error(1, "Node with Init Line Number greater than End Number Line. ",
                    "lineNumberInit = ", String.valueOf(lineNumberInit),
                    ", lineNumberEnd = ", String.valueOf(lineNumberEnd));
        }
        if (lineNumberInit < this.lineNumberInit) {
            exit.error(2, "Trying to insert a node tree parser that has: lineNumberInit(",
                    String.valueOf(lineNumberInit), ") less or equal than Parent_Node->lineNumberInit(0).");
        }
        if (lineNumberEnd > this.lineNumberEnd) {
            exit.error(3, "Trying to insert a node tree parser that has: lineNumberEnd(",
                    String.valueOf(lineNumberEnd), ") less or equal than Parent_Node->lineNumberEnd(",
                    String.valueOf(this.lineNumberEnd), ").");
        }
    }
    
    /**
     * Adiciona um novo node no array dos filhos.
     * @param newNodeIndex
     * @param type
     * @param lineNumberInit
     * @param lineNumberEnd 
     */
    private void newNode(final int newNodeIndex, final Code.Type type,
            final int lineNumberInit, final int lineNumberEnd) {
        NodeImpl2 newNode = new NodeImpl2();
        newNode.type = type;
        newNode.lineNumberInit = lineNumberInit;
        newNode.lineNumberEnd = lineNumberEnd;
        newNode.parent = this;
        
        child.add(newNodeIndex, newNode); // adiciona o new Node na posicao correta
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Interface methods
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public void add(final Code.Type type, 
            final int lineNumberInit, final int lineNumberEnd) {
        // checagem dos limites do novo node
        if (this == root) {
            checkLineNumber(lineNumberInit, lineNumberEnd);
        }
        
        ////////////////////////////////////////////////////////////////////////////
        // Adiciona o primeiro filho
        ////////////////////////////////////////////////////////////////////////////
        if (child == null) {
            child = new ArrayList<>();
            newNode(0, type, lineNumberInit, lineNumberEnd);
            return;
        }
        
        ///////////////////////////////////////////////////////////////////////////
        // Adiciona o novo node como o primeiro da lista de uma lista já existente
        ////////////////////////////////////////////////////////////////////////////
        newNode(0, type, lineNumberInit, lineNumberEnd);
    }
}