package yapl.interfaces;

import java.util.List;
import yapl.exceptions.YAPLException;
import yapl.impl.Symbol;
import yapl.lib.ArrayType;
import yapl.lib.Type;
import yapl.parser.Token;

/**
 * Interface to code generator methods called by the parser. Some of these
 * methods should also implement type checking.
 * <p>
 * An implementation of this interface should not emit assembler or machine code
 * directly, but is expected to virtually "generate" generic 3-address-code by calling
 * methods of an appropriate backend interface only.
 * </p><p>
 * The term <em>register</em> is used for both register and stack machines.
 * In the latter case, a register value refers to an element
 * on the expression stack; register numbers are not needed then, because
 * the order of operands is implied by the stack.
 * </p>
 * 
 * @author Mario Taschwer
 * @version $Id: CodeGen.java 171 2011-05-16 13:31:35Z mt $
 */
public interface ICodeGen {

	/**
	 * Generate a new address label. Labels must be unique.
	 */
	public String newLabel();

	/** Assign an address label to the current code address. */
	public void assignLabel(String label);

	/**
	 * Load the value represented by <code>attr</code> into a register
	 * if <code>attr</code> does not already represent a register value. The
	 * {@link IAttrib#getRegister() register property} of <code>attr</code>
	 * will be set to the register number.
	 * 
	 * @return the register number.
	 * @throws YAPLException
	 *             (NoMoreRegs) if there are no free registers available;
	 *             cannot occur with stack machine backends.
	 * @throws YAPLException
	 *             (Internal) if the {@link IAttrib#getKind() kind} or
	 *             {@link IAttrib#getType() type} properties of <code>attr</code>
	 *             have an unexpected value.
	 */
	public byte loadReg(IAttrib attr) 
	throws YAPLException;

	/**
	 * Release the register used by the register operand <code>attr</code>. 
	 * The operand's {@link IAttrib#getKind() kind} will be set to
	 * {@link IAttrib#None}.
	 * Has no effect with stack machine backends or
	 * if <code>attr</code> does not represent a register operand.
	 */
	public void freeReg(IAttrib attr);

	/**
	 * Allocate space for a memory object (i.e. a variable) at compile time. 
	 * If the symbol belongs to a {@link Symbol#isGlobal() global scope}, space will be
	 * allocated in the global data area; otherwise, space will be allocated 
	 * in the current stack frame.
	 * 
	 * @param sym
	 *            the symbol to allocate space for. The symbol's
	 *            {@link Symbol#getOffset() address offset} will be updated.
	 * @throws YAPLException (Internal)
	 *            if <code>sym</code> does not provide sufficient information
	 *            (data type, scope), etc.
	 */
	public void allocVariable(ISymbol sym) 
	throws YAPLException;
	
	/**
	 * Allocate array at run time.
	 * @param arrayType  array type.
	 * @return           IAttrib object representing a register operand
	 *                   holding the array base address.
	 * @throws YAPLException
	 */
	public IAttrib allocArray(ArrayType arrayType, IAttrib dim1, IAttrib dim2)
	throws YAPLException;
        
        public IAttrib allocArray(ArrayType arrayType, IAttrib dim1)
	throws YAPLException;
	
	/**
	 * Generate code for address offset computation of an array element.
	 * With stack machines supporting array access, this method may simply
	 * push the array base address and the index operand to the expression
	 * stack.
	 * 
	 * @param arr
	 *            the operand representing the array base address; its
	 *            {@link IAttrib#getKind() kind},
	 *            {@link IAttrib#getRegister() register}, and
	 *            {@link IAttrib#getType() type} IAttributes will be updated in
	 *            place to represent the <em>array element</em>,
	 *            see {@link IAttrib#ArrayElement}.
	 * @param index
	 *            the operand (expression) representing the index of the array
	 *            element (starts at 0).
	 * @throws YAPLException
	 *            (Internal) if <code>arr</code> does not represent an array type.
	 */
	public void arrayOffset(IAttrib arr, IAttrib index) 
	throws YAPLException;
//        
//        public byte loadArrayElement(IAttrib arr, IAttrib index);
//        
//        public void storeArrayElement(IAttrib arr, IAttrib index, IAttrib src);

	/**
	 * Generate code for array length computation at run time.
	 * @param arr   operand representing the array.
	 * @return      the object referenced by <code>arr</code>, updated to represent
	 *              the number of elements of the first dimension of <code>arr</code>.
	 * @throws YAPLException
	 */
	public IAttrib arrayLength(IAttrib arr)
	throws YAPLException;
	
	/**
	 * Generate code for variable assignment.
	 * Releases any registers occupied by LHS and RHS expressions.
	 * 
	 * @param lvalue
	 *            left-hand side value of assignment (target).
	 * @param expr
	 *            right-hand side value of assignment (source).
	 * @throws YAPLException
	 *             (Internal) if <code>lvalue</code> has an illegal
	 *             {@link IAttrib#getKind() kind property}.
	 */
	public void assign(IAttrib lvalue, IAttrib expr) 
	throws YAPLException;

	/**
	 * Check types and generate code for binary operation
	 * <code>x = x op y</code>. <code>x</code> will be updated in place to
	 * represent the result. If y occupies a register, it will be released.
	 * 
	 * @param x
	 *            the left operand.
	 * @param op
	 *            the operator symbol.
	 * @param y
	 *            the right operand.
	 * @return the object referenced by <code>x</code>.
	 * @throws YAPLException
	 *             (Internal) if the operator symbol is not a valid binary
	 *             operator.
	 * @throws YAPLException
	 *             (IllegalOp2Type) if the operator cannot be applied to
	 *             the given operand types.
	 */
	public IAttrib op2(IAttrib x, Token op, IAttrib y) 
	throws YAPLException;

	/**
	 * Check types and generate code for relational operation
	 * <code>x op y</code>. The result is represented by <code>x</code>,
	 * which is updated in place. Its type is set to Boolean.
	 * If y occupies a register, it will be released.
	 * 
	 * @param x
	 *            the left operand.
	 * @param op
	 *            the operator symbol.
	 * @param y
	 *            the right operand.
	 * @return the object referenced by <code>x</code>.
	 * @throws YAPLException
	 *             (Internal) if the operator symbol is not a valid relational
	 *             operator.
	 * @throws YAPLException
	 *             (IllegalRelOpType) if the operator cannot be applied to
	 *             the given operand types.
	 */
	public IAttrib relOp(IAttrib x, Token op, IAttrib y) 
	throws YAPLException;

	/**
	 * Enter procedure. Generate the procedure's prolog (setup stack frame).
	 */
	public void enterMain() 
	throws YAPLException;

	/**
	 * Exit procedure. Generate the procedure's epilog (release stack frame).
	 */
	public void exitMain() 
	throws YAPLException;
        
        public void exitProc(String procName, IAttrib retAttr) throws YAPLException;

	/**
	 * Generate code for writing a string constant to standard output.
	 * 
	 * @param string
	 *            string to be written, enclosed in double quotes.
	 */
	public void writeString(String string) 
	throws YAPLException;
	
	/**
	 * Generate code for writing a integer constant to standard output.
	 * @param atr
	 * @throws YAPLException
	 */
	public void writeInt(IAttrib atr) 
	throws YAPLException;
	
	/**
	 * Generate code jumping to <code>label</code> if
	 * <code>condition</code> is <code>false</code>.
	 */
	public void branchIfFalse(IAttrib condition, String label)
	throws YAPLException;

	/** Generate code unconditionally jumping to <code>label</code>. */
	public void jump(String label);

        public byte callProc(String procName, List<IAttrib> arguments);
        
        public void neg(IAttrib attrib) throws YAPLException;
}
