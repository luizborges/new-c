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
public class ExitImpl implements Exit {
    
    private String header;
    
    /**
     * Init the ExitImpl class.
     * @param errorNumber identify the error number, that identify the class or error type
     */
    public ExitImpl(final int errorNumber) {
        header = String.valueOf(errorNumber);
    }

    /**
     * Send error message and exit program.
     * @param msg 
     */
    @Override
    public void error(final String... msg) {
        System.out.format("\n\nERROR: %s - ", header);
        for(int i=0; i < msg.length; ++i)
            System.out.format("%s ", msg[i]);
        
        System.out.println();
        System.out.format("=====================================================\n\n");
        new Throwable().getStackTrace();
        System.exit(1); 
    }

    @Override
    public void error(int errorCode, String... msg) {
        System.out.format("ERROR: %s.%d - ", header, errorCode);
        for(int i=0; i < msg.length; ++i)
            System.out.format("%s ", msg[i]);
        
        System.out.println();
        System.out.format("=====================================================\n\n");
        new Throwable().getStackTrace();
        System.exit(1); 
    }

    @Override
    public void errorLine(int errorCode, int lineNumber, String line, String... msg) {
        System.out.format("ERROR: %s.%d - ", header, errorCode);
        for(int i=0; i < msg.length; ++i) {
            System.out.format("%s ", msg[i]);
        }
        System.out.format("\nLine Number: %d\n", lineNumber+1);
        System.out.format("Line : \"%s\"\n", line);
        System.out.format("=====================================================\n\n");
        new Throwable().getStackTrace();
        System.exit(1); 
    }
    
}
