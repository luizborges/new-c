/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParser;

import achillesParserUtil.AbstractFactory;
import achillesParserUtil.Exit;
import achillesParserUtil.Token;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author borges
 */
public class ParserFunctionImpl implements Parser, ParserOutput {

    ////////////////////////////////////////////////////////////////////////////
    // variables
    ////////////////////////////////////////////////////////////////////////////
    enum FileType {
        Header,
        Code
    }
    
    private final ArrayList<String> fileHeader = new ArrayList<>(); // file.h
    private final ArrayList<String> fileCode = new ArrayList<>();   // file.c
    private Token token;
    private final Exit exit = AbstractFactory.newExit(75007);
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Interface methods - Parser
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public ArrayList<String> parser(final ArrayList<String> sourceCode,
                                    final String fileName) {
        includeHeaderInCodeFile(fileName);
        token = AbstractFactory.newToken(sourceCode);
        for (token.init(); !token.isEnd(); token.nextToken()) {
            if (token.isNewLine()) {
                if (token.getString().compareTo("func") == 0) {
                    parserFunction(true);
                } else if (token.getString().compareTo("text") == 0) {
                    // funções que não possuem declarações no arquivo .h, existem apenas no
                    // arquivo .c, exemplo a função "int main() {...}"
                    parserFunction(false);
                } else {
                    parserGeneral();
                }
            } else {
                parserGeneral();
            }
        }
        return fileHeader;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // Interface methods - ParserOutput
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public ArrayList<String> getFileHeader() {
        return fileHeader;
    }

    @Override
    public ArrayList<String> getFileCode() {
        return fileCode;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////
    private void parserFunction(final boolean isFunc) {
        token.nextToken();
        // cria linhas novas para separação nos arquivos destinos
        if (isFunc == true) {
            fileHeader.add(""); fileHeader.add("");
        }
        fileCode.add(""); fileCode.add("");
        
        // escreve a declaração da função em ambos os arquivos
        for (;token.getString().compareTo("{") != 0; token.nextToken()) {
            if (isFunc == true) {
                writeFileOutput(FileType.Header);
            }
            writeFileOutput(FileType.Code);
        }
        
        // finaliza a escrita no hearder file .h se necessário
        if (isFunc == true) {
            writeFileOutput(FileType.Header, false, ";");
        }
        
        saveFunctionBody();
    }
    
    
    private void parserGeneral() {
        writeFileOutput(FileType.Header);
    }

    /**
     * Write a token on fileHeader
     */
    private void writeFileOutput(final FileType type) {
        ArrayList<String> file = null;
        switch (type) {
            case Header: file = fileHeader; break;
            case Code: file = fileCode; break;
            default: exit.error(1, "File Output Type is incorrect. Type name is ", type.toString());
        }
        
        if (token.isNewLine()) {
            file.add(token.getString());
        } else {
            int lastId = file.size() - 1;
            String line = file.get(lastId);
            line += " " + token.getString();
            file.set(lastId, line);
        }
    }
    
    /**
     * Write a string array on output file.
     * @param type
     * @param isNewLine
     * @param str 
     */
    private void writeFileOutput(final FileType type,
            final boolean isNewLine, final String... str) {
        ArrayList<String> file = null;
        switch (type) {
            case Header: file = fileHeader; break;
            case Code: file = fileCode; break;
            default: exit.error(1, "File Output Type is incorrect. Type name is ", type.toString());
        }
        
        if (isNewLine == true) {
            file.add("");
        } 
        
        int lastId = file.size() - 1;
        String line = file.get(lastId);
        for (int i=0; i < str.length; ++i) {
            line += " " + str[i];
        }
        file.set(lastId, line);
    }
    
    private void saveFunctionBody() {
        Stack codeBlock = new Stack();
        for (; !token.isEnd(); token.nextToken()) {
            if (token.getString().compareTo("{") == 0) {
                String str = "Token: \""+ token.getString() + "\":: Line: " + token.getLineNumber() + ":: pos: " + token.getPositionInLine();
                codeBlock.push(str);
            } else if (token.getString().compareTo("}") == 0) {
                if (!codeBlock.isEmpty()) {
                    codeBlock.pop();
                    if (codeBlock.isEmpty()) {
                        writeFileOutput(FileType.Code); // write '}' on output file
                        return;
                    }
                } else {
                    exit.errorLine(2, token.getLineNumber(), token.getLine(),
                            "Close a \'}\' without open.");
                }
            }
            
            writeFileOutput(FileType.Code); // write token on output file
        }
        
        exit.error(9, "Exit Source Code without ending a code block.");
    }
    
    /**
     * Create a line: "# include "file.h""
     * in the file.c
     * where file is the name of the user's file
     * @param fileNameWithPath String that represents the name of the file
     */
    private void includeHeaderInCodeFile(final String fileNameWithPath) {
        Path p = Paths.get(fileNameWithPath);
        String fullName = p.getFileName().toString();  
        String name[] = fullName.split("\\s++" // retira a extensão do nome do arquivo
                + "|(?!^|$)(?:(?<=\\.)(?!\\.)|(?<!\\.)(?=\\.))");
        
        String header = "\"" + name[0] + ".h\""; // create header file name
        writeFileOutput(FileType.Code, true, "# include", header);
        fileCode.add(""); // create a new line
    }
}
