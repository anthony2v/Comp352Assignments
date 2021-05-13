public class DualArrayStack<E> {
    public static final int CAPACITY = 20;                  // default array capacity
    private E[] data;                                       // generic array used for storage
    private int t = -1;                                     // index of top element in stack 1
    private int u = CAPACITY;                           // index of top element in stack 2
    public DualArrayStack(){                                // constructs a stack with default capacity
        this(CAPACITY);
    }
    public DualArrayStack(int capacity){                    // constructs a stack with given capacity
        data = (E[]) new Object[capacity];                  // safe cast; compiler may give warning
        u = data.length;
    }
    public int size(int stackNumber){
        if(stackNumber == 1)
            return t + 1;
        else
            return data.length - u;
    }
    public boolean isEmpty(int stackNumber){
        if(stackNumber == 1)
            return (t == -1);
        else
            return (u == data.length);
    }
    public boolean isFull(int stackNumber){
        return (t == u - 1);                                      // for dual dynamic stacks
        //return (size(stackNumber) == data.length/2);                 // for evenly sized stack
    }
    public void push(E element, int stackNumber){
        if(isFull(stackNumber))
            throw new IllegalStateException("Stack is full");
        if(stackNumber == 1) {
            data[++t] = element;                                // increment stack 1 index before storing new element
        }
        else
            data[--u] = element;                                // decrement stack 2 index before storing new element
    }
    public E top(int stackNumber){
        if(isEmpty(stackNumber))
            return null;
        else if(stackNumber == 1)
            return data[t];
        else
            return data[u];
    }
    public E pop(int stackNumber){
        E answer = null;
        if(isEmpty(stackNumber))
            return null;
        if(stackNumber == 1) {
            answer = data[t];
            data[t--] = null;                                   // dereference to help garbage collection
        }
        else{
            answer = data[u];
            data[u++] = null;                                   // dereference to help garbage collection
        }
        return answer;
    }
}
