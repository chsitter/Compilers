package yapl.exceptions;

import yapl.parser.Token;

/**
 *
 * @author chsitter
 */
public class ProcedureNameMismatchException extends YAPLException  {
 
    public ProcedureNameMismatchException(Token startIdent, Token endIdent) {
        super(EndIdentMismatch, "End " + endIdent.image + " does not match Procedure " + startIdent.image, endIdent.beginLine, endIdent.beginColumn);
    }
}
