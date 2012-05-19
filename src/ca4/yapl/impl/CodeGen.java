package ca4.yapl.impl;

import ca4.yapl.exceptions.YAPLException;
import ca4.yapl.interfaces.CompilerError;
import ca4.yapl.interfaces.IAttrib;
import ca4.yapl.interfaces.ICodeGen;
import ca4.yapl.lib.ArrayType;
import ca4.yapl.parser.Token;

/**
 *
 * @author richie
 */
public class CodeGen implements ICodeGen {

    @Override
    public String newLabel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void assignLabel(String label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public byte loadReg(IAttrib attr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void freeReg(IAttrib attr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void allocVariable(Symbol sym) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void storeArrayDim(int dim, IAttrib length) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IAttrib allocArray(ArrayType arrayType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void arrayOffset(IAttrib arr, IAttrib index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IAttrib arrayLength(IAttrib arr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void assign(IAttrib lvalue, IAttrib expr) throws YAPLException {
        if (!lvalue.getType().isCompatible(expr.getType()))
            throw new YAPLException(CompilerError.TypeMismatchAssign);
    }

    @Override
    public IAttrib op2(IAttrib x, Token op, IAttrib y) throws YAPLException {
        if (!x.getType().isCompatible(y.getType()))
            throw new YAPLException(CompilerError.IllegalOp2Type);
        return x;   //TODO: stimmt des so?
    }

    @Override
    public IAttrib relOp(IAttrib x, Token op, IAttrib y) throws YAPLException {
        if (!x.getType().isCompatible(y.getType()))
            throw new YAPLException(CompilerError.IllegalRelOpType);
        return x;   //TODO: stimmt des so?
    }

    @Override
    public void enterMain() throws YAPLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void exitMain() throws YAPLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void writeString(String string) throws YAPLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void writeInt(IAttrib atr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void branchIfFalse(IAttrib condition, String label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void jump(String label) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
