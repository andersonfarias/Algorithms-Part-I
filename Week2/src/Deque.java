import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item value;
        private Node next;
        private Node previous;

        public Node(Item value, Node next, Node previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    private Node first;

    private Node last;

    private int size;

    public boolean isEmpty() {
        return first == null || last == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();

        Node oldFirst = first;

        first = new Node(item, oldFirst, null);
        if (isEmpty()) this.last = first;
        if (oldFirst != null) oldFirst.previous = first;

        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();

        Node oldLast = last;

        last = new Node(item, null, oldLast);
        if (oldLast != null) oldLast.next = last;
        if (isEmpty()) this.first = last;

        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Node n = first;
        first = n.next;

        if (first != null) first.previous = null;

        n.next = null;
        n.previous = null;

        size--;

        if (isEmpty()) last = null;

        return n.value;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Node n = last;
        last = n.previous;

        if (last != null) last.next = null;

        n.next = null;
        n.previous = null;
        size--;

        if (isEmpty()) first = null;

        return n.value;
    }

    public Iterator<Item> iterator() {
        return new FrontToEndIterator();
    }

    private class FrontToEndIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.value;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        deque.addFirst(0);
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.removeLast();

        System.out.println(deque.isEmpty());
        Iterator<Integer> i = deque.iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }

}