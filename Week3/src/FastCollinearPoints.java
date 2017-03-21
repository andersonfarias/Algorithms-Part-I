import java.util.Arrays;

/**
 * Created by andersonfarias on 2017-03-13.
 */
public class FastCollinearPoints {

    private LineSegment[] lineSegments;

    private int numberOfSegments;

    public FastCollinearPoints(Point[] ps) {
        Point[] points = ps.clone();

        if (points == null) throw new NullPointerException();

        for (Point p : points) checkForNull(p);

        Arrays.sort(points);

        for (int i = 1, length = points.length; i < length; i++) {
            if (i + 1 >= points.length) break;

            checkForDuplicate(points[i], points[i + 1]);
        }

        Point q, r, s, maximal;
        double slopePQ;
        Point[] slopeOrdered;

        LineSegment[] temp = new LineSegment[points.length * points.length];

        for (int j = 0; j < points.length; j++) {
            Point p = points[j];

            slopeOrdered = new Point[points.length - j - 1];
            copy(points, slopeOrdered, j);

            Arrays.sort(slopeOrdered, p.slopeOrder());

            for (int i = 0, length = slopeOrdered.length; i < length - 2; i++) {
                q = slopeOrdered[i];
                r = slopeOrdered[i + 1];
                s = slopeOrdered[i + 2];

                slopePQ = p.slopeTo(q);

                if (slopePQ == p.slopeTo(r) && slopePQ == p.slopeTo(s)) {
                    i += 2;
                    maximal = s;

                    while (i + 1 < length && slopePQ == p.slopeTo(slopeOrdered[i + 1])) {
                        maximal = slopeOrdered[++i];
                    }

                    temp[numberOfSegments++] = new LineSegment(p, maximal);
                }

            }
        }

        lineSegments = new LineSegment[numberOfSegments];
        for (int i = 0, length = numberOfSegments; i < length; i++) {
            lineSegments[i] = temp[i];
        }
    }

    private void copy(Point[] points, Point[] slopeOrdered, int j) {
        for (int i = j + 1; i < points.length; i++) slopeOrdered[i - j - 1] = points[i];
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    private void checkForNull(Point q) {
        if (q == null) throw new NullPointerException();
    }

    private void checkForDuplicate(Point p, Point q) {
        if (p.compareTo(q) == 0) throw new IllegalArgumentException();
    }
}
