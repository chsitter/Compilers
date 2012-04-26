package ca1.yapl.test.backend;

import java.io.FileOutputStream;
import java.io.PrintStream;
import ca1.yapl.impl.BackendMIPS;
import ca1.yapl.interfaces.BackendAsmRM;

/**
 *
 * @author rtaupe
 */
public class TestEverything {
    private static BackendAsmRM backend;
    
    public static void main(String[] args) throws Exception {
        PrintStream out = (args.length > 0) 
        		? new PrintStream(new FileOutputStream(args[0])) : System.out;
        backend = new BackendMIPS(out);
        int string = backend.allocStringConstant("Hallo Welt!");
        int staticBytes = backend.allocStaticData(4, "statische 4 bytes");
        
        //function:
        backend.enterProc("function1", 2);      //write function Epilogue
        byte array = backend.allocReg();        
        backend.allocArray(array, 2, 2);        
        byte arg1 = backend.allocReg();
        byte arg2 = backend.allocReg();
        backend.loadWord(arg1, backend.paramOffset(1), false);  //load arg1
        backend.loadWord(arg2, backend.paramOffset(2), false);  //load arg2
        backend.storeArrayElement(arg1, array, (byte) 0, (byte) 0);
        backend.storeArrayElement(arg2, array, (byte) 0, (byte) 1);
        
        byte tmp = backend.allocReg();
        backend.mul(tmp, arg1, arg2);       
        backend.storeArrayElement(tmp, array, (byte) 1, (byte) 0);
        backend.loadArrayElement(tmp, array, (byte) 1, (byte) 0);
        backend.add(tmp, tmp, arg1);
        backend.storeArrayElement(tmp, array, (byte) 1, (byte) 1);
        
        backend.freeReg(tmp);
        backend.freeReg(arg1);
        backend.freeReg(arg2);
        
        backend.add((byte)2 /*v0*/, array, backend.zeroReg());
        backend.freeReg(array);        
                
        
        backend.exitProc("exit_function1");
        
        // main program
        backend.enterMain();
        
        backend.writeString(string);
        
        byte reg = backend.allocReg();
        backend.loadConst(reg, 12356);
        backend.prepareProcCall(1);
        backend.passArg(0, reg);
        backend.callProc((byte)-1, BackendMIPS.PRINT_INT);
        backend.returnFromProc("exit_" + BackendMIPS.PRINT_INT, (byte)-1);
        backend.freeReg(reg);
        
        backend.storeWord(reg, staticBytes, true);
        
        //call function:
        backend.comment("jetz schauma mol genau");
        byte p1 = backend.allocReg();
        byte p2 = backend.allocReg();
        byte ret = backend.allocReg();
        backend.loadConst(p1, 5);
        backend.loadConst(p2, 7);
        backend.prepareProcCall(2);
        backend.passArg(0, p1);        
        backend.passArg(1, p2);
        backend.callProc(ret, "function1");
        backend.returnFromProc("exit_function1", ret);
        byte arrEl = backend.allocReg();
        backend.loadArrayElement(arrEl, ret, (byte)1, (byte)0);
        
        
        backend.prepareProcCall(1);
        backend.passArg(0, arrEl);
        backend.callProc((byte)-1, BackendMIPS.PRINT_INT);
        backend.returnFromProc("exit_" + BackendMIPS.PRINT_INT, (byte)-1);
        
        backend.loadArrayElement(arrEl, ret, (byte)1, (byte)1);        
        
        
        backend.prepareProcCall(1);
        backend.passArg(0, arrEl);
        backend.callProc((byte)-1, BackendMIPS.PRINT_INT);
        backend.returnFromProc("exit_" + BackendMIPS.PRINT_INT, (byte)-1);
        
        backend.freeReg(arrEl);
        backend.freeReg(p1);
        backend.freeReg(p2);
        backend.freeReg(ret);
              
        
        backend.exitMain("main_end");
    }
}
