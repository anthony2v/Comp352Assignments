public class ArrayStack<E> {
    public static final int CAPACITY = 1000;                // default array capacity
    private E[] data;                                       // generic array used for storage
    private int t = -1;                                     // index of top element in stack
    public ArrayStack(){                                    // constructs a stack with default capacity
        this(CAPACITY);
    }
    public ArrayStack(int capacity){                        // constructs a stack with given capacity
        data = (E[]) new Object[capacity];                  // safe cast; compiler may give warning
    }
    public int size(){
        return t + 1;
    }
    public boolean isEmpty(){
        return (t == -1);
    }
    public void push(E element){
        if(size() == data.length)
            throw new IllegalStateException("Stack is full");
        data[++t] = element;                                // increment index before storing new element
    }
    public E top(){
        if(isEmpty())
            return null;
        return data[t];
    }
    public E pop(){
        if(isEmpty())
            return null;
        E answer = data[t];
        data[t--] = null;                                   // dereference to help garbage collection
        return answer;
    }
}
