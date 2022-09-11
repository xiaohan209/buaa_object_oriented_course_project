import java.util.Iterator;

class PathNotFoundException extends Exception {
    PathNotFoundException(final Path path) {
        System.err.println(path.toString() + " Not Found!");
    }
}