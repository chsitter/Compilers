/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca3.yapl.impl;

import ca3.yapl.interfaces.ISymbol;
import ca3.yapl.lib.Type;
import ca3.yapl.parser.Token;

/**
 *
 * @author chsitter
 */
public class Symbol implements ISymbol {

    private int kind;
    private String name;
    private Type type;
    private boolean reference;
    private boolean global;
    private int offset;
    private ISymbol next;
    private boolean returnSeen;
    private Token token = null;
    
    public Symbol(String name, int kind) {
        this.name = name;
        this.kind = kind;
    }   
    
    public Symbol(Token token, int kind) {
        this(token.image, kind);
        this.token = token;        
    }

    @Override
    public int getKind() {
        return kind;
    }

    @Override
    public String getKindString() {
        switch (kind) {
            case Program:
                return "Program";
            case Procedure:
                return "Procedure";
            case Variable:
                return "Variable";
            case Constant:
                return "Constant";
            case Typename:
                return "Typename";
            case Field:
                return "Field";
            case Parameter:
                return "Parameter";
        }
        
        return "unknown";
    }
        

    @Override
    public void setKind(int kind) {
        this.kind = kind;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean isReference() {
        return reference;
    }

    @Override
    public void setReference(boolean isReference) {
        reference = isReference;
    }

    @Override
    public boolean isGlobal() {
        return global;
    }

    @Override
    public void setGlobal(boolean isGlobal) {
        this.global = isGlobal;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public ISymbol getNextSymbol() {
        return next;
    }

    @Override
    public void setNextSymbol(ISymbol symbol) {
        next = symbol;
    }

    @Override
    public boolean getReturnSeen() {
        return returnSeen;
    }

    @Override
    public void setReturnSeen(boolean seen) {
        this.returnSeen = seen;
    }

    @Override
    public Token getToken() {
        return token;
    }
}
