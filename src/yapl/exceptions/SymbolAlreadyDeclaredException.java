/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yapl.exceptions;

import yapl.interfaces.ISymbol;
import yapl.parser.Token;

/**
 *
 * @author chsitter
 */
public class SymbolAlreadyDeclaredException extends YAPLException {
    public SymbolAlreadyDeclaredException(ISymbol originalSymbol, ISymbol illegalSymbol) {
        super(SymbolExists, "symbol " + originalSymbol.getName() + " already declared in current scope (as " + originalSymbol.getKindString() + ")", illegalSymbol);
    }
}
