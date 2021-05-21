package foundation;

import java.util.LinkedList;

public class SmartAREntry<K,V> {
    private K key;
    private V value;
    private LinkedList<V> history;

    private SmartAREntry() {
        // purposefully left empty
    }

    public SmartAREntry(K key, V value){
        this.key = key;
        this.value = value;
        history = new LinkedList<>();
    }

    public K getKey(){
        return key;
    }

    public V getValue(){
        return value;
    }

    public LinkedList<V> getHistory(){
        return history;
    }

    public K setKey(K key) {
        K temp = this.key;
        this.key = key;
        return temp;
    }

    public V setValue(V value) {
        V temp = this.value;
        this.value = value;
        history.add(temp);
        return temp;
    }

    public void setHistory(LinkedList<V> history){
        this.history = history;
    }
    public String toString(){
        return key.toString() + " " + value.toString();
    }
}
