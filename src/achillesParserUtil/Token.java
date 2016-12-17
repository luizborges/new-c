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
public interface Token {
    /**
     * Init the token
     * Go to the first of the text.
     * @return The string of the first token.
     * Never returned a empty string token
     */
    String init();
    
    /**
     * Get the next token.
     * @return The String of the next token.
     * null = If token get the end or not to initialized.
     * Never returned a empty string token
     */
    String nextToken();
    
    /**
     * Set the token to the current position
     * If the token do not exists the current behavior is unexpected 
     * @param lineNumber
     * @param linePos
     * @return string of the token 
     * or null if the token do not exists.
     */
    String getToken(final int lineNumber, final int linePos);
    
    /**
     * Get the string of the token
     * @return 
     * null = If token get the end or not to initialized.
     */
    String getString();
    
    /**
     * The number of the lineNumber of the token
     * Init in the zero position.
     * @return 
     */
    int getLineNumber();
    
    /**
     * The position of the token in the current line,
     * Text is like a matrix, this argument is like a column of the matrix,
     * Init in the zero position.
     * @return 
     */
    int getPositionInLine();
    
    /**
     * Tell if the end of the text was reached.
     * @return true if get the end,
     * false otherwise
     */
    boolean isEnd();
}
