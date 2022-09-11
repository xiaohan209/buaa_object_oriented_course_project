import java.util.Objects;

public class NewMinPath implements Comparable<NewMinPath> {
    private int minPath;
    private int point;

    public NewMinPath(int minPath, int point) {
        this.minPath = minPath;
        this.point = point;
    }

    @Override
    public int compareTo(NewMinPath o) {
        return minPath - o.getMinPath();
    }

    public int getMinPath() {
        return minPath;
    }

    public int getPoint() {
        return point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)  {
            return true;
        }
        if (o == null || getClass() != o.getClass())  {
            return false;
        }
        NewMinPath thisO = (NewMinPath) o;
        return minPath == thisO.minPath && point == thisO.point;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minPath, point);
    }
}

