import java.util.Arrays;

public class List{
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
         * Initializes the list with a starting capacity of 1.
         */
        public ClassList(){
            ln = 1;
            list = new Object[ln];
            tracker = 0;
        }
        /**
         * Adds an element to the end of the list.
         * If the list is full, it doubles its capacity before adding the new element.
         * @param elem The object to be added to the list.
         */
        public void add(Object elem){
            // Add the new element at the next available position.
            list[tracker] = elem;
            tracker++;
            // Check if the internal array is now full.
            if (tracker>=ln){
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
            // The return statement is redundant for a void method but is harmless.
            return;
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
         * Note: This method uses reference equality (==) to find the element, which may not
         * behave as expected for objects like Strings or boxed primitives unless they are the exact same instance.
         * @param elem The element to be removed.
         */
        public void remove(Object elem){
            // Iterate through the elements to find the one to remove.
            for (int i=0 ; i<tracker ; i++) {
                // If the element is found by reference...
                if(list[i]==elem){
                    // ...shift all subsequent elements one position to the left to fill the gap.
                    for (int j=i ; j<tracker ; j++){
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
         * Prints a string representation of the list to the console.
         * The format is [ element1 , element2 , ... , elementN ].
         */
        public void printData(){
            String data = "";
            // Build the string representation of the list.
            for (int i=0 ; i<tracker ; i++) {
                // Ternary operators are used to handle the start, middle, and end of the string formatting.
                String item="";
                switch (list[i].getClass().getName()) {
                    case "java.lang.Character":
                        item = "\'"+list[i]+"\'";
                        break;
                    case "java.lang.String":
                        item = "\""+list[i]+"\"";
                        break;
                    default:
                        item = ""+list[i];
                        break;
                }
                data = (i==0) ? "[ "+item : data+item ;
                data = (i<tracker-1) ? data+" , " : data+" ]" ;
            }
            System.out.println(data);
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
         * Note: This method uses reference equality (==) for comparison.
         * @param elem The element to search for.
         * @return The index of the element, or -1 if it is not found.
         */
        public int indexOf(Object elem){
            for (int i=0 ; i<tracker ; i++) {
                if(list[i]==elem){
                    return i;
                }
            }
            // Return -1 if the element was not found in the loop.
            return -1;
        }
    }
    
    public static void main(String[] args){
        DynamicList list = new DynamicList();
        list.add(1);
        list.add("2");
        list.add('3');
        list.add("43");
        list.add(52);

        System.out.println(list.len());
        list.printData();
        list.remove("3");
        list.printData();
        System.out.println(list.indexOf("3"));
        System.out.println(list.len());
    }
}