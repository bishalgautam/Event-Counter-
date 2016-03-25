
import java.util.*;

// Class Definitions
public class RedBlackTree<T extends Comparable<T>> {

	// Root initialized to nil.
	public RedBlackNode<T> nil = new RedBlackNode<T>();
	public int level=0;
	
	public RedBlackNode<T> root = nil;

    public RedBlackTree() {
        root.left = nil;
        root.right = nil;
        root.parent = nil;
    }
 
 // @param: eventList, the list containing the events 
 // @param: countList, the list containing the counts for the corresponding events. 
 // build a balanced binary tree and returns the root.
    
    public RedBlackNode<T> buildTree(ArrayList<T> eventList, ArrayList<Integer> countList, int start, int end){

//    	System.out.print("HEY HEY "+elementCount);
    	if (start > end) {
            return nil;
        }
    	
        /* Get the middle element and make it root */
        int mid = (start + end) / 2;
        RedBlackNode<T> node = new RedBlackNode<T>(eventList.get(mid),countList.get(mid));
         
        /* Recursively construct the left subtree and make it
         left child of root */
        node.left = buildTree(eventList,countList, start, mid - 1);
 
        /* Recursively construct the right subtree and make it
         right child of root */
        node.right = buildTree(eventList,countList, mid + 1, end);
         
        return node;
    	/*returns the root of the tree */
    	 
    }
    // @param: node, the root of the tree
    // build a RedBlackTree from a BBST assigning a parent pointer and returns the root.  
    
   public void levelOrderTraversal(RedBlackNode<T> node){
	    
       //@ value of level is passed after the tree is created and here it calculates the toatl number of black nodes
       double elementCount = Math.pow(2,level) - 1;
    	if(node ==null) return;
       // using queue for level order traversal 
        Queue<RedBlackNode<T>> queue=new LinkedList<RedBlackNode<T>>();  
          queue.add(node);  
           node.parent = nil;   
           while(!queue.isEmpty())  
           {  
//        	   System.out.print(elementCount );
//        	   System.out.print("Level Inside :"+ level + "\n");
        	  
        	  --elementCount;
              RedBlackNode<T> tempNode=queue.poll();
               //checks for the counts of blacknodes and colors red for the last level nodes
              if( (elementCount < 0) && tempNode != nil)
         	   		tempNode.color = 1;
              //System.out.print(tempNode.key + ":"+ tempNode.color+":"+ tempNode.getCounter()+ "  ");
              
           	   	if(tempNode.left!=nil)  
           	   	{  tempNode.left.parent = tempNode;
                       queue.add(tempNode.left);}  
           	   	if(tempNode.right!=nil){  
           	   		   tempNode.right.parent = tempNode;
                       queue.add(tempNode.right); }
               }  
 
    }  


    public int rangeCount(RedBlackNode<T> root, T lo, T hi){
    	 // Base case
    	if (root == nil) return 0;
    	// Special Optional case for improving efficiency
    	if (root.key.compareTo(hi) == 0 && root.key.compareTo(lo)== 0) return root.getCounter();
    	// If current node is in range, then include it in count and
        // recur for left and right children of it
        if (root.key.compareTo(hi) <= 0 && root.key.compareTo(lo) >= 0)
             return root.getCounter() + rangeCount(root.left, lo, hi) +
                        rangeCount(root.right, lo, hi);
     
        // If current node is smaller than low, then recur for right
        // child
        else if (root.key.compareTo(lo)< 0)
             return rangeCount(root.right, lo, hi);
     
        // Else recur for left child
        else return rangeCount(root.left, lo, hi);
    	
     }
    
    // @param: value , The value of evenId 
 	// Returns the Closest Node in the tree for the passed eventID.
     
    public RedBlackNode<T> getClosestNode(T value)
    {
    	RedBlackNode<T> pClosest = null;
        int minDistance = 0x7FFFFFFF;
        RedBlackNode<T> pNode = root;
//        int distance = Math.abs((int)pNode.key - (int)value);
//        System.out.println("pnode :"+pNode.key);
        while(pNode != nil)
        {   
//        	System.out.println("pnode :"+pNode.key);
//        	System.out.println("value :"+value);
        	
            int distance = Math.abs((Integer)pNode.key - (Integer)value);
        	
            if(distance < minDistance)
            {
                minDistance = distance;
                pClosest = pNode;
            }

            if(distance == 0)
                break;

            if(pNode.key.compareTo(value) > 0)
            	 pNode = pNode.left;
   
            else if(pNode.key.compareTo(value)< 0)
                pNode = pNode.right;
        }

        return pClosest;
    }
    
	// @param: x, The node which the lefRotate is to be performed on.
	// Performs a leftRotate around x.
	private void leftRotate(RedBlackNode<T> x){

		// Perform the left rotate as described in the algorithm
		// in the course text.
		RedBlackNode<T> y;
		y = x.right;
		x.right = y.left;

		// Check for existence of y.left and make pointer changes
		if (!isNil(y.left))
			y.left.parent = x;
		y.parent = x.parent;

		// x's parent is nul
		if (isNil(x.parent))
			root = y;

		// x is the left child of it's parent
		else if (x.parent.left == x)
			x.parent.left = y;

		// x is the right child of it's parent.
		else
			x.parent.right = y;

		// Finish of the leftRotate
		y.left = x;
		x.parent = y;
	}// end leftRotate(RedBlackNode x)



 	// @param: x, The node which the rightRotate is to be performed on.

	private void rightRotate(RedBlackNode<T> y){


        // Perform the rotate as described in the course text.
        RedBlackNode<T> x = y.left;
        y.left = x.right;

        // Check for existence of x.right
        if (!isNil(x.right))
            x.right.parent = y;
        x.parent = y.parent;

        // y.parent is nil
        if (isNil(y.parent))
            root = x;

        // y is a right child of it's parent.
        else if (y.parent.right == y)
            y.parent.right = x;

        // y is a left child of it's parent.
        else
            y.parent.left = x;
        x.right = y;

        y.parent = x;

	}// end rightRotate(RedBlackNode y)





    public void insert(T key,int count) {
        insert(new RedBlackNode<T>(key,count));
    }

    // @param: z, the node to be inserted into the Tree rooted at root
	// Inserts z into the appropriate position in the RedBlackTree while
	private void insert(RedBlackNode<T> z) {

			// Create a reference to root & initialize a node to nil
			RedBlackNode<T> y = nil;
			RedBlackNode<T> x = root;
		//	System.out.print(root.key + ":"+ root.color+":"+ root.getCounter()+ "\n");
			// While we haven't reached a the end of the tree keep
			// tryint to figure out where z should go
			while (!isNil(x)){
				y = x;

				// if z.key is < than the current key, go left
				if (z.key.compareTo(x.key) < 0){

					x = x.left;
				}

				// else z.key >= x.key so go right.
				else{
					x = x.right;
				}
			}
			// y will hold z's parent
			z.parent = y;

			// Depending on the value of y.key, put z as the left or
			// right child of y
			if (isNil(y))
				root = z;
			else if (z.key.compareTo(y.key) < 0)
				y.left = z;
			else
				y.right = z;

			// Initialize z's children to nil and z's color to red
			z.left = nil;
			z.right = nil;
			z.color = RedBlackNode.RED;

			// Call insertFixup(z)
			insertFixup(z);

	}// end insert(RedBlackNode z)


	// @param: z, the node which was inserted and may have caused a violation
	// of the RedBlackTree properties
	// Fixes up the violation of the RedBlackTree properties that may have
	// been caused during insert(z)
	private void insertFixup(RedBlackNode<T> z){

		RedBlackNode<T> y = nil;
		// While there is a violation of the RedBlackTree properties..
		while (z.parent.color == RedBlackNode.RED){

			// If z's parent is the the left child of it's parent.
			if (z.parent == z.parent.parent.left){

				// Initialize y to z 's cousin
				y = z.parent.parent.right;

				// Case 1: if y is red...recolor
				if (y.color == RedBlackNode.RED){
					z.parent.color = RedBlackNode.BLACK;
					y.color = RedBlackNode.BLACK;
					z.parent.parent.color = RedBlackNode.RED;
					z = z.parent.parent;
				}
				// Case 2: if y is black & z is a right child
				else if (z == z.parent.right){

					// leftRotaet around z's parent
					z = z.parent;
					leftRotate(z);
				}

				// Case 3: else y is black & z is a left child
				else{
					// recolor and rotate round z's grandpa
					z.parent.color = RedBlackNode.BLACK;
					z.parent.parent.color = RedBlackNode.RED;
					rightRotate(z.parent.parent);
				}
			}

			// If z's parent is the right child of it's parent.
			else{

				// Initialize y to z's cousin
				y = z.parent.parent.left;

				// Case 1: if y is red...recolor
				if (y.color == RedBlackNode.RED){
					z.parent.color = RedBlackNode.BLACK;
					y.color = RedBlackNode.BLACK;
					z.parent.parent.color = RedBlackNode.RED;
					z = z.parent.parent;
				}

				// Case 2: if y is black and z is a left child
				else if (z == z.parent.left){
					// rightRotate around z's parent
					z = z.parent;
					rightRotate(z);
				}
				// Case 3: if y  is black and z is a right child
				else{
					// recolor and rotate around z's grandpa
					z.parent.color = RedBlackNode.BLACK;
					z.parent.parent.color = RedBlackNode.RED;
					leftRotate(z.parent.parent);
				}
			}
		}
	// Color root black at all times
	root.color = RedBlackNode.BLACK;

	}// end insertFixup(RedBlackNode z)

	// @param: node, a RedBlackNode
	// @param: node, the node with the smallest key rooted at node
	public RedBlackNode<T> treeMinimum(RedBlackNode<T> node){

		// while there is a smaller key, keep going left
		while (!isNil(node.left))
			node = node.left;
		return node;
	}// end treeMinimum(RedBlackNode node)

	// @param: node, a RedBlackNode
	// @param: node, the node with the smallest key rooted at node
	public RedBlackNode<T> treeMaximum(RedBlackNode<T> node){

			// while there is a smaller key, keep going left
			while (!isNil(node.right))
				node = node.right;
			return node;
		}// end treeMaximum(RedBlackNode node)

	// @param: x, a RedBlackNode whose successor we must find
	// @return: return's the node the with the next largest key
	// from x.key
	public RedBlackNode<T> treeSuccessor(RedBlackNode<T> x){

		// if x.left is not nil, call treeMinimum(x.right) and
		// return it's value
		if (!isNil(x.right) )
			return treeMinimum(x.right);

		RedBlackNode<T> y = x.parent;

		// while x is it's parent's right child...
		while (!isNil(y) && x == y.right){
			// Keep moving up in the tree
			x = y;
			y = y.parent;
		}
		// Return successor
		return y;
	}// end treeSuccessor(RedBlackNode x)
	
	    // @param: x, a RedBlackNode whose successor we must find
		// @return: return's the node the with the next largest key
		// from x.key
		public RedBlackNode<T> treePredecessor(RedBlackNode<T> x){

			// if x.left is not nil, call treeMinimum(x.right) and
			// return it's value
			if (!isNil(x.left) )
				return treeMaximum(x.left);

			RedBlackNode<T> y = x.parent;

			// while x is it's parent's right child...
			while (!isNil(y) && x == y.left){
				// Keep moving up in the tree
				x = y;
				y = y.parent;
			}
			// Return successor
			return y;
		}// end treeMinimum(RedBlackNode x)
   	


	// @param: z, the RedBlackNode which is to be removed from the the tree
	// Remove's z from the RedBlackTree rooted at root
	public void remove(RedBlackNode<T> z){

//		RedBlackNode<T> z = search(v.key);

		// Declare variables
		RedBlackNode<T> x = nil;
		RedBlackNode<T> y = nil;

		// if either one of z's children is nil, then we must remove z
		if (isNil(z.left) || isNil(z.right))
			y = z;

		// else we must remove the successor of z
		else y = treeSuccessor(z);

		// Let x be the left or right child of y (y can only have one child)
		if (!isNil(y.left))
			x = y.left;
		else
			x = y.right;

		// link x's parent to y's parent
		x.parent = y.parent;

		// If y's parent is nil, then x is the root
		if (isNil(y.parent))
			root = x;

		// else if y is a left child, set x to be y's left sibling
		else if (!isNil(y.parent.left) && y.parent.left == y)
			y.parent.left = x;

		// else if y is a right child, set x to be y's right sibling
		else if (!isNil(y.parent.right) && y.parent.right == y)
			y.parent.right = x;

		// if y != z, trasfer y's satellite data into z.
		if (y != z){
			z.key = y.key;
		}


		// If y's color is black, it is a violation of the
		// RedBlackTree properties so call removeFixup()
		if (y.color == RedBlackNode.BLACK)
			removeFixup(x);
	}// end remove(RedBlackNode z)

	
	// @param: x, the child of the deleted node from remove(RedBlackNode v)
	// Restores the Red Black properties that may have been violated during
	// the removal of a node in remove(RedBlackNode v)
	private void removeFixup(RedBlackNode<T> x){

		RedBlackNode<T> w;

		// While we haven't fixed the tree completely...
		while (x != root && x.color == RedBlackNode.BLACK){

			// if x is it's parent's left child
			if (x == x.parent.left){

				// set w = x's sibling
				w = x.parent.right;

				// Case 1, w's color is red.
				if (w.color == RedBlackNode.RED){
					w.color = RedBlackNode.BLACK;
					x.parent.color = RedBlackNode.RED;
					leftRotate(x.parent);
					w = x.parent.right;
				}

				// Case 2, both of w's children are black
				if (w.left.color == RedBlackNode.BLACK &&
							w.right.color == RedBlackNode.BLACK){
					w.color = RedBlackNode.RED;
					x = x.parent;
				}
				// Case 3 / Case 4
				else{
					// Case 3, w's right child is black
					if (w.right.color == RedBlackNode.BLACK){
						w.left.color = RedBlackNode.BLACK;
						w.color = RedBlackNode.RED;
						rightRotate(w);
						w = x.parent.right;
					}
					// Case 4, w = black, w.right = red
					w.color = x.parent.color;
					x.parent.color = RedBlackNode.BLACK;
					w.right.color = RedBlackNode.BLACK;
					leftRotate(x.parent);
					x = root;
				}
			}
			// if x is it's parent's right child
			else{

				// set w to x's sibling
				w = x.parent.left;

				// Case 1, w's color is red
				if (w.color == RedBlackNode.RED){
					w.color = RedBlackNode.BLACK;
					x.parent.color = RedBlackNode.RED;
					rightRotate(x.parent);
					w = x.parent.left;
				}

				// Case 2, both of w's children are black
				if (w.right.color == RedBlackNode.BLACK &&
							w.left.color == RedBlackNode.BLACK){
					w.color = RedBlackNode.RED;
					x = x.parent;
				}

				// Case 3 / Case 4
				else{
					// Case 3, w's left child is black
					 if (w.left.color == RedBlackNode.BLACK){
						w.right.color = RedBlackNode.BLACK;
						w.color = RedBlackNode.RED;
						leftRotate(w);
						w = x.parent.left;
					}

					// Case 4, w = black, and w.left = red
					w.color = x.parent.color;
					x.parent.color = RedBlackNode.BLACK;
					w.left.color = RedBlackNode.BLACK;
					rightRotate(x.parent);
					x = root;
				}
			}
		}// end while

		// set x to black to ensure there is no violation of
		// RedBlack tree Properties
		x.color = RedBlackNode.BLACK;
	}// end removeFixup(RedBlackNode x)


	// @param: key, the key whose node we want to search for
	// @return: returns a node with the key, key, if not found, returns null
	// Searches for a node with key k and returns the first such node, if no
	// such node is found returns null
	public RedBlackNode<T> search(T key){

		// Initialize a pointer to the root to traverse the tree
		RedBlackNode<T> current = root;

		// While we haven't reached the end of the tree
		while (!isNil(current)){

			// If we have found a node with a key equal to key
			if (current.key.equals(key))

				// return that node and exit search(int)
				return current;

			// go left or right based on value of current and key
			else if (current.key.compareTo(key) < 0)
				current = current.right;

			// go left or right based on value of current and key
			else
				current = current.left;
		}
		// we have not found a node whose key is "key"
		return null;
	}// end search(int key)


	// @param: node, the RedBlackNode we must check to see whether it's nil
	// @return: return's true of node is nil and false otherwise
	private boolean isNil(RedBlackNode<T> node){

		// return appropriate value
		return node == nil;

	}// end isNil(RedBlackNode node)

	
}// end class RedBlackTree
