package ca4.yapl.impl;

import ca4.yapl.interfaces.IAttrib;
import ca4.yapl.interfaces.ISymbol;
import ca4.yapl.lib.Type;

/**
 *
 * @author richie
 */
public class Attrib implements IAttrib {
    private byte kind;
    private Type type;
    private byte register;
    private boolean constant;
    private boolean global;
    private IAttrib index;
    private int offset;
    
    public Attrib(Type type, boolean constant) {
        this.type = type;
        this.constant = constant;                
    }
    
    public Attrib(byte kind, Type type) {
        this.kind = kind;
        this.type = type;
    }
    
    public Attrib(ISymbol symbol) {
        this.type = symbol.getType();
        constant = symbol.isReadonly();
    }

    public boolean isConstant() {
        return constant;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public IAttrib getIndex() {
        return index;
    }

    public void setIndex(IAttrib index) {
        this.index = index;
    }

    public byte getKind() {
        return kind;
    }

    public void setKind(byte kind) {
        this.kind = kind;
    }

    public byte getRegister() {
        return register;
    }

    public void setRegister(byte register) {
        this.register = register;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
}
