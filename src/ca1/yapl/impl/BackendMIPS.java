package ca1.yapl.impl;

import java.io.PrintStream;
import java.util.ArrayList;
import ca1.yapl.interfaces.BackendAsmRM;

/**
 *
 * @author rtaupe
 */
public class BackendMIPS implements BackendAsmRM {
    private static final byte WORD_SIZE = 4;
    private static final byte ZERO_REG = 0;
    private static final int TRUE = 1;
    private static final int FALSE = 0;
    private static final String SEG_TEXT = ".text";
    private static final String SEG_DATA = ".data";
    private static final String LABEL_HEAP = "heap";
    private static final byte REG_FROM = 8;
    private static final byte REG_TO = 25;
    
    private static final byte REG_STACK_SEPARATOR = -1;
    
    public final static String PRINT_INT = "printInt";
    public final static String PRINT_STR = "printString";
    
    
    private String currSegType = null;
    private int currHeapOffset = 0;
    private ArrayList<Byte> freeRegisters = new ArrayList<Byte>(18);
    private ArrayList<Byte> storedRegsStack = new ArrayList<Byte>();
    private int curProcNumArgs = -1;
    private int curProcArgsPassed = -1;
    
    private PrintStream out;
    
    public BackendMIPS(PrintStream out) {
        this.out = out;
        for (byte i = REG_FROM; i <= REG_TO; i++) {
            freeRegisters.add(i);
        }
        
        writePredefProcedures();
               
    }
    
    private void pushRegsStack(byte reg) {
        if (reg == REG_STACK_SEPARATOR || (reg >= REG_FROM && reg <= REG_TO)) {
            storedRegsStack.add(0, reg);
        } else {
            throw new IllegalArgumentException("Parameter reg must be -1 or between " + REG_FROM + " and " + REG_TO);
        }             
    }
    
    private byte popRegsStack() {
        if(storedRegsStack.isEmpty()) throw new IllegalStateException("Stack is empty!");
        return storedRegsStack.remove(0);                
    }
    
    
    @Override
    public byte wordSize() {
        return WORD_SIZE;
    }

    @Override
    public int boolValue(boolean value) {
        return value ? TRUE : FALSE;
    }
    

    @Override
    public byte allocReg() {
        if (freeRegisters.isEmpty())
            return -1;
        else
            return freeRegisters.remove(0);
    }
    
    private byte allocReg(byte reg) {
        if(freeRegisters.remove(new Byte(reg))) {
            return reg;
        } else {
            throw new IllegalArgumentException("register not available - check your Code");
        }
        
    }

    @Override
    public void freeReg(byte reg) {
        if (!freeRegisters.contains(reg))
            freeRegisters.add(reg);
    }

    @Override
    public byte zeroReg() {
        return ZERO_REG;
    }

    @Override
    public void comment(String comment) {
        out.println("#" + comment);
    }

    @Override
    public void emitLabel(String label, String comment) {
        out.println(label + ":" + " #" + comment);
    }

    @Override
    public int allocStaticData(int bytes, String comment) {
        setSegType(SEG_DATA);
        checkFirstDataSeg();
        out.println(".align 2");
        out.print(".space " + bytes);
        comment(comment);
        int oldOffset = currHeapOffset;
        currHeapOffset += bytes;
        return oldOffset;
    }

    @Override
    public int allocStringConstant(String string) {
        setSegType(SEG_DATA);
        checkFirstDataSeg();
        out.println(".asciiz \"" + string + "\"");
        int oldOffset = currHeapOffset;
        currHeapOffset += (string.length() + 1);    //+ 1 because of terminating 0
        return oldOffset;
    }
    
    private void checkFirstDataSeg() {
        if (currHeapOffset == 0)
            emitLabel(LABEL_HEAP, "begin of heap");
    }

    @Override
    public int allocStack(int bytes, String comment) {
        setSegType(SEG_TEXT);
        out.print("subi $sp, $sp, " + bytes + "\t");
        comment(comment);
        //TODO: what to return???
        return 0;
    }

    @Override
    public void storeArrayDim(int dim, byte lenReg) {
        //TODO: wegschmeissen
        comment("storeArrayDim: not implemented");
    }

    @Override
    public void allocArray(byte destReg, int lenDim1, int lenDim2) {
        setSegType(SEG_TEXT);
        out.println("li $a0, " + lenDim1);
        out.println("mul   $a0, $a0, " + lenDim2);
        out.print("addi   $a0, $a0, 2");
        comment("$a0 = n * m + 2");
        out.println("li $v0, 9");
        comment("sbrk");
        out.println("syscall");
        byte tmp = allocReg();
        out.println("li $" + tmp + ", " + lenDim1);
        storeWordReg(tmp, (byte) 2 /* v0 */);
        out.println("li $" + tmp + ", " + lenDim2);
        storeWordReg(tmp, (byte) 2 /* v0 */, wordSize());
        out.print("move   $" + destReg + ", $v0");
        freeReg(tmp);
        comment("move start address of array to destReg");
    }

    @Override
    public void loadConst(byte reg, int value) {
        setSegType(SEG_TEXT);
        out.println("li $" + reg + ", " + value);
    }

    @Override
    public void loadAddress(byte reg, int addr, boolean isStatic) {
        setSegType(SEG_TEXT);
        if (isStatic) {
            out.println("la $" + reg + ", " + LABEL_HEAP + "    # load heap base address");
            out.println("addi   $" + reg + ", $" + reg + ", " + addr + "  # add offset");
        } else {
            out.println("addi $" + reg + ", " + "$fp, " + addr + "  # get addr relative to fp");
        }
    }

    @Override
    public void loadWord(byte reg, int addr, boolean isStatic) {
        setSegType(SEG_TEXT);
        loadAddress(reg, addr, isStatic);
        loadWordReg(reg, reg);
    }

    @Override
    public void storeWord(byte reg, int addr, boolean isStatic) {
        setSegType(SEG_TEXT);
        loadAddress(reg, addr, isStatic);
        storeWordReg(reg, reg);
    }

    @Override
    public void loadWordReg(byte reg, byte addrReg) {
        setSegType(SEG_TEXT);
        out.println("lw $" + reg + ", ($" + addrReg + ")");
    }

    @Override
    public void storeWordReg(byte reg, byte addrReg) {
        setSegType(SEG_TEXT);
        out.println("sw $" + reg + ", ($" + addrReg + ")");      
    }

    @Override
    public void loadWordReg(byte reg, byte addrReg, int offset) {
        setSegType(SEG_TEXT);
        out.println("lw $" + reg + ", " + offset + "($" + addrReg + ")");
    }

    @Override
    public void storeWordReg(byte reg, byte addrReg, int offset) {
        setSegType(SEG_TEXT);
        out.println("sw $" + reg + ", " + offset + "($" + addrReg + ")");      
    }

    @Override
    public void loadArrayElement(byte dest, byte baseAddr, byte index) {
        setSegType(SEG_TEXT);
        if (index < 0)
            throw new IllegalArgumentException("index must be >= 0");
        
        loadWordReg(dest, baseAddr, (index + 2) * wordSize());
    }

    @Override
    public void storeArrayElement(byte src, byte baseAddr, byte index) {
        setSegType(SEG_TEXT);
        if (index < 0)
            throw new IllegalArgumentException("index must be >= 0");
        
        storeWordReg(src, baseAddr, (index + 2) * wordSize());
    }

    @Override
    public void loadArrayElement(byte dest, byte baseAddr, byte indexDim1, byte indexDim2) {
        setSegType(SEG_TEXT);
        if (indexDim1 < 0 || indexDim2 < 0)
            throw new IllegalArgumentException("index must be >= 0");
        
        comment("calc address of element:");
        loadWordReg(dest, baseAddr, wordSize());
        out.println("mul   $" + dest + ", $" + dest + ", " + indexDim1 * wordSize());
        out.println("addi   $" + dest + ", $" + dest + ", " + indexDim2 * wordSize());
        add(dest, dest, baseAddr);
        out.println("addi   $" + dest + ", $" + dest + ", " + 2 * wordSize());
        loadWordReg(dest, dest);
    }

    @Override
    public void storeArrayElement(byte src, byte baseAddr, byte indexDim1, byte indexDim2) {
        setSegType(SEG_TEXT);
        if (indexDim1 < 0 || indexDim2 < 0)
            throw new IllegalArgumentException("index must be >= 0");
        
        comment("calc address of element:");
        byte tmp = allocReg();
        loadWordReg(tmp, baseAddr, wordSize());
        out.println("mul   $" + tmp + ", $" + tmp + ", " + indexDim1 * wordSize());
        out.println("addi   $" + tmp + ", $" + tmp + ", " + indexDim2 * wordSize());
        add(tmp, tmp, baseAddr);
        out.println("addi   $" + tmp + ", $" + tmp + ", " + 2 * wordSize()); //add offset 8 because 2 words are used for internal representation
        storeWordReg(src, tmp);
        freeReg(tmp);
    }

    @Override
    public void arrayLength(byte dest, byte baseAddr) {
        setSegType(SEG_TEXT);
        loadWordReg(dest, baseAddr);
    }

    @Override
    public void writeString(int addr) {
        setSegType(SEG_TEXT);
        byte reg = allocReg();
        loadAddress(reg, addr, true);
        prepareProcCall(1);
        passArg(0, reg);
        callProc((byte)-1, BackendMIPS.PRINT_STR);
        returnFromProc("exit_" + BackendMIPS.PRINT_STR, (byte)-1);
        freeReg(reg);        
    }

    @Override
    public void neg(byte regDest, byte regX) {
        setSegType(SEG_TEXT);
        sub(regDest, zeroReg(), regX);
    }

    @Override
    public void add(byte regDest, byte regX, byte regY) {
        setSegType(SEG_TEXT);
        out.println("add    $" + regDest + ", $" + regX + ", $" + regY);
    }

    @Override
    public void sub(byte regDest, byte regX, byte regY) {
        setSegType(SEG_TEXT);
        out.println("sub    $" + regDest + ", $" + regX + ", $" + regY);
    }

    @Override
    public void mul(byte regDest, byte regX, byte regY) {
        setSegType(SEG_TEXT);
        out.println("mult    $" + regX + ", $" + regY);
        out.println("mflo   $" + regDest);
    }

    @Override
    public void div(byte regDest, byte regX, byte regY) {
        setSegType(SEG_TEXT);
        out.println("div    $" + regX + ", $" + regY);
        out.println("mflo   $" + regDest);
    }

    @Override
    public void mod(byte regDest, byte regX, byte regY) {
        setSegType(SEG_TEXT);
        out.println("div    $" + regX + ", $" + regY);
        out.println("mfhi   $" + regDest);
    }

    @Override
    public void isLess(byte regDest, byte regX, byte regY) {
        setSegType(SEG_TEXT);
        out.println("slt    $" + regDest + ", $" + regX + ", $" + regY);
    }

    @Override
    public void isLessOrEqual(byte regDest, byte regX, byte regY) {
        setSegType(SEG_TEXT);
        out.println("sle    $" + regDest + ", $" + regX + ", $" + regY);
    }

    @Override
    public void isEqual(byte regDest, byte regX, byte regY) {
        setSegType(SEG_TEXT);
        isLessOrEqual(regDest, regX, regY);
        byte tmp = allocReg();
        isLessOrEqual(tmp, regY, regX);
        and(regDest, regDest, tmp);
        freeReg(tmp);
    }

    @Override
    public void not(byte regDest, byte regSrc) {
        setSegType(SEG_TEXT);
        out.println("not    $" + regDest + ", $" + regSrc);
    }

    @Override
    public void and(byte regDest, byte regX, byte regY) {
        setSegType(SEG_TEXT);
        out.println("and    $" + regDest + ", $" + regX + ", $" + regY);
    }

    @Override
    public void or(byte regDest, byte regX, byte regY) {
        setSegType(SEG_TEXT);
        out.println("or    $" + regDest + ", $" + regX + ", $" + regY);
    }

    @Override
    public void branchIf(byte reg, boolean value, String label) {
        setSegType(SEG_TEXT);
        if (value)
            out.println("bnez   $" + reg + ", " + label);
        else
            out.println("beqz   $" + reg + ", " + label);
    }

    @Override
    public void jump(String label) {
        setSegType(SEG_TEXT);
        out.println("j  " + label);        
    }

    @Override
    public void enterMain() {
        setSegType(SEG_TEXT);
        out.println(".globl main");        
        emitLabel("main", "main procedure");
    }

    @Override
    public void exitMain(String label) {
        setSegType(SEG_TEXT);
        emitLabel(label, "exit main");
        out.println("li $v0, 10 # exit");
        out.println("syscall");
    }

    @Override
    public void enterProc(String label, int nParams) {
        setSegType(SEG_TEXT);
        emitLabel(label, "procedure declaration");
        out.println("addi   $sp, $sp, -8    # make space for $fp and $ra");
        out.println("sw     $fp, 4($sp)     # save frame pointer");
        out.println("sw     $ra, 0($sp)     # save return address");
        out.println("addi   $fp, $sp, 4     # fp points to first word in stack frame");
    }

    @Override
    public void exitProc(String label) {
        setSegType(SEG_TEXT);
        emitLabel(label, "procedure epilogue");
        out.print("addi   $sp, $fp, " + wordSize());
        comment("free stack space of curr proc");
        out.println("lw     $ra, -4($fp)    # restore return adddress");
	out.println("lw     $fp, 0($fp)     # restore frame pointer");
	out.println("jr     $ra             # return");
    }

    @Override
    public void returnFromProc(String label, byte reg) {
        setSegType(SEG_TEXT);
        // restore saved registers:
        int numAlreadyRestored = 0;
        int numToRestore = storedRegsStack.indexOf((byte)-1);
        byte regToRestore;
        while ((regToRestore = popRegsStack()) != -1) {
            if (reg == (byte)-1 || regToRestore != reg) {
                out.println("lw $" + regToRestore + ", " + paramOffset(numToRestore - numAlreadyRestored - 1) + "($sp) # restore $" + regToRestore + " from stack");
                    
            }
            allocReg(regToRestore);
            numAlreadyRestored++;
        }
        
        out.println("addi   $sp, $sp, " + numAlreadyRestored * wordSize() + "   # free room on stack for caller saved");        
    }

    @Override
    public void prepareProcCall(int numArgs) {
        
        if(curProcNumArgs != -1 || curProcArgsPassed != -1) throw  new IllegalStateException("something went wrong on passing arguments");
        
        setSegType(SEG_TEXT);        
        
        //save all caller-saved variables on the stack (s0 - s7, t0 - t9)
        int numAlreadySaved = 0;
        int numAllocated = REG_TO - REG_FROM + 1 - freeRegisters.size();
        out.println("subi   $sp, $sp, " + numAllocated * wordSize() + "   # make room on stack for caller saved");
        
        pushRegsStack(REG_STACK_SEPARATOR);
        for (byte i = REG_FROM; i <= REG_TO; i++) {
            if (!freeRegisters.contains(i)){
                out.println("sw $" + i + ", " + paramOffset(numAlreadySaved) + "($sp) # save $" + i + " on stack");
                numAlreadySaved++;
                pushRegsStack(i);
                freeReg(i);
            }
        }        
        
        curProcNumArgs = numArgs;     
        curProcArgsPassed = 0;
        out.println("subi   $sp, $sp, " + (numArgs * wordSize()) + " # make room on stack for arguments");
    }

    @Override
    public void passArg(int arg, byte reg) {        
        if(curProcNumArgs - curProcArgsPassed > 0) {
            setSegType(SEG_TEXT);
            out.println("sw $" + reg + ", " + paramOffset(arg) + "($sp) # store arg on stack");
            curProcArgsPassed++;
        } else {
            throw new IllegalStateException("too many arguments passed");            
        }
    }

    @Override
    public void callProc(byte reg, String name) {
        //Exception handling because of the necessity to free the allocated stack after procedure returns
        if(curProcNumArgs != curProcArgsPassed) 
            throw new IllegalStateException("prepareProcCall not called or wrong number of arguments passed!");
        
        setSegType(SEG_TEXT);                       
        out.println("jal " + name);
        if (reg != -1)
            out.println("move $" + reg + ", $v0 # copy return value");
               
        out.println("addi   $sp,    $sp,    " + curProcNumArgs * wordSize() + "   # free room for arguments on stack");
        curProcNumArgs = -1;        
        curProcArgsPassed = -1;
    }

    @Override
    public int paramOffset(int index) {
        return index * wordSize();
    }

    private void writePredefProcedures() {
        writePrintString();
        writePrintInt();
    }
    
    /**
     * Initializes a segment if the new segment type does not equal the old one
     * @param segType 
     */
    private void setSegType(String segType) {                
        if (!segType.equals(currSegType)) {            
            out.println(segType);
            if(segType == SEG_TEXT && currSegType == null)
                out.println("j main");              
            currSegType = segType;            
        }
    }

    private void writePrintString() {
        setSegType(SEG_TEXT);
        enterProc(BackendMIPS.PRINT_STR, 1);
        out.println("lw $a0, 4($fp) # a0 = start address of string");
        out.println("li $v0, 4      # print_string");
        out.println("syscall");
        exitProc("exit_" + BackendMIPS.PRINT_STR);
    }

    /**
     * The integer to be printed out must be passed as the first (and only) Parameter on the Stack
     */
    private void writePrintInt() {
        setSegType(SEG_TEXT);
        enterProc(BackendMIPS.PRINT_INT, 1);
        out.println("lw $a0, 4($fp) # a0 = value");
        out.println("li $v0, 1      # print_int");
        out.println("syscall");
        exitProc("exit_" + BackendMIPS.PRINT_INT);
    }
    
}
