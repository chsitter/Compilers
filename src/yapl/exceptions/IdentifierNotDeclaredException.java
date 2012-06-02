/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yapl.exceptions;

import yapl.parser.Token;

/**
 *
 * @author chsitter
 */
public class IdentifierNotDeclaredException extends YAPLException {
 
    public IdentifierNotDeclaredException(Token token) {
        super(IdentNotDecl, "identifier " + token.image + " not declared", token.beginLine, token.beginColumn);
    }
}
