/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca3.yapl.impl;

import ca3.yapl.exceptions.IdentifierNotDeclaredException;
import ca3.yapl.exceptions.SymbolAlreadyDeclaredException;
import ca3.yapl.exceptions.YAPLException;
import ca3.yapl.interfaces.ISymbol;
import ca3.yapl.interfaces.ISymboltable;
import ca3.yapl.parser.Token;
import java.util.ArrayList;

/**
 *
 * @author chsitter
 */
public class SymbolTable implements ISymboltable {
    
    private int globalScopeIdx = -1;
    public boolean useGlobalScope = false; 
    public boolean debug;
    
    ArrayList<Scope> scopes;
    
    public SymbolTable() {
        scopes = new ArrayList<Scope>();        
        
        Scope predefinedScope = new Scope();
                
        predefinedScope.getSymbols().put("writeint", new Symbol("writeint", Symbol.Procedure));
        predefinedScope.getSymbols().put("writebool", new Symbol("writebool", Symbol.Procedure));
        predefinedScope.getSymbols().put("writeln", new Symbol("writeln", Symbol.Procedure));
        
        predefinedScope.getSymbols().put("readint", new Symbol("readint", Symbol.Procedure));
        
        scopes.add(predefinedScope);
        
    }
   

    @Override
    public void openScope(boolean isGlobal) {        
        useGlobalScope = isGlobal;
        
        if(!useGlobalScope) {
            debugOut("add scope " + scopes.size());
            scopes.add(new Scope());
        } else {
            
            if(globalScopeIdx == -1) {
                globalScopeIdx = scopes.size();
                scopes.add(new Scope());                
            }
            
            debugOut("open global scope");
        }
    }

    @Override
    public void closeScope() {        
        debugOut("close scope " + (scopes.size()-1));        
        scopes.remove(scopes.size()-1);            
        
        if (scopes.size() == globalScopeIdx) globalScopeIdx = -1;        
    }

    @Override
    public void addSymbol(ISymbol s) throws YAPLException {        
        if (s == null || s.getName() == null || s.getName().length() < 1) 
            throw new YAPLException(YAPLException.Internal);
                
        debugOut("adding symbol " + s.getName());
        
        Scope curScope = getCurrentScope();
        
        if(curScope.getSymbols().containsKey(s.getName())) 
            throw new SymbolAlreadyDeclaredException(curScope.getSymbols().get(s.getName()), s);
        
        curScope.getSymbols().put(s.getName(), s);
    }

    @Override
    public ISymbol lookup(Token token) throws YAPLException {
        debugOut("looking up symbol: " + token.image);
        if (token == null || token.image == null) throw new YAPLException(YAPLException.Internal);
        
        for (int i = scopes.size()-1; i >= 0; i--) {
                        
            if (scopes.get(i).getSymbols().containsKey(token.image)) {
                debugOut("Symbol found in scope " + i);
                return scopes.get(i).getSymbols().get(token.image);   
            }                
        }
        
        debugOut("Symol not found");
        throw new IdentifierNotDeclaredException(token);
    }

    @Override
    public void setParentSymbol(ISymbol sym) {
        debugOut("Set parent Symbol " + sym.getName());
        getCurrentScope().setParent(sym);
    }

    @Override
    public ISymbol getNearestParentSymbol(int kind) {
        for (int i = scopes.size() - 1; i >= 0; i--) {            
            ISymbol sym = scopes.get(i).getParent();
            debugOut(sym.getName() + " of kind: " + sym.getKind() + " " + sym.getKindString());            
            if(sym.getKind() == kind) return sym;
        }
        return null;
    }

    @Override
    public void setDebug(boolean on) {
        debugOut("debug set to off");
        debug = on;
    }
    
    public Scope getCurrentScope() {
        int idx = useGlobalScope ? globalScopeIdx : scopes.size() -1;
        return scopes.get(idx);
    }
    
    
    public void debugOut(String message) {
        if(debug)
            System.out.println(message);
    }
}
