/*Lucas Igel-Dunn
 * minPriorityQueue class
 */
import java.util.ArrayList;

public class minPriorityQueue {
	
	// Arraylist for minPriorityQueue methods
	ArrayList<Airplane> A;
	
	// constructor for minPriorityQueue
	public minPriorityQueue() {
		A = new ArrayList<Airplane>();
	}
	
	// min-heapify method
	public void MinHeapify(int i){
		
		// left and right child
		int left =  2 * i + 1;
		int right = 2 * i + 2;
		
		// heapsize and min value
		int heapsize = A.size()-1;
		int smallest = i;
		
		// checks if left child is less than index
		if (left <= heapsize && A.get(left).landingPriority <= A.get(i).landingPriority) {
			
			// if statement to check for same landingPriority  
			if(A.get(left).landingPriority == A.get(i).landingPriority && A.get(left).arrivalTime <= A.get(i).arrivalTime) {
				smallest = left;
			}else if (A.get(left).landingPriority < A.get(i).landingPriority) {
				smallest = left;
			}else {
				smallest = i;
			}	
	}
		// check if right child is less than smallest index
		if (right <= heapsize && A.get(right).landingPriority <= A.get(smallest).landingPriority) {
			smallest = right;
			// if statement to check for same landingPriority 
			if(A.get(right).landingPriority == A.get(smallest).landingPriority && A.get(right).arrivalTime <= A.get(smallest).arrivalTime) { 
			smallest = right;
			}
	}
			
		// swaps values accordingly 
		if(smallest != i) {
			Airplane index = A.get(i);
			Airplane index1 = A.get(smallest);
			A.set(i, index1);
			A.set(smallest, index);
			MinHeapify(smallest);	
		}
		
	}
		
	// heap extract min method (returns and removes min value)
    public Airplane HeapExtractMin(){
    	
    	if(A.size() == 0) {
    		System.out.println("heap underflow");
    	}
    
    	Airplane min = A.get(0);
    	A.remove(0);
  
    	MinHeapify(0);
    	return min;
    	
    }
    
    // min heap insert method (inserts value into Airplane array) 
    public void MinHeapInsert(Airplane key) {

    	A.add(key);
  
    } 
    // build min heap method 
    public void BuildMinHeap() {
    int n = A.size()-1;
    	
    	for (int i = (n/2); i >= 0; i--) {
    		MinHeapify(i);
    	}
    }
   
    
}
    