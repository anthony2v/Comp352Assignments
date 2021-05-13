package foundation;

public class Entry<K,V> {
    private K key;
    private V value;
    private Sequence<V> history;
    public Entry(){
    }
    public Entry(K key, V value){
        this.key = key;
        this.value = value;
        history = new Sequence<>();
    }
    public K getKey(){
        return key;
    }
    public V getValue(){
        return value;
    }
    public Sequence<V> getHistory(){
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
        history.addLast(temp);
        return temp;
    }
    public void setHistory(Sequence<V> history){
        this.history = history;
    }
    public String toString(){
        return key.toString() + " " + value.toString();
    }
}
