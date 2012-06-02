package yapl.impl;

import java.io.PrintStream;
import java.util.List;
import yapl.exceptions.YAPLException;
import yapl.interfaces.IBackendMIPS;
import yapl.interfaces.CompilerError;
import yapl.interfaces.IAttrib;
import yapl.interfaces.ICodeGen;
import yapl.lib.ArrayType;
import yapl.parser.Token;

/**
 *
 * @author richie
 */
public class CodeGen implements ICodeGen {
    
    public static final String EXIT_MAIN = "exitMain";
    
    private IBackendMIPS backend;
    
    public CodeGen(PrintStream out) {
        this.backend = new BackendMIPS(out);
    }

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
        backend.enterMain();
    }

    @Override
    public void exitMain() throws YAPLException {
        backend.exitMain(EXIT_MAIN);
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

    @Override
    public byte callProc(String procName, List<IAttrib> arguments) {
        backend.prepareProcCall(arguments.size());
        for (int i = 0; i < arguments.size(); i++)
            backend.passArg(i, arguments.get(i).getRegister());
        
        byte destReg = backend.allocReg();
        backend.callProc(destReg, procName);
        return destReg;
    }
    
}
