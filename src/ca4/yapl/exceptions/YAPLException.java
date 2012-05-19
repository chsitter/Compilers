/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca4.yapl.exceptions;

import ca4.yapl.interfaces.CompilerError;
import ca4.yapl.interfaces.ISymbol;
import ca4.yapl.parser.Token;

/**
 *
 * @author chsitter
 */
public class YAPLException extends Exception implements CompilerError {    
    int errorCode;
    int line = -1;
    int column = -1;
    
    public YAPLException(int errorCode) {
        super();  
        this.errorCode = errorCode;
    }
    
    public YAPLException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public YAPLException(int errorCode, String message, int line, int column) {
        this(errorCode, message);
        this.line = line;
        this.column = column;
    }
    
    public YAPLException(int errorCode, String message, ISymbol symbol) {
        this(errorCode, message);
        
        if(symbol != null) {
            Token t = symbol.getToken();
            if (t != null) {
                this.line = t.beginLine;
                this.column = t.beginColumn;
            }        
        }
    }
    
    public YAPLException(int errorCode, String message, Token t) {
        this(errorCode, message);
        
        if (t != null) {
            this.line = t.beginLine;
            this.column = t.beginColumn;
        }
    }

    @Override
    public int errorNumber() {
        return errorCode;
    }

    @Override
    public int line() {
        return line;    
    }

    @Override
    public int column() {
        return column;
    }       
}
