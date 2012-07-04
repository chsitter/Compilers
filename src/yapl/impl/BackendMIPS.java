package yapl.impl;

import java.io.PrintStream;
import java.util.ArrayList;
import yapl.interfaces.IBackendMIPS;

/**
 *
 * @author rtaupe
 */
public class BackendMIPS implements IBackendMIPS {
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
    
    public final static String PRINT_INT = "writeint";
    public final static String PRINT_STR = "printString";
    public final static String PRINT_LN = "writeln";
    public final static String PRINT_BOOL = "writebool";
    
    public final static String LBL_NEWLINE = "newLine";
    public final static String LBL_TRUE = "True";
    public final static String LBL_FALSE = "False";
    
    
    private String currSegType = null;
    private int currHeapOffset = 0;
    private ArrayList<Byte> freeRegisters = new ArrayList<Byte>(18);
    private ArrayList<Byte> storedRegsStack = new ArrayList<Byte>();
    private int curProcNumArgs = -1;
    private int curProcArgsPassed = -1;
    private int currStackOffset = 0;
    
    private static int labelCount = 0;
    
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
    
    public int getNextLabelNumber() {
        return labelCount++;
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
            freeRegisters.add(0, reg);
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
        //int oldOffset = currStackOffset;
        
        out.print("subi $sp, $sp, " + bytes + "\t");
        comment(comment);        
        currStackOffset -= bytes;
        
        return currStackOffset;
    }
    
    public void allocArray(byte destReg, byte regDim1) {
        allocArray(destReg, regDim1, (byte)-1);
    }

    @Override
    public void allocArray(byte destReg, byte regDim1, byte regDim2) {
        setSegType(SEG_TEXT);        
        out.println("move $a0, $" + regDim1);
        out.print("addi $a0, $a0, 1");
        comment("alloc space for length information");
        byte wordSize = allocReg();
        out.println("li $" + wordSize + ", " + wordSize());
        mul((byte)4 /*a0*/, (byte)4, wordSize);
        out.print("li $v0, 9");
        comment("issue sbrk");
        out.print("syscall");
        comment("alloc space for 1st array dim");
        out.print("move   $" + destReg + ", $v0");
        comment("move start address of array to destReg");                
        out.println("sw $" + regDim1 + ", ($" + destReg + ")");
        
        //if regDim2 != -1       
        if(regDim2 != (byte)-1) {
            byte t1 = allocReg();
            byte t2 = allocReg();            
            out.println("move $" + t1 + ", $" + regDim1);
            out.print("add $" + t2 + ", $a0, $" + destReg);
            comment("compute addr. of dim1 elem");
            int lNr = getNextLabelNumber();
            
            out.println("move $a0, $" + regDim2);
            out.println("addi $a0, $a0, 1");
            mul((byte)4 /*a0*/, (byte)4, wordSize);
            emitLabel("allocArrayWhile_" + lNr, "");
            out.println("blez $" + t1 + ", endAllocArray_" + lNr);
            out.print("li $v0, 9");
            out.print("sycall");
            comment("sbrk");            
            
            out.println("subi $" + t2 + ", " + wordSize());
            out.println("sw $v0, ($" + t2 + ")");
            out.println("sw $" + regDim2 + ", ($v0)");
            
            out.println("subi, $" + t1 + ", $" + t1 + ", 1");
            jump("allocArrayWhile_" + lNr);
            emitLabel("endAllocArray_" + lNr, "");
            freeReg(t1);
            freeReg(t2);
        }
        freeReg(wordSize);
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
        byte tmp = allocReg();
        loadAddress(tmp, addr, isStatic);
        storeWordReg(reg, tmp);
        freeReg(tmp);
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
        
        arrayOffset(baseAddr, baseAddr, index);
        loadWordReg(dest, baseAddr);
    }

    @Override
    public void storeArrayElement(byte src, byte baseAddr, byte index) {
        setSegType(SEG_TEXT);                
        arrayOffset(baseAddr, baseAddr, index);    
        storeWordReg(src, baseAddr);        
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
        byte tmp = allocReg();
        loadConst(tmp, TRUE);
        sub(regDest, tmp, regSrc);
        comment("logical not");
        freeReg(tmp);
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
        //out.println("addi   $fp, $sp, 4     # fp points to first word in stack frame");        
        out.println("move   $fp, $sp        # fp points to old $sp");
        
        currStackOffset = 0;
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
        //out.println("addi   $fp, $sp, 4     # fp points to first word in stack frame");
        out.println("move   $fp, $sp        # fp points to old $sp");
        
        currStackOffset = 0;
    }

    @Override
    public void exitProc(String label) {
        setSegType(SEG_TEXT);
        emitLabel(label, "procedure epilogue");
        out.print("addi     $sp, $fp, " + 2 * wordSize());
        comment("free stack space of curr proc");
        out.println("lw     $ra, 0($fp)     # restore return adddress");
	out.println("lw     $fp, 4($fp)     # restore frame pointer");
	out.println("jr     $ra             # return");
    }

    @Override
    public void returnFromProc(String label, byte reg) {
        setSegType(SEG_TEXT);
        if (reg != -1)
            out.println("move   $v0, $" + reg + "    # copy return value to v0");
        jump(label);
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
        writePrintLn();
        writePrintBool();
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
        out.println("lw $a0, 8($fp) # a0 = start address of string");
        out.println("li $v0, 4      # print_string");
        out.println("syscall");
        exitProc("exit_" + BackendMIPS.PRINT_STR);
    }
    
    private void writePrintLn() {
        setSegType(SEG_DATA);
        out.println(LBL_NEWLINE + ": .asciiz  \"\\n\"");
        
        setSegType(SEG_TEXT);
        enterProc(BackendMIPS.PRINT_LN, 1);
        out.println("la $a0, " + LBL_NEWLINE + " #load newline character");
        out.println("li $v0, 4      # print_string");
        out.println("syscall");
        exitProc("exit_" + BackendMIPS.PRINT_LN);
    }

    /**
     * The integer to be printed out must be passed as the first (and only) Parameter on the Stack
     */
    private void writePrintInt() {
        setSegType(SEG_TEXT);
        enterProc(BackendMIPS.PRINT_INT, 1);
        out.println("lw $a0, 8($fp) # a0 = value");
        out.println("li $v0, 1      # print_int");
        out.println("syscall");
        exitProc("exit_" + BackendMIPS.PRINT_INT);
    }
    
    private void writePrintBool() {
        setSegType(SEG_DATA);
        out.println(LBL_TRUE + ": .asciiz  \"True\"");
        out.println(LBL_FALSE + ": .asciiz  \"False\"");
        
        setSegType(SEG_TEXT);
        enterProc(BackendMIPS.PRINT_BOOL, 1);
        out.println("la $a0, " + LBL_TRUE);     //load true into $a0
                
        byte reg = allocReg();
        byte reg1 = allocReg();
        out.println("lw $" + reg + ", 8($fp)    # load boolean argument");
        out.println("li $" + reg1 + ", " + TRUE);
        out.println("beq $" + reg + ", $" + reg1 + ", printBoolSkip");
        out.println("la $a0, " + LBL_FALSE);
        emitLabel("printBoolSkip", "skip load 'False' String in case of True");        
        out.println("li $v0, 4      # print_string");
        out.println("syscall");
        
        freeReg(reg);
        freeReg(reg1);
        exitProc("exit_" + BackendMIPS.PRINT_BOOL);
    }

    @Override
    public void arrayOffset(byte destReg, byte baseReg, byte idxReg) {
        byte tmp = allocReg();
        out.println("addi   $" + idxReg + ", $" + idxReg + ", 1");
        loadConst(tmp, wordSize());
        mul(idxReg, idxReg, tmp);
        add(destReg, baseReg, idxReg);        
        freeReg(tmp);
    }    
}
