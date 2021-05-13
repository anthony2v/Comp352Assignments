package foundation;

import java.util.ArrayList;
import java.util.Iterator;

public class SortedMap<K,V> implements Map<K,V>{
    private DefaultComparator comp = null;
    private ArrayList<Entry<K,V>> table = new ArrayList<>();
    public SortedMap(){
        comp = new DefaultComparator<>();
    }
    public SortedMap(DefaultComparator<K> comp){
        this.comp = comp;
    }
    public boolean isEmpty(){
        return size() == 0;
    }
    /** Returns the smallest index for range table[low..high] inclusive storing an entry with
     * a key greater then or equal to k (or else index high + 1, by convention).
     */
    private int findIndex(K key, int low, int high){
        if(high < low)          // no entry qualifies
            return high + 1;
        int mid = (low + high) / 2;
        int comp = compare(key, table.get(mid));
        if(comp == 0)
            return mid;         // found exact match
        else if(comp < 0)
            return findIndex(key, low, mid - 1); // answer is left of mid or possible mid
        else
            return findIndex(key, mid + 1, high); // answer is right of mid
    }
    /** Version of findIndex that searches the entire table. */
    private int findIndex(K key){
        return findIndex(key, 0,table.size() - 1);
    }
    // Support for public keySet method...
    private class KeyIterator implements Iterator<K> {
        private Iterator<Entry<K, V>> entries = entrySet().iterator(); // reuse entrySet
        public boolean hasNext() {
            return entries.hasNext();
        }

        public K next() {
            return entries.next().getKey(); // return key!
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    private class KeyIterable implements Iterable<K>{
        public Iterator<K> iterator(){
            return new KeyIterator();
        }
    }
    public Iterable<K> keySet(){
        return new KeyIterable();
    }

    // Support for public values method...
    private class ValueIterator implements Iterator<V> {
        private Iterator<Entry<K,V>> entries = entrySet().iterator(); // reuse entrySet
        public boolean hasNext() {
            return entries.hasNext();
        }

        public V next() {
            return entries.next().getValue(); // return value!
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    private class ValueIterable implements Iterable<V>{
        public Iterator<V> iterator(){
            return new ValueIterator();
        }
    }
    public Iterable<V> values(){
        return new ValueIterable();
    }
    /** Return the number of entries in the map. */
    public int size(){
        return table.size();
    }
    /** Returns the value associated with the specified key (or else null). */
    public V get(K key) {
        int j = findIndex(key);
        if(j == size() || compare(key, table.get(j)) != 0) // no match
            return null;
        return table.get(j).getValue();
    }
    public Entry<K,V> get(K key, boolean entry){
        int j = findIndex(key);
        if(j == size() || compare(key, table.get(j)) != 0) // no match
            return null;
        return table.get(j);
    }
    public Entry<K,V> put(Entry<K,V> newEntry){
        int j = findIndex(newEntry.getKey());
        table.add(j, newEntry);
        return null;
    }
    /**
     * Associates the given value with the given key, returning any overridden value.
     */
    public V put(K key, V value){
        int j = findIndex(key);
        if(j < size() && compare(key, table.get(j)) == 0)   // match exist
            return table.get(j).setValue(value);
        table.add(j, new Entry<K,V> (key, value));       // otherwise new
        return null;
    }
    /**
     * Removes the entry having key k (if any) and returns its associated value.
     */
    public V remove(K key){
        int j = findIndex(key);
        if(j == size() || compare(key, table.get(j)) != 0)  // no match
            return null;
        return table.remove(j).getValue();
    }
    public Entry<K,V> removeEntry(K key){
        int j = findIndex(key);
        if(j == size() || compare(key, table.get(j)) != 0)  // no match
            return null;
        return table.remove(j);
    }
    /** Utility returns the entry at index j, or else null if j is out of bounds. */
    public Entry<K,V> safeEntry(int j){
        if(j < 0 || j >= table.size())
            return null;
        return table.get(j);
    }
    /** Returns the entry having the least key (or null if map is empty). */
    public Entry<K,V> firstEntry() {
        return safeEntry(0);
    }
    /** Returns the entry having the greatest key (or null if map is empty). */
    public Entry<K,V> lastEntry(){
        return safeEntry(table.size() - 1);
    }
    /** Returns the entry with least key greater than or equal to given key (if any). */
    public Entry<K,V> ceilingEntry(K key){
        return safeEntry(findIndex(key));
    }
    /** Returns the entry with greatest key less than or equal to given key (if any). */
    public Entry<K,V> floorEntry(K key){
        int j = findIndex(key);
        if(j == size() || !key.equals(table.get(j).getKey()))
            j--;    // start one earlier (unless we had found a perfect match
        return safeEntry(j);
    }
    /** Returns the entry with greatest key strictly less than given key (if any). */
    public Entry<K,V> lowerEntry(K key){
        return safeEntry(findIndex(key) - 1);   // go strictly before the ceiling entry
    }
    /** Returns the entry with least key strictly greater than given key (if any). */
    public Entry<K,V> higherEntry(K key) {
        int j = findIndex(key);
        if(j < size() && key.equals(table.get(j).getKey()))
            j++;    // go past exact match
        return safeEntry(j);
    }
    // support for snapshot iterators for entrySet() and subMap() follow
    private Iterable<Entry<K,V>> snapshot(int startIndex, K stop){
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        int j = startIndex;
        while(j < table.size() && (stop == null || compare(stop, table.get(j)) > 0))
            buffer.add(table.get(j++));
        return buffer;
    }
    public Iterable<Entry<K,V>> entrySet(){
        return snapshot(0, null);
    }
    public Iterable<Entry<K,V>> subMap(K fromKey, K toKey){
        return snapshot(findIndex(fromKey), toKey);
    }
    protected boolean checkKey(K key){
        for(K k: keySet())
            if(key == k)
                return true;
        return false;
    }
    protected int compare(Entry<K,V> a, Entry<K,V> b){
        return comp.compare(a.getKey(), b.getKey());
    }
    protected int compare(Entry<K,V> a, K b){
        return comp.compare(a.getKey(), b);
    }
    protected int compare(K a, Entry<K,V> b){
        return comp.compare(a, b.getKey());
    }
}
