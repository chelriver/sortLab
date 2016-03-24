# sortLab
Due: Thursday, 9 PM on Dropbox.
 
Exercise 1:  indexOf
Complete the indexOf method in Sort.java, which performs a binary search on the given array of strings (already arranged in alphabetical order) in order to return the index of the given string value.  If the value is not present, indexOf returns -1.  (You may base your solution on the binary search code we saw in class, or you may choose to implement this method on your own.)  Your code must perform a binary search.  No credit will be awarded for using sequential search.  You may call the testIndexOf method (provided in Sort.java) to test your code.
Note:  You can compare two strings to determine which comes first alphabetically using the following method in the String class:
int compareTo(String other)

Assuming that we're only considering strings consisting purely of lowercase letters,

x.compareTo(y) returns a negative number if x comes before y alphabetically
x.compareTo(y) returns a positive number if x comes after y alphabetically
x.compareTo(y) returns 0 if x.equals(y)
Therefore, the following expressions would be true:
"apple".compareTo("banana") < 0
"banana".compareTo("apple") > 0
"apple".compareTo("apple") == 0



Exercise 2:  indexOfMin
Complete the indexOfMin method, which returns the index of the lowest value in the given array, considering only the range from the given index to the end of the array.


Exercise 3:  selectionSort
Complete the selectionSort method, which performs the selection sort algorithm on the given array.  Be sure to call your indexOfMin method where appropriate.

You can use the SortDisplay class to visualize your algorithm.  Whenever you swap 2 elements, call SortDisplay.update().  Then test your code by opening SortDisplay.java and pressing DrJava's run button (which runs SortDisplay's main method).  Click on the algorithm you wish to test, and then click on the illustration to step through your sort.


Exercise 4:  insert
Complete the insert method, which assumes that all values before the given index already appear in sorted order.  This method inserts the value at the given index into the appropriate place in the portion of the array ending at that index, shifting elements when appropriate.  Your code can only look at elements between the initial and final location of the inserted value.


Exercise 5:  insertionSort
Complete the insertionSort method, which performs the insertion sort algorithm on the given array.  Be sure to call your insert method where appropriate.  You may use SortDisplay to test your code.

Exercise 6:
Complete the mergeSort method by completing the following steps.
Step 1:  subArray
Write the subArray helper method, which copies all values from the specified range of the given array into a new array.  For example, if a contains [2, 3, 5, 7], lowIndex is 1 and highIndex is 2, then subArray returns [3, 5].
public static double[] subArray(double[] a, int lowIndex, int highIndex)
Step 2:  merge
Write the merge helper method, which takes in two sorted arrays and returns an array containing all those values in sorted order.  For example, if left contains [2, 5], and right contains [3, 7], then merge returns [2, 3, 5, 7].
public static double[] merge(double[] left, double[] right)
Step 3:  mergeSort
Complete the mergeSort method, which should look something like this:

if ( simplest case )
{
    handle simplest case
}
else
{
    two calls to mergeSort itself, along with calls to subArray and merge
}

Extra Credit:  QuickSort
Add a quickSort method, which performs the quick sort algorithm on the given array. Modify the program so that you can visualize the algorithm by running the sortDisplay.
