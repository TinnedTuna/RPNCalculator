/*
 * This is an advanced reverse polish notation calculator.
 * Language:
 * +  -- add the top two operands
 * -  -- subtract the top two operands
 * *  -- multiply the top two operands
 * ^  -- clear the top operand
 * &  -- Swap the top two operands
 * $  -- duplicate the top two operands
 * [  -- enter a loop if the top is not zero
 * ]  -- exit the loop
 * ?  -- skip an instruction if the top is not zero
 * !  -- print debugging information
 * ,  -- store at the addr on the top of the stack, the next value on the stack
 * .  -- load from the addr at the top of the stack.
 */

package rpncalculator;

import java.util.Stack;
import java.util.ArrayList;

/**
 *
 * @author tuna
 */
public class RPNCalc {
     private Stack<Integer> mainStack;
     private Stack<Integer> loopStack; // Stack for holding loop depth in.
     private Integer currentPosition; // The position on the input string
     private String inputString; // The input string.
     private ArrayList<Integer> mainMemory; // The main memory
     
     /**
      * Initialize the stacks and ensure they're empty.
      */
     public RPNCalc() {
         this.mainStack = new Stack<Integer>();
         this.currentPosition = 0;
         this.loopStack = new Stack<Integer>();
         this.mainMemory = new ArrayList<Integer>(); // The main memory 
         for (int i = 0; i < 1024; i++) {
             this.mainMemory.add(0);
         }
     }
     
     /**
      * Reset this RPNCalcultor.
      */
     public void reset() {
         this.mainStack = new Stack<Integer>();
         this.currentPosition = 0;
         this.loopStack = new Stack<Integer>();
         this.mainMemory = new ArrayList<Integer>(); // The main memory 
         for (int i = 0; i < 1024; i++) {
             this.mainMemory.add(0);
         }
     }
     
     /**
      * pop the first two elements off the stack, push the sum.
      */
     private void add() {
         Integer a, b, c;
         a = this.mainStack.pop();
         b = this.mainStack.pop();
         c = a + b;
         this.mainStack.push(c);
     }
     
     /**
      * pop the first two elements off the stack, push the difference.
      */
     private void sub() {
         Integer a, b, c;
         a = this.mainStack.pop();
         b = this.mainStack.pop();
         c = b - a;
         this.mainStack.push(c);
     }
     
     /**
      * pop the first two elements off the stack, push the product.
      */
     private void mul() {
          Integer a, b, c;
          a = this.mainStack.pop();
          b = this.mainStack.pop();
          c = a * b;
          this.mainStack.push(c); 
      }
      
      /**
       * Clear the top of the stack.
       */
      private void clr() {
          this.mainStack.pop();
      }
      
      /**
       * Duplicate the top of the stack.
       */
      private void dup() {
          Integer temp = this.mainStack.pop();
          this.mainStack.push(temp);
          this.mainStack.push(temp);
      }
      
      /**
       * Swap the order of the top two elements.
       */
      private void swp() {
          Integer a, b;
          a = this.mainStack.pop();
          b = this.mainStack.pop();
          this.mainStack.push(a);
          this.mainStack.push(b);
      }
      
      /**
       * Perform the necessary housekeeping for entering a loop.
       * Loops terminate when the top of the stack is 0. If it's 0 on entering
       * the loop, simply skip to the end.
       */
      private void enterLoop() {
          Integer topOfStack = this.mainStack.peek();
          Integer nestedLoops = 1;
          if (topOfStack == 0) {
              // This loop is over, advance to the end
              while (nestedLoops != 0) {
                  // Keep track of how deep the loops are we're going past so 
                  // we can exit correctly.
                  if (this.inputString.charAt(this.currentPosition) == '[') {
                      // We're in a new loop.
                      nestedLoops++;
                  }
                  else if (this.inputString.charAt(this.currentPosition) 
                                       == ']') {
                      // We've just left a loop.
                      nestedLoops--;
                  }
                  this.currentPosition++;
              }
              return;
          }
          else {
              // Enter the loop.
              this.loopStack.push(this.currentPosition);
              return;
          }
      }
      
      /**
       * Perform the necessary house keeping when exiting a loop, e.g. 
       * messing with the loopStack.
       */
      private void exitLoop() {
          Integer topOfStack = this.mainStack.peek();
          if (topOfStack == 0) {
              // The loop is finished, take it off the loopStack and continue
              // with the rest of the program.
              this.loopStack.pop();
              return;
              
          }
          else {
              // The loop has not yet finished, oh take me back to the start.
              this.currentPosition = this.loopStack.peek();
          }
      }
      
      /**
       * Load a value from memory to the top of the stack. The top of stack is
       * popped and used as the address
       */
      private void ld() {
          Integer addr = this.mainStack.pop();
          Integer value = this.mainMemory.get(addr);
          this.mainStack.push(value);
      }
      
      /**
       * Store a value to a given place in memory. The top of the stack is the 
       * location in memory, the next is the value to put into it.
       */
      private void str() {
          Integer addr = this.mainStack.pop();
          Integer val = this.mainStack.pop();
          this.mainMemory.set(addr, val);
      }
      
      /**
       * Implementation for skip if not 0.
       */
      private void iff() {
          Integer val = this.mainStack.pop();
          if (val == 0) {
              this.step(this.inputString.charAt(this.currentPosition+1));
          }
          else {
              this.step(this.inputString.charAt(this.currentPosition+2));
          }
          this.currentPosition+=2;
      }
     
      /**
       * Run a step for a single input symbol.
       * @param input the input symbol
       */
     private void step(char input) {
         Integer currentValue;
         if (input == '+') {
                 this.add();
             }
             else if (input == '-') {
                 this.sub();
             }
             else if (input == '*') {
                 this.mul();
             }
             else if (input == '^') {
                 this.clr();
             }
             else if (input == '?') {
                 this.iff();
             }
             else if (input == '[') {
                 this.enterLoop();
             }
             else if (input == ']') {
                 this.exitLoop();
             }
             else if (input == '$') {
                 this.dup();
             }
             else if (input == '&') {
                 this.swp();
             }
             else if (input == '.') {
                 this.ld();
             }
             else if (input == ',') {
                 this.str();
             }
             else if (input == '!') {
                 // Debugging info
                 System.out.println("--------------------");
                 System.out.println("Current Position in input string: " +
                         this.currentPosition.toString());
                 System.out.println("Current Main Stack: " + 
                         this.mainStack.toString());
                 System.out.println("Current Loop Stack: " + 
                         this.loopStack.toString());
             }
        else {
                 currentValue = Integer.valueOf(input)-48;
                 if (currentValue >= 0 ||  currentValue <= 9) {
                    this.mainStack.push(currentValue);
                 }
        }
     }
      
     /**
      * Process an input string, returning the top of the stack as the result
      * @param input The input String
      * @return the result on the top of the stack.
      */
     public Integer run(String input) {
         this.inputString = input;
         for (this.currentPosition = 0; this.currentPosition<input.length(); 
                     this.currentPosition++) {
             this.step(input.charAt(this.currentPosition));
         }
         return this.mainStack.pop();
     }
}
