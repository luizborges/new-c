/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author borges
 */
public class InputImpl implements Input {
    
    private final String[] fileName; // equal to fileName in public static int main(String[] fileName) that program Java receive in input argument
    private int filePosition;

    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////
    /**
     * 
     * @param args 
     */
    InputImpl(final String[] fileName) {
        if(fileName == null) error("No input file array. Argument to program is null.");
        this.fileName = fileName;
    }
    
    
    /**
     * Send error message and exit program.
     * @param msg 
     */
    private void error(final String... msg) {
        System.out.format("ERROR: 75001 - ");
        for(int i=0; i < msg.length; ++i)
            System.out.format("%s ", msg[i]);
        
        System.out.println();
        System.out.format("=====================================================\n\n");
        new Throwable().getStackTrace();
        System.exit(1);
        
    }
    
    /**
     * Create a matrix that represents a file.
     * Each line of the file will be a line in the matrix.
     * The matrix is a ArrayList<String>
     * @param position
     * @return null if position is >= fileName.length
         otherwise create a bufferedReader from fileName file
     */
    private ArrayList<String> getFileString(final int position) {
        if(position < 0) error("position is less than zero. Position: ", String.valueOf(position));
        
        ArrayList<String> fileStr = null;
        if(position < fileName.length) {
            BufferedReader breader = null;
            try {
                FileInputStream fis = new FileInputStream(fileName[position]);
                breader = new BufferedReader(new InputStreamReader(fis));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(InputImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            try {
                fileStr = new ArrayList<>();
                String line = null;
                while ((line = breader.readLine()) != null) {
                    fileStr.add(line);
                }
            } catch (IOException ex) {
                Logger.getLogger(InputImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return fileStr;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Interface methods
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public ArrayList<String> initReader() {
        filePosition = 0; // inicializa a leitura do file position
        ArrayList<String> fileStr = getFileString(filePosition);
        filePosition++; // increment file position
        
        return fileStr;
    }

    @Override
    public ArrayList<String> nextReader() {
        if(filePosition < 0)
            error("filPposition is less than zero. Position: ", String.valueOf(filePosition));
        
        ArrayList<String> fileStr = getFileString(filePosition);
        if(fileStr != null) filePosition++; // increment file position
        
        return fileStr;
    }
    
}
