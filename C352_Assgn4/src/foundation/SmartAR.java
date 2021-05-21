/**
 *  A GARO database for keeping entries of cars using license plate numbers as keys. The underlying ADTs are
 *  a sequence and sorted map.
 */

package foundation;

import java.sql.Array;
import java.util.*;

public class SmartAR {
    private int size, threshold, keyLength, maxKeyLength = 6;
    private ArrayList<SmartAREntry<String, Car>> carList;
    private TreeMap<String, SmartAREntry<String, Car>> carMap;

    public SmartAR(){
        size = 0;
        threshold = 100;
        keyLength = 6;
        carList = new ArrayList<>();
        carMap = new TreeMap<>();
    }
    public int size(){
        if (overThreshold())
            return carMap.size();
        return carList.size();
    }

    public boolean isEmpty(){
        if (overThreshold())
            return carMap.isEmpty();
        return carList.isEmpty();
    }

    public SmartAREntry<String, Car> getSmartAREntry(String key) {
    	if(overThreshold())
            return carMap.get(key);
        for (SmartAREntry<String, Car> entry : carList) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
        }
        return null;
    }

    public void setThreshold(int threshold){
        this.threshold = threshold;
        checkThreshold();
    }

    public void setKeyLength(int length) {
        if (length >= 6 && length <= 12)
            maxKeyLength = length;
    }

    public void generate(int numberOfKeys) {
        String alphaNumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder key = new StringBuilder();
        for(int i = 0; i < numberOfKeys; i++){
            for(int j = 0; j <= keyLength; j++){
                key.append(alphaNumeric.charAt((int)(Math.random() * alphaNumeric.length())));
            }
            if(getSmartAREntry(key.toString()) != null)
                i--;
            else
                add(key.toString(), new Car("SUV", "Cadillac"));
            key.setLength(0);
        }
    }

    public String[] allKeys(){
    	if(overThreshold()) {
    		return (String[])carMap.keySet().toArray();
    	}
        String[] keySet = (String[])carList.toArray();
    	Arrays.sort(keySet);
    	return keySet;
    }

    public SmartAREntry<String, Car> add(String key, Car value){
        SmartAREntry<String, Car> smartAREntry = getSmartAREntry(key);
        if(overThreshold()) {
            carMap.put(key, new SmartAREntry<>(key, value));
            size = carMap.size();
        }
        else {
            if (smartAREntry == null) {               // if new key, increase size and add to data structure
                carList.addLast(new SmartAREntry<>(key, value));
                size++;
                checkThreshold();
            } else {
                smartAREntry.setValue(value);
            }
        }
        if(key.length() > maxKeyLength)
            maxKeyLength = key.length();
        return smartAREntry;
    }
    public void remove(String key){
    	SmartAREntry<String, Car> answer;
    	if(overThreshold()) {
    		size--;
    		carMap.remove(key);
    	}
    	else {
	        Iterator<SmartAREntry<String, Car>> iterator = carList.elements();
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
    	SmartAREntry<String, Car> smartAREntry;
    	if(overThreshold())
    		return carMap.get(key);
    	else {
    		smartAREntry = getSmartAREntry(key);
            if(smartAREntry == null)
                return null;
    	}
        return smartAREntry.getValue();
    }
    public SmartAREntry<String, Car> nextKey(String key){
    	Position<SmartAREntry<String, Car>> entry;
    	if(overThreshold())
    		return carMap.higherEntry(key);
		else {
			entry = getPosition(key);
			return carList.after(entry).getElement();
		}
    }
    public SmartAREntry<String, Car> prevKey(String key){
        Position<SmartAREntry<String, Car>> entry;
        if(overThreshold())
        	return carMap.lowerEntry(key);
        else {
        	entry = getPosition(key);
        	return carList.before(entry).getElement();
        }
    }
    public LinkedList<Car> previousCars(String key){
        SmartAREntry<String, Car> smartAREntry = getSmartAREntry(key);
        if(smartAREntry != null)
            return smartAREntry.getHistory();
        return null;
    }

    // -------------------------- Helper Methods --------------------------
    private boolean overThreshold() {
        return size > threshold;
    }
    private void checkThreshold() {
    	if(overThreshold() && carMap.isEmpty())
    		toMap(carList.elements());
    	else if(!overThreshold() && carList.isEmpty())
    		toSequence(carMap.entrySet());
    }
    public void toLinkedList() {
    	carList = new LinkedList<>();
    	for(SmartAREntry<String, Car> element: positions)
    		carList.addLast(element);
    	carMap = null;                 // to help garbage collection
    }
    public void toMap(Iterator<SmartAREntry<String, Car>> elements) {
    	carMap = new TreeMap<>();
		while(elements.hasNext()){
    		carMap.put(elements.next());
		}
		carList = null;                // to help garbage collection
    }
}
