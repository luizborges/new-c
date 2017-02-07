/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParser;

import java.util.ArrayList;

/**
 *
 * @author borges
 */
public interface Parser {
    ArrayList<String> parser(final ArrayList<String> sourceCode, final String fileName);
}
