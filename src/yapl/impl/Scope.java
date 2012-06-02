/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yapl.impl;

import yapl.interfaces.ISymbol;
import java.util.HashMap;

/**
 *
 * @author chsitter
 */
public class Scope {
    private ISymbol parent;
    private HashMap<String, ISymbol> symbols;
    
    public Scope() {
        symbols = new HashMap<String, ISymbol>();        
    }
    
    public Scope(ISymbol parent) {
        this();
        this.parent = parent;        
    }

    public ISymbol getParent() {
        return parent;
    }

    public void setParent(ISymbol parent) {
        this.parent = parent;
    }

    public HashMap<String, ISymbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(HashMap<String, ISymbol> symbols) {
        this.symbols = symbols;
    }   
    
}
