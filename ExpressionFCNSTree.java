import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// @author <Paul Lanham>
// CS310 Spring 2018
// Project 3
// George Mason University
// File Name: ExpressionFCNSTree.java

public class ExpressionFCNSTree{

	//==========================
	// DO NOT CHANGE
	

	FCNSTreeNode root;
	public ExpressionFCNSTree(){
		root = null;
	}
	
	public ExpressionFCNSTree(FCNSTreeNode root){
		this.root = root;
	}

	public boolean equals(ExpressionFCNSTree another){
		return root.equals(another.root);
	
	}
	
	// END OF DO NOT CHANGE
	//==========================
	
		
	public int size(){
		// return how many nodes are there in the tree
      
      //node used to traverse tree
      FCNSTreeNode node = root;
      if(node == null) return 0;
      
      return 1 + new ExpressionFCNSTree(node.firstChild).size() + new ExpressionFCNSTree(node.nextSibling).size();
   }

	public int height(){
		// return the height of the tree
		// return -1 for null tree
		FCNSTreeNode node = root;
      if(root == null) return -1;
      
      if(node == null) return 0;
      
      // comparing the maximum height of the tree's left branches against the maximum height
      // of the tree's right branches to find the tree's maximum height
      else{
         int leftHeight = -1;
         int rightHeight = -1;
         if(node.firstChild != null){
            leftHeight += 1 + new ExpressionFCNSTree(node.firstChild).size();
         }
         if(node.nextSibling != null){
            rightHeight += 1 + new ExpressionFCNSTree(node.nextSibling).size();
         }
         return Math.max(leftHeight, rightHeight);
      }
	}
	
	public int countNode(String s){
		// count how many nodes in the tree are with the specified symbol s
		FCNSTreeNode node = root;
      //count variable used to count nodes containing string s
      int count = 0;
      if(node == null)
         return 0;
      
      //adding to count when node's element is equal to string s
      if(node.element.equals(s))
         count++;
      
      return count + new ExpressionFCNSTree(node.firstChild).countNode(s) + new ExpressionFCNSTree(node.nextSibling).countNode(s);
	}
		
	
	public int countNan(){
		// count and return how many nodes are marked as not-a-number
		FCNSTreeNode node = root;
      //count variable used to count nodes that aren't numbers
      int count = 0;
      if(node == null)
         return 0;
      
      //adding to count if node isn't a number
      if(node.nan)
         count++;
      
      return count + new ExpressionFCNSTree(node.firstChild).countNan() + new ExpressionFCNSTree(node.nextSibling).countNan();	
	}	
	
	public String toStringPreFix(){
		// return a string representation of pre-order tree traversal
		// there should be exactly one single space after each tree node 
		// see main method below for examples
      FCNSTreeNode node = root;
      String str = "";
      if(node == null)
         return "";
      
      str += new ExpressionFCNSTree(node.firstChild).toStringPreFix();
      
      str += new ExpressionFCNSTree(node.nextSibling).toStringPreFix();
      
      return node.element + " " + str;
	}
	
	public String toStringPostFix(){
		// return a string representation of post-order tree traversal
		// there should be exactly one single space after each tree node 
		// see main method below for examples
      FCNSTreeNode node = root;
      String str = "";
      if(node == null)
         return "";
      
      str += new ExpressionFCNSTree(node.firstChild).toStringPostFix();
      
      str += new ExpressionFCNSTree(node.nextSibling).toStringPostFix();
            
      return str + node.element + " ";
	}

   public String toStringLevelOrder(){
		// return a string representation of level-order (breadth-first) tree traversal
		// there should be exactly one single space after each tree node 
		// see main method below for examples
      
      //converting expression tree to binary tree
      ExpressionBinaryTree btree = new ExpressionFCNSTree(root).buildBinaryTree();
      BinaryTreeNode node = btree.root;
      String str = "";
      //initializing a queue and a tempQueue
      //queue is used as the main queue
      //tempQueue is used to store children from the main queue until they can be transferred to the main queue
      Queue queue = new Queue();
      Queue tempQueue = new Queue();
      queue.enqueue(node);
      str += node.element + " ";
      
      //loops until there are no more nodes in the main queue
      //and the tree has been fully traversed
      while(!queue.isEmpty()){
         //moves down the left side of the tree until node.left is null
         if(node.left == null){
            //the nodes stored in the main queue are dequeued one by one and their right children are added
            //to the tempQueue until there are no more elements in the main queue. The tempQueue elements are then
            //transferred to queue
            while(!queue.isEmpty()){
               node = (BinaryTreeNode)queue.getFront();
               queue.dequeue();
               node = node.right;
               if(node != null){
                  //node is only added to the tempQueue if it has right and left children
                  if(node.left != null && node.right != null)
                     tempQueue.enqueue(node);
                  //adding the node's element followed by a space to str
                  str += node.element + " ";
               }
            }
            //when tempQueue is empty there are no nodes to transfer to the main queue
            //which means there are no more nodes to look at and the tree has been fully traversed. The loop
            //breaks and the string str is returned
            if(tempQueue.isEmpty())
               break;
            //transferring the nodes stored in tempQueue to queue until tempQueue is empty
            while(!tempQueue.isEmpty()){
               queue.enqueue(tempQueue.dequeue());
            }
            node = (BinaryTreeNode)queue.getFront();
         }
         node = node.left;
         //node is stored in queue only if it has right and left children
         if(node.left != null && node.right != null)
            queue.enqueue(node);
         str += node.element + " ";
      }
      return str;
   }

	public void buildTree(String fileName) throws FileNotFoundException{
		// This method need to open file specified by the string fileName, 
		// read in a one-line numeric expression in prefix notation, and 
		// construct a first-child-next-sibling expression tree base on the input
      
		// set root to be the newly constructed tree root	
		// if there is any exception, root should be null
		Scanner sc = new Scanner(new File(fileName));
      FCNSTreeNode node = new FCNSTreeNode(sc.next());
      root = node;
      FCNSTreeNode current;
      Stack stack = new Stack();
      //numCount is used to keep track of consecutive numbers
      int numCount = 0;
      //pop variable is used to let the loop know when a "~" has been used
      boolean pop = false;
      
      //loops until all the values in the file have been scanned
      while(sc.hasNext()){
         current = new FCNSTreeNode(sc.next());
         
         //checking if the previous node was an operator
         if(hasOperator(node)){
            if(hasOperator(current)){
               node.firstChild = current;
               node = current;
               stack.push(node);
               numCount = 0;
            }
            //current node isn't an operator
            else{
               node.firstChild = current;
               node = current;
               numCount++;
            }
         }
         //if previous node isn't an operator
         else{
            //if the stack isn't empty and there have been two consecutive number or the element on top of the stack is a "~"
            //an element is popped from the stack
            if(!stack.isEmpty() && (numCount == 2 || new ExpressionFCNSTree((FCNSTreeNode)stack.peek()).root.element.equals("~"))){
               //if the element on top of the stack is "~", pop is set to true
               if(new ExpressionFCNSTree((FCNSTreeNode)(stack.peek())).root.element.equals("~"))
                  pop = true;
                  
               node = (FCNSTreeNode)stack.pop();
               node.nextSibling = current;
               node = current;
               numCount = 0;
            }
            //number is added to tree and know operators need to be popped from the stack unless the boolean
            //pop is set to true
            else{
               if(pop){
                  node = (FCNSTreeNode)stack.pop();
                  node.nextSibling = current;
                  node = current;
                  numCount = 0;
                  pop = false;
                  continue;
               }
               node.nextSibling = current;
               node = current;
               numCount++;
               
               //if the current node is an operator, numCount is reset
               if(hasOperator(node))
                  numCount = 0;
            }
         }
      }
	}
   
   //checks if the a node's element is an operator or an operand
   private boolean hasOperator(FCNSTreeNode node){
   
      switch(node.element){
         case "+":
         case "-":
         case "*":
         case "/":
         case "%":
         case "~":
            return true;
      }
      return false;
   }

	public ExpressionBinaryTree buildBinaryTree(){
		// construct the binary tree representation of this expression 
		// and return the tree root
      
      return new ExpressionBinaryTree(buildBinaryTreeNode(root));
	}
   
   //private helper method used for building binary tree
   private BinaryTreeNode buildBinaryTreeNode(FCNSTreeNode node){
      
      if(node == null)
         return null;
      if(node.firstChild == null)
         return new BinaryTreeNode(node.element, null, null);
      if(node.firstChild.nextSibling == null){
         return new BinaryTreeNode(node.element, buildBinaryTreeNode(node.firstChild), null);
      }
      
      return new BinaryTreeNode(node.element, buildBinaryTreeNode(node.firstChild), buildBinaryTreeNode(node.firstChild.nextSibling));
   }
	
	public String toStringPrettyInFix(){
		// return a string representation as the normal human-friendly infix expression
		// it is like we are simulating in-order tree traversal of the binary tree
		// Format of the infix string: 
		//		- there should be no space, but  
		//		- parenthesizes must be inserted wrapping around the sub-expression of each operator
		// see main method below for examples
		FCNSTreeNode node = root;
      
      if(node.firstChild == null)
         return node.element;
      if(node.firstChild.nextSibling == null && node.element.equals("~"))
         return "(-" + new ExpressionFCNSTree(node.firstChild).toStringPrettyInFix() + ")";
      if(node.firstChild.nextSibling == null)
         return new ExpressionFCNSTree(node.firstChild).toStringPrettyInFix() + node.element;
 
      return "(" + new ExpressionFCNSTree(node.firstChild).toStringPrettyInFix() + node.element + new ExpressionFCNSTree(node.firstChild.nextSibling).toStringPrettyInFix()+ ")";
	}

	public Integer evaluate(){
		// This method evaluates the expression and marks every tree node with:
		//    - operand node: node.value should be the integer value of the operand
		//    - operator node: node.value should be the integer value associated with
		//                     the sub-expression rooted at the node 
		// Return: the integer value of the root node 
		
		// return null for null tree
		
		// if there is a division by zero: keep node.value to be null and set node.nan to be true.
		// not-a-number should be propagated: for an operator, if any of its operand is not-a-number,
		//			then the node of this operator should also be marked as not-a-number
		
	   FCNSTreeNode node = root;
      //null tree
      if(root == null) return null;
      //returns 0 if node is empty
      if(node == null)
         return 0;
      
      //checking to see what the node's element is and applying its case to the evaluation
      switch(node.element){
         
         case "+":
            node.value = new ExpressionFCNSTree(node.firstChild).evaluate() + new ExpressionFCNSTree(node.firstChild.nextSibling).evaluate();
            break;
         case "-":
            node.value = new ExpressionFCNSTree(node.firstChild).evaluate() - new ExpressionFCNSTree(node.firstChild.nextSibling).evaluate();
            break;
         case "*":
            node.value = new ExpressionFCNSTree(node.firstChild).evaluate() * new ExpressionFCNSTree(node.firstChild.nextSibling).evaluate();
            break;
         case "/":
            //checking for division by zero
            if(new ExpressionFCNSTree(node.firstChild.nextSibling).evaluate() == 0){
               node.value = null;
               break;
            }
            node.value = new ExpressionFCNSTree(node.firstChild).evaluate() / new ExpressionFCNSTree(node.firstChild.nextSibling).evaluate();
            break;
         case "%":
            node.value = new ExpressionFCNSTree(node.firstChild).evaluate() % new ExpressionFCNSTree(node.firstChild.nextSibling).evaluate();
            break;
         case "~":
            node.value = new ExpressionFCNSTree(node.firstChild).evaluate() * -1;
            break;
         
         //node is an operand so there is no special case
         default:
            node.value = Integer.parseInt(node.element);
            break;
      }
      //if the value of node is null, node.nan is set to true
      if(node.value == null)
         node.nan = true;
      return node.value;
	}
	
	public Integer evaluateNonRec(){
		// This method evaluates the expression leaving the tree unchanged 
		// You must implement it as a non-recursive method
		// Return: the integer value of answer
		
		// return null for null tree
		
		// For this method only, assume there are no division-by-zero issues in the input
		
      //returns null if root node is null
		if(root == null){
         return null;
      }
      
      //evalStr is the expression in preorder form
      String evalStr = new ExpressionFCNSTree(root).toStringPreFix();
      //splitting the string up by its spaces to form an array of the operations
      String[] eval = evalStr.split(" ");
      //operationStack is used to hold the operations contained in the string[]
      Stack operationStack = new Stack();
      //valueStack is used to hold the values contained in the string[] and the values are used with the operation stack
      //when an operation has to be performed
      Stack valueStack = new Stack();
      //two Integer variables used when popping values from valueStack so that operations can be performed on them
      Integer a, b;
      //operation string used to hold the type of operation to be performed
      String operation = "";
      //numCount is used to keep track of consecutive numbers
      int numCount = 0;
      
      //looping through the length of the eval array
      for(int i = 0; i < eval.length; i++){
         
         //element at i in eval is an operator and is pushed onto the operatorStack
         //numCount is reset to 0
         if(isOperator(eval[i])){
            operationStack.push(eval[i]);
            numCount = 0;
         }
         
         //element at in in eval is an operand and is pushed onto the valueStack
         //numCount is incremented
         else{
            numCount++;
            valueStack.push(Integer.parseInt(eval[i]));
            
            //checking for ~
            //if "~" is on top of the operationStack, the value just pushed
            //onto the valueStack is popped so that the "~" can be applied to it and then the
            //value is pushed back on to the value stack
            if(operationStack.peek().equals("~")){
               operation = "" + operationStack.pop();
               a = (Integer)valueStack.pop();
               valueStack.push(a * -1);
            }
            //two numbers in a row
            //integers are popped from value stack and the operation on top of
            //the operationStack is applied to the values. The resulting value
            //is pushed on to the valueStack
            if(numCount == 2){
               b = (Integer)valueStack.pop();
               a = (Integer)valueStack.pop();
               
               operation = "" + operationStack.pop();
               
               //determining value by operation
               switch(operation){
                  
                  case "+":
                     valueStack.push(a + b);
                     break;
                  case "-":
                     valueStack.push(a - b);
                     break;
                  case "*":
                     valueStack.push(a * b);
                     break;
                  case "/":
                     valueStack.push(a / b);
                     break;
                  case "%":
                     valueStack.push(a % b);
                     break;
               }
               //resetting numCount
               numCount = 0;
            }
         }
      }
      //looping until all operations are used
      while(!operationStack.isEmpty()){
         
         operation = "" + operationStack.pop();
         
         //checking for "~"
         if(operation.equals("~")){
            a = (Integer)valueStack.pop();
            valueStack.push(a * -1);
            continue;
         }
         
         b = (Integer)valueStack.pop();
         a = (Integer)valueStack.pop();
         
         //determining value by operation
         switch(operation){
                  
                  case "+":
                     valueStack.push(a + b);
                     break;
                  case "-":
                     valueStack.push(a - b);
                     break;
                  case "*":
                     valueStack.push(a * b);
                     break;
                  case "/":
                     valueStack.push(a / b);
                     break;
                  case "%":
                     valueStack.push(a % b);
                     break;
         }
      }
      //returns the one value remaining on valueStack which is the expression's value
      return (Integer)valueStack.pop();
	}
	   
	//checks if the a node's element is an operator or an operand
   private boolean isOperator(String element){
   
      switch(element){
         case "+":
         case "-":
         case "*":
         case "/":
         case "%":
         case "~":
            return true;
      }
      return false;
   }
	
	//----------------------------------------------------
	// example testing code... make sure you pass all ...
	// and edit this as much as you want!

	public static void main(String[] args) throws FileNotFoundException{
	
		//     *					    *
		//   /  \				   /
		//  /    \             1
		//  1     +			==>   \
		//       / \             +
		//      2   3			   /
		//                     2
		//						      \
		//                       3
		//
		// prefix: * 1 + 2 3 (expr1.txt)
		
		FCNSTreeNode n1 = new FCNSTreeNode("3");
		FCNSTreeNode n2 = new FCNSTreeNode("2",null,n1);
		FCNSTreeNode n3 = new FCNSTreeNode("+",n2,null);
		FCNSTreeNode n4 = new FCNSTreeNode("1",null,n3);
		FCNSTreeNode n5 = new FCNSTreeNode("*",n4,null);
		ExpressionFCNSTree etree = new ExpressionFCNSTree(n5);
		
		if (etree.size()==5 && etree.height()==4 && etree.countNan()==0 && etree.countNode("+") == 1){
			System.out.println("Yay 1");
		}
		
		if (etree.toStringPreFix().equals("* 1 + 2 3 ") && etree.toStringPrettyInFix().equals("(1*(2+3))")){
			System.out.println("Yay 2");
		
		}
		
		if (etree.toStringPostFix().equals("3 2 + 1 * ") && etree.toStringLevelOrder().equals("* 1 + 2 3 ")){
			System.out.println("Yay 3");
		
		}
      
		if (etree.evaluateNonRec() == 5)
			System.out.println("Yay 4");
		

		if (etree.evaluate() == 5  && n4.value==1 && n3.value==5 && !n5.nan){
			System.out.println("Yay 5");
		
		}
		
		ExpressionFCNSTree etree2 = new ExpressionFCNSTree();
		etree2.buildTree("expressions/expr1.txt"); // construct expression tree from pre-fix notation
		
		if (etree2.equals(etree)){
			System.out.println("Yay 6");
		}

		BinaryTreeNode bn1 = new BinaryTreeNode("1");
		BinaryTreeNode bn2 = new BinaryTreeNode("2");
		BinaryTreeNode bn3 = new BinaryTreeNode("3");
		BinaryTreeNode bn4 = new BinaryTreeNode("+",bn2,bn3);
		BinaryTreeNode bn5 = new BinaryTreeNode("*",bn1,bn4);
		ExpressionBinaryTree btree = new ExpressionBinaryTree(bn5);
		
		//construct binary tree from first-child-next-sibling tree
		ExpressionBinaryTree btree2 = etree.buildBinaryTree();
             
		if (btree2.equals(btree)){
			System.out.println("Yay 7");
		}
		
		ExpressionFCNSTree etree3 = new ExpressionFCNSTree();
		etree3.buildTree("expressions/expr5.txt"); // an example of an expression with division-by-zero
		if (etree3.evaluate() == null && etree3.countNan() == 1){
			System.out.println("Yay 8");
		}	
	}
}


//=======================================
// Tree node class implemented for you
// DO NOT CHANGE
class FCNSTreeNode{
		   
	//members
	String element;	//symbol represented by the node, can be either operator or operand (integer)
	Boolean nan;	//boolean flag, set to be true if the expression is not-a-number
	Integer value;  //integer value associated with the node, used in evaluation
	FCNSTreeNode firstChild;
	FCNSTreeNode nextSibling;
		   
	//constructors
	public FCNSTreeNode(String el){
		element = el;
		nan = false;
		value = null;
		firstChild = null;
		nextSibling = null;
   }
   
	//constructors
	public FCNSTreeNode(String el,FCNSTreeNode fc, FCNSTreeNode ns ){
		element = el;
		nan = false;
		value = null;
		firstChild = fc;
		nextSibling = ns;
   }
   
   	
   	// toString
   	@Override 
   	public String toString(){
   		return element.toString();
   	}
   	
   	// compare two nodes 
   	// return true if: 1) they have the same element; and
   	//                 2) their have matching firstChild (subtree) and nextSibling (subtree)
   	public boolean equals(FCNSTreeNode another){
   		if (another==null)
   			return false;
   			
   		if (!this.element.equals(another.element))
   			return false;
   		
  		if (this.firstChild==null){
   			if (another.firstChild!=null)
   				return false;
   		}
   		else if (!this.firstChild.equals(another.firstChild))
   			return false;
   			
   		if (this.nextSibling==null){
   			if (another.nextSibling!=null)
   				return false;
   		}
   		else if (!this.nextSibling.equals(another.nextSibling))
   			return false;
   			
   		return true;
   	
   	}

}