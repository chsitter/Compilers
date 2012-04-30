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
public class SymbolAlreadyDeclaredException extends YAPLException {
    public SymbolAlreadyDeclaredException(ISymbol symbol) {
        super(SymbolExists, "symbol " + symbol.getName() + " already declared in current scope (as " + symbol.getKindString() + ")", symbol);
    }
}
