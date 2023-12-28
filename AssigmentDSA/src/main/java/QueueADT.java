public interface QueueADT <T>{
    void enqueue(T t);
    T dequeue();
    T peek();
    boolean isEmpty();
    boolean isFull();

}
