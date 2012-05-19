package ca4.yapl.lib;

/**
 *
 * @author richie
 */
public class IntType extends Type {
    @Override
    public boolean isCompatible(Type other) {
        return other instanceof IntType;
    }
}
