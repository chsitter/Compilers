/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca3.yapl.exceptions;

import ca3.yapl.parser.Token;

/**
 *
 * @author chsitter
 */
public class ProgramNameMismatchException extends YAPLException {
    
    public ProgramNameMismatchException(Token startIdent, Token endIdent) {
        super(EndIdentMismatch, "End " + endIdent.image + " does not match Program " + startIdent.image, endIdent.beginLine, endIdent.beginColumn);
    }
}
