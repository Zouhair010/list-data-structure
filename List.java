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
         * Initializes the list with a starting capacity of 1 if not assign the list to default argumment (array of objects)
         */
        public DynamicList(Object... args){
            Object[] items = (args.length>0) ? args : null;
            ln = (items!=null) ? items.length : 1;
            list = (items!=null) ? items : new Object[ln];
            tracker = (items!=null) ? items.length :0;
        }
        /**
         * Adds an element to the end of the list.
         * If the list is full, it doubles its capacity before adding the new element.
         * @param elem The object to be added to the list.
         */
        public void add(Object elem){
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
            // Add the new element at the next available position.
            list[tracker] = elem;
            tracker++;
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
                if(list[i].equals(elem)){
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
         * @return The index of the element, or null if it is not found.
         * throws an exception if the index is invalid (out of range)
         */
        public Object indexOf(Object elem){
            try{
                // iterate through the list to find the element
                for (int i=0 ; i<tracker ; i++) {
                    if(list[i].equals(elem)){
                        // returns the index of the element is found
                        return i;
                    }
                }
                throw new Exception("the element "+elem+" is not on the list!");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            // returns null if the element not found
            return null;
        }
        /**
         * updates an element in the list at a given index
         *throws an exception if the index is invalid (out of range
         */
        public void update(int index, Object elem){
            try{
                // check if the given index is outside the valid range
                if (index>=tracker){
                    // throws an exception if the index is invalid
                    throw new IndexOutOfBoundsException("Index "+index+" is out of range!");
                }
                // if the index is valid replace the element at that position
                list[index] = elem;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static void main(String[] args){
        DynamicList list = new DynamicList(new Object[]{1,'2',"3",4,'5'});
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
        list.update(8, 0);
        System.out.println(list.len());
        list.printData();
        
    }
}