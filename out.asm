.text
j main
printString: #procedure declaration
addi   $sp, $sp, -8    # make space for $fp and $ra
sw     $fp, 4($sp)     # save frame pointer
sw     $ra, 0($sp)     # save return address
addi   $fp, $sp, 4     # fp points to first word in stack frame
lw $a0, 4($fp) # a0 = start address of string
li $v0, 4      # print_string
syscall
exit_printString: #procedure epilogue
addi   $sp, $fp, 4#free stack space of curr proc
lw     $ra, -4($fp)    # restore return adddress
lw     $fp, 0($fp)     # restore frame pointer
jr     $ra             # return
writeint: #procedure declaration
addi   $sp, $sp, -8    # make space for $fp and $ra
sw     $fp, 4($sp)     # save frame pointer
sw     $ra, 0($sp)     # save return address
addi   $fp, $sp, 4     # fp points to first word in stack frame
lw $a0, 4($fp) # a0 = value
li $v0, 1      # print_int
syscall
exit_writeint: #procedure epilogue
addi   $sp, $fp, 4#free stack space of curr proc
lw     $ra, -4($fp)    # restore return adddress
lw     $fp, 0($fp)     # restore frame pointer
jr     $ra             # return
.data
newLine: .asciiz  "\n"
.text
writeln: #procedure declaration
addi   $sp, $sp, -8    # make space for $fp and $ra
sw     $fp, 4($sp)     # save frame pointer
sw     $ra, 0($sp)     # save return address
addi   $fp, $sp, 4     # fp points to first word in stack frame
la $a0, newLine #load newline character
li $v0, 4      # print_string
syscall
exit_writeln: #procedure epilogue
addi   $sp, $fp, 4#free stack space of curr proc
lw     $ra, -4($fp)    # restore return adddress
lw     $fp, 0($fp)     # restore frame pointer
jr     $ra             # return
.data
True: .asciiz  "True"
False: .asciiz  "False"
.text
writebool: #procedure declaration
addi   $sp, $sp, -8    # make space for $fp and $ra
sw     $fp, 4($sp)     # save frame pointer
sw     $ra, 0($sp)     # save return address
addi   $fp, $sp, 4     # fp points to first word in stack frame
la $a0, True
lw $8, 4($fp)    # load boolean argument
li $9, 1
beq $8, $9, printBoolSkip
la $a0, False
printBoolSkip: #skip load 'False' String in case of True
li $v0, 4      # print_string
syscall
exit_writebool: #procedure epilogue
addi   $sp, $fp, 4#free stack space of curr proc
lw     $ra, -4($fp)    # restore return adddress
lw     $fp, 0($fp)     # restore frame pointer
jr     $ra             # return
.globl main
main: #main procedure
.data
heap: #begin of heap
.align 2
.space 4#len
.text
li $10, 10
la $11, heap    # load heap base address
addi   $11, $11, 0  # add offset
sw $10, ($11)
.data
.align 2
.space 4#true
.text
li $12, 1
la $13, heap    # load heap base address
addi   $13, $13, 4  # add offset
sw $12, ($13)
.data
.align 2
.space 4#a
.align 2
.space 4#b
.asciiz "--- Start test07 ---"
.text
la $14, heap    # load heap base address
addi   $14, $14, 16  # add offset
subi   $sp, $sp, 4   # make room on stack for caller saved
sw $14, 0($sp) # save $14 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $14, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $14, 0($sp) # restore $14 from stack
addi   $sp, $sp, 4   # free room on stack for caller saved
subi   $sp, $sp, 0   # make room on stack for caller saved
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
move $15, $v0 # copy return value
addi   $sp,    $sp,    0   # free room for arguments on stack
addi   $sp, $sp, 0   # free room on stack for caller saved
li $16, 10
move $a0, $16
addi $a0, $a0, 1#alloc space for length information
li $18, 4
mult    $4, $18
mflo   $4
li $v0, 9#issue sbrk
syscall#alloc space for 1st array dim
move   $17, $v0#move start address of array to destReg
sw $16, ($17)
la $19, heap    # load heap base address
addi   $19, $19, 8  # add offset
sw $17, ($19)
li $20, 10
li $21, 1
add    $20, $20, $21
move $a0, $20
addi $a0, $a0, 1#alloc space for length information
li $23, 4
mult    $4, $23
mflo   $4
li $v0, 9#issue sbrk
syscall#alloc space for 1st array dim
move   $22, $v0#move start address of array to destReg
sw $20, ($22)
la $24, heap    # load heap base address
addi   $24, $24, 12  # add offset
sw $22, ($24)
li $25, 1
la $8, heap    # load heap base address
addi   $8, $8, 8  # add offset
lw $8, ($8)
la $9, heap    # load heap base address
addi   $9, $9, 12  # add offset
lw $9, ($9)
lw $9, ($9)
