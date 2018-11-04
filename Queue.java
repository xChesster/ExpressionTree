// This is the class for a generic queue (FIFO)
// Your header comments here

// @author <Paul Lanham>
// CS310 Spring 2018
// Project 3
// George Mason University
// File Name: Queue.java

public class Queue<AnyType>{

	// You decide internal attributtes or additional classes to implement a queue
	// All those must be private
	private ListNode<AnyType> front, back;
   private int length;
   
	public Queue() {
		// Queue Constructor: should not need any argument
		// initialize your internal attributes, could be empty
      front = new ListNode<AnyType>(null);
      back = front;
      length = 0;
	}
   
   //private ListNode class
   private class ListNode<AnyType>{
   
      private ListNode<AnyType> next;
      private AnyType value;
      
      public ListNode(AnyType value){
         this.value = value;
         this.next = null;   
      }
      
   }

	public boolean isEmpty() {
		// check if queue is empty 
		// return true if empty, false otherwise
		// O(1)
      return length == 0;
	}

	public AnyType getFront() {
		// peek the front element
		// return the front element if there is any but do not dequeue
		// return null if queue is empty
		// O(1)
      if(isEmpty())
         return null;
         
		return front.value;
	}

	public void enqueue(AnyType value) {
      // add an element to the back
		// O(1)
		ListNode<AnyType> temp = new ListNode<AnyType>(value);
      
      //no values have been added to queue 
      if(isEmpty()){
         front = temp;
         back = front;
      }
         
      back.next = temp;
      back = temp;
      length++;
	}

	public AnyType dequeue(){
      // remove and return an element from the front
   	// return null if queue empty
		// O(1)
            
		//no values to remove
      if(isEmpty()){
         return null;
      }
         
      ListNode<AnyType> temp = new ListNode<AnyType>(front.value);
      
      //only one node in list
      if(length == 1){
         front.value = null;
         back = front;
         length--;
         return temp.value;
      }
      
      length--;
      front = front.next;
      return temp.value;
	}

	//----------------------------------------------------
	// example testing code... make sure you pass all ...
	// and edit this as much as you want!

	public static void main(String[] args){
		Queue<Integer> iq = new Queue<Integer>();
		if (iq.isEmpty() && iq.getFront()==null){
			System.out.println("Yay 1");
		}
		
		iq.enqueue(new Integer(12));
		iq.enqueue(new Integer(20));
		iq.enqueue(new Integer(-233));
      
		if (iq.dequeue()==12 && iq.getFront()==20 ){
			System.out.println("Yay 2");
		}
      
		if (iq.dequeue()==20 && iq.dequeue()==-233 && iq.isEmpty() ){
		   System.out.println("Yay 3");
		}
		
	}

  }