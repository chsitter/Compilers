.text
j main
printString: #procedure declaration
addi   $sp, $sp, -8    # make space for $fp and $ra
sw     $fp, 4($sp)     # save frame pointer
sw     $ra, 0($sp)     # save return address
move   $fp, $sp        # fp points to old $sp
lw $a0, 8($fp) # a0 = start address of string
li $v0, 4      # print_string
syscall
exit_printString: #procedure epilogue
addi     $sp, $fp, 8#free stack space of curr proc
lw     $ra, 0($fp)     # restore return adddress
lw     $fp, 4($fp)     # restore frame pointer
jr     $ra             # return
writeint: #procedure declaration
addi   $sp, $sp, -8    # make space for $fp and $ra
sw     $fp, 4($sp)     # save frame pointer
sw     $ra, 0($sp)     # save return address
move   $fp, $sp        # fp points to old $sp
lw $a0, 8($fp) # a0 = value
li $v0, 1      # print_int
syscall
exit_writeint: #procedure epilogue
addi     $sp, $fp, 8#free stack space of curr proc
lw     $ra, 0($fp)     # restore return adddress
lw     $fp, 4($fp)     # restore frame pointer
jr     $ra             # return
.data
newLine: .asciiz  "\n"
.text
writeln: #procedure declaration
addi   $sp, $sp, -8    # make space for $fp and $ra
sw     $fp, 4($sp)     # save frame pointer
sw     $ra, 0($sp)     # save return address
move   $fp, $sp        # fp points to old $sp
la $a0, newLine #load newline character
li $v0, 4      # print_string
syscall
exit_writeln: #procedure epilogue
addi     $sp, $fp, 8#free stack space of curr proc
lw     $ra, 0($fp)     # restore return adddress
lw     $fp, 4($fp)     # restore frame pointer
jr     $ra             # return
.data
True: .asciiz  "True"
False: .asciiz  "False"
.text
writebool: #procedure declaration
addi   $sp, $sp, -8    # make space for $fp and $ra
sw     $fp, 4($sp)     # save frame pointer
sw     $ra, 0($sp)     # save return address
move   $fp, $sp        # fp points to old $sp
la $a0, True
lw $8, 8($fp)    # load boolean argument
li $9, 1
beq $8, $9, printBoolSkip
la $a0, False
printBoolSkip: #skip load 'False' String in case of True
li $v0, 4      # print_string
syscall
exit_writebool: #procedure epilogue
addi     $sp, $fp, 8#free stack space of curr proc
lw     $ra, 0($fp)     # restore return adddress
lw     $fp, 4($fp)     # restore frame pointer
jr     $ra             # return
printArray: #procedure declaration
addi   $sp, $sp, -8    # make space for $fp and $ra
sw     $fp, 4($sp)     # save frame pointer
sw     $ra, 0($sp)     # save return address
move   $fp, $sp        # fp points to old $sp
subi $sp, $sp, 4	#i
li $9, 0
addi $8, $fp, -4  # get addr relative to fp
sw $9, ($8)
L0: #while L0
addi $9, $fp, -4  # get addr relative to fp
lw $9, ($9)
addi $8, $fp, 8  # get addr relative to fp
lw $8, ($8)
lw $8, ($8)
slt    $9, $9, $8
beqz   $9, L1
addi $9, $fp, -4  # get addr relative to fp
lw $9, ($9)
addi $8, $fp, 8  # get addr relative to fp
lw $8, ($8)
addi   $9, $9, 1
li $11, 4
mult    $9, $11
mflo   $9
add    $8, $8, $9
lw $10, ($8)
subi   $sp, $sp, 4   # make room on stack for caller saved
sw $10, 0($sp) # save $10 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $10, 0($sp) # store arg on stack
jal writeint
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $10, 0($sp) # restore $10 from stack
addi   $sp, $sp, 4   # free room on stack for caller saved
.data
heap: #begin of heap
.asciiz " "
.text
la $10, heap    # load heap base address
addi   $10, $10, 0  # add offset
subi   $sp, $sp, 4   # make room on stack for caller saved
sw $10, 0($sp) # save $10 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $10, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $10, 0($sp) # restore $10 from stack
addi   $sp, $sp, 4   # free room on stack for caller saved
addi $10, $fp, -4  # get addr relative to fp
lw $10, ($10)
li $9, 1
add    $10, $10, $9
addi $9, $fp, -4  # get addr relative to fp
sw $10, ($9)
j  L0
L1: #end while L0
subi   $sp, $sp, 0   # make room on stack for caller saved
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
addi   $sp,    $sp,    0   # free room for arguments on stack
addi   $sp, $sp, 0   # free room on stack for caller saved
exit_printArray: #procedure epilogue
addi     $sp, $fp, 8#free stack space of curr proc
lw     $ra, 0($fp)     # restore return adddress
lw     $fp, 4($fp)     # restore frame pointer
jr     $ra             # return
createArray: #procedure declaration
addi   $sp, $sp, -8    # make space for $fp and $ra
sw     $fp, 4($sp)     # save frame pointer
sw     $ra, 0($sp)     # save return address
move   $fp, $sp        # fp points to old $sp
subi $sp, $sp, 4	#i
subi $sp, $sp, 4	#j
subi $sp, $sp, 4	#k
subi $sp, $sp, 4	#a
addi $10, $fp, 8  # get addr relative to fp
lw $10, ($10)
move $a0, $10
addi $a0, $a0, 1#alloc space for length information
li $8, 4
mult    $4, $8
mflo   $4
li $v0, 9#issue sbrk
syscall#alloc space for 1st array dim
move   $9, $v0#move start address of array to destReg
sw $10, ($9)
addi $8, $fp, -16  # get addr relative to fp
sw $9, ($8)
li $9, 0
addi $8, $fp, -4  # get addr relative to fp
sw $9, ($8)
li $9, 20
addi $8, $fp, -8  # get addr relative to fp
sw $9, ($8)
li $9, 1
sub    $9, $0, $9
addi $8, $fp, -12  # get addr relative to fp
sw $9, ($8)
L2: #while L2
addi $9, $fp, -4  # get addr relative to fp
lw $9, ($9)
addi $8, $fp, -16  # get addr relative to fp
lw $8, ($8)
lw $8, ($8)
slt    $9, $9, $8
beqz   $9, L3
addi $9, $fp, -4  # get addr relative to fp
lw $9, ($9)
addi $8, $fp, -16  # get addr relative to fp
lw $8, ($8)
addi $11, $fp, -4  # get addr relative to fp
lw $11, ($11)
addi $12, $fp, -8  # get addr relative to fp
lw $12, ($12)
add    $11, $11, $12
addi   $9, $9, 1
li $12, 4
mult    $9, $12
mflo   $9
add    $8, $8, $9
sw $11, ($8)
addi $8, $fp, -4  # get addr relative to fp
lw $8, ($8)
li $11, 1
add    $8, $8, $11
addi $11, $fp, -4  # get addr relative to fp
sw $8, ($11)
addi $8, $fp, -8  # get addr relative to fp
lw $8, ($8)
li $11, 30
add    $8, $8, $11
li $11, 64
div    $8, $11
mfhi   $8
addi $11, $fp, -12  # get addr relative to fp
lw $11, ($11)
mult    $8, $11
mflo   $8
addi $11, $fp, -8  # get addr relative to fp
sw $8, ($11)
addi $8, $fp, -12  # get addr relative to fp
lw $8, ($8)
sub    $8, $0, $8
addi $11, $fp, -12  # get addr relative to fp
sw $8, ($11)
j  L2
L3: #end while L2
addi $8, $fp, -16  # get addr relative to fp
lw $8, ($8)
move   $v0, $8    # copy return value to v0
j  exit_createArray
exit_createArray: #procedure epilogue
addi     $sp, $fp, 8#free stack space of curr proc
lw     $ra, 0($fp)     # restore return adddress
lw     $fp, 4($fp)     # restore frame pointer
jr     $ra             # return
quicksort: #procedure declaration
addi   $sp, $sp, -8    # make space for $fp and $ra
sw     $fp, 4($sp)     # save frame pointer
sw     $ra, 0($sp)     # save return address
move   $fp, $sp        # fp points to old $sp
subi $sp, $sp, 4	#i
subi $sp, $sp, 4	#j
subi $sp, $sp, 4	#x
subi $sp, $sp, 4	#w
subi $sp, $sp, 4	#middle
addi $8, $fp, 12  # get addr relative to fp
lw $8, ($8)
addi $11, $fp, -4  # get addr relative to fp
sw $8, ($11)
addi $8, $fp, 16  # get addr relative to fp
lw $8, ($8)
addi $11, $fp, -8  # get addr relative to fp
sw $8, ($11)
addi $8, $fp, 12  # get addr relative to fp
lw $8, ($8)
addi $11, $fp, 16  # get addr relative to fp
lw $11, ($11)
add    $8, $8, $11
li $11, 2
div    $8, $11
mflo   $8
addi $11, $fp, -20  # get addr relative to fp
sw $8, ($11)
addi $8, $fp, -20  # get addr relative to fp
lw $8, ($8)
addi $11, $fp, 8  # get addr relative to fp
lw $11, ($11)
addi   $8, $8, 1
li $12, 4
mult    $8, $12
mflo   $8
add    $11, $11, $8
lw $9, ($11)
addi $8, $fp, -12  # get addr relative to fp
sw $9, ($8)
L4: #while L4
addi $9, $fp, -4  # get addr relative to fp
lw $9, ($9)
addi $8, $fp, -8  # get addr relative to fp
lw $8, ($8)
sle    $9, $9, $8
beqz   $9, L5
L6: #while L6
addi $9, $fp, -4  # get addr relative to fp
lw $9, ($9)
addi $8, $fp, 8  # get addr relative to fp
lw $8, ($8)
addi   $9, $9, 1
li $12, 4
mult    $9, $12
mflo   $9
add    $8, $8, $9
lw $11, ($8)
addi $9, $fp, -12  # get addr relative to fp
lw $9, ($9)
slt    $11, $11, $9
beqz   $11, L7
addi $11, $fp, -4  # get addr relative to fp
lw $11, ($11)
li $9, 1
add    $11, $11, $9
addi $9, $fp, -4  # get addr relative to fp
sw $11, ($9)
j  L6
L7: #end while L6
L8: #while L8
addi $11, $fp, -8  # get addr relative to fp
lw $11, ($11)
addi $9, $fp, 8  # get addr relative to fp
lw $9, ($9)
addi   $11, $11, 1
li $12, 4
mult    $11, $12
mflo   $11
add    $9, $9, $11
lw $8, ($9)
addi $11, $fp, -12  # get addr relative to fp
lw $11, ($11)
slt    $8, $11, $8
beqz   $8, L9
addi $8, $fp, -8  # get addr relative to fp
lw $8, ($8)
li $11, 1
sub    $8, $8, $11
addi $11, $fp, -8  # get addr relative to fp
sw $8, ($11)
j  L8
L9: #end while L8
addi $8, $fp, -4  # get addr relative to fp
lw $8, ($8)
addi $11, $fp, -8  # get addr relative to fp
lw $11, ($11)
sle    $8, $8, $11
beqz   $8, L10
addi $8, $fp, -4  # get addr relative to fp
lw $8, ($8)
addi $11, $fp, 8  # get addr relative to fp
lw $11, ($11)
addi   $8, $8, 1
li $12, 4
mult    $8, $12
mflo   $8
add    $11, $11, $8
lw $9, ($11)
addi $8, $fp, -16  # get addr relative to fp
sw $9, ($8)
addi $9, $fp, -4  # get addr relative to fp
lw $9, ($9)
addi $8, $fp, 8  # get addr relative to fp
lw $8, ($8)
addi $11, $fp, -8  # get addr relative to fp
lw $11, ($11)
addi $12, $fp, 8  # get addr relative to fp
lw $12, ($12)
addi   $11, $11, 1
li $14, 4
mult    $11, $14
mflo   $11
add    $12, $12, $11
lw $13, ($12)
addi   $9, $9, 1
li $11, 4
mult    $9, $11
mflo   $9
add    $8, $8, $9
sw $13, ($8)
addi $8, $fp, -8  # get addr relative to fp
lw $8, ($8)
addi $13, $fp, 8  # get addr relative to fp
lw $13, ($13)
addi $9, $fp, -16  # get addr relative to fp
lw $9, ($9)
addi   $8, $8, 1
li $11, 4
mult    $8, $11
mflo   $8
add    $13, $13, $8
sw $9, ($13)
addi $13, $fp, -4  # get addr relative to fp
lw $13, ($13)
li $9, 1
add    $13, $13, $9
addi $9, $fp, -4  # get addr relative to fp
sw $13, ($9)
addi $13, $fp, -8  # get addr relative to fp
lw $13, ($13)
li $9, 1
sub    $13, $13, $9
addi $9, $fp, -8  # get addr relative to fp
sw $13, ($9)
L10: #null
j  L4
L5: #end while L4
addi $13, $fp, 12  # get addr relative to fp
lw $13, ($13)
addi $9, $fp, -8  # get addr relative to fp
lw $9, ($9)
slt    $13, $13, $9
beqz   $13, L12
addi $13, $fp, 8  # get addr relative to fp
lw $13, ($13)
addi $9, $fp, 12  # get addr relative to fp
lw $9, ($9)
addi $8, $fp, -8  # get addr relative to fp
lw $8, ($8)
subi   $sp, $sp, 16   # make room on stack for caller saved
sw $8, 0($sp) # save $8 on stack
sw $9, 4($sp) # save $9 on stack
sw $10, 8($sp) # save $10 on stack
sw $13, 12($sp) # save $13 on stack
subi   $sp, $sp, 12 # make room on stack for arguments
sw $13, 0($sp) # store arg on stack
sw $9, 4($sp) # store arg on stack
sw $8, 8($sp) # store arg on stack
jal quicksort
addi   $sp,    $sp,    12   # free room for arguments on stack
lw $13, 12($sp) # restore $13 from stack
lw $10, 8($sp) # restore $10 from stack
lw $9, 4($sp) # restore $9 from stack
lw $8, 0($sp) # restore $8 from stack
addi   $sp, $sp, 16   # free room on stack for caller saved
L12: #null
addi $8, $fp, -4  # get addr relative to fp
lw $8, ($8)
addi $9, $fp, 16  # get addr relative to fp
lw $9, ($9)
slt    $8, $8, $9
beqz   $8, L14
addi $8, $fp, 8  # get addr relative to fp
lw $8, ($8)
addi $9, $fp, -4  # get addr relative to fp
lw $9, ($9)
addi $13, $fp, 16  # get addr relative to fp
lw $13, ($13)
subi   $sp, $sp, 16   # make room on stack for caller saved
sw $8, 0($sp) # save $8 on stack
sw $9, 4($sp) # save $9 on stack
sw $10, 8($sp) # save $10 on stack
sw $13, 12($sp) # save $13 on stack
subi   $sp, $sp, 12 # make room on stack for arguments
sw $8, 0($sp) # store arg on stack
sw $9, 4($sp) # store arg on stack
sw $13, 8($sp) # store arg on stack
jal quicksort
addi   $sp,    $sp,    12   # free room for arguments on stack
lw $13, 12($sp) # restore $13 from stack
lw $10, 8($sp) # restore $10 from stack
lw $9, 4($sp) # restore $9 from stack
lw $8, 0($sp) # restore $8 from stack
addi   $sp, $sp, 16   # free room on stack for caller saved
L14: #null
exit_quicksort: #procedure epilogue
addi     $sp, $fp, 8#free stack space of curr proc
lw     $ra, 0($fp)     # restore return adddress
lw     $fp, 4($fp)     # restore frame pointer
jr     $ra             # return
subi $sp, $sp, 4	#ARRAYLEN
li $13, 10
addi $9, $fp, -24  # get addr relative to fp
sw $13, ($9)
subi $sp, $sp, 4	#a
.globl main
main: #main procedure
move   $fp, $sp        # fp points to old $sp
.data
.asciiz "--- Start test18 ---"
.text
la $13, heap    # load heap base address
addi   $13, $13, 2  # add offset
subi   $sp, $sp, 8   # make room on stack for caller saved
sw $10, 0($sp) # save $10 on stack
sw $13, 4($sp) # save $13 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $13, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $13, 4($sp) # restore $13 from stack
lw $10, 0($sp) # restore $10 from stack
addi   $sp, $sp, 8   # free room on stack for caller saved
subi   $sp, $sp, 4   # make room on stack for caller saved
sw $10, 0($sp) # save $10 on stack
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
addi   $sp,    $sp,    0   # free room for arguments on stack
lw $10, 0($sp) # restore $10 from stack
addi   $sp, $sp, 4   # free room on stack for caller saved
li $13, 10
subi   $sp, $sp, 12   # make room on stack for caller saved
sw $9, 0($sp) # save $9 on stack
sw $10, 4($sp) # save $10 on stack
sw $13, 8($sp) # save $13 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $13, 0($sp) # store arg on stack
jal createArray
move $9, $v0 # copy return value
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $13, 8($sp) # restore $13 from stack
lw $10, 4($sp) # restore $10 from stack
addi   $sp, $sp, 12   # free room on stack for caller saved
addi $13, $fp, -28  # get addr relative to fp
sw $9, ($13)
.data
.asciiz "Input Array: "
.text
la $9, heap    # load heap base address
addi   $9, $9, 23  # add offset
subi   $sp, $sp, 8   # make room on stack for caller saved
sw $9, 0($sp) # save $9 on stack
sw $10, 4($sp) # save $10 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $9, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $10, 4($sp) # restore $10 from stack
lw $9, 0($sp) # restore $9 from stack
addi   $sp, $sp, 8   # free room on stack for caller saved
addi $9, $fp, -28  # get addr relative to fp
lw $9, ($9)
subi   $sp, $sp, 8   # make room on stack for caller saved
sw $9, 0($sp) # save $9 on stack
sw $10, 4($sp) # save $10 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $9, 0($sp) # store arg on stack
jal printArray
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $10, 4($sp) # restore $10 from stack
lw $9, 0($sp) # restore $9 from stack
addi   $sp, $sp, 8   # free room on stack for caller saved
addi $9, $fp, -28  # get addr relative to fp
lw $9, ($9)
li $13, 0
addi $8, $fp, -28  # get addr relative to fp
lw $8, ($8)
lw $8, ($8)
li $11, 1
sub    $8, $8, $11
subi   $sp, $sp, 16   # make room on stack for caller saved
sw $8, 0($sp) # save $8 on stack
sw $9, 4($sp) # save $9 on stack
sw $10, 8($sp) # save $10 on stack
sw $13, 12($sp) # save $13 on stack
subi   $sp, $sp, 12 # make room on stack for arguments
sw $9, 0($sp) # store arg on stack
sw $13, 4($sp) # store arg on stack
sw $8, 8($sp) # store arg on stack
jal quicksort
addi   $sp,    $sp,    12   # free room for arguments on stack
lw $13, 12($sp) # restore $13 from stack
lw $10, 8($sp) # restore $10 from stack
lw $9, 4($sp) # restore $9 from stack
lw $8, 0($sp) # restore $8 from stack
addi   $sp, $sp, 16   # free room on stack for caller saved
.data
.asciiz "Sorted Array: "
.text
la $8, heap    # load heap base address
addi   $8, $8, 37  # add offset
subi   $sp, $sp, 8   # make room on stack for caller saved
sw $8, 0($sp) # save $8 on stack
sw $10, 4($sp) # save $10 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $8, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $10, 4($sp) # restore $10 from stack
lw $8, 0($sp) # restore $8 from stack
addi   $sp, $sp, 8   # free room on stack for caller saved
addi $8, $fp, -28  # get addr relative to fp
lw $8, ($8)
subi   $sp, $sp, 8   # make room on stack for caller saved
sw $8, 0($sp) # save $8 on stack
sw $10, 4($sp) # save $10 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $8, 0($sp) # store arg on stack
jal printArray
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $10, 4($sp) # restore $10 from stack
lw $8, 0($sp) # restore $8 from stack
addi   $sp, $sp, 8   # free room on stack for caller saved
.data
.asciiz "--- End test18 ---"
.text
la $8, heap    # load heap base address
addi   $8, $8, 52  # add offset
subi   $sp, $sp, 8   # make room on stack for caller saved
sw $8, 0($sp) # save $8 on stack
sw $10, 4($sp) # save $10 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $8, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $10, 4($sp) # restore $10 from stack
lw $8, 0($sp) # restore $8 from stack
addi   $sp, $sp, 8   # free room on stack for caller saved
subi   $sp, $sp, 4   # make room on stack for caller saved
sw $10, 0($sp) # save $10 on stack
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
addi   $sp,    $sp,    0   # free room for arguments on stack
lw $10, 0($sp) # restore $10 from stack
addi   $sp, $sp, 4   # free room on stack for caller saved
exitMain: #exit main
li $v0, 10 # exit
syscall
