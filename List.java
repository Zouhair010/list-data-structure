import java.util.Arrays;
public class List {
    /**
     * A custom implementation of a dynamic array, similar to ArrayList.
     * It can store objects of any type and automatically resizes itself when full.
     */
    public static class DynamicList{
        /** The current capacity of the internal array. */
        private int ln;
        /** The internal array used to store elements. */
        private Object[] list;
        /** Tracks the number of elements currently in the list (its size). */
        private int tracker;
        /**
         * The internal index used by the `next()` method for iteration.
         * It is initialized to -1 to ensure the first call to `next()` returns the first element.
         */
        private int index = -1;
        /**
         * Default constructor.
         * Initializes the list with the provided elements. If no elements are provided,
         * it initializes an empty list with a default capacity of 1.
         * @param elements A variable number of objects to initialize the list with.
         */
        public DynamicList(Object... elements){
            // Determine if initial elements were provided.
            Object[] items = (elements.length>0) ? elements : null;
            // Set the initial capacity. If elements are provided, capacity matches the number of elements. Otherwise, it defaults to 1.
            ln = (items!=null) ? items.length : 1;
            // Initialize the internal array. If elements are provided, the list directly references them. Otherwise, a new array is created.
            list = (items!=null) ? items : new Object[ln]; // `items` is a reference to `elements`
            // Set the initial number of elements in the list.
            tracker = (items!=null) ? items.length :0;
        }
        /**
         * Adds an element to the end of the list.
         * @param element The object to be added to the list.
         */
        public void add(Object element){
            // If the list is full, resize the internal array to make space.
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
            // This method provides custom string formatting for different object types.
            String string;
            switch (item.getClass().getName()) {
                    case "java.lang.Character":
                        string = "\'"+item+"\'";
                        break;
                    case "java.lang.String":
                        string = "\""+item+"\"";
                        break;
                    default:
                        // For all other types, use their default toString() representation.
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
         * @param element The element to search for.
         * @return The index of the element, or null if it is not found.
         */
        public Object indexOf(Object element){
            try{
                // Iterate through the list to find the element.
                for (int i=0 ; i<tracker ; i++) {
                    if(list[i].equals(element)){
                        // Return the index if the element is found.
                        return i;
                    }
                }
                // If the loop completes without finding the element, throw an exception.
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
                // Check if the given index is outside the valid range (0 to size-1).
                if (index>=tracker){
                    // Throws an exception if the index is invalid.
                    throw new IndexOutOfBoundsException("Index "+index+" is out of range!");
                }
                // If the index is valid, replace the element at that position.
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
            // Calls the quickSort helper method on the populated part of the list.
            quickSort(list,0,tracker-1);
        }
        /**
         * A recursive helper method to perform QuickSort on an array of objects.
         * @param arr The array to be sorted.
         * @param varArgs The low and high indices for the partition.
         */
        private void quickSort(Object[] arr, int... varArgs) {
            // Set default low and high if not provided.
            int low = (varArgs.length>0) ? varArgs[0] : 0;
            int high = (varArgs.length>0) ? varArgs[1] : (arr.length-1);
            if (low < high){
                // Find the partition index.
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
            // Use the hash code of the last element as the pivot.
            int pivot = arr[high].hashCode();
            // Index of the smaller element.
            int i = low-1;
            for(int j=low ; j<high ; j++){
                // If the current element's hash code is smaller than or equal to the pivot.
                if (arr[j].hashCode() <= pivot){
                    i++;
                    // Swap arr[i] and arr[j].
                    Object temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            // Swap the pivot element (arr[high]) with the element at the partition boundary (arr[i+1]).
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
        /**
         * Returns the next element in the list, cycling back to the start if the end is reached.
         * This method maintains an internal cursor.
         * @return The next object in the sequence.
         */
        public Object next(){
            // Increment index, or reset to 0 if it reaches the end of the list.
            index = (index<tracker-1) ? index+1 : 0;
            return list[index];
        }
        /**
         * A utility method to check if an object represents a digit.
         * It handles String, Character and Integer types.
         * @param obj The object to check.
         * @return true if the object is a integer, string or character representing a single digit, false otherwise.
         */
        public static boolean isDigit(Object obj){
            if (obj instanceof String){
                try{
                    Integer n =  Integer.valueOf(obj.toString());
                    if(n.toString().equals(obj)){
                        return true;
                    }
                }
                catch (NumberFormatException e){
                    return false;
                }
            }
            else if (obj instanceof Character){
                try{
                    Integer n = Integer.valueOf(obj.toString());
                    Character c = n.toString().toCharArray()[0];
                    if(c.equals(obj)){
                        return true;
                    }
                }
                catch (NumberFormatException e){
                    return false;
                }
            }
            else if (obj instanceof Integer){
                return true;
            }
            return false;
        }
    }

    /**
     * The main method to demonstrate the functionality of the DynamicList.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args){
        // Create a new DynamicList with initial integer elements.
        DynamicList list = new DynamicList(3,0,7,6,4);
        list.add(5);
        list.add(1);
        list.add(2);
        list.extend(8,9,10);

        System.out.println(list.len());
        // The list initially contains: [3, 0, 7, 6, 4, 5, 1, 2, 8, 9, 10]
        System.out.println(list);
        // Note: remove("3") will not work as expected because the list contains Integers, not Strings.
        // The .equals() check will fail. To remove the integer 3, you would call list.remove(3).
        list.remove("3");
        System.out.println(list);
        // Similarly, indexOf("3") will return null and print an error.
        System.out.println(list.indexOf("3"));
        // Update the element at index 8 (the 9th element) to 0.
        list.update(8, 0);
        System.out.println(list.len());
        System.out.println(list);
        // Sort the list based on the hash codes of the elements.
        list.sort();
        System.out.println(list);
        // Reverse the sorted list.
        list.reverse();
        System.out.println(list);

        // Iterate through the list using the next() method.
        for(int i=0 ; i<list.len() ; i++) {
            System.out.print(list.next()+", ");
        }
    }
}