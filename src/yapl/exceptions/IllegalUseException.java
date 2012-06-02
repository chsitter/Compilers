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
public class IllegalUseException extends YAPLException {
    
    public IllegalUseException(ISymbol originalSymbol, Token illegalToken) {
        super(SymbolIllegalUse, "illegal use of " + originalSymbol.getKindString() + " " + originalSymbol.getName(), illegalToken);
    }
}
