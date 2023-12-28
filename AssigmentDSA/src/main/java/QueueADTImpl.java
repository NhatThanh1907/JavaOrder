import java.util.ArrayList;
import java.util.List;

public class QueueADTImpl<T> implements QueueADT<T>{

    private int capacity;
    private final List<T> item;

    public QueueADTImpl() {
        item = new ArrayList<>();
    }

    public QueueADTImpl(int capacity) {
        this.capacity = capacity;
        this.item = new ArrayList<>(capacity);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void enqueue(T t) {
        if(!isFull()) {
            this.item.add(t);
        }
        else{
            throw new RuntimeException("Queue is full");
        }
    }

    @Override
    public T dequeue() {
        if(!isEmpty()) {
            T t = item.get(0);
            item.remove(0);
            return t;
        }
        return null;
    }

    @Override
    public T peek() {
        if(!isEmpty()) {
            return item.get(0);
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return item.isEmpty();
    }

    @Override
    public boolean isFull() {
        return capacity == item.size();
    }
}
