public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Test Case 1: =========================================");
        HeapAdaptablePriorityQueue test = new HeapAdaptablePriorityQueue<Integer, Double>();
        test.insert(1, 0);
        test.insert(6, 0);
        test.insert(2, 0);
        test.insert(24, 0);
        test.insert(15, 0);
        test.insert(7, 0);
        HeapAdaptablePriorityQueue.AdaptablePQEntry p = new HeapAdaptablePriorityQueue.AdaptablePQEntry<Integer, Double>(4, 0.0);
        test.insert(4, 0.0);
        System.out.println(test.top());
        System.out.println("Heap is empty is " + test.isEmpty());
        System.out.println(test.state());
        System.out.println("Size of heap is " + test.size());
        System.out.println(test.remove(p));
        System.out.println("Size of heap is " + test.size());
        System.out.println(test.removeTop());
        System.out.println("Size of heap is " + test.size());
        test.toggle();
        System.out.println(test.state());
        while(!test.isEmpty()) {
            System.out.println(test.removeTop());
            System.out.println("Size of heap is " + test.size());
        }
        test.top();
        System.out.println("Test Case 2: =========================================");
        HeapAdaptablePriorityQueue test2 = new HeapAdaptablePriorityQueue();
        System.out.println("Heap is empty is " + test2.isEmpty());
        test2.insert(2, 29);
        test2.insert(7, 40);
        test2.insert(5, 34);
        test2.insert(6, 12);
        System.out.println("Heap is empty is " + test2.isEmpty());
        System.out.println(test2.state());
        System.out.println(test2.state());
        System.out.println(test.top());
        System.out.println(test.removeTop());
        System.out.println("Size of heap is " + test.size());
        while(!test.isEmpty()) {
            System.out.println(test.removeTop());
            System.out.println("Size of heap is " + test.size());
        }
    }
}
