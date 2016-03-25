/**
 */ // class RedBlackNode
class RedBlackNode<T extends Comparable<T>> {

    /** Possible color for this node */
    public static final int BLACK = 0;
    /** Possible color for this node */
    public static final int RED = 1;
	// the key of each node
	public T key;

    /** Parent of node */
    RedBlackNode<T> parent;
    /** Left child */
    RedBlackNode<T> left;
    /** Right child */
    RedBlackNode<T> right;
   
    public int color;
    private int counter; 
    RedBlackNode(){
        color = BLACK;
        parent = null;
        left = null;
        right = null;
        counter = 0;
        
    }
     
	// Constructor which sets key to the argument.
	RedBlackNode(T key, int count){
        this();
        this.key = key;
        this.counter = count;
	}
	

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
}// end class RedBlackNode
