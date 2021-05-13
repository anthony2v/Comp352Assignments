import foundation.Car;
import foundation.SmartAR;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner inputFile = null;
        // TEST 1:
        try{
            inputFile = new Scanner(new FileInputStream("ar_test_file5.txt"));
        }
        catch(FileNotFoundException e){
            System.out.println("Error opening file: file not found.");
        }
        SmartAR test = new SmartAR();
        while(inputFile.hasNext())
            test.add(inputFile.next(), new Car());
        System.out.println();
        System.out.println("Test 1 complete");
        test.remove("I0IMFYHLO8Y7");
        System.out.println();
        /*
        // TEST 2
        Iterator iter = test.allKeys().elements();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
        System.out.println("Test 2 complete");
        // TEST 3
        try{
            inputFile = new Scanner(new FileInputStream("ar_test_file0.txt"));
        }
        catch(FileNotFoundException e){
            System.out.println("Error opening file: file not found.");
        }
        test = new SmartAR();
        while(inputFile.hasNext())
            test.add(inputFile.next(), new Car());
        System.out.println("Test 3 complete.");
        // TEST 4
        try{
            inputFile = new Scanner(new FileInputStream("ar_test_file1.txt"));
        }
        catch(FileNotFoundException e){
            System.out.println("Error opening file: file not found.");
        }
        test = new SmartAR();
        while(inputFile.hasNext())
            test.add(inputFile.next(), new Car());
        System.out.println("Test 4 complete.");
        // TEST 5
        try{
            inputFile = new Scanner(new FileInputStream("ar_test_file2.txt"));
        }
        catch(FileNotFoundException e){
            System.out.println("Error opening file: file not found.");
        }
        test = new SmartAR();
        while(inputFile.hasNext())
            test.add(inputFile.next(), new Car());
        System.out.println("Test 5 complete.");
        // TEST 6
        try{
            inputFile = new Scanner(new FileInputStream("ar_test_file3.txt"));
        }
        catch(FileNotFoundException e){
            System.out.println("Error opening file: file not found.");
        }
        test = new SmartAR();
        while(inputFile.hasNext())
            test.add(inputFile.next(), new Car());
        System.out.println("Test 6 complete.");
        // TEST 7
        test.add("MYWARXX3", new Car());
        test.add("MYWARXX3", new Car());
        test.add("MYWARXX3", new Car());
        test.add("MYWARXX3", new Car());
        iter = test.previousCars("MYWARXX3").elements();
        while(iter.hasNext()){
            System.out.println("iter.next()");
        }
        System.out.println("Test 7 complete.");*/
    }
}
