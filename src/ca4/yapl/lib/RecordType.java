package ca4.yapl.lib;

import ca4.yapl.interfaces.ISymbol;
import java.util.ArrayList;

/**
 *
 * @author richie
 */
public class RecordType extends Type {
    private ArrayList<ISymbol> members = new ArrayList<ISymbol>();
    private boolean readonly;
    
    public RecordType(boolean readonly) {
        this.readonly = readonly;
    }            
    
    public void addMember(ISymbol member) {
        members.add(member);
    }
    
    public int getMembersCount() {
        return members.size();
    }
    
    public ISymbol getMember(int i) {
        return members.get(i);
    }
    
    public ISymbol getMember(String name) {        
        for (ISymbol member : members) {
            if (member.getName().equals(name))
                return member;
        }
        return null;
    }

    @Override
    public boolean isCompatible(Type other) {
        if (other instanceof RecordType) {
            RecordType b = (RecordType)other;
            if (members.size() == b.getMembersCount()) {
                ISymbol thisMember, otherMember;
                for (int i = 0; i < members.size(); i++) {
                    thisMember = getMember(i);
                    otherMember = b.getMember(i);
                    if (!thisMember.getName().equals(otherMember.getName()) || !thisMember.getType().isCompatible(otherMember.getType()))
                        return false;
                }
                return true;
            }
        }
        return false;
    }
}
