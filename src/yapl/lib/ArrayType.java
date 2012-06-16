package yapl.lib;

import yapl.exceptions.YAPLException;
import yapl.interfaces.CompilerError;

/**
 *
 * @author richie
 */
public class ArrayType extends Type {
    private Type elem;
    private int dim;    
    
    public ArrayType(Type type) {
        if (type instanceof IntType || type instanceof BoolType || type instanceof RecordType) {
            this.elem = type;
            this.dim = 1;
        } else if (type instanceof ArrayType) {
            this.elem = type;
            this.dim = ((ArrayType)type).getDim() + 1;
        } else {
            throw new IllegalArgumentException("Wir hom an Potsch gebaut - Array nit vom Typ int, bool, Record oder Array");
        }
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public Type getElem() {
        return elem;
    }
    
    public Type subarray(int k) throws YAPLException {
        if (k > dim)
            throw new YAPLException(CompilerError.SelectorNotArray);
        Type t = elem;
        for (; k > 1; k--) {
            t = ((ArrayType)t).getElem();
        }
        return t;
    }
    
    @Override
    public boolean isCompatible(Type other) {
        if (!(other instanceof ArrayType))
            return false;
        
        ArrayType b = (ArrayType)other;
        return elem.isCompatible(b.getElem()) && dim == b.getDim();
    }
}
