import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int OPENED = 1;

    private final int n;

    private int openedSites = 0;

    private int[][] sites;

    private int topElement;

    private int bottomElement;

    private WeightedQuickUnionUF unionFind;

    private WeightedQuickUnionUF unionFind2;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        this.sites = new int[n][n];
        this.topElement = n * n;
        this.bottomElement = topElement + 1;

        init();
    }

    public void open(int row, int col) {
        if (row <= 0 || row > n) throw new IndexOutOfBoundsException();
        if (col <= 0 || col > n) throw new IndexOutOfBoundsException();

        if (isOpen(row, col)) return;

        int p = component(row, col);

        sites[row - 1][col - 1] = OPENED;

        if (row == 1) {
            unionFind.union(p, topElement);
            unionFind2.union(p, topElement);
        }

        if (row == n) unionFind.union(p, bottomElement);

        connectToNeighbor(p, row - 1, col);
        connectToNeighbor(p, row + 1, col);
        connectToNeighbor(p, row, col - 1);
        connectToNeighbor(p, row, col + 1);

        openedSites++;
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > n) throw new IndexOutOfBoundsException();
        if (col <= 0 || col > n) throw new IndexOutOfBoundsException();

        return sites[row - 1][col - 1] == OPENED;
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || row > n) throw new IndexOutOfBoundsException();
        if (col <= 0 || col > n) throw new IndexOutOfBoundsException();

        if (!isOpen(row, col)) return false;

        if (row == 1) return true;

        return unionFind2.connected(component(row, col), topElement);
    }

    public int numberOfOpenSites() {
        return openedSites;
    }

    public boolean percolates() {
        return unionFind.connected(topElement, bottomElement);
    }

    private void init() {
        unionFind = new WeightedQuickUnionUF((n * n) + 2);
        unionFind2 = new WeightedQuickUnionUF((n * n) + 1);
    }

    private void connectToNeighbor(int p, int row, int col) {
        if (row < 1 || row > n) return;
        if (col < 1 || col > n) return;
        if (!isOpen(row, col)) return;

        unionFind.union(p, component(row, col));
        unionFind2.union(p, component(row, col));
    }

    private int component(int row, int column) {
        return ((row * n) - (n - column)) - 1;
    }
}