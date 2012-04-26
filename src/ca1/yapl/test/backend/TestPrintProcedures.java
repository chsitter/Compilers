package ca1.yapl.test.backend;

import java.io.FileOutputStream;
import java.io.PrintStream;
import ca1.yapl.impl.BackendMIPS;
import ca1.yapl.interfaces.BackendAsmRM;

/**
 *
 * @author rtaupe
 */
public class TestPrintProcedures {
    private static BackendAsmRM backend;
    
    public static void main(String[] args) throws Exception {
        PrintStream out = (args.length > 0) 
        		? new PrintStream(new FileOutputStream(args[0])) : System.out;
        backend = new BackendMIPS(out);
        int wordSize = backend.wordSize();
        int string = backend.allocStringConstant("Hallo Welt!");
        
        // main program
        backend.enterMain();
        
        backend.writeString(string);
        
        
        byte reg = backend.allocReg();
        backend.loadConst(reg, 12356);
        backend.prepareProcCall(1);
        backend.passArg(0, reg);
        backend.callProc((byte)-1, BackendMIPS.PRINT_INT);
        backend.freeReg(reg);
        
        backend.exitMain("main_end");
    }
}
