import com.oocourse.elevator3.PersonRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Cutter {
    private HashMap<Integer, ArrayList<Integer>> map;
    private ArrayList<Integer> floorA;
    private ArrayList<Integer> floorB;
    private ArrayList<Integer> floorC;
    private ArrayList<Integer> floorAB;
    private ArrayList<Integer> floorAC;
    private ArrayList<Integer> floorBC;
    private ArrayList<Integer> floorAll;

    public Cutter() {
        map = new HashMap<Integer, ArrayList<Integer>>();
        floorA = new ArrayList<Integer>(Arrays.asList(-3,-2,-1,1,15,16,17,18,19,20));
        floorB = new ArrayList<Integer>(Arrays.asList(-2,-1,1,2,4,5,6,7,8,9,10,11,12,13,14,15));
        floorC = new ArrayList<Integer>(Arrays.asList(1,3,5,7,9,11,13,15));
        floorAB = new ArrayList<Integer>(
                Arrays.asList(-3,-2,-1,1,2,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));
        floorAC = new ArrayList<Integer>(
                Arrays.asList(-3,-2,-1,1,3,5,7,9,11,13,15,16,17,18,19,20));
        floorBC = new ArrayList<Integer>(
                Arrays.asList(-2,-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15));
        floorAll = new ArrayList<Integer>(
                Arrays.asList(-3,-2,-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));
        map.put(-3,floorA);
        map.put(-2,floorAB);
        map.put(-1,floorAB);
        map.put(1,floorAll);
        map.put(2,floorB);
        map.put(3,floorC);
        map.put(4,floorB);
        map.put(5,floorBC);
        map.put(6,floorB);
        map.put(7,floorBC);
        map.put(8,floorB);
        map.put(9,floorBC);
        map.put(10,floorB);
        map.put(11,floorBC);
        map.put(12,floorB);
        map.put(13,floorBC);
        map.put(14,floorB);
        map.put(15,floorAll);
        map.put(16,floorA);
        map.put(17,floorA);
        map.put(18,floorA);
        map.put(19,floorA);
        map.put(20,floorA);
    }

    public ArrayList<PartRequest> cut(PersonRequest request) {
        ArrayList<Integer> reach = map.get(request.getToFloor());
        ArrayList<PartRequest> reqList = new ArrayList<PartRequest>();
        if (reach.contains(request.getFromFloor())) {
            PartRequest thisRequest = new PartRequest(request,false,0);
            reqList.add(thisRequest);
            return reqList;
        }
        int minFloor = 100;
        int targetFloor = distance(request.getToFloor());
        int fromFloor = distance(request.getFromFloor());
        for (int i = 0; i < reach.size(); i++) {
            Integer stepFloor = reach.get(i);
            if (map.get(stepFloor).contains(request.getFromFloor())) {
                stepFloor = distance(stepFloor);
                int result = (Math.abs(targetFloor - stepFloor) + Math.abs(stepFloor - fromFloor)) -
                        (Math.abs(targetFloor - minFloor) + Math.abs(minFloor - fromFloor));
                if (result < 0) {
                    minFloor = stepFloor;
                }
                else if (result == 0) {
                    if (Math.abs(stepFloor - targetFloor) < Math.abs(minFloor - targetFloor)) {
                        minFloor = stepFloor;
                    }
                }
            }
        }
        if (minFloor <= 0) {
            minFloor--;
        }
        PersonRequest first = new PersonRequest(
                request.getFromFloor(),minFloor,request.getPersonId());
        PersonRequest second = new PersonRequest(
                minFloor,request.getToFloor(),request.getPersonId());
        PartRequest firstStep = new PartRequest(first,true,0);
        PartRequest secondStep = new PartRequest(second,false,1);
        reqList.add(firstStep);
        reqList.add(secondStep);
        return reqList;
    }

    public int distance(int floor) {
        if (floor < 0) {
            return floor + 1;
        }
        return floor;
    }
}
