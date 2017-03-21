import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        int k = Integer.parseInt(args[0]);

        String s;
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(randomizedQueue.dequeue());
        }

    }

}