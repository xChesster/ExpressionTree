// This is the class for binary tree node
// It is already implemented for you
// Do NOT change it

class BinaryTreeNode{
	   
	//members: all package visible
	String element;
	BinaryTreeNode left;
	BinaryTreeNode right;
   
	//constructors
	public BinaryTreeNode(String el){
		element = el;
		left = null;
		right = null;
	}
   
	//constructors
	public BinaryTreeNode(String el,BinaryTreeNode l, BinaryTreeNode r ){
		element = el;
		left = l;
		right = r;
	}
	
	// toString
   	@Override 
   	public String toString(){
   		return element.toString();
   	}
	
   	// compare two nodes 
   	// return true if: 1) they have the same element; and
   	//                 2) their have matching left (subtree) and right (subtree)

   	public boolean equals(BinaryTreeNode another){
   		if (another==null)
   			return false;
   			
   		if (!this.element.equals(another.element))
   			return false;
   		
  		if (this.left==null){
   			if (another.left!=null)
   				return false;
   		}
   		else if (!this.left.equals(another.left))
   			return false;
   			
   		if (this.right==null){
   			if (another.right!=null)
   				return false;
   		}
   		else if (!this.right.equals(another.right))
   			return false;
   			
   		return true;
   	
   	}


}

// This is the class for binary expression tree
// It is already implemented for you
// Do NOT change it

public class ExpressionBinaryTree{
		
	BinaryTreeNode root;
	public ExpressionBinaryTree(){
		root = null;
	}
	
	// constructor
	public ExpressionBinaryTree(BinaryTreeNode root){
		this.root = root;
	}

	// compare two trees
	public boolean equals(ExpressionBinaryTree another){
		return root.equals(another.root);
	
	}

}
