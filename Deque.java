import java.util.Iterator;

/**
 * This class represents a <em>double-ended queue</em>, this implementation that I use is 
 * basically a doubly linked list. Assessment score: 100/100
 * @author Kevin Sun
 * @since 2018 Jan 04
 * @param <Item> Generic type of the data in the double-ended queue
 */
public class Deque<Item> implements Iterable<Item>{
    
    private int size;
    private Node head;   // dummy head
    private Node tail;   // dummy tail
    
    public Deque() {
        head = new Node(null);
        tail = new Node(null);
        head.next = tail;
        tail.prev = head;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void addFirst(Item item) {
        validateArg(item);
       
        Node newHead = new Node(item);
        newHead.next = head.next;
        head.next.prev = newHead;
        head.next = newHead;     
        newHead.prev = head;
        
        size++;
    }
   
    public void addLast(Item item) {
        validateArg(item);
       
        Node newTail = new Node(item);         
        tail.prev.next = newTail;
        newTail.prev = tail.prev;
        tail.prev = newTail;
        newTail.next = tail;
        size++;
    }
    
    public Item removeFirst() {
        validateDeque();
         
        Node tmp = head.next;
        head.next = head.next.next;
        head.next.prev = head;
        Item item = tmp.item;
        tmp = null;
        size--;
        return item;
    }
    
    public Item removeLast() {
        validateDeque();
        Node tmp = tail.prev;
        tail.prev = tail.prev.prev;
        tail.prev.next = tail;
        Item item = tmp.item;
        tmp = null;
        size--;
        return item;
    }
    
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    
    
    private void validateDeque() {
        if ( size == 0 ) throw new java.util.NoSuchElementException("The deque is empty.");  
    }
    
    private void validateArg(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException("The argument is null.");
    }
    
    
    
    private class Node {
        Item item;
        Node next;
        Node prev;
        
        Node(Item item) {
            this.item = item;
        }
        
    }
    
    private class DequeIterator implements Iterator<Item>{
        Node curr = head.next;

        @Override
        public boolean hasNext() {
            
            return curr != tail;
        }

        @Override
        public Item next() {

            if( !hasNext() ) throw new java.util.NoSuchElementException("There are no more items to return.");
            Item item = curr.item;
            curr = curr.next;
            return item;
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException("This operation is not supported.");
        }
        
    }
    
}
