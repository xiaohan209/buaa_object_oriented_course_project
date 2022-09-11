package GCSimulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MyJVM myJVM = new MyJVM();
        System.out.println("Start JVM Garbage Collection Simulation.");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String operation = scanner.next();
            if (operation.equals("CreateObject")) {
                int count = scanner.nextInt();
                myJVM.createObject(count);
                System.out.println("Create " + count + " Objects.");
            } else if (operation.equals("SetUnreference")) {
                List<Integer> unreferenceList = new ArrayList<>();
                while (scanner.hasNextInt()) {
                    int id = scanner.nextInt();
                    unreferenceList.add(id);
                    System.out.println("Set id: " + id + " Unreferenced Object.");
                }
                myJVM.setUnreference(unreferenceList);
            } else if (operation.equals("MinorGC")) {
                myJVM.minorGC();
                System.out.println("Execute Minor Garbage Collection.");
            } else if (operation.equals("MajorGC")) {
                myJVM.majorGC();
                System.out.println("Execute Major Garbage Collection.");
            } else if (operation.equals("SnapShoot")) {
                myJVM.getSnapShoot();
            } else {
                System.err.println("Invalid operation.");
            }
        }
        scanner.close();
        System.out.println("End of JVM Garbage Collection Simulation.");
    }
}