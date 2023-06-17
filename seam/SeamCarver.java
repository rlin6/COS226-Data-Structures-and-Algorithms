/* *****************************************************************************
 *  Name:    Karen Li
 *  NetID:   karenli
 *  Precept: P01
 *
 *  Partner Name:    Ricky Lin
 *  Partner NetID:   rickyl
 *  Partner Precept: P09
 *
 *  Description:  Data type that resizes a W-by-H image using the seam-carving
 *  technique.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
    private Picture pic; // stores picture to be resized

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("picture is null");
        }
        pic = new Picture(picture);
    }


    // current picture
    public Picture picture() {
        return new Picture(pic);
    }

    // width of current picture
    public int width() {
        return pic.width();
    }

    // height of current picture
    public int height() {
        return pic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        checkPixel(x, y);

        /*
        Color left = pic.get(Math.floorMod(x - 1, width()), y);
        Color right = pic.get(Math.floorMod(x + 1, width()), y);
        Color up = pic.get(x, Math.floorMod(y - 1, height()));
        Color down = pic.get(x, Math.floorMod(y + 1, height()));

         */

        int left = pic.getRGB(Math.floorMod(x - 1, width()), y);
        int right = pic.getRGB(Math.floorMod(x + 1, width()), y);
        int up = pic.getRGB(x, Math.floorMod(y - 1, height()));
        int down = pic.getRGB(x, Math.floorMod(y + 1, height()));

        double xGradient = gradient(left, right);
        double yGradient = gradient(up, down);
        return Math.sqrt(xGradient + yGradient);
    }

    // return the square of the gradient between pixels a and b
    private double gradient(int a, int b) {
        int red = extractRed(a) - extractRed(b);
        int green = extractGreen(a) - extractGreen(b);
        int blue = extractBlue(a) - extractBlue(b);
        return red * red + green * green + blue * blue;
    }

    // extract red component
    private int extractRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    // extract green component
    private int extractGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    // extract blue component
    private int extractBlue(int rgb) {
        return (rgb) & 0xFF;
    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] seam = findVerticalSeam();
        transpose();
        return seam;
    }


    // We referenced DijkstraSP.java
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[height()];
        double[] distTo = new double[height() * width()];
        int[] edgeTo = new int[height() * width()];
        IndexMinPQ<Double> pq = new IndexMinPQ<>(width() * height());

        // read energy of all pixels into energy matrix
        // initialize all distances as infinity in distTo
        double[][] energy = new double[height()][width()];
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                energy[row][col] = energy(col, row);
                if (row == 0) {
                    distTo[index(row, col)] = energy[row][col];
                    pq.insert(index(row, col), energy[row][col]);
                }
                else {
                    distTo[index(row, col)] = Double.POSITIVE_INFINITY;
                }
            }
        }

        int min = 0;
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            if (v / width() == height() - 1) {
                min = v % width();
                break;
            }
            relax(v / width(), v % width(), energy, distTo, edgeTo, pq);

        }


        int counter = height() - 1;
        seam[counter] = min;
        counter--;
        while (counter >= 0) {
            seam[counter] = edgeTo[index(counter + 1, min)];
            min = seam[counter];
            counter--;
        }

        return seam;
    }

    // convert 2D coordinates to 1D
    private int index(int row, int col) {
        return row * width() + col;
    }

    // relax the given vertex
    private void relax(int row, int col, double[][] energy, double[] distTo,
                       int[] edgeTo, IndexMinPQ<Double> pq) {
        int[] increments = { -1, 0, 1 };
        for (int inc : increments) {
            if (row < height() && col + inc >= 0 && col + inc < width()) {
                int neighbor = index(row + 1, col + inc);

                if (distTo[neighbor] > distTo[index(row, col)] +
                        energy[row + 1][col + inc]) {

                    distTo[neighbor] = distTo[index(row, col)] +
                            energy[row + 1][col + inc];

                    edgeTo[neighbor] = col;
                    if (pq.contains(neighbor)) {
                        pq.decreaseKey(neighbor, distTo[neighbor]);
                    }
                    else {
                        pq.insert(neighbor, distTo[neighbor]);
                    }
                }
            }
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        transpose();
        removeVerticalSeam(seam);
        transpose();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("seam is null");
        }
        if (pic.width() == 1) {
            throw new IllegalArgumentException("width is 1");
        }
        if (seam.length != height()) {
            throw new IllegalArgumentException("seam array not valid");
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= width()) {
                throw new IllegalArgumentException("seam value not valid");
            }
            if (i < seam.length - 1 && Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException("seam value not adjacent");
            }

        }
        Picture newPic = new Picture(width() - 1, height());

        for (int row = 0; row < height(); row++) {
            int col = 0;
            while (col < width() - 1) {
                if (col < seam[row]) {
                    newPic.setRGB(col, row, pic.getRGB(col, row));
                }
                else {
                    newPic.setRGB(col, row, pic.getRGB(col + 1, row));
                }
                col++;
            }

        }
        pic = newPic;

    }

    // transpose the picture
    private void transpose() {
        Picture transposed = new Picture(height(), width());
        for (int row = 0; row < width(); row++) {
            for (int col = 0; col < height(); col++) {
                transposed.setRGB(col, row, pic.getRGB(row, col));
            }
        }
        pic = transposed;
    }


    // check is pixel is in range of image
    private void checkPixel(int x, int y) {
        if (x < 0 || x > width() - 1) {
            throw new IllegalArgumentException("x is out of bounds");
        }
        if (y < 0 || y > height() - 1) {
            throw new IllegalArgumentException("y is out of bounds");
        }
    }


    public static void main(String[] args) {

        Picture pic = new Picture(args[0]);
        SeamCarver seamcarver = new SeamCarver(pic);

        StdOut.println("height: \t" + seamcarver.height());
        StdOut.println("width: \t\t" + seamcarver.width());


        int[] vertical = seamcarver.findVerticalSeam();
        System.out.print("vertical seam: \t\t");
        for (int seam : vertical) {
            StdOut.print(seam + " \t");
        }
        System.out.println();
        double verticalEnergy = 0;
        for (int row = 0; row < seamcarver.height(); row++) {
            verticalEnergy += seamcarver.energy(vertical[row], row);
        }
        StdOut.println("energy of vertical seam: " + verticalEnergy);
        seamcarver.removeVerticalSeam(vertical);

        int[] horizontal = seamcarver.findHorizontalSeam();
        System.out.print("horizontal seam: \t\t");
        for (int seam : horizontal) {
            StdOut.print(seam + " \t");
        }
        System.out.println();
        double horizontalEnergy = 0;
        for (int col = 0; col < seamcarver.width(); col++) {
            horizontalEnergy += seamcarver.energy(col, horizontal[col]);
        }
        StdOut.println("energy of horizontal seam: " + horizontalEnergy);
        seamcarver.removeHorizontalSeam(horizontal);


        seamcarver.picture().show();


    }
}
