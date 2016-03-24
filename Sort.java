import java.util.*;

public class Sort {
  //precondition:  a is arranged in alphabetical order
  //postcondition: uses binary search to return the index of string s in a;
  //               returns -1 if not found 
  public static int indexOf(String[] a, String s) {
    int right = a.length-1;
    int left = 0;
    while (left <= right)  {
      int middle = (left + right) / 2;
      if (s.equals(a[middle]))
        return middle;
      else if (s.compareTo(a[middle]) < 0)
        right = middle - 1;
      else     
        left = middle + 1;
    }
    return -1;
  }
  
  //call this method to test your indexOf code
  public static void testIndexOf() {
    String[] words = {
      "case", "child", "day", "eye", "government",
      "hand", "life", "man", "part", "person",
      "place", "point", "thing", "time", "way",
      "week", "woman", "work", "world", "year"};
    ArrayList<String> list = new ArrayList<String>();
    for (int i = 0; i < words.length; i++)
      list.add(words[i]);
    String[] a = new String[(int)(Math.random() * 11 + 5)];
    while (list.size() > a.length)
      list.remove((int)(Math.random() * list.size()));
    for (int i = 0; i < a.length; i++)
      a[i] = list.get(i);
    System.out.println("a:  " + list);
    for (int i = 0; i < words.length; i++) {
      System.out.print("Searching for:  " + words[i]);
      int index = indexOf(a, words[i]);
      System.out.println("\t\tindexOf returned:  " + index);
      int correctIndex = list.indexOf(words[i]);
      if (index != correctIndex)
        throw new RuntimeException("Should have returned:  " + correctIndex);
    }
    System.out.print("Searching for:  boy");
    int index = indexOf(a, "boy");
    System.out.println("\t\tindexOf returned:  " + index);
    if (index != -1)
      throw new RuntimeException("Should have returned:  -1");
    System.out.print("Searching for:  you");
    index = indexOf(a, "you");
    System.out.println("\t\tindexOf returned:  " + index);
    if (index != -1)
      throw new RuntimeException("Should have returned:  -1");
  }
  
  //precondition:  0 <= startIndex < a.length
  //postcondition: Returns the index of the minimum value from
  //               a[startIndex] to end of array
  public static int indexOfMin(double[] a, int startIndex) {
    double small = a[startIndex];
    int j = startIndex;
    for (int i = startIndex; i < a.length; i++) {
      if (small > a[i]) {
        small = a[i];
        j = i;
      }
    }
    return j;
  }
  
  public static void selectionSort(double[] a) {
    for (int i = 0; i < a.length; i++) {
      double temp = a[i];
      int indexOfSmall = indexOfMin(a, i);
      a[i] = a[indexOfSmall];
      a[indexOfSmall] = temp;
      SortDisplay.update();
    }
  }
  
  //precondition:  a[0] to a[nextIndex - 1] are in increasing order
  //postcondition: a[0] to a[nextIndex] are in increasing order
  public static void insert(double[] a, int nextIndex) {
    int indexOfSmall = indexOfMin(a, nextIndex);
    double min = a[indexOfSmall];
    for (int i = indexOfSmall; i > nextIndex; i--)
      a[i] = a[i - 1];
    a[nextIndex] = min;
  }
  
  public static void insertionSort(double[] a) {
    for (int i = 0; i < a.length; i++) {
      insert(a, i);
      SortDisplay.update();
    }
  }
  
  public static double[] subArray(double[] a, int lowIndex, int highIndex) {
    double[] arr = new double[highIndex - lowIndex + 1];
    for (int i = 0; i < arr.length; i++) {
      for (int j = lowIndex; j <= highIndex; j++) {
        arr[i] = a[j];
      }
    }
    return arr;
  }
  
  public static void mergeSort(double[] a) {
    double[] tmp = new double[a.length];
    mergeSort(a, tmp,  0,  a.length - 1);
  }
  
  private static void mergeSort(double[] a, double[] tmp, int left, int right) {
    if (left < right) {
      int center = (left + right) / 2;
      mergeSort(a, tmp, left, center);
      mergeSort(a, tmp, center + 1, right);
      merge(a, tmp, left, center + 1, right);
    }
  }
  
  private static void merge(double[] a, double[] tmp, int left, int right, int rightEnd) {
    int leftEnd = right - 1;
    int k = left;
    int num = rightEnd - left + 1;
    
    while (left <= leftEnd && right <= rightEnd)
      if (a[left] <= (a[right]))
      tmp[k++] = a[left++];
    else
      tmp[k++] = a[right++];
    
    while (left <= leftEnd)    // Copy rest of first half
      tmp[k++] = a[left++];
    
    while(right <= rightEnd)  // Copy rest of right half
      tmp[k++] = a[right++];
    
    // Copy tmp back
    for (int i = 0; i < num; i++, rightEnd--)
      a[rightEnd] = tmp[rightEnd];
    
    SortDisplay.update();
  }
  
  public static void quickSort(double[] a) {
    sort(a, 0, a.length-1);
  }
  
  private static void sort(double[] a, int start, int end) {
    int i = start;                          
    int k = end;                            
    if (end - start >= 1) {
      double pivot = a[start];      
      while (k > i) {
        while (a[i] <= pivot && i <= end && k > i)
          i++;                                
        while (a[k] > pivot && k >= start && k >= i) 
          k--;                                       
        if (k > i)   {
          double temp = a[i];          
          a[i] = a[k];      
          a[k] = temp;  
        }
        SortDisplay.update();
      }
      double temp = a[start];
      a[start] = a[k];      
      a[k] = temp;   
      sort(a, start, k - 1); 
      sort(a, k + 1, end);   
    }              
  }
}