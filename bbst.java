/* UFID # 13103195 Bishal Gautam 
 
 * I have tried to follow the OOPs design concepts while making the program.
 * I have tried to make it easily readble by separating the commands by sequence of the simple red black tree methods but i realized later for very huge nodes it was slightly reducing the performance.
 * I have used the pseudo code for insert , delete for the textbook CLRS in the code.
 * To make the comparison easy and the program more modular I have used a coparable interface for storing the eventIds
 
 */




import java.io.*;
import java.util.*;
public class bbst {
	public static ArrayList<Integer> id = new ArrayList<Integer>(); 
	public static ArrayList<Integer> count = new ArrayList<Integer>();
//	public static ArrayList<String> command = new ArrayList<String>();

	
	public static void main(String [] args){
        //build a tree object with comparable Integer
		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		
		if(args.length > 0) {// only read if the argument is passed
            File file = new File(args[0]);
            try {
            		readNode(file);
            		
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            }  
            // trying to get the no of nodes that are black and are passed to the build tree so that while levelOrder traversing it can color the nodes properly
            float n = (float) Math.log10(count.size());
    	    float b =(float)  Math.log10(2);
    	    tree.level =(int)( n/b);
    	    long startTime = System.currentTimeMillis();
    		RedBlackNode<Integer> z = tree.buildTree(id,count,0,count.size()-1);
    	    long endTime   = System.currentTimeMillis();
       	    long totalTime = endTime - startTime;
    	    System.out.println("tree build time :"+totalTime);
    	    tree.root = z;
            //clear the arraylists after the build to optimize the memory use.
	        id.clear();
	        count.clear();
//    	    System.out.println("z:"+z.key);
//    	    System.out.println("root :"+ tree.root.key);
    	    tree.levelOrderTraversal(tree.root);
//    	    
//            File file1 = new File(args[1]);
//            try {
//            	    readCommand(file1);
//            	   
//            } catch (IOException e) {
//                System.err.println("IOException: " + e.getMessage());
//            } 
            
            Scanner command = new Scanner(System.in);
            String cmd;
//            = command.nextLine();
//            for (int i=0 ; i != command.size(); i++){
            while(( cmd = command.nextLine()) != null){
            	 String[] lineRead =cmd.split(" ");
            	 String compare = lineRead[0];
            	// System.out.println(lineRead[0]);
                // the logic to implement the increase
            	 if (compare.equals( "increase")){
                     //search for the node
            		 RedBlackNode<Integer> node = tree.search(Integer.parseInt(lineRead[1]));
            		 if (node != null){
                         //if found it wil set the counter by addign the passed count
            			 int temp =node.getCounter();
            			 node.setCounter(temp+ Integer.parseInt(lineRead[2]));
            			 System.out.println(node.getCounter());
            		 }else{// if not found it inserts the eventId as a node.
            			 tree.insert(Integer.parseInt(lineRead[1]), Integer.parseInt(lineRead[2]));
                         //this statement could have been inside the insert but to make it more readble i have kept it here
            			 System.out.println((tree.search(Integer.parseInt(lineRead[1]))).getCounter());
            		 }
            		 
            	 }else if(compare.equals("reduce")){
                      //search for the node
            		 RedBlackNode<Integer> node = tree.search(Integer.parseInt(lineRead[1]));
            		 if(node != null){//if found it wil reduce the counter by subtracting the passed count
            			 int temp = node.getCounter();
            			 int check = temp - Integer.parseInt(lineRead[2]);
            			 if (check > 0){ //also checks for negative count and deletes if negative
            				 node.setCounter(check);
            				 System.out.println(node.getCounter());
            			 }else {
            				 tree.remove(node);
            				 System.out.println("0");
            			 }

            		 }else{
            			 System.out.println("0"); 
            		 }
            		 
            	 }else if(compare.equals("count")){// returns the count of the passed node or 0 if not present
            		 RedBlackNode<Integer> node = tree.search(Integer.parseInt(lineRead[1]));
            		 if (node != null) System.out.println(node.getCounter());
            		 else System.out.println("0");
            	 }else if(compare.equals("inrange") ) {//returns the number of total counters of the eventids in range
            		 System.out.println(tree.rangeCount(tree.root, Integer.parseInt(lineRead[1]), Integer.parseInt(lineRead[2])));
            	 }else if(compare.equals("previous")){
            		 RedBlackNode<Integer> node = tree.getClosestNode(Integer.parseInt(lineRead[1]));
//            		 if (node != null){
            			 if (node.key.compareTo(Integer.parseInt(lineRead[1]))==0) {
            				 if(tree.treePredecessor(node) != tree.nil){ 
            					 RedBlackNode<Integer> temp = tree.treePredecessor(node);
            					 System.out.println(temp.key+" "+temp.getCounter());
            				 }else 
            					 System.out.println("0 0");
            					 
            			 }else if(node.key.compareTo(Integer.parseInt(lineRead[1])) > 0){
            				 if(tree.treePredecessor(node) != tree.nil){ 
            				 RedBlackNode<Integer> temp = tree.treePredecessor(node);
        					 System.out.println(temp.key+" "+temp.getCounter());
            				 } else
            					 System.out.println("0 0");		 
            			 }
            			 else System.out.println(node.key+" "+node.getCounter());
            			 
//            		 }else 
//            			 System.out.println("0 0");
            	 }else if(compare.equals("next")){
            		 RedBlackNode<Integer> node = tree.getClosestNode(Integer.parseInt(lineRead[1]));
            		 if (node.key.compareTo(Integer.parseInt(lineRead[1]))==0) {
        				 if(tree.treeSuccessor(node) != tree.nil){ 
        					 RedBlackNode<Integer> temp = tree.treeSuccessor(node);
        					 System.out.println(temp.key+" "+temp.getCounter());
        				 }else 
        					 System.out.println("0 0");
        					 
        			 }else if(node.key.compareTo(Integer.parseInt(lineRead[1])) < 0){
        				 if(tree.treeSuccessor(node) != tree.nil){ 
        					 RedBlackNode<Integer> temp = tree.treeSuccessor(node);
        					 System.out.println(temp.key+" "+temp.getCounter());
        				 }else
        					 System.out.println("0 0");		 
        			 }
        			 else System.out.println(node.key+" "+node.getCounter());
//            		 if (node != null)
//            			System.out.println(tree.treeSuccessor(node).key);
//            		    tree.inOrderTraversal(tree.root);
//            		 else
//            			 System.out.println("0 0");
//            			 tree.inOrderTraversal(tree.root);}
            	 }else if(compare.equals("quit")){
            		 System.exit(0);
            	 }else System.out.println("Unexpected command found");  
    	    }
    		
			}
	 } 
	   //@ reads the file passed and stores the eventIds and counts in two different arraylists.
	
		private static void readNode(File fin) throws IOException {
		FileInputStream fis = new FileInputStream(fin);
	 
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = br.readLine();
//		System.out.println(line);
		
		while ((line = br.readLine()) != null) {

			String[] lineRead = line.split(" ");
//			System.out.println(Integer.parseInt(lineRead[0])+","+Integer.parseInt(lineRead[1]));
			id.add(Integer.parseInt(lineRead[0]));
			count.add(Integer.parseInt(lineRead[1]));
		}
		br.close();
		}
 

 }



