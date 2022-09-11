import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {
    public static void addIt(Scanner sc,
                             ArrayList<ArrayList<CuboidBox>> volumn) {
        int num = sc.nextInt();
        String type = sc.next();
        switch (type) {
            case "1":
                Hexahedron sixBox = new Hexahedron(sc.nextDouble(),
                        sc.nextDouble(), sc.nextDouble(),sc.nextDouble(),
                        sc.nextDouble(),sc.nextDouble());
                volumn.get(num).add(sixBox);
                break;
            case "1.1":
                CuboidBox myBox = new CuboidBox(sc.nextDouble(),
                        sc.nextDouble(),sc.nextDouble(),"1.1");
                volumn.get(num).add(myBox);
                break;
            case "1.1.1":
                Double length = sc.nextDouble();
                Cube cube = new Cube(length);
                volumn.get(num).add(cube);
                break;
            default:
                ;
        }
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<CuboidBox>> volumn =
                new ArrayList<ArrayList<CuboidBox>>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int num;
        Double max;

        for (int i = 0;i <= n;i++) {
            ArrayList<CuboidBox> arr = new ArrayList<>();
            volumn.add(arr);
        }
        for (int i = 0;i < m;i++) {
            int opt = sc.nextInt();
            switch (opt) {
                case 1:
                    num = sc.nextInt();
                    max = .0;
                    for (int j = 0;j < volumn.get(num).size();j++) {
                        if (volumn.get(num).get(j).printVolumn() > max) {
                            max = volumn.get(num).get(j).printVolumn();
                        }
                    }
                    System.out.println(max);
                    break;
                case 2:
                    num = sc.nextInt();
                    max = .0;
                    CuboidBox type = volumn.get(num).get(0);
                    for (int j = 0;j < volumn.get(num).size();j++) {
                        if (volumn.get(num).get(j).printVolumn() > max) {
                            type = volumn.get(num).get(j);
                            max = type.printVolumn();
                        }
                    }
                    System.out.println(type.getType());
                    break;
                case 3:
                    num = sc.nextInt();
                    double sum = 0.0;
                    for (int j = 0;j < volumn.get(num).size();j++) {
                        sum = sum + volumn.get(num).get(j).printVolumn();
                    }
                    System.out.println(sum);
                    break;
                default:
                    addIt(sc,volumn);
            }
        }
    }
}

