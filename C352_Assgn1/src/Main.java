/**
 * Anthony van Voorst
 * COMP 352 Section AA
 * Assignment 1
 * Due May 19, 2019
 * This program tests 2 algorithms that calculate tetranacci numbers. The first one uses exponential time complexity
 * while the second is tail recursive with linear time complexity. The program sends all output to a text file along
 * with the amount of time each algorithm took in milliseconds. Actual time depends on the machine used.
 */

import java.io.*;

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
        // Testing the first algorithm.
        output.println("ExponentialTetranacci (5-100 by steps of 5):");
        long time = System.currentTimeMillis();
        for(int i = 5; i < 100; i += 5){
            output.println(ExponentialTetranacci(i-1));
        }
        output.println("Time: " + (System.currentTimeMillis()-time) + " ms.");
        // Testing the second algorithm.
        output.println("TRTetranacci(5-100 by steps of 5):");
        time = System.currentTimeMillis();
        for(int i = 5; i < 100; i += 5) {
            long[] result = TRTetranacci(i-1);
            output.println(result[0]);
        }
        output.println("Time: " + (time - System.currentTimeMillis()) + " ms.");
        // Closing the output file.
        output.close();
    }

    /**
     * The exponential time complexity algorithm for tetranacci numbers.
     * @param k the kth tetranacci number to calculate
     * @return the result of the calculation
     */
    static public long ExponentialTetranacci(int k){
        if(k < 3)
            return 0;
        else if(k == 3)
            return 1;
        else
            return ExponentialTetranacci(k-1) + ExponentialTetranacci(k-2) + ExponentialTetranacci(k-3) + ExponentialTetranacci(k-4);
    }

    /**
     * The tail recursive algorithm for tetranacci numbers.
     * @param k the kth tetranacci number to calculate
     * @return the result of the calculation along with the 3 previous tetranacci numbers
     */
    static public long[] TRTetranacci(int k){
        if(k < 3)
            return new long[]{0, 0, 0, 0};
        else if(k == 3)
            return new long[]{1, 0, 0, 0};
        else {
            long[] temp = TRTetranacci(k-1);
            return new long[]{temp[0] + temp[1] + temp[2] + temp[3], temp[0], temp[1], temp[2]};
        }
    }
}
