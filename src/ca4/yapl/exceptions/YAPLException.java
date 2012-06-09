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
        this(errorCode, createMessage(errorCode));  
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
    
    public YAPLException(int errorCode, Token t) {
        this(errorCode, createMessage(errorCode), t);
    }
    
    private static String createMessage(int errorCode) {
        switch (errorCode) {
            case CompilerError.SelectorNotArray: return "expression before '[' is not an array type";
            case CompilerError.SelectorNotRecord: return "expression before '.' is not a record type";
            case CompilerError.BadArraySelector: return " array index or dimension is not an integer type";
            case CompilerError.InvalidRecordField: return " invalid field <name> of record <name>";
            case CompilerError.ArrayLenNotArray: return " expression after '#' is not an array type";
            case CompilerError.IllegalOp1Type: return " illegal operand type for unary operator <op>";
            case CompilerError.IllegalOp2Type: return " illegal operand types for binary operator <op>";
            case CompilerError.IllegalRelOpType: return " non-integer operand types for relational operator <op>";
            case CompilerError.IllegalEqualOpType: return " illegal operand types for equality operator <op>";
            case CompilerError.ProcNotFuncExpr: return " using procedure <name> (not a function) in expression";
            case CompilerError.ReadonlyAssign: return " read-only l-value in assignment";
            case CompilerError.TypeMismatchAssign: return " type mismatch in assignment";
            case CompilerError.ArgNotApplicable: return " argument #<n> not applicable to procedure <name>";
            case CompilerError.ReadonlyArg: return " read-only argument #<n> passed to read-write procedure <name>";
            case CompilerError.TooFewArgs: return " too few arguments for procedure <name>";
            case CompilerError.CondNotBool: return " condition is not a boolean expression";
            case CompilerError.ReadonlyNotReference: return " type qualified as readonly is not a reference type";
            case CompilerError.MissingReturn: return " missing Return statement in function <name>";
            case CompilerError.InvalidReturnType: return " returning none or invalid type from function <name>";
            case CompilerError.IllegalRetValProc: return " illegal return value in procedure <name> (not a function)";
            case CompilerError.IllegalRetValMain: return " illegal return value in main program";
            default: throw new IllegalArgumentException("Fehler im Compiler: ungueltiger errorCode");
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
