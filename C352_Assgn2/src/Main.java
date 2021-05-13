/**
 * Anthony van Voorst
 * COMP 352 Section G
 * Assignment 2
 * Due October 30, 2019
 * This program is a driver for my jump game solver. It tests 20 random arrays of varying length starting at
 * different points. There are 5 custom arrays at the end for use in the demo.
 */
public class Main {
	public static boolean forward = true;
	public static ArrayQueue<Integer> visited = new ArrayQueue<>();
	public static int temp;
    public static void main(String[] args) {
	    int[] testCase = null;
		visited = new ArrayQueue<>();
		int arrayLength = 0, randomStartingPoint = 0;
		/*------ First 20 test cases ------*/
	    for(int i = 0; i < 20; i++) {
			arrayLength = 1 + (int) (Math.random() * 20);
			testCase = new int[arrayLength];
			randomStartingPoint = (int) (Math.random() * arrayLength);
			System.out.print("(" + randomStartingPoint + "):\t");
			for(int j = 0; j < arrayLength - 1; j++) {
				testCase[j] = 1 + (int) (Math.random() * arrayLength/2);
				System.out.print(testCase[j] + "\t");
			}
			testCase[arrayLength - 1] = 0;
			System.out.print(0 + ": ");
			System.out.println(scoreFarRightGood(randomStartingPoint, testCase));
			visited = new ArrayQueue<>();
		}
	    /*------ End of the 20 test cases ------*/
	    // Manual test cases for demo
		testCase = new int[]{4, 2, 3, 6, 7, 1, 6, 2, 8, 16, 0}; // false
		System.out.println(scoreFarRightGood(2, testCase));
		visited = new ArrayQueue<>();
	    testCase = new int[]{3, 8, 2, 3, 1, 5, 3, 5, 6, 0}; // true
	    System.out.println(scoreFarRightGood(0, testCase));
		visited = new ArrayQueue<>();
		testCase = new int[]{4, 7, 7, 6, 3, 9, 2, 5, 0}; // true
		System.out.println(scoreFarRightGood(0, testCase));
		visited = new ArrayQueue<>();
		testCase = new int[]{1, 3, 1, 2, 3, 0}; // false
		System.out.println(scoreFarRightGood(1, testCase));
		testCase = new int[]{4, 12, 3, 6, 3, 1, 2, 18, 16, 0}; // false
		System.out.println(scoreFarRightGood(2, testCase));
    }
	public static int[] checkDuplicates(int[] A) {
		ArrayQueue Q = new ArrayQueue<Integer>();
		int temp, temp2;
		boolean duplicate = false;
		Q.enqueue(A[0]);
		for(int i = 1; i < A.length; i++) {
            temp = A[i];
            for(int j = 0; j < Q.size(); j++){
                temp2 = (int)Q.dequeue();
                if(temp == temp2)
                    duplicate = true;
                Q.enqueue(temp2);
            }
            if(!(duplicate)) {
                Q.enqueue(temp);
            }
            duplicate = false;
        }
		int[] toReturn = new int[Q.size()];
		for(int j = 0; j < toReturn.length; j++){
			toReturn[j] = (int)Q.dequeue();
		}
		return toReturn;
	}

	/**
	 * Really inefficient and buggy jump game method. Runs in O(n^2) and Omega(1)
	 * @param startingPosition
	 * @param listOfSquares
	 * @return whether the game can be completed or not
	 */
	public static boolean scoreFarRight(int startingPosition, int[] listOfSquares){
		while(forward){
			System.out.print(listOfSquares[startingPosition] + " -> ");
			visited.enqueue(startingPosition);
			// you've reached the end
			if(startingPosition == listOfSquares.length - 1)
				return true;
			// goes past the end of the list
			else if(startingPosition + listOfSquares[startingPosition] > listOfSquares.length - 1) {
				// check if visited queue is empty
				if(visited.first() == null)
					visited.enqueue(startingPosition);
				// check if the node has been visited already
				else {
					for(int i = 0; i < visited.size(); i++) {
						if (visited.first() == startingPosition)
							return false;
						else
							visited.enqueue(visited.dequeue());
					}
				}
				forward = false;
			}
			// Stuck between 2 indexes that have the same number
			else if(listOfSquares[startingPosition] == listOfSquares[startingPosition + listOfSquares[startingPosition]] && startingPosition + 2 * listOfSquares[startingPosition] > listOfSquares.length - 1)
				return false;
			// Move forward by number
			else
				startingPosition += listOfSquares[startingPosition];
		}
		while(!forward){
			// If you go out of bounds in either direction
			if(startingPosition + listOfSquares[startingPosition] > listOfSquares.length - 1 && startingPosition - listOfSquares[startingPosition] < 0)
				return false;
			// If going backward goes out of bounds
			else if(startingPosition - listOfSquares[startingPosition] < 0) {
				forward = true;
			}
			// Move backward by number
			else{
				forward = true;
				startingPosition -= listOfSquares[startingPosition];
			}
		}
		// Recur to go forward again
		return scoreFarRight(startingPosition, listOfSquares);
	}

	/**
	 * More efficient jump game method. Runs in O(2^n) and Omega(1)
	 * @param SP Starting Point
	 * @param LOS List Of Squares
	 * @return whether the game can be completed or not
	 */
	static public boolean scoreFarRightGood(int SP, int[] LOS){
		// Position is out of bounds
		if(SP < 0 || SP > LOS.length - 1)
			return false;
		// Position has reached the end
		if(SP == LOS.length - 1)
			return true;
		// Check if the position has been visited already
		for (int i = 0; i < visited.size(); i++) {
			if (visited.first() == SP)
				return false;
			else
				visited.enqueue(visited.dequeue());
		}
		// Position has been visited, store in "visited" queue
		visited.enqueue(SP);
		return scoreFarRightGood(SP + LOS[SP], LOS) || scoreFarRightGood(SP - LOS[SP], LOS);
	}
}
