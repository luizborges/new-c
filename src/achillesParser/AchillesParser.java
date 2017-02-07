/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParser;

import achillesParserUtil.AbstractFactory;
import achillesParserUtil.Input;
import achillesParserUtil.InputImpl;
import achillesParserUtil.Exit;
import achillesParserUtil.ExitImpl;
import achillesParserUtil.Output;
import achillesParserUtil.OutputImpl;
import java.util.ArrayList;

/**
 *
 * @author borges
 */
public class AchillesParser {

    private Exit exit = AbstractFactory.newExit(75000);
    private Input input;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.format("Initing Parser Programming...\n\n");
        long _start = System.currentTimeMillis(); // get time in input
        
        AchillesParser ap = new AchillesParser();
        ap.parser(args);
        
        // show time end
        System.out.format("\nEnd of the Programming.\n");
        ap.showTime(System.currentTimeMillis(), _start);
    }
    
    /**
     * 
     * @param args 
     */
    public void parser(String[] args) {
        input = new InputImpl(args); // initialize input
        
        // parser files in argument
        for(ArrayList<String> sourceCode = input.initReader();
                sourceCode != null; sourceCode = input.nextReader()) {
            //////////////
            // show init time and get init time
            System.out.format("Initing Parser... File: \"%s\"\n", input.getFileName());
            long start = System.currentTimeMillis();
            
            Parser pr = AbstractFactory.newParserComment();
            sourceCode = pr.parser(sourceCode, null);
            
            pr = AbstractFactory.newParserString();
            sourceCode = pr.parser(sourceCode, null);

            pr = AbstractFactory.newParserFunction();
            pr.parser(sourceCode, input.getFileName());
            
            ParserOutput po = AbstractFactory.newParserFunction();
            ParserString ps = AbstractFactory.newParserString();
            
            Output out = AbstractFactory.newOutput();
            out.save(ps.reverse(po.getFileHeader()), input.getFileName(), ".h");
            out.save(ps.reverse(po.getFileCode()), input.getFileName(), ".c");
//            out.save(po.getFileHeader(), po.getFileCode(), input.getFileName());
            
            showTime(System.currentTimeMillis(), start); // show end time
        }
    }

    private void showTime(final long end, final long start) {
        // count time
        long milliseconds = end - start;
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000*60)) % 60;
        long hours   = (milliseconds / (1000*60*60)) % 24;
        long days    = (milliseconds / (1000*60*60*24)) % 30;
        
        // show result
        System.out.format("Finished Parser - Time:: ");
        boolean showNextTime = false;
        if (days > 0) {
            System.out.format("Days: %d | ", days);
            showNextTime = true;
        }
        if (hours > 0 || showNextTime == true) {
            System.out.format("Hours: %d | ", hours);
            showNextTime = true;
        }
        if (minutes > 0 || showNextTime == true) {
            System.out.format("Minutes: %d | ", minutes);
            showNextTime = true;
        }
        if (seconds > 0 || showNextTime == true) {
            System.out.format("Seconds: %d | ", seconds);
            showNextTime = true;
        }
        if (milliseconds > 0 || showNextTime == true) {
            System.out.format("Milliseconds: %d\n", milliseconds);
        }
    }
    
    
    
    
    
}
