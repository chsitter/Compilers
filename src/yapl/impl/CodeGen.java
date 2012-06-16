package yapl.impl;

import java.io.PrintStream;
import java.util.List;
import yapl.exceptions.YAPLException;
import yapl.interfaces.*;
import yapl.lib.ArrayType;
import yapl.lib.BoolType;
import yapl.lib.IntType;
import yapl.lib.Type;
import yapl.parser.Token;

/**
 *
 * @author richie
 */
public class CodeGen implements ICodeGen {

    public static final String EXIT_MAIN = "exitMain";
    private IBackendMIPS backend;
    private int labelNum = 0;
       

    public CodeGen(PrintStream out) {
        this.backend = new BackendMIPS(out);
    }

    /**
     * Return the number of bytes occupied by a variable of the given data type
     * on the target architecture.
     *
     * @param type	the data type to calculate the size of.
     * @throws YAPLException (Internal) if
     * <code>type</code> is not supported by this method.
     */
    protected int sizeOf(Type type) throws YAPLException {
        if (type instanceof IntType
                || type instanceof BoolType
                || type instanceof ArrayType) // array variables also occupy only 1 word (start address)
        {
            return backend.wordSize();
        }
        throw new YAPLException(YAPLException.Internal);
    }

    @Override
    public String newLabel() {
        //TODO: falls uns intressiert baun ma's noch ein
        return "L" + labelNum++;
    }

    @Override
    public void assignLabel(String label) {
        backend.emitLabel(label, null);
    }

    @Override
    public byte loadReg(IAttrib attr) throws YAPLException {
        int attrKind = attr.getKind();
        if (attrKind == Attrib.Register) {
            return attr.getRegister();
        }
        byte reg = backend.allocReg();
        if (reg < 0) {
            throw new YAPLException(YAPLException.NoMoreRegs);
        }

        switch (attrKind) {
            case IAttrib.Constant: {
                int value = 0;
                Type attrType = attr.getType();
                if (attrType instanceof IntType) {
                    value = ((IntType) attrType).getValue();
                } else if (attrType instanceof BoolType) {
                    value = backend.boolValue(((BoolType) attrType).getValue());
                } else {
                    throw new YAPLException(YAPLException.Internal);
                }
                backend.loadConst(reg, value);
            }
            break;
            case IAttrib.Address:
                backend.loadWord(reg, attr.getOffset(), attr.isGlobal());
                break;
            case IAttrib.ArrayElement: {
                // both base address and index should reside in registers already!
                IAttrib idx = attr.getIndex();
                byte baseReg = attr.getRegister();
                backend.loadArrayElement(reg, baseReg, idx.getRegister());
                backend.freeReg(baseReg);
                freeReg(idx);
                break;
            }
            default:
                throw new YAPLException(YAPLException.Internal);
        }
        attr.setRegister(reg);
        attr.setKind(Attrib.Register);
        return reg;
    }

    @Override
    public void freeReg(IAttrib attr) {
        if (attr.getKind() != Attrib.Register
                && attr.getKind() != Attrib.ArrayElement) {
            return;
        }
        backend.freeReg(attr.getRegister());
        attr.setKind(Attrib.None);
    }

    @Override
    public void allocVariable(ISymbol sym) throws YAPLException {
        int bytes = sizeOf(sym.getType());
        if (sym.isGlobal()) {
            sym.setOffset(backend.allocStaticData(bytes, sym.getName()));
        } else {
            sym.setOffset(backend.allocStack(bytes, sym.getName()));
        }
    }   

    @Override
    public void arrayOffset(IAttrib arr, IAttrib index) throws YAPLException {
        if (!(arr.getType() instanceof ArrayType)) {
            throw new YAPLException(YAPLException.Internal);
        }
        ArrayType arrtype = (ArrayType) arr.getType();
        byte baseReg = loadReg(arr);
        if (arr.getKind() == Attrib.ArrayElement) {
            // multi-dimensional array - update base address
            IAttrib idx = arr.getIndex();
            byte idxReg = loadReg(idx);
            backend.loadArrayElement(baseReg, baseReg, idxReg);
            freeReg(idx);
        }
        loadReg(index);
        // for ArrayElements, both base address and index must reside in registers!
        arr.setKind(IAttrib.ArrayElement);
        arr.setType(arrtype.getElem());
        arr.setIndex(index);
        freeReg(index);
    }

    @Override
    public IAttrib arrayLength(IAttrib arr) throws YAPLException {
        byte reg = loadReg(arr);   // arr represents an address operand, so load its value!
        backend.arrayLength(reg, reg);
        arr.setType(new IntType());
        return arr;
    }

    @Override
    public void assign(IAttrib lvalue, IAttrib expr) throws YAPLException {
        if (!lvalue.getType().isCompatible(expr.getType())) {
            throw new YAPLException(CompilerError.TypeMismatchAssign);
        }

        loadReg(expr);
        switch (lvalue.getKind()) {
            case Attrib.Address:
                backend.storeWord(expr.getRegister(), lvalue.getOffset(), lvalue.isGlobal());
                break;
            case Attrib.ArrayElement: {
                IAttrib idx = lvalue.getIndex();
                backend.storeArrayElement(expr.getRegister(), lvalue.getRegister(), idx.getRegister());
                freeReg(idx);
            }
            break;
            default:
                throw new YAPLException(YAPLException.Internal);
        }
        freeReg(expr);
        freeReg(lvalue);
    }

    @Override
    public IAttrib op2(IAttrib x, Token op, IAttrib y) throws YAPLException {
        if (!x.getType().isCompatible(y.getType())) {
            throw new YAPLException(CompilerError.IllegalOp2Type);
        }

        byte xReg = loadReg(x);
        byte yReg = loadReg(y);
        if (x.getType() instanceof IntType && y.getType() instanceof IntType) {
            switch (op.image) {
                case "+":                                    
                    backend.add(xReg, xReg, yReg);
                    break;                    
                case "-":
                    backend.sub(xReg, xReg, yReg);
                    break;
                case "*":
                    backend.mul(xReg, xReg, yReg);                    
                    break;                
                case "/":
                    backend.div(xReg, xReg, yReg);
                    break;
                case "%":
                    backend.mod(xReg, xReg, yReg);
                    break;
                default:
                    throw new YAPLException(YAPLException.IllegalOp2Type, op);
            }
        } else if (x.getType() instanceof BoolType && y.getType() instanceof BoolType) {
            switch (op.image) {          
                case "Or":
                    backend.add(xReg, xReg, yReg);
                    backend.isLess(xReg, (byte)0, xReg);
                    break;                 
                case "And":
                    backend.mul(xReg, xReg, yReg);                    
                    break;
                default:
                    throw new YAPLException(YAPLException.IllegalOp2Type, op);
            }
        } else {
            throw new YAPLException(YAPLException.IllegalOp2Type, op);
        }
        x.setConstant(x.isConstant() && y.isConstant());
        freeReg(y);
        return x;
    }

    @Override
    public IAttrib relOp(IAttrib x, Token op, IAttrib y) throws YAPLException {
        if (!x.getType().isCompatible(y.getType())) {
            throw new YAPLException(CompilerError.IllegalRelOpType);
        }
        if (!(x.getType() instanceof IntType && y.getType() instanceof IntType)) {
            throw new YAPLException(YAPLException.IllegalRelOpType, op);
        }
        byte xReg = loadReg(x);
        byte yReg = loadReg(y);
        switch (op.image) {
            case "<":
                backend.isLess(xReg, xReg, yReg);
                break;
            case ">":
                backend.isLess(xReg, yReg, xReg);
                break;
            case "<=":
                backend.isLessOrEqual(xReg, xReg, yReg);
                break;
            case ">=":
                backend.isLess(xReg, xReg, yReg);
                backend.not(xReg, xReg);
                break;
            case "==":
                backend.isEqual(xReg, xReg, yReg);
                break;
            case "!=":
                backend.isEqual(xReg, xReg, yReg);
                backend.not(xReg, xReg);
                break;
            default:
                throw new YAPLException(YAPLException.IllegalRelOpType, op);
        }
        x.setType(new BoolType());
        x.setConstant(x.isConstant() && y.isConstant());
        freeReg(y);
        return x;
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
        int offset = backend.allocStringConstant(string);
        backend.writeString(offset);
    }

    @Override
    public void writeInt(IAttrib atr) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void branchIfFalse(IAttrib condition, String label) throws YAPLException {
        byte reg = loadReg(condition);
        backend.branchIf(reg, false, label);
        freeReg(condition);
    }

    @Override
    public void jump(String label) {
        backend.jump(label);
    }

    @Override
    public byte callProc(String procName, List<IAttrib> arguments) {
        if (arguments != null) {
            backend.prepareProcCall(arguments.size());
            for (int i = 0; i < arguments.size(); i++) {
                backend.passArg(i, arguments.get(i).getRegister());
            }
        } else {
            backend.prepareProcCall(0);
        }

        byte destReg = backend.allocReg();
        backend.callProc(destReg, procName);
        
        if(arguments != null)
            for(IAttrib a : arguments)
                freeReg(a);
        
        return destReg;
    }

    @Override
    public void exitProc(String procName, IAttrib retAttr) throws YAPLException {
        backend.returnFromProc("exit_" + procName, retAttr.getRegister());
        freeReg(retAttr);
    }

    @Override
    public IAttrib allocArray(ArrayType arrayType, IAttrib dim1, IAttrib dim2) throws YAPLException {        
        IAttrib retAttr = new Attrib(IAttrib.Register, arrayType);
        retAttr.setConstant(false);
        retAttr.setRegister(backend.allocReg());
                
        backend.allocArray(retAttr.getRegister(), dim1.getRegister(), (dim2 != null ? dim2.getRegister() : (byte)-1));        
        
        return retAttr;
    }
    
    @Override
    public IAttrib allocArray(ArrayType arrayType, IAttrib dim1) throws YAPLException {
        return allocArray(arrayType, dim1, null);
    }

    @Override
    public void neg(IAttrib attrib) throws YAPLException {
        loadReg(attrib);
        backend.neg(attrib.getRegister(), attrib.getRegister());
    }
//
//    @Override
//    public byte loadArrayElement(IAttrib arr, IAttrib index) {
//        byte elementReg = backend.allocReg();
//        backend.loadArrayElement(elementReg, arr.getRegister(), index.getRegister());
//        return elementReg;
//    }
//
//    @Override
//    public void storeArrayElement(IAttrib arr, IAttrib index, IAttrib src) {
//        backend.storeArrayElement(src.getRegister(), arr.getRegister(), index.getRegister());
//    }
}
