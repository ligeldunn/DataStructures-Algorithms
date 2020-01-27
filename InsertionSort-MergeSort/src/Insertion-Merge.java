/*
 *  Insertion Sort vs. Merge Sort Time Complexity
 *  
 *
 * @author Lucas Igel-Dunn
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Program2 {

  /**
   * AlphaSorted - check if an array is sorted into ascending order
   * 
   * @param a array to check
   * @return true if sorted, false if not
   */
  static boolean AlphaSorted(ArrayList<String> a) {
    for (int i = 1; i < a.size(); ++i) {
      if (a.get(i-1).compareTo(a.get(i)) > 0)
        return false;
    }
    return true;
  }
  
  /**
   * Compute the XOR of the hash of the first n elements of the input array
   * 
   * @param a input array
   * @return XOR of all elements of a
   */
  static int CheckHash(ArrayList<String> a, int n) {
    int hash = 0;
    for (int i = 0; i < n; ++i) {
      hash = hash ^ a.get(i).hashCode();
    }
    return hash;
  }
  
  /**
   * InsertionSort 
   * 
   * @param aIn array to be sorted
   * @param n number of items to sort
   * @return copy of aIn in ascending order
   */
  static ArrayList<String> InsertionSort(ArrayList<String> aIn, int n) {
    // Copy input array to output array
    ArrayList<String> a = new ArrayList<>(aIn.subList(0, n));
    
    // For 2nd through last items
    for (int j = 1; j < n; ++j) {
      // Copy value of next item
      String key = a.get(j);
      // Start looking here for insertion point
      int i = j - 1;
      // Keep looking while current value is > key
      while (i >= 0 && a.get(i).compareTo(key) > 0) {
        // Move current value up one
        a.set(i+1, a.get(i));
        // Continue looking to the left
        --i;
      }
      // Put key in sequence
      a.set(i+1, key);
    }
    
    return a;
  }

  
  /**
   * MergeSortSetup 
   * 
   * @param aIn array to be sorted
   * @param n number of items to sort
   * @return copy of aIn in ascending order
   */
  static ArrayList<String> MergeSortSetup(ArrayList<String> aIn, int n) {
    // Copy input array to output array
    ArrayList<String> a = new ArrayList<>(aIn.subList(0, n));

    // Apply recursive sort to output array
    MergeSort(a, 0, n-1);
    return a;
  }
  
  /**
   * MergeSort - this is the simple recursive part of Merge Sort
   * 
   * @param a array to sort
   * @param low low end (inclusive) of range to sort
   * @param high high end (inclusive) of range to sort
   */
  static void MergeSort(ArrayList<String> a, int low, int high) {
    // If sort range has more than 1 element
    if (low < high) {
      int mid = (low + high) / 2;  // mid point of range
      MergeSort(a, low, mid);  // sort lower range
      MergeSort(a, mid+1, high); // sort higher range
      Merge(a, low, mid, high);  // merge sorted halves
    }
  }
  
  /**
   * Merge - merge two adjacent sorted sub-arrays into a single sorted sub-array
   * 
   * @param a array with sorted sub-arrays
   * @param low low end (inclusive) of first sub-array
   * @param mid high end (inclusive) of first sub-array
   * @param high high end (inclusive) of second sub-array
   */
  static void Merge(ArrayList<String> a, int low, int mid, int high) {
    // Allocate temporary arrays for low range and high range
    ArrayList<String> lowRange = new ArrayList<>(a.subList(low, mid+1));
    ArrayList<String> highRange = new ArrayList<>(a.subList(mid+1, high+1));
    
    // Set indices for next value from lowRange and highRange
    int lowNext = 0;
    int highNext = 0;
    
    // Merge values back into a
    for (int cur = low; cur <= high; ++cur) {
      if (highNext >= highRange.size()) {
        a.set(cur, lowRange.get(lowNext++));
      } else if (lowNext >= lowRange.size()) {
        a.set(cur, highRange.get(highNext++));
      } else if (lowRange.get(lowNext).compareTo(highRange.get(highNext)) <= 0) {
        a.set(cur, lowRange.get(lowNext++));
      } else {
        a.set(cur, highRange.get(highNext++));
      }
    }
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws FileNotFoundException {
    // Get input argument
    if (args.length != 1) {
      System.err.println("usage: java Program2 file_name");
      System.exit(1);  // error exit
    }
    // Create empty array
    ArrayList<String> a = new ArrayList<>();
    
    Scanner dataIn = new Scanner(new File(args[0]));
    
    // Read input integer values
    while (dataIn.hasNext()) {
      a.add(dataIn.next());
    }
    dataIn.close();
    
    // Check that there is enough data
    int maxSize = 8192;  // maximum number of items to sort
    if (a.size() < maxSize) {
      System.err.println("Insufficient input data, " + maxSize +
          " words required, only " + a.size() + " words in input file " + args[0]);
      System.exit(1);  // error exit
    }
    
    // Loop through values of n
    for (int n = 8; n <= maxSize; n *= 2) {
      
      // Calculate number of iterations for timing (more for smaller arrays,
      // at least 4 for larger arrays)
      int iter = Integer.max(4, 8192/n);
      ArrayList<String> tmp = null;
      
      // Time insertion sort
      CpuTimer insTimer = new CpuTimer();
      for (int i = 0; i < iter; ++i) {
        tmp = InsertionSort(a, n);
      }
      // Compute average time for insertion sort
      double avgISTime = insTimer.getElapsedCpuTime() / (double)iter;
      
      // Check for correctness after last iteration
      if (AlphaSorted(a) || !AlphaSorted(tmp) || CheckHash(a,n) != CheckHash(tmp,n)) {
        System.err.println("ERROR: InsertionSort failed at size " + n);
        System.exit(2);
      }
      
      // Time merge sort
      CpuTimer mergeSortTimer = new CpuTimer();
      for (int i = 0; i < iter; ++i) {
        tmp = MergeSortSetup(a, n);
      }
      // Compute average time for merge sort
      double avgMSTime = mergeSortTimer.getElapsedCpuTime() / (double)iter;
      
      // Check for correctness
      if (AlphaSorted(a) || !AlphaSorted(tmp) || CheckHash(a,n) != CheckHash(tmp,n)) {
        System.err.println("ERROR: MergeSort failed at size " + n);
        System.exit(2);
      }
      
      System.out.println("Avg. times for n = " + n + ": Insertion Sort " + (float)avgISTime
        + " sec., Merge Sort " + (float)avgMSTime + " sec.");
    }
  }
  
}
