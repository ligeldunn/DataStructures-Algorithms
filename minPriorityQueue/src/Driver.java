/*Lucas Igel-Dunn
 * Driver Class
 * Tuesday, October 23, 2018
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		// Airplane ArrayList 
		ArrayList<Airplane> Ap = new ArrayList<Airplane>();
		
		// minQueue class
		minPriorityQueue que = new minPriorityQueue();

		// variable for runways
		int numrunways = 0;
		
		// scanner for output file
		Scanner sc = new Scanner(new File(args[2]));
		
		// read in input file and take in # of runways
		Scanner dataIn = new Scanner(new File(args[2]));
	    numrunways = dataIn.nextInt();
	   
	    // skip line
	    dataIn.nextLine();
	   
	    // read in data
	    while(dataIn.hasNextLine()) {
	  
	    	// read in data for each line
	    	String line = dataIn.nextLine();
	    	String[] inputs = line.split("		");
	    String input1 = inputs[0];
	    int input2 = Integer.parseInt(inputs[1]);
	    int input3 = Integer.parseInt(inputs[2]);
	    
	    Airplane insert = new Airplane(input1, input2, input3);
	    
	    // insert Airplane object into ArrayList
	    Ap.add(insert);
	    	
	    }
	    
	    // start of time simulation 
		for(int t = 0; t <= 200; t++) {
			
			if(Ap.size() != 0) {
			
			//get first element 
			Airplane placement = Ap.get(0);
			
			// while loop to determine if aircraft's arrive in current time
			while(placement.arrivalTime <= t && Ap.size() != 0) {
				
				// insert into queue
				que.MinHeapInsert(placement);
				
				// remove and update 
				if(Ap.size() != 0) {
					Ap.remove(0);
				}
				if(Ap.size() != 0) {
					placement = Ap.get(0);
				}
				
			}
		}
		
		// print out results after adding new arrivals and when aircraft "lands"
		for(int i = 0; i < numrunways; i++) {
			if(!que.A.isEmpty()) {
				
			// build min heap and remove aircraft from queue
			que.BuildMinHeap();
			Airplane placement = que.HeapExtractMin();
			
			// print result
			System.out.println(placement.flightID + "   " + placement.arrivalTime + "   " + placement.landingPriority+ "   " + t);
			}
		}

		}
	}
}
