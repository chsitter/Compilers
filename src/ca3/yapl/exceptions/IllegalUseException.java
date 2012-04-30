/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca3.yapl.exceptions;

import ca3.yapl.interfaces.ISymbol;

/**
 *
 * @author chsitter
 */
public class IllegalUseException extends YAPLException {
    
    public IllegalUseException(ISymbol symbol) {
        super(SymbolIllegalUse, "illegal use of " + symbol.getKindString() + " " + symbol.getName(), symbol);
    }
}
