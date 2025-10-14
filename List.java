import java.util.Arrays;
public class List {
    /**
     * A custom implementation of a dynamic array, similar to ArrayList.
     * It can store objects of any type and automatically resizes itself when full.
     */
    public static class DynamicList{
        private int ln; // Represents the current capacity of the internal array.
        private Object[] list; // The internal array used to store elements.
        private int tracker; // Tracks the number of elements currently in the list.
        /**
         * Default constructor.
         * Initializes the list with the provided elements. If no elements are provided,
         * it initializes an empty list with a default capacity of 1.
         * @param args A variable number of objects to initialize the list with.
         */
        public DynamicList(Object... elements){
            Object[] items = (elements.length>0) ? elements : null;
            ln = (items!=null) ? items.length : 1;
            list = (items!=null) ? items : new Object[ln]; // `items` is a reference to `elements`
            tracker = (items!=null) ? items.length :0;
        }
        /**
         * Adds an element to the end of the list.
         * @param element The object to be added to the list.
         */
        public void add(Object element){
            // Check if the internal array is now full.
            if (tracker>=ln){
                resizedlist();
            }
            // Add the new element at the next available position.
            list[tracker] = element;
            tracker++;
            // The return statement is redundant for a void method but is harmless.
            return;
        }
        /**
         * Appends all of the elements in the specified varargs to the end of this list.
         * @param elements The elements to be added to this list.
         */
        public void extend(Object... elements){
            // Iterate over the provided elements and add each one to the list.
            for (Object object : elements) {
                add(object);
            }
        }
        /**
         * Resizes the internal array when it is full.
         * It doubles the current capacity.
         */
        private void resizedlist(){
            // Double the capacity.
            ln *= 2;
            // Create a new array with the larger capacity.
            Object[] resizedlist = new Object[ln];
            // Copy all elements from the old array to the new one.
            for(int i=0 ; i<tracker ; i++){
                resizedlist[i]=list[i];
            }
            // Replace the old array with the new, resized array.
            list = resizedlist;
        }
        /**
         * Returns the number of elements in the list.
         * @return The current size of the list.
         */
        public int len(){
            return tracker;
        }
        /**
         * Removes the first occurrence of the specified element from the list.
         * This method uses the `equals()` method to find the element.
         * @param element The element to be removed.
         */
        public void remove(Object element){
            // Iterate through the elements to find the one to remove.
            for (int i=0 ; i<tracker ; i++) {
                // If the element is found...
                if(list[i].equals(element)){
                    // ...shift all subsequent elements one position to the left to fill the gap.
                    for (int j=i ; j<tracker-1 ; j++){
                        list[j] = list[j+1];
                    }
                    // Decrease the element count.
                    tracker--;
                    // Exit the loop since the first occurrence has been removed.
                    break;
                }
            }
        }
        /**
         * Returns a string representation of the list.
         * The format is `[element1, element2, ..., elementN]`.
         */
        @Override
        public String toString(){
            // Build the string representation of the list.
            String[] data = new String[tracker];
            for(int i=0 ; i<tracker ; i++){
                data[i] = typeFormat(list[i]);
            }
            return String.format("[%s]",String.join(" ,",data));
        }
        /**
         * Formats an object into a string for display, distinguishing between data types.
         * Integers are unquoted, Characters are in single quotes, and Strings are in double quotes.
         * @param item The object to format.
         * @return A formatted string representation of the item.
         */
        private static String typeFormat(Object item){
            String string;
            switch (item.getClass().getName()) {
                    case "java.lang.Character":
                        string = "\'"+item+"\'";
                        break;
                    case "java.lang.String":
                        string = "\""+item+"\"";
                        break;
                    default:
                        string = ""+item;
                        break;
            }
            return string;
        }
        /**
         * Returns a safe copy of the internal array containing only the elements in the list.
         * The length of the returned array is equal to the number of elements.
         * @return A new array containing the elements of the list.
         */
        public Object[] getData(){
            return Arrays.copyOf(list, tracker);
        }
        /**
         * Returns the index of the first occurrence of the specified element in this list.
         * If the element is not found, it prints an error message and returns null.
         * @return The index of the element, or null if it is not found.
         */
        public Object indexOf(Object element){
            try{
                // iterate through the list to find the element
                for (int i=0 ; i<tracker ; i++) {
                    if(list[i].equals(element)){
                        // returns the index of the element is found
                        return i;
                    }
                }
                throw new Exception("the element "+element+" is not on the list!");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            // returns null if the element not found
            return null;
        }
        /**
         * Updates an element in the list at a given index.
         * If the index is out of range, it prints an error message.
         * @param index The index of the element to update.
         * @param element The new element.
         */
        public void update(int index, Object element){
            try{
                // check if the given index is outside the valid range
                if (index>=tracker){
                    // throws an exception if the index is invalid
                    throw new IndexOutOfBoundsException("Index "+index+" is out of range!");
                }
                // if the index is valid replace the element at that position
                list[index] = element;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        /**
         * Checks if the list contains the specified element.
         * @param element The element to search for.
         * @return true if the object is found, false otherwise.
         */
        public boolean contains(Object element){
            for (int i=0 ; i<tracker ; i++){
                if (list[i].equals(element)){
                    return true;
                }
            }
            return false;
        }
        /**
         * Sorts the elements in the list in ascending order based on their hash codes.
         * Note: This is not a stable or predictable sort for complex objects.
         * It's generally better to sort elements that implement the `Comparable` interface.
         */
        public void sort(){
            quickSort(list,0,tracker-1);
        }
        /**
         * A recursive helper method to perform QuickSort on an array of objects.
         * @param arr The array to be sorted.
         * @param varArgs The low and high indices for the partition.
         */
        private void quickSort(Object[] arr, int... varArgs) {
            int low = (varArgs.length>0) ? varArgs[0] : 0;
            int high = (varArgs.length>0) ? varArgs[1] : (arr.length-1);
            if (low < high){
                int p = partition(arr,low,high);
                quickSort(arr, low, p-1);
                quickSort(arr, p+1, high);
            }
        }
        /**
         * Partitions the array for the QuickSort algorithm using the last element as the pivot.
         * @param arr The array to partition.
         * @param low The starting index.
         * @param high The ending index (pivot).
         * @return The partition index.
         */
        private int partition(Object[] arr, int low, int high) {
            int pivot = arr[high].hashCode();
            int i = low-1;
            for(int j=low ; j<high ; j++){
                if (arr[j].hashCode() <= pivot){
                    i++;
                    Object temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            Object temp = arr[i+1];
            arr[i+1] = arr[high];
            arr[high] = temp;
            return i+1;
        }
        /**
         * Reverses the order of the elements in the list.
         */
        public void reverse(){
            int start = 0;
            int end = tracker-1;
            // Loop until the middle of the list is reached, swapping elements from the start and end.
            for (int i=0 ; i<tracker ; i++){
                if(start>=end){
                    break;
                }
                Object temp = list[start];
                list[start] = list[end];
                list[end] =  temp;
                start++;
                end--;
            }
        }
    }

    /**
     * The main method to demonstrate the functionality of the DynamicList.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args){
        DynamicList list = new DynamicList(3,0,7,6,4);
        list.add(5);
        list.add(1);
        list.add(2);
        list.extend(8,9,10);

        System.out.println(list.len());
        System.out.println(list);
        list.remove("3");
        System.out.println(list);
        System.out.println(list.indexOf("3"));
        list.update(8, 0);
        System.out.println(list.len());
        System.out.println(list);
        list.sort();
        System.out.println(list);
        list.reverse();
        System.out.println(list);
    }
}