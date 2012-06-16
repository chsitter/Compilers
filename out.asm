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
.space 4#k
.asciiz "--- Start test06 ---"
.text
la $10, heap    # load heap base address
addi   $10, $10, 4  # add offset
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
la $13, heap    # load heap base address
addi   $13, $13, 0  # add offset
sw $12, ($13)
.data
.asciiz "k*1+(k*1+(k*1+(k*1+(k*1+(k*1+(k*1+(k*1+1))))))) = "
.text
la $14, heap    # load heap base address
addi   $14, $14, 25  # add offset
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
addi   $15, $15, 0  # add offset
lw $15, ($15)
li $16, 1
mult    $15, $16
mflo   $15
la $17, heap    # load heap base address
addi   $17, $17, 0  # add offset
lw $17, ($17)
li $18, 1
mult    $17, $18
mflo   $17
la $19, heap    # load heap base address
addi   $19, $19, 0  # add offset
lw $19, ($19)
li $20, 1
mult    $19, $20
mflo   $19
la $21, heap    # load heap base address
addi   $21, $21, 0  # add offset
lw $21, ($21)
li $22, 1
mult    $21, $22
mflo   $21
la $23, heap    # load heap base address
addi   $23, $23, 0  # add offset
lw $23, ($23)
li $24, 1
mult    $23, $24
mflo   $23
la $25, heap    # load heap base address
addi   $25, $25, 0  # add offset
lw $25, ($25)
li $8, 1
mult    $25, $8
mflo   $25
la $9, heap    # load heap base address
addi   $9, $9, 0  # add offset
lw $9, ($9)
li $10, 1
mult    $9, $10
mflo   $9
la $13, heap    # load heap base address
addi   $13, $13, 0  # add offset
lw $13, ($13)
li $12, 1
mult    $13, $12
mflo   $13
li $14, 1
add    $13, $13, $14
add    $9, $9, $13
add    $25, $25, $9
add    $23, $23, $25
add    $21, $21, $23
add    $19, $19, $21
add    $17, $17, $19
add    $15, $15, $17
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
.asciiz " (9 expected)"
.text
la $18, heap    # load heap base address
addi   $18, $18, 76  # add offset
subi   $sp, $sp, 12   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $16, 4($sp) # save $16 on stack
sw $18, 8($sp) # save $18 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $18, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $18, 8($sp) # restore $18 from stack
lw $16, 4($sp) # restore $16 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 12   # free room on stack for caller saved
subi   $sp, $sp, 8   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $16, 4($sp) # save $16 on stack
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
move $20, $v0 # copy return value
addi   $sp,    $sp,    0   # free room for arguments on stack
lw $16, 4($sp) # restore $16 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 8   # free room on stack for caller saved
.data
.asciiz "--- End test06 ---"
.text
la $22, heap    # load heap base address
addi   $22, $22, 90  # add offset
subi   $sp, $sp, 16   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $16, 4($sp) # save $16 on stack
sw $20, 8($sp) # save $20 on stack
sw $22, 12($sp) # save $22 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $22, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $22, 12($sp) # restore $22 from stack
lw $20, 8($sp) # restore $20 from stack
lw $16, 4($sp) # restore $16 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 16   # free room on stack for caller saved
subi   $sp, $sp, 12   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $16, 4($sp) # save $16 on stack
sw $20, 8($sp) # save $20 on stack
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
move $24, $v0 # copy return value
addi   $sp,    $sp,    0   # free room for arguments on stack
lw $20, 8($sp) # restore $20 from stack
lw $16, 4($sp) # restore $16 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 12   # free room on stack for caller saved
exitMain: #exit main
li $v0, 10 # exit
syscall
