/**
 * Anthony van Voorst
 * COMP 352 Section G
 * Assignment 1
 * Due September 30, 2019
 * This program is a tetranacci calculator using multiple recursion. It will calculate tetranacci numbers from 5 to 40
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
        output.println("MultipleTetranacci(5-40 by steps of 5):");
        time = System.nanoTime();
        for(int i = 5; i < 40; i += 5) {
            long result = MultipleTetranacci(i);
            output.println(result);
        }
        output.println("Time: " + (System.nanoTime() - time) + " ns.");
        // Closing the output file.
        output.close();
    }

    /**
     * Tetranacci calculator method using multiple recursion.
     * @param k the specific tetranacci number to calculate.
     * @return the kth tetranacci number.
     */
    static public long MultipleTetranacci(int k) {
        if (k < 3)
            return 0;
        else if (k == 4)
            return 1;
        else
            return MultipleTetranacci(k - 1) + MultipleTetranacci(k - 2) + MultipleTetranacci(k - 3) + MultipleTetranacci(k - 4);
    }
}
