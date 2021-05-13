/**
 * Anthony van Voorst
 * COMP 352 Section G
 * Assignment 1
 * Due September 30, 2019
 * This program is a tetranacci calculator using tail recursion. It will calculate tetranacci numbers from 5 to 100
 * in steps of 5. The numbers are written to a text file along with the execution time in nanoseconds.
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

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
        long time = System.nanoTime();
        // Testing the algorithm.
        output.println("TRTetranacci(5-100 by steps of 5):");
        time = System.nanoTime();
        for(int i = 5; i < 100; i += 5) {
            long result = TRTetranacci(i);
            output.println(result);
        }
        output.println("Time: " + (System.nanoTime() - time) + " ns.");
        // Closing the output file.
        output.close();
    }

    /**
     * Helper method for the tail recursive tetranacci calculator.
     * @param j the desired tetranacci number to calculate.
     * @return the jth tetranacci number
     */
    static public long TRTetranacci(int j){
        long[] numbers = new long[]{j, 0, 0, 0, 1};
        return TheRealTRTetranacci(numbers);
    }
    /**
     * Tetranacci calculator method using tail recursion.
     * @param input the specific tetranacci number to calculate with the 3 previous tetranacci numbers.
     * @return an array with the kth tetranacci number and the 3 previous tetranacci numbers.
     */
    static public long TheRealTRTetranacci(long[] input){
        if(input[0] == 4)
            return input[4];
        else
            return TheRealTRTetranacci(new long[]{input[0]-1, input[2], input[3], input[4], input[1]+input[2]+input[3]+input[4]});
    }
}
