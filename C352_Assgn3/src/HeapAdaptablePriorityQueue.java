public class HeapAdaptablePriorityQueue<K, V> extends HeapPriorityQueue {
    protected static class AdaptablePQEntry<K,V> implements Entry{
        K key;
        V value;
        protected AdaptablePQEntry(K k, V v){
            key = k;
            value = v;
        }
        public K getKey(){
            return key;
        }
        public V getValue(){
            return value;
        }
        public void setKey(K k){
            key = k;
        }
        public void setValue(V v){
            value = v;
        }
        public String toString(){
            return key + " - " + value;
        }
    }
    private static final int CAPACITY = 1;   // default array capacity
    public HeapAdaptablePriorityQueue(){                    // constructs stack with default capacity
        this(CAPACITY);
    }
    public HeapAdaptablePriorityQueue(int capacity){        // constructs priority queue with given capacity
        heap = new Object[capacity];
        isMaxPQ = true;
    }
    public HeapAdaptablePriorityQueue(int capacity, boolean isMax){
        heap = new Object[capacity];
        isMaxPQ = isMax;
    }

    /**
     * Algorithm toggle()
     * Changes the state of the heap from max to min or vice-versa
     * Input: nothing
     * Output: nothing
     * if heap is max do
     *      change to min
     * else
     *      change to max
     * upheap every entry in the heap starting from the bottom
     */
    public void toggle(){
        if(isMaxPQ)
            isMaxPQ = false;
        else
            isMaxPQ = true;
        for(int i = heap.length - 1; i >= 0; i--)
            upheap(i);
    }
    public String state(){
        if(isMaxPQ)
            return "State: Max Priority Queue";
        return "State: Min Priority Queue";
    }
    public boolean isEmpty(){
        return size() == 0 || heap[0] == null;
    }

    /**
     * Algorithm AdaptablePQEntry(Object k, Object v)
     * Initialize new entry
     * Use add method with new entry
     * Upheap to maintain heap characteristic
     */
    public AdaptablePQEntry insert(Object k, Object v){
        AdaptablePQEntry newEntry = new AdaptablePQEntry<K,V>((K)k, (V)v);
        add(newEntry);
        upheap(heap.length - 1);
        return newEntry;
    }

    /** Algorithm top()
     * return value of heap at index 0 or null if heap is empty
     */
    public AdaptablePQEntry top(){
        if(size() == 0)
            return null;
        return (AdaptablePQEntry)heap[0];
    }
    public AdaptablePQEntry removeTop(){
        if(size() == 0)
            return null;
        AdaptablePQEntry oldTop = (AdaptablePQEntry)heap[0];
        swap(0, heap.length - 1);
        decrementArraySize();
        bubble(0);
        return oldTop;
    }
    /**
     * Algorithm DecrementArraySize()
     * Input: Array which is too big for current application
     * Output: Same array decremented by 1
     * Initialize new array
     * For each element in the old array
     *      Copy the element from the old array to the new one except the last one
     * Assign reference of new array to old one
     * Dereference old array
     */
    protected void decrementArraySize() {
        Object[] newData = new Object[size() - 1];
        for (int i = 0; i < newData.length; i++)
            newData[i] = heap[i];
        heap = newData;
        newData = null; // dereference to help garbage collection
    }

    /**
     * Algorithm bubble(integer)
     * if integer is not 0 and j is less than it's parent do
     *      Upheap to maintain max/min characteristic
     * else do
     *      Downheap to maintain max/min characteristic
     */

    public void bubble(int j){
        if(j > 0 && compare(get(j), get(parent(j))) < 0)
            upheap(j);
        else
            downheap(j);
    }
    public AdaptablePQEntry get(int i){
        return (AdaptablePQEntry)heap[i];
    }

    /**
     * Algorithm remove(AdaptablePQEntry e)
     * Find the entry with find method
     * if entry is valid do
     *      remove entry from heap
     * Decrement array size
     * Bubble heap
     */
    public AdaptablePQEntry remove(AdaptablePQEntry e){
        int k = find(e);
        if(k == -1)
            return null;
        AdaptablePQEntry l = (AdaptablePQEntry)heap[k];
        if(k == heap.length - 1){
            decrementArraySize();
        }
        else{
            swap(k, heap.length - 1);
            decrementArraySize();
            bubble(k);
        }
        return l;
    }

    /**
     * Algorithm find(AdaptablePQEntry e)
     * Searches for the corresponding entry with the same key. Returns -1 is no such entry exists.
     * Input: the entry to look for
     * Output: the index of the found entry, -1 otherwise
     *      Algorithm recurseFind(e, 0, heap length)
     */
    public int find(AdaptablePQEntry e){
        return recurseFind(e, 0, heap.length - 1);
    }
    /**
     * Algorithm recurseFind(AdaptablePQEntry e, beginning index, ending index)
     * Input: the entry to look for, the beginning index, the ending index
     * Output: the index of the found entry, -1 otherwise
     * if beginning = ending do
     *      return -1
     * else if middle of list is greater than e do
     *      return recurseFind(e, upper half of heap)
     * else if middle of list is less than e do
     *      return recurseFind(e, lower half of heap)
     * else if middle of list is equal to e do
     *      return middle index of list
     * else do
     *      return -1 //if not such entry exists
     */
    public int recurseFind(AdaptablePQEntry e, int beginning, int ending){
        if(beginning == ending)
            return -1; //Returns this if no such entry exists.
        else if(compare((AdaptablePQEntry)heap[ending-beginning/2], e) > 0)
            return recurseFind(e, ending/2, ending);
        else if(compare((AdaptablePQEntry)heap[ending-beginning/2], e) < 0)
            return recurseFind(e, beginning, ending/2);
        else if(compare((AdaptablePQEntry)heap[ending-beginning/2], e) == 0)
            return ending-beginning/2;
        else
            return -1; //Returns this if no such entry exists.
    }

    /**
     * Algorithm replaceKey(AdaptablePQEntry e, Integer k)
     * Find index of e with find method
     * Store old key in variable
     * if index is valid do
     *      Get entry from heap
     *      Assign entry key to old key variable
     *      Set key to k
     * return old key variable
     */
    public Integer replaceKey(AdaptablePQEntry e, Integer k){
        int u = find(e);
        Integer oldKey = u;
        if(u != -1) {
            AdaptablePQEntry t = (AdaptablePQEntry) heap[u];
            oldKey = (Integer)t.getKey();
            t.setKey(k);
        }
        return oldKey;
    }
    /**
     * Algorithm replaceValue(AdaptablePQEntry e, Double v)
     * Find index of e with find method
     * Store old value in variable
     * if index is valid do
     *      Get entry from heap
     *      Assign entry value to old key variable
     *      Set value to k
     * return old value variable
     */
    public Double replaceValue(AdaptablePQEntry e, Double v){
        int u = find(e);
        Double oldValue = -1.0;
        if(u != -1) {
            AdaptablePQEntry t = (AdaptablePQEntry) heap[u];
            oldValue = (Double)t.getValue();
            t.setValue(v);
        }
        return oldValue;
    }
}
