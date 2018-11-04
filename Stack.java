// This is the class for a generic stack (FILO)
// Your header comments here

// @author <Paul Lanham>
// CS310 Spring 2018
// Project 3
// George Mason University
// File Name: Stack.java

public class Stack<AnyType>{

	// You decide internal attributtes or additional classes to implement a stack
	// All those must be private
	private AnyType stack[];
   private int tos;
   private int size;
   
	public Stack() {
		// Stack Constructor
		// use this to initialize internal attributes
		// could be empty
      size = 10;
      stack = (AnyType[])new Object[size];
      tos = -1;
	}
   
   //private helper method for resizing stack
   private void resize(AnyType stack[]){
      AnyType temp[] = stack;
      stack = (AnyType[])new Object[size*2];
      for(int i = 0; i < size; i++){
         stack[i] = temp[i];
      }
   }

	public boolean isEmpty() {
		// check if stack is empty
		// return true if empty, false otherwise
		// O(1)
		return tos == -1;
	}

	public AnyType peek() {
		// peek the stack top
		// return stack top but do not pop
		// return null for empty stack
		// O(1)
      if(isEmpty())
         return null;
      
		return stack[tos];
	}

	public void push(AnyType value) {
		// push an element to the stack
		// O(1)
      if(tos == size - 1)
         resize(stack);
         
      stack[++tos] = value;
	}

	public AnyType pop(){
		// pop from the top
		// remove and return the stack top
		// return null for empty stack
		// O(1)
      if(isEmpty())
         return null;
      
      return stack[tos--];
	}

	//----------------------------------------------------
	// example testing code... make sure you pass all ...
	// and edit this as much as you want!

	public static void main(String[] args){
		Stack<Integer> iStack = new Stack<Integer>();
		if (iStack.isEmpty() && iStack.peek()==null){
			System.out.println("Yay 1");
		}
		
		iStack.push(new Integer(12));
		iStack.push(new Integer(20));
		iStack.push(new Integer(-233));
		
		if (iStack.pop()==-233 && iStack.peek()==20 ){
			System.out.println("Yay 2");
		}
		
		if (iStack.pop()==20 && iStack.pop()==12 && iStack.isEmpty() ){
			System.out.println("Yay 3");
		}

		
	}

}