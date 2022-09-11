import java.util.HashMap;

public class PathContainer {
    //@ public instance model non_null Path[] pList;
    //@ public instance model non_null int[] pidList;

    private HashMap<Path, Integer> pathList;
    private HashMap<Integer, Path> pathIdList;
    private int idCounter;
    private HashMap<Integer, Integer> globalNodesCount;

    public PathContainer() {
        pathList = new HashMap<>();
        pathIdList = new HashMap<>();
        globalNodesCount = new HashMap<>();
        idCounter = 0;
    }

    //@ ensures \result == pList.length; //TODO
    public /*@pure@*/ int size() {
        return pathList.size();
    }

    /*@ requires path != null;
      @ assignable \nothing;
      @ ensures \result == (\exists int i; 0 <= i && i < pList.length;
      @                     pList[i].equals(path));
      @*/
    public boolean containsPath(Path path) {
        if (path == null) {
            System.err.println("path in containsPath(path) is null !");
            return false;
        }
        return (pathList.get(path) != null);
    }

    /*@ ensures \result == (\exists int i; 0 <= i && i < pidList.length; //TODO
      @                      pidList[i] == pathId);
      @*/
    public /*@pure@*/ boolean containsPathId(int pathId) {
        return (pathIdList.get(pathId) != null);
    }

    /*@ public normal_behavior
      @ requires containsPathId(pathId);
      @ assignable \nothing;  //TODO
      @ ensures (pidList.length == pList.length) && (\exists int i; 0 <= i && i < pList.length; pidList[i] == pathId && \result == pList[i]);
      @ also
      @ public exceptional_behavior // TODO
      @ requires !containsPathId(pathId);
      @ assignable \nothing;
      @ signals_only PathIdNotFoundException;
      @*/
    public Path getPathById(int pathId) throws PathIdNotFoundException {
        if (containsPathId(pathId)) {
            return pathIdList.get(pathId);
        }
        throw new PathIdNotFoundException(pathId);
    }

    /*@ public normal_behavior
      @ requires path != null && path.isValid() && containsPath(path);
      @ assignable \nothing;
      @ ensures (pidList.length == pList.length) && (\exists int i; 0 <= i && i < pList.length; pList[i].equals(path) && pidList[i] == \result);
      @ also
      @ public exceptional_behavior
      @ signals (PathNotFoundException e) path == null;
      @ signals (PathNotFoundException e) !path.isValid(); //TODO
      @ signals (PathNotFoundException e) !containsPath(path);
      @*/
    public int getPathId(Path path) throws PathNotFoundException {
        if (path != null && path.isValid() && containsPath(path)) {
            return pathList.get(path);
        } else {
            throw new PathNotFoundException(path); //TODO
        }
    }

    /*@ normal_behavior
      @ requires path != null && path.isValid();
      @ assignable pList, pidList;
      @ ensures (pidList.length == pList.length); //TODO
      @ ensures (\exists int i; 0 <= i && i < pList.length; pList[i] == path &&
      @           \result == pidList[i]);
      @ ensures !\old(containsPath(path)) ==>
      @          pList.length == (\old(pList.length) + 1) &&
      @          pidList.length == (\old(pidList.length) + 1);
      @ ensures (\forall int i; 0 <= i && i < \old(pList.length);
      @          containsPath(\old(pList).get(i)) && containsPathId(\old(pidList).get(i)));
      @ also
      @ normal_behavior
      @ requires path == null || path.isValid() == false;
      @ assignable \nothing;
      @ ensures \result == 0;
      @*/
    public int addPath(Path path) throws PathNotFoundException {
        if (path != null && path.isValid()) {
            if (containsPath(path)) {
                return getPathId(path);
            } else {
                idCounter++;
                pathList.put(path, idCounter);
                pathIdList.put(idCounter, path);
                // add to globalNodesCount !
                for (int node : path) {
                    Integer prev = globalNodesCount.get(node);
                    if (prev == null) {
                        globalNodesCount.put(node,1); //TODO
                    } else {
                        globalNodesCount.put(node,prev + 1); //TODO
                    }
                }
                return idCounter;
            }
        }
        return 0;
    }

    /*@ public normal_behavior
      @ requires path != null && path.isValid() && \old(containsPath(path));
      @ assignable pList, pidList;
      @ ensures containsPath(path) == false;
      @ ensures (pidList.length == pList.length);
      @ ensures (\exists int i; 0 <= i && i < \old(pList.length); \old(pList).get(i).equals(path) &&
      @           \result == \old(pidList).get(i));
      @ also
      @ public exceptional_behavior
      @ assignable \nothing;
      @ signals (PathNotFoundException e) path == null;
      @ signals (PathNotFoundException e) path.isValid()==false;
      @ signals (PathNotFoundException e) !containsPath(path);
      @*/
    public int removePath(Path path) throws PathNotFoundException {
        if (path != null && path.isValid() && containsPath(path)) {
            int idToRemove = pathList.remove(path);
            pathIdList.remove(idToRemove);
            // UPDATE globalNodeCount !
            for (int node : path) {
                Integer prev = globalNodesCount.get(node); //TODO
                if (prev == 1) {
                    globalNodesCount.remove(node);
                } else {
                    globalNodesCount.put(node, prev - 1);
                }
            }
            return idToRemove;
        } else {
            throw new PathNotFoundException(path);
        }
    }

    /*@ public normal_behavior
      @ requires \old(containsPathId(pathId));
      @ assignable pList, pidList;
      @ ensures (pList.length == pidList.length);
      @ ensures (\forall int i; 0 <= i && i < pidList.length; pidList[i] != pathId);
      @ ensures (\forall int i; 0 <= i && i < pList.length; !pList[i].equals(\old(getPathById(pathId))));
      @ also
      @ public exceptional_behavior
      @ assignable \nothing;
      @ signals (PathIdNotFoundException e) \old(!containsPathId(pathId)); //TODO
      @*/
    public void removePathById(int pathId) throws PathIdNotFoundException {
        if (containsPathId(pathId)) {
            Path path = pathIdList.remove(pathId);
            pathList.remove(path);
            for (int node : path) {
                Integer prev = globalNodesCount.get(node);
                if (prev == 1) {
                    globalNodesCount.remove(node);
                } else {
                    globalNodesCount.put(node, prev - 1);
                }
            }
        } else {
            throw new PathIdNotFoundException(pathId);
        }
    }

    /*@ ensures (\exists int[] arr; (\forall int i, j; 0 <= i && i < j && j < arr.length; arr[i] != arr[j]);
      @            (\forall int i; 0 <= i && i < arr.length; (\exists Path p; this.containsPath(p); p.containsNode(arr[i])))
      @            &&(\forall Path p; this.containsPath(p); (\forall int node; p.containsNode(node); (\exists int i; 0 <= i && i < arr.length; node == arr[i])))
      @            &&(\result == arr.length));
      @*/
    public int getDistinctNodeCount() {//在容器全局范围内查找不同的节点数
        return globalNodesCount.size(); //TODO
    }

    /*@ public normal_behavior
      @ requires containsPathId(pathId); //TODO
      @ assignable \nothing;
      @ ensures (pidList.length == pList.length);
      @ ensures (\exists int i; 0 <= i && i < pList.length; pidList[i] == pathId && \result == pList[i].loopPath()); // TODO
      @ also
      @ public exceptional_behavior
      @ requires !containsPathId(pathId)
      @ assignable \nothing
      @ signals_only PathIdNotFoundException; 
      @*/
    public Path loopPath(int pathId) throws PathIdNotFoundException { // 查看某个id对应的Path是否有loop
        if (containsPathId(pathId)) {
            return pathIdList.get(pathId).loopPath();
        }
        else {
            throw new PathIdNotFoundException(pathId);
        }
    }
}