class PathIdNotFoundException extends Exception {
    PathIdNotFoundException(final int pathId) {
        System.err.println("PathId : " + pathId + " Not Found!");
    }
}