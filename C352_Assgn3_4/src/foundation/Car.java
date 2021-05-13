package foundation;

public class Car {
    private String classification, key, make;

    private Car() {
        // purposefully left empty
    }

    public Car(String classification, String key, String make) {
        this.classification = classification;
        this.key = key;
        this.make = make;
    }

    public String getClassification() {
        return classification;
    }

    public String getKey() {
        return key;
    }

    public String getMake() {
        return make;
    }

    public String toString(){
        return String.format("%s: %s %s", key , make, classification);
    }
}
