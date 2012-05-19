/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca4.yapl.exceptions;

import ca4.yapl.interfaces.ISymbol;
import ca4.yapl.parser.Token;

/**
 *
 * @author chsitter
 */
public class SymbolAlreadyDeclaredException extends YAPLException {
    public SymbolAlreadyDeclaredException(ISymbol originalSymbol, ISymbol illegalSymbol) {
        super(SymbolExists, "symbol " + originalSymbol.getName() + " already declared in current scope (as " + originalSymbol.getKindString() + ")", illegalSymbol);
    }
}
