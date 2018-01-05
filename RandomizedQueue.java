import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

/**
 * This class represents a <em>randomized queue</em>, I used  
 * the circular array implementation for the queue, which is an overkill, 
 * I realized that after the submission. The swap is the right move,
 * but I should have taken advantage of the fact that dequeue is random and 
 * swap an random element with the tail instead of the head. A better implementation 
 * is to use an array and a int to store the size. When enqueue, add to the array[size],
 * when dequeue, pick a random element, swap with array[size-1], and dequeue array[size-1],
 * shrink and enlarge when necessary.
 * Assessment score: 100/100
 * @author Kevin Sun
 * @since 2018 Jan 04
 * @param <Item> Generic type of the data in the double-ended queue
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] array;
    private int head;
    private int tail;
    private int size;
    
    public RandomizedQueue() {
        head = 0;
        tail = -1;
        array = (Item[])new Object[1];
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    
    public void enqueue(Item item) {
        validateArg(item);
        
        int length = array.length;
        
        if(size == length) {
            resize(length*2);
        }
        
        // possibly a new array
        tail = (tail + 1) % array.length;
        array[tail] = item;
        size++;
    }
    
    public Item dequeue() {
        
        validateQueue();
        
        int length = array.length;
        
        if(size<= length/4) {
            resize(length/2);
        }
        
        rdmSwapHead();
        
        Item item = array[head];
        array[head] = null;
        head = (head + 1) % array.length;
        
        size--;
        return item;
    }
    
    public Item sample() {
        validateQueue();
        rdmSwapHead();
        return array[head];
    }
  
    private void rdmSwapHead() {
        int index = StdRandom.uniform(size);
        index = (head+index)%array.length;
        Item item = array[head];
        array[head] = array[index];
        array[index] = item;
    }
    
    private void resize(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
        
        for(int i=0;i<size;i++) {
            newArray[i] = array[(head + i) % array.length];
        }
        
        head = 0;
        tail = size-1;
        array = newArray;
       
    }
    
    private void validateQueue() {
        if ( size == 0 ) throw new java.util.NoSuchElementException("The queue is empty.");  
    }
    
    private void validateArg(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException("The argument is null.");
    }
    

    @Override
    public Iterator<Item> iterator() {

        return new RandomQIterator();
    }
    
    private class RandomQIterator implements Iterator<Item> {
        
        private int[] rdmIndices = new int[size];
        private int curr = 0;
        
        public RandomQIterator() {
            for(int i=0;i<size;i++) {
                rdmIndices[i] = i;
            }
            
            StdRandom.shuffle(rdmIndices);
        }

        @Override
        public boolean hasNext() {

            return curr != size;
        }

        @Override
        public Item next() {
         
            if( !hasNext() ) throw new java.util.NoSuchElementException(
                    "There are no more items to return.");
            return array[ rdmIndices[curr++] ];
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException("This operation is not supported.");
        }
        
    }
    
    
}
