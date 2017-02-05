/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achillesParser;

import achillesParserUtil.AbstractFactory;
import achillesParserUtil.Exit;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author borges
 */
public class ParserFunctionImpl implements Parser {

    @Override
    public ArrayList<String> getFileHeader() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getFileCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
        public int lineInitPos;
        public int lineEnd;
        public Type type;
    }
    
    private ArrayList<String> sourceCode;
    private final ArrayList<String> sourceCodeFunction = new ArrayList<>();
    private int lineNumber;
    private int linePos;
    private final Stack context = new Stack();
    private final Stack className = new Stack();
    private String token[];
    private boolean isCheckStyleOn;
    private boolean isNewContext;
    private final Exit exit = AbstractFactory.newExit(75005);
    

    ////////////////////////////////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    // Interface methods
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public ArrayList<String> parser(ArrayList<String> sourceCode, final String fileName) {
        this.sourceCode = sourceCode;
        isNewContext = true;
        for (lineNumber=0; lineNumber < sourceCode.size(); ++lineNumber) {
            lineToTokens();
            discoverContext();
        }
        
        return sourceCodeFunction;
    }

    ////////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Parser a line in a multiple string
     */
    private void lineToTokens() {
        String lineStr = sourceCode.get(lineNumber).trim();
        token = lineStr.split("\\s++|(?!^|$)(?:(?<=;)(?!;)|(?<!;)(?=;))"
                            + "|(?!^|$)(?:(?<=\\{)(?!\\{)|(?<!\\{)(?=\\{))"
                            + "|(?!^|$)(?:(?<=\\})(?!\\})|(?<!\\})(?=\\}))"
                            + "|(?!^|$)(?:(?<=\\()(?!\\()|(?<!\\()(?=\\())"
                            + "|(?!^|$)(?:(?<=\\))(?!\\))|(?<!\\))(?=\\)))"
                            + "|(?!^|$)(?:(?<=\\,)(?!\\,)|(?<!\\,)(?=\\,))"
                            + "|(?!^|$)(?:(?<=\\:)(?!\\:)|(?<!\\:)(?=\\:))"
                            + "|(?!^|$)(?:(?<=\\::)(?!\\::)|(?<!\\::)(?=\\::))"
                            + "|(?!^|$)(?:(?<=\\<)(?!\\<)|(?<!\\<)(?=\\<))"
                            + "|(?!^|$)(?:(?<=\\>)(?!\\>)|(?<!\\>)(?=\\>))");
        
        // code to print tokens in terminal
//        System.out.format("%d:: ", lineNumber+1);
//        for (int i=0; i < token.length; ++i) {
//            System.out.format("[%s] ", token[i]);
//        }
//        System.out.println();
    }
    
    /**
     * Discover the context,
     * what is the 
     */
    private void discoverContext() {
        for (linePos=0; linePos < token.length; ++linePos) {
            if (token[linePos].compareTo("template") == 0) {
                linePos = parserTemplate(linePos+1); break;
            } else if (token[linePos].compareTo("class") == 0) {
                linePos = parserClass(linePos+1);
            } else if (token[linePos].compareTo("namespace") == 0) {
                linePos = parserNamespace(linePos);
            } else if (token[linePos].compareTo("inline") == 0) {
                linePos = parserInlineFunction(linePos+1);
            } else if (token[linePos].compareTo("{") == 0) {
                linePos = parserFunction(linePos);
            } else if (token[linePos].compareTo(";") == 0) {
                if(isCheckStyleOn) {
                    checkStyle_endExpression(linePos);
                }
                finishContext();
            } else if (token[linePos].isEmpty()) {
                // do nothing
            } else {
                initContext(linePos);
            }
        }
    }
    
    private int parserTemplate(final int initPos) {
        setContext(Type.Template, initPos-1);
        linePos = initPos;
        while(true) {
            for (; linePos < token.length; ++linePos) {
                if (token[linePos].compareTo("class") == 0) {
                linePos = jumpToEndOfBlock(linePos+1);
                } else if (token[linePos].compareTo("{") == 0) {
                    linePos = jumpToEndOfBlock(linePos);
                } else if (token[linePos].compareTo(";") == 0) {
                    if (isCheckStyleOn) {
                        checkStyle_endExpression(linePos);
                    }
                    finishContext();
                    return linePos;
                }
            }
            ++lineNumber;
            lineToTokens();
            linePos = 0;
        }
    }
    
    private int parserClass(final int initPos) {
        setContext(Type.Class, initPos-1);
        linePos = jumpToBeginOfBlock(initPos+1);
        if (linePos > -1) {
            className.push(token[initPos]);
        } else if(linePos == -1) {
            finishContext();
        } else {
            exit.errorLine(6, lineNumber, sourceCode.get(lineNumber),
                    "Line Position (linePos) must be equal or greater than -1.",
                    String.valueOf(linePos));
        }
        return linePos;
    }
    
    private int parserNamespace(final int initPos) {
        setContext(Type.Namespace, initPos);
        linePos = jumpToBeginOfBlock(initPos+1);
        if (linePos > -1) {
//            for ()
            className.push(token[initPos]);
        } else if(linePos == -1) {
            finishContext();
        } else {
            exit.errorLine(6, lineNumber, sourceCode.get(lineNumber),
                    "Line Position (linePos) must be equal or greater than -1.",
                    String.valueOf(linePos));
        }
        return linePos;
    }
    
    private int parserInlineFunction(final int initPos) {
        setContext(Type.Inline_Function, linePos-1);
        linePos = initPos;
        while(true) {
            for (; linePos < token.length; ++linePos) {
                if (token[linePos].compareTo("{") == 0) {
                    linePos = jumpToEndOfBlock(linePos);
                } else if (token[linePos].compareTo(";") == 0) {
                    if (isCheckStyleOn) {
                        checkStyle_endExpression(linePos);
                    }
                    finishContext();
                    return linePos;
                }
            }
            ++lineNumber;
            lineToTokens();
            linePos = 0;
        }
    }
    
    private int parserFunction(final int initPos) {
//        if (isFunction(initPos) == false) return;
        
        setContext(Type.Function, initPos-1);
        linePos = initPos;
        while(true) {
            for (; linePos < token.length; ++linePos) {
                if (token[linePos].compareTo("class") == 0) {
                linePos = jumpToEndOfBlock(linePos+1);
                } else if (token[linePos].compareTo("{") == 0) {
                    linePos = jumpToEndOfBlock(linePos);
                } else if (token[linePos].compareTo(";") == 0) {
                    if (isCheckStyleOn) {
                        checkStyle_endExpression(linePos);
                    }
                    finishContext();
                    return linePos;
                }
            }
            ++lineNumber;
            lineToTokens();
            linePos = 0;
        }
    }
    
    private boolean isFunction(final int endPos) {
        return false;
//        boolean isFunction = false;
//        ////////////////////////////////////////////////////////////////////////////
//        // save the current position
//        ////////////////////////////////////////////////////////////////////////////
//        final Context top = (Context) context.peek();
//        final int endLineNumber = lineNumber;
//        
//        ////////////////////////////////////////////////////////////////////////////
//        // check if the statment correspond a function declaration
//        ////////////////////////////////////////////////////////////////////////////
//        
//        for (lineNumber = top.lineInit; lineNumber <= endLineNumber; ++lineNumber) {
//            lineToTokens();
//            
//            linePos = lineNumber == top.lineInit ? top.lineInitPos : 0;
//            for (; linePos < token.length; ++linePos) {
//                // erros names
//                if (token[linePos].compareTo("class") == 0) {
//                    
//                } else if (token[linePos].compareTo("namespace") == 0) {
//                    
////                } else if ()
//            }
//        }
//        while(true) {
//            for (; linePos < token.length; ++linePos) {
//                if (token[linePos].compareTo("class") == 0) {
//                    return false;
//                } else if (token[linePos].compareTo(";") == 0) {
//                    linePos = jumpToEndOfBlock(linePos);
//                }}
    }
    
    private void initContext(final int linePos) {
        if (isNewContext) {
            Context newContext = new Context();
            newContext.lineInit = lineNumber;
            newContext.lineInitPos = linePos;
            newContext.type = Type.Other;
            context.add(newContext);
            isNewContext = false;
        }
    }
    
    private void setContext(final Type type, final int linePos) {
        initContext(linePos);
        Context top = (Context) context.peek();
        top.type = type;
    }
    
    private void finishContext() {
        isNewContext = true;
        if (context.empty() == false) {
            Context endContext = (Context) context.pop();
            ////////////////////////////////////////////////////////////////////
            // end of class. - limpa a pilha que guarda o nome das classes
            ////////////////////////////////////////////////////////////////////
            if (endContext.type == Type.Class) {
                if (className.empty() == false) {
                    exit.errorLine(5, lineNumber, sourceCode.get(lineNumber),
                            "End of Class, but className stack is empty.");
                } else {
                    className.pop();
                }
            }
        }
    }
    
    private int jumpToEndOfBlock(final int initPos) {
        Stack codeBlock = new Stack();
        linePos = initPos;
        while (true) {
            for (; linePos < token.length; ++linePos) {
                if (token[linePos].compareTo("{") == 0) {
                    String str = "Line: " + lineNumber + ":: pos: " + linePos;
                    codeBlock.push(str);
                } else if (token[linePos].compareTo("}") == 0) {
                    if (codeBlock.isEmpty() == false) {
                        codeBlock.pop();
                        if (codeBlock.isEmpty()) {
                            return linePos;
                        }
                    } else {
                        exit.errorLine(2, lineNumber+1, sourceCode.get(lineNumber).trim(),
                                "Close a \'}\' without open.");
                    }
                }  else if (token[linePos].compareTo(";") == 0) {
                    if (codeBlock.isEmpty()) {
                        exit.errorLine(7, lineNumber, sourceCode.get(lineNumber).trim(),
                                "It is not a Block Code. Find a terminator statement without inside a block code. Position in Line of ';': ", 
                                String.valueOf(linePos));
                    }
                }
            }
        ++lineNumber;
        lineToTokens();
        linePos = 0;
       }
    }
    
    private int jumpToBeginOfBlock(final int initPos) {
        linePos = initPos;
        while (true) {
            for (; linePos < token.length; ++linePos) {
                if (token[linePos].compareTo("{") == 0) {
                    if (isCheckStyleOn) {
                        checkStyle_endExpression(linePos, "{");
                    }
                    return linePos;
                } else if (token[linePos].compareTo("}") == 0) {
                    exit.errorLine(3, lineNumber+1, sourceCode.get(lineNumber).trim(),
                                "Close a \'}\' without open.");
                } else if (token[linePos].compareTo(";") == 0) {
                    return linePos = -1;
                }
            }
        ++lineNumber;
        lineToTokens();
        linePos = 0;
       }
    }
    
    private void checkStyle_endExpression(final int initPos) {
        for (linePos=initPos+1; linePos < token.length; ++linePos) {
            if (token[linePos].isEmpty() == false) {
                exit.errorLine(1, lineNumber+1, sourceCode.get(lineNumber).trim(),
                        "Malformed Line. Can not be any code after \';\'.\n",
                        "For new codes and commands, utilize a new line.");
            }
        }
    }
    
     private void checkStyle_endExpression(final int initPos, final String code) {
        for (linePos=initPos+1; linePos < token.length; ++linePos) {
            if (token[linePos].isEmpty() == false) {
                exit.errorLine(4, lineNumber+1, sourceCode.get(lineNumber).trim(),
                        "Malformed Line. Can not be any code after \'", code, "\'.\n",
                        "For new codes and commands, utilize a new line.");
            }
        }
    }
}
