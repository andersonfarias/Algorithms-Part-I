import java.util.Arrays;

/**
 * Created by andersonfarias on 2017-03-13.
 */
public class BruteCollinearPoints {

    private int numberOfSegments;

    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] ps) {
        Point[] points = ps.clone();

        if (points == null) throw new NullPointerException();

        Arrays.sort(points);

        Point p, q, r, s, maximal;
        double slopePQ, slopePR, slopePS;
        LineSegment[] temp = new LineSegment[points.length - 1];

        for (int i = 0, length = points.length; i < length; i++) {
            p = points[i];
            checkForNull(p);

            for (int j = i + 1; j < length; j++) {
                q = points[j];

                checkForNull(q);
                checkForDuplicate(p, q);

                slopePQ = p.slopeTo(q);

                for (int k = j + 1; k < length; k++) {
                    r = points[k];

                    checkForNull(r);
                    checkForDuplicate(r, q);

                    slopePR = p.slopeTo(r);

                    if (slopePR != slopePQ) continue;

                    for (int l = k + 1; l < length; l++) {
                        s = points[l];

                        checkForNull(s);
                        checkForDuplicate(s, r);

                        slopePS = p.slopeTo(s);

                        if (slopePQ == slopePS) {
                            maximal = s;

                            while (l + 1 < length && slopePQ == p.slopeTo(points[l + 1])) {
                                maximal = points[++l];
                            }

                            temp[numberOfSegments++] = new LineSegment(p, maximal);
                        }

                    }
                }
            }
        }

        lineSegments = new LineSegment[numberOfSegments];
        for (int i = 0, length = numberOfSegments; i < length; i++) {
            lineSegments[i] = temp[i];
        }
    }

    private void checkForDuplicate(Point p, Point q) {
        if (p.compareTo(q) == 0) throw new IllegalArgumentException();
    }

    private void checkForNull(Point q) {
        if (q == null) throw new NullPointerException();
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }
}
