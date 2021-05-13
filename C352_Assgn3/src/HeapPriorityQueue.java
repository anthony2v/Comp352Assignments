import java.util.Comparator;

public class HeapPriorityQueue<K,V> extends AbstractPriorityQueue {
    protected boolean isMaxPQ;
    protected Object[] heap = new Object[1];                // generic array used for storage
    public HeapPriorityQueue(){
        super();
    }
    public HeapPriorityQueue(Comparator<K> comp){
        super(comp);
    }
    protected int parent(int j){
        return (j - 1) / 2;
    }
    protected int left(int j){
        return 2 * j + 1;
    }
    protected int right(int j){
        return 2 * j + 2;
    }
    protected boolean hasLeft(int j){
        return left(j) < heap.length;
    }
    protected boolean hasRight(int j){
        return right(j) < heap.length;
    }
    protected void swap(int i, int j){
        Object temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Algorithm upheap(int j)
     * while j is greater than 0 do
     *      get parent
     *      if max heap do
     *          if j is greater than or equal to parent do
     *              break
     *      else
     *          if j is lass than or equal to parent do
     *              break
     *      swap j with parent in heap
     *      assign parent to j
     */
    protected void upheap(int j){
        while(j > 0){
            int p = parent(j);
            if(!isMaxPQ) {
                if (compare((Entry) heap[j], (Entry) heap[p]) >= 0)
                    break;
            }
            else
                if(compare((Entry)heap[j], (Entry)heap[p]) <= 0)
                    break;
            swap(j, p);
            j = p;
        }
    }

    /**
     * Algorithm downheap(int j)
     * Input: key of the entry to downheap
     * while key has left leaf do
     *      get left leaf index and assign to new variable
     *      If key has right leaf do
     *          get right leaf index and assign to new variable
     *        if max heap do
     *           find max of the 2 leaves
     *        if min heap do
     *            find min of the 2 leaves
     *      if max heap dp
     *          compare max of 2 leaves with entry of input key
     *      else do
     *          compare min of 2 leaves with entry of input key
     *      swap input key with min/max
     *      assign input key with min/max
     */
    protected void downheap(int j){
        while(hasLeft(j)){
            int leftIndex = left(j);
            int smallChildIndex = leftIndex;
            if(hasRight(j)) {
                int rightIndex = right(j);
                if(!isMaxPQ) {
                    if (compare((Entry) heap[leftIndex], (Entry) heap[rightIndex]) >= 0)
                        smallChildIndex = rightIndex;
                }
                else
                    if (compare((Entry)heap[leftIndex], (Entry)heap[rightIndex]) <= 0)
                        smallChildIndex = rightIndex;
            }
            if(!isMaxPQ) {
                if (compare((Entry) heap[smallChildIndex], (Entry) heap[j]) >= 0)
                    break;
            }
            else
                if (compare((Entry) heap[smallChildIndex], (Entry) heap[j]) <= 0)
                    break;
            swap(j, smallChildIndex);
            j = smallChildIndex;
        }
    }
    /**
     * Algorithm IncrementArraySize()
     * Input: Array which is too small for current application
     * Output: Same array incremented by 1
     * Initialize new array
     * For each element in the old array
     *      Copy the element from the old array to the new one
     * Assign reference of new array to old one
     * Dereference old array
     */
    protected void incrementArraySize(){
        Object[] newData = new Object[size() + 1]; // safe cast; compiler may give warning
        for(int i = 0; i < newData.length - 1; i++)
            newData[i] = heap[i];
        heap = newData;
        newData = null;                        // dereference to help garbage collection
    }
    public void add(Entry<K,V> newEntry){
        if(heap[heap.length - 1] != null)
            incrementArraySize();
        heap[heap.length - 1] = newEntry;
    }
    public int size(){
        return heap.length;
    }
    public Entry<K,V> min(){
        return (Entry)heap[0];
    }
    public Entry<K,V> insert(Object key, Object value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K,V> newest = new PQEntry<K, V>((K)key, (V)value);
        add(newest);
        upheap(heap.length - 1);
        return newest;
    }
    public Entry<K,V> removeMin(){
        if(heap[0] == null)
            return null;
        Entry<K,V> answer = (Entry)heap[0];
        swap(0, heap.length - 1);
        downheap(0);
        return answer;
    }
}
