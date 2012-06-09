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
.asciiz "--- Start test03 ---"
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
subi   $sp, $sp, 0   # make room on stack for caller saved
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
move $11, $v0 # copy return value
addi   $sp,    $sp,    0   # free room for arguments on stack
addi   $sp, $sp, 0   # free room on stack for caller saved
.data
.asciiz "i = "
.text
la $12, heap    # load heap base address
addi   $12, $12, 21  # add offset
subi   $sp, $sp, 8   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $12, 4($sp) # save $12 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $12, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $12, 4($sp) # restore $12 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 8   # free room on stack for caller saved
li $13, 5
subi   $sp, $sp, 8   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $13, 4($sp) # save $13 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $13, 0($sp) # store arg on stack
jal writeint
move $14, $v0 # copy return value
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $13, 4($sp) # restore $13 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 8   # free room on stack for caller saved
subi   $sp, $sp, 12   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $13, 4($sp) # save $13 on stack
sw $14, 8($sp) # save $14 on stack
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
move $15, $v0 # copy return value
addi   $sp,    $sp,    0   # free room for arguments on stack
lw $14, 8($sp) # restore $14 from stack
lw $13, 4($sp) # restore $13 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 12   # free room on stack for caller saved
.data
.asciiz "b = "
.text
la $16, heap    # load heap base address
addi   $16, $16, 26  # add offset
subi   $sp, $sp, 20   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $13, 4($sp) # save $13 on stack
sw $14, 8($sp) # save $14 on stack
sw $15, 12($sp) # save $15 on stack
sw $16, 16($sp) # save $16 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $16, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $16, 16($sp) # restore $16 from stack
lw $15, 12($sp) # restore $15 from stack
lw $14, 8($sp) # restore $14 from stack
lw $13, 4($sp) # restore $13 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 20   # free room on stack for caller saved
li $17, 1
subi   $sp, $sp, 20   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $13, 4($sp) # save $13 on stack
sw $14, 8($sp) # save $14 on stack
sw $15, 12($sp) # save $15 on stack
sw $17, 16($sp) # save $17 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $17, 0($sp) # store arg on stack
jal writebool
move $18, $v0 # copy return value
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $17, 16($sp) # restore $17 from stack
lw $15, 12($sp) # restore $15 from stack
lw $14, 8($sp) # restore $14 from stack
lw $13, 4($sp) # restore $13 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 20   # free room on stack for caller saved
subi   $sp, $sp, 24   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $13, 4($sp) # save $13 on stack
sw $14, 8($sp) # save $14 on stack
sw $15, 12($sp) # save $15 on stack
sw $17, 16($sp) # save $17 on stack
sw $18, 20($sp) # save $18 on stack
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
move $19, $v0 # copy return value
addi   $sp,    $sp,    0   # free room for arguments on stack
lw $18, 20($sp) # restore $18 from stack
lw $17, 16($sp) # restore $17 from stack
lw $15, 12($sp) # restore $15 from stack
lw $14, 8($sp) # restore $14 from stack
lw $13, 4($sp) # restore $13 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 24   # free room on stack for caller saved
.data
.asciiz "--- End test03 ---"
.text
la $20, heap    # load heap base address
addi   $20, $20, 31  # add offset
subi   $sp, $sp, 32   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $13, 4($sp) # save $13 on stack
sw $14, 8($sp) # save $14 on stack
sw $15, 12($sp) # save $15 on stack
sw $17, 16($sp) # save $17 on stack
sw $18, 20($sp) # save $18 on stack
sw $19, 24($sp) # save $19 on stack
sw $20, 28($sp) # save $20 on stack
subi   $sp, $sp, 4 # make room on stack for arguments
sw $20, 0($sp) # store arg on stack
jal printString
addi   $sp,    $sp,    4   # free room for arguments on stack
lw $20, 28($sp) # restore $20 from stack
lw $19, 24($sp) # restore $19 from stack
lw $18, 20($sp) # restore $18 from stack
lw $17, 16($sp) # restore $17 from stack
lw $15, 12($sp) # restore $15 from stack
lw $14, 8($sp) # restore $14 from stack
lw $13, 4($sp) # restore $13 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 32   # free room on stack for caller saved
subi   $sp, $sp, 28   # make room on stack for caller saved
sw $11, 0($sp) # save $11 on stack
sw $13, 4($sp) # save $13 on stack
sw $14, 8($sp) # save $14 on stack
sw $15, 12($sp) # save $15 on stack
sw $17, 16($sp) # save $17 on stack
sw $18, 20($sp) # save $18 on stack
sw $19, 24($sp) # save $19 on stack
subi   $sp, $sp, 0 # make room on stack for arguments
jal writeln
move $21, $v0 # copy return value
addi   $sp,    $sp,    0   # free room for arguments on stack
lw $19, 24($sp) # restore $19 from stack
lw $18, 20($sp) # restore $18 from stack
lw $17, 16($sp) # restore $17 from stack
lw $15, 12($sp) # restore $15 from stack
lw $14, 8($sp) # restore $14 from stack
lw $13, 4($sp) # restore $13 from stack
lw $11, 0($sp) # restore $11 from stack
addi   $sp, $sp, 28   # free room on stack for caller saved
exitMain: #exit main
li $v0, 10 # exit
syscall
