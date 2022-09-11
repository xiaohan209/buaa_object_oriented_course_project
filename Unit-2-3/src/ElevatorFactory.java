import java.util.ArrayList;
import java.util.Arrays;

public class ElevatorFactory {
    public Elevator produceElevator(String elevatorId,String elevatorType,Buffer buffer) {
        final ArrayList<Integer> floorA = new ArrayList<Integer>(
                Arrays.asList(-3,-2,-1,1,15,16,17,18,19,20));
        final ArrayList<Integer> floorB = new ArrayList<Integer>(
                Arrays.asList(-2,-1,1,2,4,5,6,7,8,9,10,11,12,13,14,15));
        final ArrayList<Integer> floorC = new ArrayList<Integer>(
                Arrays.asList(1,3,5,7,9,11,13,15));
        int maxA = 6;
        int maxB = 8;
        int maxC = 7;
        int timeFloorA = 400;
        int timeFloorB = 500;
        int timeFloorC = 600;
        int timeOpen = 200;
        int timeClose = 200;
        Elevator newElevator = null;
        switch (elevatorType) {
            case "A":
                newElevator = new Elevator(elevatorId,floorA,maxA,
                        timeOpen,timeClose,timeFloorA,buffer);
                break;
            case "B":
                newElevator = new Elevator(elevatorId,floorB,maxB,
                        timeOpen,timeClose,timeFloorB,buffer);
                break;
            case "C":
                newElevator = new Elevator(elevatorId,floorC,maxC,
                        timeOpen,timeClose,timeFloorC,buffer);
                break;
            default:
                int a = 1;
        }
        return newElevator;
    }
}
