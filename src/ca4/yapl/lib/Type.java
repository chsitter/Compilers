package ca4.yapl.lib;

/**
 *
 * @author chsitter
 */
public abstract class Type {
    private boolean readonly;
    
    public abstract boolean isCompatible(Type other);

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
}
