/**
 *  A GARO database for keeping entries of cars using license plate numbers as keys. The underlying ADTs are
 *  a sequence and sorted map.
 */

package foundation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

public class SmartAR {
    private int size, threshold, keyLength, maxKeyLength = 6;
    private LinkedList<Car> carList;
    private TreeMap<String, Car> carMap;

    public SmartAR(){
        size = 0;
        threshold = 100;
        keyLength = 6;
        carList = new Sequence<>();
        Object[] buckets = new Object[43];
        carMap = new SortedMap<>();
    }
    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return carList.isEmpty();
    }
    public Position<Entry<String, Car>> getPosition(String key){
    	if(!overThreshold()) {
	        for(Position<Entry<String, Car>> position: carList.positions()){
	            if(position.getElement().getKey().equals(key))
	                return position;
	        }
    	}
    	else {
    		System.out.println("Operation not supported for maps.");
    	}
        return null;
    }
    public Entry<String, Car> getElement(String key){
    	Iterator<Entry<String, Car>> iterator = null;
    	Entry<String, Car> answer = null;
    	if(!overThreshold()) {
	        iterator = carList.elements();
	        while(iterator.hasNext()) {
	            answer = iterator.next();
	            if(answer.getKey().equals(key)){
	                return answer;
	            }
	        }
    	}
    	else {
    		return carMap.get(key);
    	}
        return null;
    }
    public void setThreshold(int newThreshold){
        threshold = newThreshold;
        checkThreshold();
    }
    public void setKeyLength(int length) {
        if(length > maxKeyLength)
            maxKeyLength = length;
        keyLength = length;
    }
    public void generate(int numberOfKeys) {
        String alphaNumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder key = new StringBuilder();
        for(int i = 0; i < numberOfKeys; i++){
            for(int j = 0; j <= keyLength; j++){
                key.append(alphaNumeric.charAt((int)(Math.random() * alphaNumeric.length())));
            }
            if(getElement(key.toString()) != null)
                i--;
            else
                add(key.toString(), new Car());
            key.setLength(0);
        }
    }
    public Sequence<String> allKeys(){
    	Sequence<String> keySet = new Sequence<>();
    	if(overThreshold()) {
    		for(String key: carMap.keySet())
    			keySet.addLast(key);
    	}
    	else {
    	    insertionSort();
	        Iterator<Entry<String, Car>> entryIterator = carList.elements();
	        while(entryIterator.hasNext()){
	            keySet.addLast(entryIterator.next().getKey());
	        }
    	}
        return keySet;
    }
    public Entry<String, Car> add(String key, Car value){
        Entry<String, Car> entry = getElement(key);
        if(overThreshold()) {
            carMap.put(key, value);
            size = carMap.size();
        }
        else {
            if (entry == null) {               // if new key, increase size and add to data structure
                carList.addLast(new Entry<>(key, value));
                size++;
                checkThreshold();
            } else {
                entry.setValue(value);
            }
        }
        if(key.length() > maxKeyLength)
            maxKeyLength = key.length();
        return entry;
    }
    public void remove(String key){
    	Entry<String, Car> answer;
    	if(overThreshold()) {
    		size--;
    		carMap.remove(key);
    	}
    	else {
	        Iterator<Entry<String, Car>> iterator = carList.elements();
	        while(iterator.hasNext()) {
	            answer = iterator.next();
	            if(answer.getKey().equals(key)){
	                iterator.remove();
	                size--;
	            }
	        }
    	}
    }
    public Car getValues(String key){
    	Entry<String, Car> entry;
    	if(overThreshold())
    		return carMap.get(key);
    	else {
    		entry = getElement(key);
            if(entry == null)
                return null;
    	}
        return entry.getValue();
    }
    public Entry<String, Car> nextKey(String key){
    	Position<Entry<String, Car>> entry;
    	if(overThreshold())
    		return carMap.higherEntry(key);
		else {
			entry = getPosition(key);
			return carList.after(entry).getElement();
		}
    }
    public Entry<String, Car> prevKey(String key){
        Position<Entry<String, Car>> entry;
        if(overThreshold())
        	return carMap.lowerEntry(key);
        else {
        	entry = getPosition(key);
        	return carList.before(entry).getElement();
        }
    }
    public Sequence<Car> previousCars(String key){
        Entry<String, Car> entry = getElement(key);
        if(entry != null)
            return entry.getHistory();
        return null;
    }
    public boolean overThreshold() {
    	if(size > threshold)
    		return true;
    	return false;
    }
    public void checkThreshold() {
    	if(overThreshold() && carMap.isEmpty())
    		toMap(carList.elements());
    	else if(!overThreshold() && carList.isEmpty())
    		toSequence(carMap.entrySet());
    }
    public void toSequence(Iterable<Entry<String, Car>> positions) {
    	carList = new Sequence<>();
    	for(Entry<String, Car> element: positions)
    		carList.addLast(element);
    	carMap = null;                 // to help garbage collection
    }
    public void toMap(Iterator<Entry<String, Car>> elements) {
    	carMap = new SortedMap<>();
		while(elements.hasNext()){
    		carMap.put(elements.next());
		}
		carList = null;                // to help garbage collection
    }
    public void insertionSort(){
        DefaultComparator comp = new DefaultComparator();
        if(!overThreshold()){
            Position<Entry<String,Car>> cursor = carList.first();
            while(cursor != carList.peekLast()) {
                Position<Entry<String, Car>> mid = carList.after(cursor);
                Entry<String, Car> element = mid.getElement();
                if(comp.compare(element.getKey(), mid.getElement().getKey()) > 0)
                    cursor = mid;
                else{
                    Position<Entry<String,Car>> position = cursor;
                    while(cursor != carList.first() && comp.compare(carList.before(cursor).getElement().getKey(), element.getKey()) > 0)
                        cursor = carList.before(cursor);
                    carList.remove(mid);
                    carList.addBefore(cursor, element);
                }
            }
        }
    }
}
