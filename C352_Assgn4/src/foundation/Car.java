/**
 *
 */

package foundation;

public class Car {
    private String classification, make;

    private Car() {
        // purposefully left empty
    }

    public Car(String classification, String make) {
        this.classification = classification;
        this.make = make;
    }

    public String getClassification() {
        return classification;
    }

    public String getMake() {
        return make;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String toString(){
        return String.format("%s %s" , make, classification);
    }
}
