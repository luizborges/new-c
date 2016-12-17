/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParser;

import achillesParserUtil.AbstractFactory;
import achillesParserUtil.Exit;
import achillesParserUtil.Token;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author borges
 */
public class ParserFunctionImpl2 implements Parser {
    ////////////////////////////////////////////////////////////////////////////
    // variables
    ////////////////////////////////////////////////////////////////////////////
    private enum Type {
        Class,
        Namespace,
        Function,
        Template,
        Inline_Function,
        Other
    }
    
    private class Context {
        public int lineInit;
        public int linePosInit;
        public int lineEnd;
        public Type type;
    }
    
    private ArrayList<String> sourceCode;
    private final ArrayList<String> sourceCodeFunction = new ArrayList<>();
    private Token token;
    private final Stack context = new Stack();
    private final Stack className = new Stack(); // used to namespace
    private boolean isCheckStyleOn;
    private boolean isNewContext;
    private final Exit exit = AbstractFactory.newExit(75006);
    

    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Interface methods
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public ArrayList<String> parser(ArrayList<String> sourceCode) {
        this.sourceCode = sourceCode;
        token = AbstractFactory.newToken(sourceCode);
        isNewContext = true;
        
        for (token.init(); !token.isEnd(); token.nextToken()) {
            if (token.getString().compareTo("template") == 0) {
                parserTemplate();
            } else if (token.getString().compareTo("class") == 0) {
                parserClass();
            } else if (token.getString().compareTo("namespace") == 0) {
                parserNamespace();
            } else if (token.getString().compareTo("{") == 0) {
                parserFunction();
            } else if (token.getString().compareTo(";") == 0) {
                if(isCheckStyleOn) {
//                    checkStyle_endExpression();
                }
                finishContext();
            } else {
                initContext();
            }
        }
        return sourceCodeFunction;
    }

    ////////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////
    private void parserTemplate() {
        setContext(Type.Template);
        for (; !token.isEnd(); token.nextToken()) {
            if (token.getString().compareTo("{") == 0) {
                jumpToEndOfBlock();
            } else if (token.getString().compareTo(";") == 0) {
                if (isCheckStyleOn) {
//                        checkStyle_endExpression();
                }
                finishContext();
            }
        }
    }
    
    private void parserClass() {
        setContext(Type.Class);
        
        if (jumpToBeginOfBlock() == true) {
            className.push(token.nextToken());
        } else {
            finishContext();
        }
    }
    
    private void parserNamespace() {
        final int namespaceLine = token.getLineNumber();
        final int namespaceLinePos = token.getPositionInLine();
        setContext(Type.Namespace);
        if (jumpToBeginOfBlock() == true) {
            // verifica se não é um namespace unnamed
            token.getToken(namespaceLine, namespaceLinePos);
            token.nextToken();
            if (token.getString().compareTo("{") != 0) {
                className.push(token.getString());
            }
            jumpToBeginOfBlock();
        } else {
            finishContext();
        }
    }
    
    private void parserFunction() {
        
    }
    
    private boolean isFunction() {
        return false;
    }
    
    private void initContext() {
        if (isNewContext) {
            Context newContext = new Context();
            newContext.lineInit = token.getLineNumber();
            newContext.linePosInit = token.getPositionInLine();
            newContext.type = Type.Other;
            context.add(newContext);
            isNewContext = false;
        }
    }
    
    private void setContext(final Type type) {
        initContext();
        Context top = (Context) context.peek();
        top.type = type;
    }
    
    private void setContext(final Type type, 
            final int lineNumberInit, final int linePosInit) {
        initContext();
        Context top = (Context) context.peek();
        top.type = type;
        top.lineInit = lineNumberInit;
        top.linePosInit = linePosInit;
    }
    
    private void finishContext() {
        isNewContext = true;
        if (!context.empty()) {
            Context endContext = (Context) context.pop();
            ////////////////////////////////////////////////////////////////////
            // end of class. - limpa a pilha que guarda o nome das classes
            ////////////////////////////////////////////////////////////////////
            if (endContext.type == Type.Class || endContext.type == Type.Namespace) {
                if (className.empty()) {
                    exit.errorLine(5, token.getLineNumber()+1, 
                            sourceCode.get(token.getLineNumber()).trim(),
                            "End of ", endContext.type.toString(), " but className stack is empty.");
                } else {
                    className.pop();
                }
            }
        }
    }
    
    private void jumpToEndOfBlock() {
        Stack codeBlock = new Stack();
        for (; !token.isEnd(); token.nextToken()) {
            if (token.getString().compareTo("{") == 0) {
                String str = "Line: " + token.getLineNumber() + ":: pos: " + token.getPositionInLine();
                codeBlock.push(str);
            } else if (token.getString().compareTo("}") == 0) {
                if (!codeBlock.isEmpty()) {
                    codeBlock.pop();
                    if (codeBlock.isEmpty()) {
                        return;
                    }
                } else {
                    exit.errorLine(2, token.getLineNumber()+1, 
                            sourceCode.get(token.getLineNumber()).trim(),
                            "Close a \'}\' without open.");
                }
            } else if (token.getString().compareTo(";") == 0) {
                if (codeBlock.isEmpty()) {
                    exit.errorLine(7, token.getLineNumber()+1,
                            sourceCode.get(token.getLineNumber()).trim(),
                            "It is not a Block Code. Find a terminator statement without inside a block code. Position in Line of ';': ",
                            String.valueOf(token.getPositionInLine()));
                    }
                }
        }
        
        exit.error(9, "Exit Source Code without ending a code block.");
    }
    
    private boolean jumpToBeginOfBlock() {
        for (; !token.isEnd(); token.nextToken()) {
            if (token.getString().compareTo("{") == 0) {
                if (isCheckStyleOn) {
//                        checkStyle_endExpression(linePos, "{");
                }
                return true;
            } else if (token.getString().compareTo("}") == 0) {
                exit.errorLine(3, token.getLineNumber()+1,
                        sourceCode.get(token.getLineNumber()).trim(),
                        "Close a \'}\' without open.");
            } else if (token.getString().compareTo(";") == 0) {
                if (isCheckStyleOn) {
//                        checkStyle_endExpression(linePos, "{");
                }
                return false;
            }
        }
        
        exit.error(10, "Exit Source Code without beging a essential code block.");
        return false;
    }
    
//    private void checkStyle_endExpression(final int initPos) {
//        for (linePos=initPos+1; linePos < lineToken.length; ++linePos) {
//            if (lineToken[linePos].isEmpty() == false) {
//                exit.errorLine(1, lineNumber+1, sourceCode.get(lineNumber).trim(),
//                        "Malformed Line. Can not be any code after \';\'.\n",
//                        "For new codes and commands, utilize a new line.");
//            }
//        }
//    }
//    
//     private void checkStyle_endExpression(final int initPos, final String code) {
//        for (linePos=initPos+1; linePos < lineToken.length; ++linePos) {
//            if (lineToken[linePos].isEmpty() == false) {
//                exit.errorLine(4, lineNumber+1, sourceCode.get(lineNumber).trim(),
//                        "Malformed Line. Can not be any code after \'", code, "\'.\n",
//                        "For new codes and commands, utilize a new line.");
//            }
//        }
//    }
}
