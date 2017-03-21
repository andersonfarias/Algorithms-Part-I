import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a = (Item[]) new Object[1];

    private int n;

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++)
            temp[i] = a[i];
        a = temp;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();

        if (n == a.length) resize(2 * a.length);
        a[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int rand = StdRandom.uniform(n);

        Item item = a[rand];
        a[rand] = null;
        shift(rand);

        n--;

        if (n > 0 && n == a.length / 4) resize(a.length / 2);

        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        return a[StdRandom.uniform(n)];
    }

    private void shift(int rand) {
        for (int i = rand; i < n - 1; i++)
            a[i] = a[i + 1];
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {

        private int[] indexes = new int[n];

        private int current = 0;

        public RandomIterator() {
            for (int i = 0; i < n; i++) {
                indexes[i] = -1;
            }

            for (int i = 0; i < n; i++) {
                int index = StdRandom.uniform(0, n);
                while (indexes[index] != -1) {
                    index = StdRandom.uniform(0, n);
                }
                indexes[index] = i;
            }
        }

        @Override
        public boolean hasNext() {
            return current < n;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            return a[indexes[current++]];
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        System.out.println(queue.isEmpty());
        System.out.println(queue.size());

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        System.out.println(queue.sample());
        System.out.println(queue.sample());
        System.out.println(queue.sample());

        System.out.println(queue.size());

        Iterator<String> i = queue.iterator();

        while (i.hasNext()) {
            System.out.println(i.next());
        }

        queue.dequeue();
        queue.dequeue();

        queue.enqueue("D");
        queue.enqueue("E");
        queue.enqueue("F");

        i = queue.iterator();

        System.out.println(queue.size());
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }
}