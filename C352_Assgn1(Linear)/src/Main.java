/**
 * Anthony van Voorst
 * COMP 352 Section G
 * Assignment 1
 * Due September 30, 2019
 * This program is a tetranacci calculator using linear recursion. It will calculate tetranacci numbers from 5 to 100
 * in steps of 5. The numbers are written to a text file along with the execution time in nanoseconds.
 */

import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        // Opening the text file for writing.
        PrintWriter output = null;
        try{
            output = new PrintWriter(new FileOutputStream("out.txt"));
        }
        catch (FileNotFoundException e){
            output.println("Error opening file \"out.txt\", system will exit.");
            System.exit(1);
        }
        // Testing the algorithm.
        output.println("LinearTetranacci (5-100 by steps of 5):");
        long time = System.nanoTime();
        for(int i = 5; i < 100; i += 5){
            long[] linearTetranacciResult = LinearTetranacci(i);
            output.println(linearTetranacciResult[0]);
        }
        output.println("Time: " + (System.nanoTime() - time) + " ns.");
        // Closing the output file.
        output.close();
    }
    /**
     * Tetranacci calculator method.
     * @param k the specific tetranacci number to calculate.
     * @return the kth tetranacci number.
     */
    static public long[] LinearTetranacci(int k){
        if(k < 3)
            return new long[]{0, 0, 0, 0};
        else if(k == 4)
            return new long[]{1, 0, 0, 0};
        else {
            long[] temp = LinearTetranacci(k-1);
            return new long[]{temp[0] + temp[1] + temp[2] + temp[3], temp[0], temp[1], temp[2]};
        }
    }
}
