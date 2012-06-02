package yapl.lib;

/**
 *
 * @author richie
 */
public class IntType extends Type {
    private int value;

    public IntType() {
    }

    public IntType(int value) {
        this.value = value;
    }

    public int isValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    @Override
    public boolean isCompatible(Type other) {
        return other instanceof IntType;
    }
}
