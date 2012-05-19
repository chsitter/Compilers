/* Generated By:JavaCC: Do not edit this line. ParserTokenManager.java */
package ca4.yapl.parser;
import ca4.yapl.exceptions.IllegalUseException;
import ca4.yapl.exceptions.ProcedureNameMismatchException;
import ca4.yapl.exceptions.ProgramNameMismatchException;
import ca4.yapl.exceptions.YAPLException;
import ca4.yapl.impl.Symbol;
import ca4.yapl.impl.SymbolTable;
import ca4.yapl.interfaces.ISymbol;
import ca4.yapl.lib.CompilerMessage;
import java.io.FileInputStream;
import java.io.IOException;

/** Token Manager. */
public class ParserTokenManager implements ParserConstants
{

  /** Debug output. */
  public static  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private static final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x40040000000000L) != 0L)
            return 6;
         if ((active0 & 0x100000000000000L) != 0L)
            return 13;
         if ((active0 & 0x7fffffe0L) != 0L)
         {
            jjmatchedKind = 58;
            return 1;
         }
         return -1;
      case 1:
         if ((active0 & 0x7fffff00L) != 0L)
         {
            jjmatchedKind = 58;
            jjmatchedPos = 1;
            return 1;
         }
         if ((active0 & 0xe0L) != 0L)
            return 1;
         if ((active0 & 0x40000000000000L) != 0L)
            return 14;
         return -1;
      case 2:
         if ((active0 & 0x37eff000L) != 0L)
         {
            if (jjmatchedPos != 2)
            {
               jjmatchedKind = 58;
               jjmatchedPos = 2;
            }
            return 1;
         }
         if ((active0 & 0x48100f00L) != 0L)
            return 1;
         return -1;
      case 3:
         if ((active0 & 0x7ffe0000L) != 0L)
         {
            jjmatchedKind = 58;
            jjmatchedPos = 3;
            return 1;
         }
         if ((active0 & 0x1f000L) != 0L)
            return 1;
         return -1;
      case 4:
         if ((active0 & 0x7f800000L) != 0L)
         {
            jjmatchedKind = 58;
            jjmatchedPos = 4;
            return 1;
         }
         if ((active0 & 0x7e0000L) != 0L)
            return 1;
         return -1;
      case 5:
         if ((active0 & 0x7e000000L) != 0L)
         {
            jjmatchedKind = 58;
            jjmatchedPos = 5;
            return 1;
         }
         if ((active0 & 0x1800000L) != 0L)
            return 1;
         return -1;
      case 6:
         if ((active0 & 0x78000000L) != 0L)
         {
            jjmatchedKind = 58;
            jjmatchedPos = 6;
            return 1;
         }
         if ((active0 & 0x6000000L) != 0L)
            return 1;
         return -1;
      case 7:
         if ((active0 & 0x60000000L) != 0L)
         {
            jjmatchedKind = 58;
            jjmatchedPos = 7;
            return 1;
         }
         if ((active0 & 0x18000000L) != 0L)
            return 1;
         return -1;
      default :
         return -1;
   }
}
private static final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
static private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
static private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 33:
         return jjMoveStringLiteralDfa1_0(0x4000000000L);
      case 34:
         return jjStartNfaWithStates_0(0, 56, 13);
      case 35:
         return jjStopAtPos(0, 47);
      case 37:
         return jjStopAtPos(0, 43);
      case 40:
         return jjStopAtPos(0, 48);
      case 41:
         return jjStopAtPos(0, 49);
      case 42:
         jjmatchedKind = 41;
         return jjMoveStringLiteralDfa1_0(0x80000000000000L);
      case 43:
         return jjStopAtPos(0, 39);
      case 44:
         return jjStopAtPos(0, 50);
      case 45:
         return jjStopAtPos(0, 40);
      case 46:
         return jjStopAtPos(0, 46);
      case 47:
         jjmatchedKind = 42;
         return jjMoveStringLiteralDfa1_0(0x40000000000000L);
      case 58:
         return jjMoveStringLiteralDfa1_0(0x8000000000000L);
      case 59:
         return jjStopAtPos(0, 52);
      case 60:
         jjmatchedKind = 33;
         return jjMoveStringLiteralDfa1_0(0x400000000L);
      case 61:
         jjmatchedKind = 53;
         return jjMoveStringLiteralDfa1_0(0x2000000000L);
      case 62:
         jjmatchedKind = 35;
         return jjMoveStringLiteralDfa1_0(0x1000000000L);
      case 65:
         return jjMoveStringLiteralDfa1_0(0x100L);
      case 66:
         return jjMoveStringLiteralDfa1_0(0x200000L);
      case 67:
         return jjMoveStringLiteralDfa1_0(0x400000L);
      case 68:
         return jjMoveStringLiteralDfa1_0(0x2000040L);
      case 69:
         return jjMoveStringLiteralDfa1_0(0x48104200L);
      case 70:
         return jjMoveStringLiteralDfa1_0(0x20000L);
      case 73:
         return jjMoveStringLiteralDfa1_0(0x20L);
      case 79:
         return jjMoveStringLiteralDfa1_0(0x80L);
      case 80:
         return jjMoveStringLiteralDfa1_0(0x24000000L);
      case 82:
         return jjMoveStringLiteralDfa1_0(0x1800000L);
      case 84:
         return jjMoveStringLiteralDfa1_0(0x12000L);
      case 87:
         return jjMoveStringLiteralDfa1_0(0xc0000L);
      case 91:
         return jjStopAtPos(0, 44);
      case 93:
         return jjStopAtPos(0, 45);
      case 98:
         return jjMoveStringLiteralDfa1_0(0x8000L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0x400L);
      case 110:
         return jjMoveStringLiteralDfa1_0(0x800L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x10000000L);
      case 118:
         return jjMoveStringLiteralDfa1_0(0x1000L);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
static private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 42:
         if ((active0 & 0x40000000000000L) != 0L)
            return jjStartNfaWithStates_0(1, 54, 14);
         break;
      case 47:
         if ((active0 & 0x80000000000000L) != 0L)
            return jjStopAtPos(1, 55);
         break;
      case 61:
         if ((active0 & 0x400000000L) != 0L)
            return jjStopAtPos(1, 34);
         else if ((active0 & 0x1000000000L) != 0L)
            return jjStopAtPos(1, 36);
         else if ((active0 & 0x2000000000L) != 0L)
            return jjStopAtPos(1, 37);
         else if ((active0 & 0x4000000000L) != 0L)
            return jjStopAtPos(1, 38);
         else if ((active0 & 0x8000000000000L) != 0L)
            return jjStopAtPos(1, 51);
         break;
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x20000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x13a00800L);
      case 102:
         if ((active0 & 0x20L) != 0L)
            return jjStartNfaWithStates_0(1, 5, 1);
         break;
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x50000L);
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0x4000L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x48100700L);
      case 111:
         if ((active0 & 0x40L) != 0L)
            return jjStartNfaWithStates_0(1, 6, 1);
         return jjMoveStringLiteralDfa2_0(active0, 0x409000L);
      case 114:
         if ((active0 & 0x80L) != 0L)
            return jjStartNfaWithStates_0(1, 7, 1);
         return jjMoveStringLiteralDfa2_0(active0, 0x24082000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
static private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x10000000L);
      case 99:
         return jjMoveStringLiteralDfa3_0(active0, 0x2800000L);
      case 100:
         if ((active0 & 0x100L) != 0L)
            return jjStartNfaWithStates_0(2, 8, 1);
         else if ((active0 & 0x200L) != 0L)
         {
            jjmatchedKind = 9;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x48100000L);
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x10000L);
      case 103:
         return jjMoveStringLiteralDfa3_0(active0, 0x200000L);
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0xc1000L);
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000L);
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x400000L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x24008000L);
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000L);
      case 116:
         if ((active0 & 0x400L) != 0L)
            return jjStartNfaWithStates_0(2, 10, 1);
         return jjMoveStringLiteralDfa3_0(active0, 0x1000000L);
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000L);
      case 119:
         if ((active0 & 0x800L) != 0L)
            return jjStartNfaWithStates_0(2, 11, 1);
         break;
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
static private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 73:
         return jjMoveStringLiteralDfa4_0(active0, 0x100000L);
      case 82:
         return jjMoveStringLiteralDfa4_0(active0, 0x40000000L);
      case 87:
         return jjMoveStringLiteralDfa4_0(active0, 0x8000000L);
      case 99:
         return jjMoveStringLiteralDfa4_0(active0, 0x20000000L);
      case 100:
         if ((active0 & 0x1000L) != 0L)
            return jjStartNfaWithStates_0(3, 12, 1);
         return jjMoveStringLiteralDfa4_0(active0, 0x10000000L);
      case 101:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(3, 13, 1);
         else if ((active0 & 0x4000L) != 0L)
            return jjStartNfaWithStates_0(3, 14, 1);
         break;
      case 103:
         return jjMoveStringLiteralDfa4_0(active0, 0x4000000L);
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x200000L);
      case 108:
         if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(3, 15, 1);
         return jjMoveStringLiteralDfa4_0(active0, 0x2040000L);
      case 110:
         if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(3, 16, 1);
         break;
      case 111:
         return jjMoveStringLiteralDfa4_0(active0, 0x800000L);
      case 115:
         return jjMoveStringLiteralDfa4_0(active0, 0x420000L);
      case 116:
         return jjMoveStringLiteralDfa4_0(active0, 0x80000L);
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x1000000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
static private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000000L);
      case 101:
         if ((active0 & 0x20000L) != 0L)
            return jjStartNfaWithStates_0(4, 17, 1);
         else if ((active0 & 0x40000L) != 0L)
            return jjStartNfaWithStates_0(4, 18, 1);
         else if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(4, 19, 1);
         return jjMoveStringLiteralDfa5_0(active0, 0x60000000L);
      case 102:
         if ((active0 & 0x100000L) != 0L)
            return jjStartNfaWithStates_0(4, 20, 1);
         break;
      case 104:
         return jjMoveStringLiteralDfa5_0(active0, 0x8000000L);
      case 110:
         if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(4, 21, 1);
         break;
      case 111:
         return jjMoveStringLiteralDfa5_0(active0, 0x10000000L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x5800000L);
      case 116:
         if ((active0 & 0x400000L) != 0L)
            return jjStartNfaWithStates_0(4, 22, 1);
         break;
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
static private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa6_0(active0, 0x4000000L);
      case 99:
         return jjMoveStringLiteralDfa6_0(active0, 0x40000000L);
      case 100:
         if ((active0 & 0x800000L) != 0L)
            return jjStartNfaWithStates_0(5, 23, 1);
         return jjMoveStringLiteralDfa6_0(active0, 0x20000000L);
      case 105:
         return jjMoveStringLiteralDfa6_0(active0, 0x8000000L);
      case 110:
         if ((active0 & 0x1000000L) != 0L)
            return jjStartNfaWithStates_0(5, 24, 1);
         return jjMoveStringLiteralDfa6_0(active0, 0x10000000L);
      case 114:
         return jjMoveStringLiteralDfa6_0(active0, 0x2000000L);
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
static private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x2000000L) != 0L)
            return jjStartNfaWithStates_0(6, 25, 1);
         break;
      case 108:
         return jjMoveStringLiteralDfa7_0(active0, 0x18000000L);
      case 109:
         if ((active0 & 0x4000000L) != 0L)
            return jjStartNfaWithStates_0(6, 26, 1);
         break;
      case 111:
         return jjMoveStringLiteralDfa7_0(active0, 0x40000000L);
      case 117:
         return jjMoveStringLiteralDfa7_0(active0, 0x20000000L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
static private int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 101:
         if ((active0 & 0x8000000L) != 0L)
            return jjStartNfaWithStates_0(7, 27, 1);
         break;
      case 114:
         return jjMoveStringLiteralDfa8_0(active0, 0x60000000L);
      case 121:
         if ((active0 & 0x10000000L) != 0L)
            return jjStartNfaWithStates_0(7, 28, 1);
         break;
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
static private int jjMoveStringLiteralDfa8_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 100:
         if ((active0 & 0x40000000L) != 0L)
            return jjStartNfaWithStates_0(8, 30, 1);
         break;
      case 101:
         if ((active0 & 0x20000000L) != 0L)
            return jjStartNfaWithStates_0(8, 29, 1);
         break;
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
static private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 13;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 13:
                  if ((0xffffff7b00000000L & l) != 0L)
                     jjCheckNAddTwoStates(4, 5);
                  else if (curChar == 34)
                  {
                     if (kind > 60)
                        kind = 60;
                  }
                  break;
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 59)
                        kind = 59;
                     jjCheckNAdd(2);
                  }
                  else if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 6;
                  else if (curChar == 34)
                     jjCheckNAddTwoStates(4, 5);
                  break;
               case 14:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddStates(0, 2);
                  else if (curChar == 42)
                     jjstateSet[jjnewStateCnt++] = 10;
                  if (curChar == 42)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 1:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 58)
                     kind = 58;
                  jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 2:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 59)
                     kind = 59;
                  jjCheckNAdd(2);
                  break;
               case 3:
                  if (curChar == 34)
                     jjCheckNAddTwoStates(4, 5);
                  break;
               case 4:
                  if ((0xffffff7b00000000L & l) != 0L)
                     jjCheckNAddTwoStates(4, 5);
                  break;
               case 5:
                  if (curChar == 34 && kind > 60)
                     kind = 60;
                  break;
               case 6:
                  if (curChar == 42)
                     jjCheckNAddStates(0, 2);
                  break;
               case 7:
                  if ((0xfffffbffffffffffL & l) != 0L)
                     jjCheckNAddStates(0, 2);
                  break;
               case 8:
                  if (curChar == 42)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 9:
                  if ((0xffff7fffffffffffL & l) != 0L)
                     jjCheckNAddStates(0, 2);
                  break;
               case 10:
                  if (curChar == 47 && kind > 61)
                     kind = 61;
                  break;
               case 11:
                  if (curChar == 42)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 12:
                  if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 13:
               case 4:
                  if ((0x3ffffffeffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(4, 5);
                  break;
               case 0:
               case 1:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 58)
                     kind = 58;
                  jjCheckNAdd(1);
                  break;
               case 14:
               case 7:
               case 9:
                  jjCheckNAddStates(0, 2);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 14:
               case 7:
               case 9:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddStates(0, 2);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 13 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   7, 8, 11, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, "\111\146", "\104\157", "\117\162", 
"\101\156\144", "\105\156\144", "\151\156\164", "\156\145\167", "\166\157\151\144", 
"\124\162\165\145", "\105\154\163\145", "\142\157\157\154", "\124\150\145\156", 
"\106\141\154\163\145", "\127\150\151\154\145", "\127\162\151\164\145", "\105\156\144\111\146", 
"\102\145\147\151\156", "\103\157\156\163\164", "\122\145\143\157\162\144", 
"\122\145\164\165\162\156", "\104\145\143\154\141\162\145", "\120\162\157\147\162\141\155", 
"\105\156\144\127\150\151\154\145", "\162\145\141\144\157\156\154\171", "\120\162\157\143\145\144\165\162\145", 
"\105\156\144\122\145\143\157\162\144", null, null, "\74", "\74\75", "\76", "\76\75", "\75\75", "\41\75", "\53", 
"\55", "\52", "\57", "\45", "\133", "\135", "\56", "\43", "\50", "\51", "\54", 
"\72\75", "\73", "\75", "\57\52", "\52\57", "\42", null, null, null, null, null, };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0x1dfffffe7fffffe1L, 
};
static final long[] jjtoSkip = {
   0x200000000000001eL, 
};
static final long[] jjtoSpecial = {
   0x2000000000000000L, 
};
static protected SimpleCharStream input_stream;
static private final int[] jjrounds = new int[13];
static private final int[] jjstateSet = new int[26];
static protected char curChar;
/** Constructor. */
public ParserTokenManager(SimpleCharStream stream){
   if (input_stream != null)
      throw new TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", TokenMgrError.STATIC_LEXER_ERROR);
   input_stream = stream;
}

/** Constructor. */
public ParserTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
static public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
static private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 13; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
static public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
static public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

static protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

static int curLexState = 0;
static int defaultLexState = 0;
static int jjnewStateCnt;
static int jjround;
static int jjmatchedPos;
static int jjmatchedKind;

/** Get the next Token. */
public static Token getNextToken() 
{
  Token specialToken = null;
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      matchedToken.specialToken = specialToken;
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         matchedToken.specialToken = specialToken;
         return matchedToken;
      }
      else
      {
         if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
         {
            matchedToken = jjFillToken();
            if (specialToken == null)
               specialToken = matchedToken;
            else
            {
               matchedToken.specialToken = specialToken;
               specialToken = (specialToken.next = matchedToken);
            }
         }
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

static private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
static private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
static private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

static private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}