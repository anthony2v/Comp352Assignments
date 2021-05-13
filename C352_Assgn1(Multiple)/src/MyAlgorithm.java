public class MyAlgorithm {
    static int numberOfCalls = 0;
    public static int[] MyAlgorithm(int[] A, int n){
        boolean done = true;
        int j = 0;
        int temp;
        while (j <= n - 2){
            if (A[j] > A[j+1]){
                swap(A, j, j+1);
                done = false;
            }
            j++;
        }
        j = n - 1;
        while (j >= 1){
            if (A[j] < A[j-1]){
                swap(A, j-1, j);
                done = false;
            }
            j -= 1;
        }
        if (!done) {
            System.out.println("Recursive call " + ++numberOfCalls);
            MyAlgorithm(A, n);
        }
        numberOfCalls = 0;
        return A;
    }
    public static void swap(int[] A, int first, int second){
        int temp = A[first];
        A[first] = A[second];
        A[second] = temp;
    }
}
