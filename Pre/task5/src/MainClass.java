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
            case "2":
                Double r1 = sc.nextDouble();
                Double r2 = sc.nextDouble();
                Double hei = sc.nextDouble();
                Tai tai = new Tai(r1,r2,hei);
                volumn.get(num).add(tai);
                break;
            case "2.1":
                Double r3 = sc.nextDouble();
                Double hei1 = sc.nextDouble();
                Cyclinder cyc = new Cyclinder(r3,hei1);
                volumn.get(num).add(cyc);
                break;
            case "2.2":
                Double r4 = sc.nextDouble();
                Double hei2 = sc.nextDouble();
                Cone cone = new Cone(r4,hei2);
                volumn.get(num).add(cone);
                break;
            default:
                Double r5 = sc.nextDouble();
                Ball ball = new Ball(r5);
                volumn.get(num).add(ball);
        }
    }

    public static void typeOut(CuboidBox mark) {
        System.out.print(mark.getType() + " ");
        switch (mark.getType()) {
            case "1":
                System.out.println(((Hexahedron)mark).getLength() + " "
                        + ((Hexahedron)mark).getBx() + " "
                        + ((Hexahedron)mark).getWidth() + " "
                        + ((Hexahedron)mark).getCx() + " "
                        + ((Hexahedron)mark).getCy() + " "
                        + ((Hexahedron)mark).getHeight());
                break;
            case "1.1":
                System.out.println(mark.getLength() + " " +
                        mark.getWidth() + " " + mark.getHeight());
                break;
            case "1.1.1":
                System.out.println(mark.getLength());
                break;
            case "2":
                System.out.println(((Tai)mark).getR1() + " " +
                        ((Tai)mark).getR2() + " " + ((Tai)mark).getHei());
                break;
            case "2.1":
                System.out.println(((Cyclinder)mark).getR1()
                        + " " + ((Cyclinder)mark).getHei());
                break;
            case "2.2":
                System.out.println(((Cone)mark).getR1() +
                        " " + ((Cone)mark).getHei());
                break;
            default:
                System.out.println(((Ball)mark).getR1());

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
                    CuboidBox mark = volumn.get(num).get(0);
                    for (int j = 0;j < volumn.get(num).size();j++) {
                        if (volumn.get(num).get(j).printVolumn() > max) {
                            mark = volumn.get(num).get(j);
                            max = mark.printVolumn();
                        }
                    }
                    typeOut(mark);
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

