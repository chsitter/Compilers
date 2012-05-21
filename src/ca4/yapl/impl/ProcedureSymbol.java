package ca4.yapl.impl;

import ca4.yapl.interfaces.ISymbol;
import ca4.yapl.parser.Token;
import java.util.ArrayList;

/**
 *
 * @author richie
 */
public class ProcedureSymbol extends Symbol {
    private ArrayList<ISymbol> formalParameters = new ArrayList<ISymbol>();
    
    public ProcedureSymbol(String name) {
        super(name, Symbol.Procedure);
    }
    
    public ProcedureSymbol(Token token) {
        super(token, Symbol.Procedure);
    }
    
    public void addParameter(ISymbol param) {
        formalParameters.add(param);
    }
    
    public int getParametersCount() {
        return formalParameters.size();
    }
    
    public ISymbol getParameter(int i) {
        return formalParameters.get(i);
    }
}
