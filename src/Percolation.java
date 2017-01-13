/**
 * Created by shunheileung on 2017-01-09.
 * Problem: http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 * Motivation: find p* in Percolation model.
 */

/**
 * Import textbook libraries from Pinceton University
 * http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Import Arrays
 */
import java.util.Arrays;

public class Percolation {
    // n-by-n grid, the n value
    private int n;

    // grid sites
    private WeightedQuickUnionUF sites;

    // maker array to mark open sites
    private boolean[] openedSites;

    private final int MAXIMUM_NEIGHBOURS = 4;

    // virtual top node identifier
    private int virtualTopID;

    // virtual bottom node identifier
    private int virtualBtmID;

    // number of open sites
    private int numOpenSites;

    // identifier for the first site of a grid
    private final int TOP_LEFT_ID = 0;

    // grid boundary
    private int siteBoundary;

    // create n-by-n grid of sites, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("n must be greater than 0");
        }

        // initialize the system with n-by-n grid of sites, plus 2 virtual nodes: top virtual node, bottom virtual node
        // top virtual node is in position n^2
        // bottom virtual node is in position n^2 + 1
        this.sites = new WeightedQuickUnionUF(n * n + 2);

        // initalize n^n items
        this.openedSites = new boolean[n * n];

        // set all opendSites items to false
        Arrays.fill(this.openedSites, false);

        this.n = n;
        this.virtualTopID = n * n;
        this.virtualBtmID = n * n + 1;
        this.numOpenSites = 0;
        this.siteBoundary = n * n - 1;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        // get the 1D coordinates
        int i = this.xyTo1D(row, col);

        // avoid repeatedly open a site
        if (!this.openedSites[i]) {
            // mark site as open
            this.openedSites[i] = true;

            // connect neighbouring sites
            // first check if the neighbouring sites are open

            // top and bottom sites are offset by n
            // left and right sites are offset by 1

            // create an array to store the position of neighbours
            // top, bottom, left, right
            int[] posNeighbours = {i - this.n, i + this.n, i - 1, i + 1};

            // for each neighbouring sites, connect the target site to it if it's open
            for (int j = 0; j < this.MAXIMUM_NEIGHBOURS; j++) {
                // check if they are within bound and if open
                // if so, union i with posNeighbours[j]
                if (this.isInBoundary(posNeighbours[j]) && this.openedSites[posNeighbours[j]]) {
                    this.sites.union(i, posNeighbours[j]);
                }
            }

            // if the site to open is in the top row, connect it to the virtual top node
            if (i < this.n) {
                sites.union(i, this.virtualTopID);
            }

            // if the site to open is in the bottom row, connect it to the virtual bottom node
            if (i >= ((this.n * this.n) - this.n)) {
                sites.union(i, this.virtualBtmID);
            }

            // update open sites counter
            this.numOpenSites += 1;
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        // get the 1D coordinates
        int i = this.xyTo1D(row, col);

        return this.openedSites[i];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        // get the 1D coordinates
        int i = this.xyTo1D(row, col);

        // check if the site in question is connected to the virtual top node
        return this.sites.connected(i, this.virtualTopID);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return this.numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {

        // check if virtual bottom node connected to virtual top node
        return this.sites.connected(this.virtualBtmID, this.virtualTopID);

    }

    /*
     * Turn 2D coordinates to 1D coordinates
     * @return int
     */
    private int xyTo1D(int row, int col) {
        if ((row <= 0 || row > this.n) || (col <= 0 || col > this.n)) {
            throw new java.lang.IndexOutOfBoundsException("row and col must be within the range 1 - " + this.n + ", " +
                    "inclusively. You entererd row: " + row + ", col: " + col);
        }

        // since by convention the row and column indices are integers between 1 and n
        // we need to offset row and col by 1
        // and the formula to caclulating indices in 1D is as followed: row * n + col
        return this.n * (row -1) + (col -1);
    }

    /*
     * Check if a site is within the boundary of the grid system
     * Expect argument to be 1D coordinates
     */
    private boolean isInBoundary(int coordinates) {
        return coordinates >= this.TOP_LEFT_ID && coordinates <= this.siteBoundary;
    }

    public static void main(String[] args) {
        try {
            Percolation system = new Percolation(4);
            System.out.println("open site Row 2 Col 2");
            system.open(2, 2);
            System.out.println("Opened Sites: " + system.numberOfOpenSites());

            if(system.isFull(2, 2)) {
                System.out.println("(error) Row 2 Col 2 is full site");
            } else {
                System.out.println("(expected) Row 2 Col 2 is not full site");
            }

            System.out.println("open site Row 1 Col 2");
            system.open(1, 2);
            System.out.println("Opened Sites: " + system.numberOfOpenSites());

            if(system.isFull(2, 2)) {
                System.out.println("(expected) Row 2 Col 2 is full site");
            } else {
                System.out.println("(error) Row 2 Col 2 is not full site");
            }

            System.out.println("open site Row 4 Col 3");
            system.open(4, 3);
            System.out.println("Opened Sites: " + system.numberOfOpenSites());

            if(system.isFull(4, 3)) {
                System.out.println("(error) Row 4 Col 3 is full site");
            } else {
                System.out.println("(expected) Row 4 Col 3 is not full site");
            }

            System.out.println("open site Row 3 Col 2");
            system.open(3, 2);
            System.out.println("Opened Sites: " + system.numberOfOpenSites());

            if(system.isFull(3, 2)) {
                System.out.println("(expected) Row 3 Col 2 is full site");
            } else {
                System.out.println("(error) Row 3 Col 2 is not full site");
            }

            System.out.println("open site Row 4 Col 2");
            system.open(4, 2);
            System.out.println("Opened Sites: " + system.numberOfOpenSites());

            if(system.isFull(4, 2)) {
                System.out.println("(expected) Row 4 Col 2 is full site");
            } else {
                System.out.println("(error) Row 4 Col 2 is not full site");
            }

            System.out.println("does system percolate?");
            if(system.percolates()) {
                System.out.println("(expected) Yes");
            } else {
                System.out.println("(error) No");
            }

        } catch(IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch(IndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
