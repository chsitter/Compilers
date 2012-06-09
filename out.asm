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
.space 4#constant
.align 2
.space 4#k
.align 2
.space 4#b
.asciiz "--- Start test04 ---"
.text
la $10, heap    # load heap base address
addi   $10, $10, 12  # add offset
subi   $sp, $sp, 4   # make room on stack for caller saved
sw $10, 0($sp) # save $10 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $10, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $10, 0($sp) # restore $10 from stack
addi   $sp, $sp, 4   # free room on stack for caller saved
subi   $sp, $sp, 0   # make room on stack for caller saved
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
move $11, $v0 # copy return value
addi   $sp,    $sp,    0   # free room for arguments on stack
addi   $sp, $sp, 0   # free room on stack for caller saved
li $12, 1
la $12, heap    # load heap base address
addi   $12, $12, 8  # add offset
sw $12, ($12)
li $13, 3
la $13, heap    # load heap base address
addi   $13, $13, 4  # add offset
sw $13, ($13)
.data
.asciiz "writeint(k): "
.text
la $14, heap    # load heap base address
addi   $14, $14, 33  # add offset
subi   $sp, $sp, 8   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $14, 4($sp) # save $14 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $14, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $14, 4($sp) # restore $14 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 8   # free room on stack for caller saved
la $15, heap    # load heap base address
addi   $15, $15, 4  # add offset
lw $15, ($15)
subi   $sp, $sp, 8   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $15, 4($sp) # save $15 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $15, 0($sp) # store arg on stack
jal writeint
move $16, $v0 # copy return value
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $15, 4($sp) # restore $15 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 8   # free room on stack for caller saved
.data
.asciiz " (expected 3)"
.text
la $17, heap    # load heap base address
addi   $17, $17, 47  # add offset
subi   $sp, $sp, 12   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $16, 4($sp) # save $16 on stack
sw $17, 8($sp) # save $17 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $17, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $17, 8($sp) # restore $17 from stack
lw $16, 4($sp) # restore $16 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 12   # free room on stack for caller saved
subi   $sp, $sp, 8   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $16, 4($sp) # save $16 on stack
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
move $18, $v0 # copy return value
addi   $sp,    $sp,    0   # free room for arguments on stack
lw $16, 4($sp) # restore $16 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 8   # free room on stack for caller saved
li $19, 10
la $19, heap    # load heap base address
addi   $19, $19, 4  # add offset
sw $19, ($19)
.data
.asciiz "writeint(k): "
.text
la $20, heap    # load heap base address
addi   $20, $20, 61  # add offset
subi   $sp, $sp, 16   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $16, 4($sp) # save $16 on stack
sw $18, 8($sp) # save $18 on stack
sw $20, 12($sp) # save $20 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $20, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $20, 12($sp) # restore $20 from stack
lw $18, 8($sp) # restore $18 from stack
lw $16, 4($sp) # restore $16 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 16   # free room on stack for caller saved
la $21, heap    # load heap base address
addi   $21, $21, 4  # add offset
lw $21, ($21)
subi   $sp, $sp, 16   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $16, 4($sp) # save $16 on stack
sw $18, 8($sp) # save $18 on stack
sw $21, 12($sp) # save $21 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $21, 0($sp) # store arg on stack
jal writeint
move $22, $v0 # copy return value
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $21, 12($sp) # restore $21 from stack
lw $18, 8($sp) # restore $18 from stack
lw $16, 4($sp) # restore $16 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 16   # free room on stack for caller saved
.data
.asciiz " (expected 10)"
.text
la $23, heap    # load heap base address
addi   $23, $23, 75  # add offset
subi   $sp, $sp, 20   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $16, 4($sp) # save $16 on stack
sw $18, 8($sp) # save $18 on stack
sw $22, 12($sp) # save $22 on stack
sw $23, 16($sp) # save $23 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $23, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $23, 16($sp) # restore $23 from stack
lw $22, 12($sp) # restore $22 from stack
lw $18, 8($sp) # restore $18 from stack
lw $16, 4($sp) # restore $16 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 20   # free room on stack for caller saved
subi   $sp, $sp, 16   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $16, 4($sp) # save $16 on stack
sw $18, 8($sp) # save $18 on stack
sw $22, 12($sp) # save $22 on stack
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
move $24, $v0 # copy return value
addi   $sp,    $sp,    0   # free room for arguments on stack
lw $22, 12($sp) # restore $22 from stack
lw $18, 8($sp) # restore $18 from stack
lw $16, 4($sp) # restore $16 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 16   # free room on stack for caller saved
.data
.asciiz "writebool(b): "
.text
la $25, heap    # load heap base address
addi   $25, $25, 90  # add offset
subi   $sp, $sp, 24   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $16, 4($sp) # save $16 on stack
sw $18, 8($sp) # save $18 on stack
sw $22, 12($sp) # save $22 on stack
sw $24, 16($sp) # save $24 on stack
sw $25, 20($sp) # save $25 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $25, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $25, 20($sp) # restore $25 from stack
lw $24, 16($sp) # restore $24 from stack
lw $22, 12($sp) # restore $22 from stack
lw $18, 8($sp) # restore $18 from stack
lw $16, 4($sp) # restore $16 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 24   # free room on stack for caller saved
la $8, heap    # load heap base address
addi   $8, $8, 8  # add offset
lw $8, ($8)
subi   $sp, $sp, 24   # make room on stack for caller saved
sw $8, 0($sp) # save $8 on stack
sw $11, 4($sp) # save $11 on stack
sw $16, 8($sp) # save $16 on stack
sw $18, 12($sp) # save $18 on stack
sw $22, 16($sp) # save $22 on stack
sw $24, 20($sp) # save $24 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $8, 0($sp) # store arg on stack
jal writebool
move $9, $v0 # copy return value
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $24, 20($sp) # restore $24 from stack
lw $22, 16($sp) # restore $22 from stack
lw $18, 12($sp) # restore $18 from stack
lw $16, 8($sp) # restore $16 from stack
lw $11, 4($sp) # restore $11 from stack
lw $8, 0($sp) # restore $8 from stack
addi   $sp, $sp, 24   # free room on stack for caller saved
.data
.asciiz " (expected True)"
.text
la $10, heap    # load heap base address
addi   $10, $10, 105  # add offset
subi   $sp, $sp, 28   # make room on stack for caller saved
sw $9, 0($sp) # save $9 on stack
sw $10, 4($sp) # save $10 on stack
sw $11, 8($sp) # save $11 on stack
sw $16, 12($sp) # save $16 on stack
sw $18, 16($sp) # save $18 on stack
sw $22, 20($sp) # save $22 on stack
sw $24, 24($sp) # save $24 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $10, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $24, 24($sp) # restore $24 from stack
lw $22, 20($sp) # restore $22 from stack
lw $18, 16($sp) # restore $18 from stack
lw $16, 12($sp) # restore $16 from stack
lw $11, 8($sp) # restore $11 from stack
lw $10, 4($sp) # restore $10 from stack
lw $9, 0($sp) # restore $9 from stack
addi   $sp, $sp, 28   # free room on stack for caller saved
subi   $sp, $sp, 24   # make room on stack for caller saved
sw $9, 0($sp) # save $9 on stack
sw $11, 4($sp) # save $11 on stack
sw $16, 8($sp) # save $16 on stack
sw $18, 12($sp) # save $18 on stack
sw $22, 16($sp) # save $22 on stack
sw $24, 20($sp) # save $24 on stack
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
move $12, $v0 # copy return value
addi   $sp,    $sp,    0   # free room for arguments on stack
lw $24, 20($sp) # restore $24 from stack
lw $22, 16($sp) # restore $22 from stack
lw $18, 12($sp) # restore $18 from stack
lw $16, 8($sp) # restore $16 from stack
lw $11, 4($sp) # restore $11 from stack
lw $9, 0($sp) # restore $9 from stack
addi   $sp, $sp, 24   # free room on stack for caller saved
.data
.asciiz "--- End test04 ---"
.text
la $13, heap    # load heap base address
addi   $13, $13, 122  # add offset
subi   $sp, $sp, 32   # make room on stack for caller saved
sw $9, 0($sp) # save $9 on stack
sw $11, 4($sp) # save $11 on stack
sw $12, 8($sp) # save $12 on stack
sw $13, 12($sp) # save $13 on stack
sw $16, 16($sp) # save $16 on stack
sw $18, 20($sp) # save $18 on stack
sw $22, 24($sp) # save $22 on stack
sw $24, 28($sp) # save $24 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $13, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $24, 28($sp) # restore $24 from stack
lw $22, 24($sp) # restore $22 from stack
lw $18, 20($sp) # restore $18 from stack
lw $16, 16($sp) # restore $16 from stack
lw $13, 12($sp) # restore $13 from stack
lw $12, 8($sp) # restore $12 from stack
lw $11, 4($sp) # restore $11 from stack
lw $9, 0($sp) # restore $9 from stack
addi   $sp, $sp, 32   # free room on stack for caller saved
subi   $sp, $sp, 28   # make room on stack for caller saved
sw $9, 0($sp) # save $9 on stack
sw $11, 4($sp) # save $11 on stack
sw $12, 8($sp) # save $12 on stack
sw $16, 12($sp) # save $16 on stack
sw $18, 16($sp) # save $18 on stack
sw $22, 20($sp) # save $22 on stack
sw $24, 24($sp) # save $24 on stack
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
move $14, $v0 # copy return value
addi   $sp,    $sp,    0   # free room for arguments on stack
lw $24, 24($sp) # restore $24 from stack
lw $22, 20($sp) # restore $22 from stack
lw $18, 16($sp) # restore $18 from stack
lw $16, 12($sp) # restore $16 from stack
lw $12, 8($sp) # restore $12 from stack
lw $11, 4($sp) # restore $11 from stack
lw $9, 0($sp) # restore $9 from stack
addi   $sp, $sp, 28   # free room on stack for caller saved
exitMain: #exit main
li $v0, 10 # exit
syscall
