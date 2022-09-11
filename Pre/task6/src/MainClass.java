import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {
    public static void realAdd(int num,CuboidBox box,
                               ArrayList<ArrayList<CuboidBox>> volumn) {
        int flag = 0;
        for (int i = 0;i < volumn.get(num).size();i++) {
            if (volumn.get(num).get(i).isEqual(box)) {
                flag = 1;
            }
        }
        if (flag == 0) {
            volumn.get(num).add(box);
        }
    }

    public static void addIt(Scanner sc,
                             ArrayList<ArrayList<CuboidBox>> volumn) {
        int num = sc.nextInt();
        String type = sc.next();
        switch (type) {
            case "1":
                Hexahedron sixBox = new Hexahedron(sc.nextDouble(),
                        sc.nextDouble(), sc.nextDouble(),sc.nextDouble(),
                        sc.nextDouble(),sc.nextDouble());
                realAdd(num,sixBox,volumn);
                break;
            case "1.1":
                CuboidBox myBox = new CuboidBox(sc.nextDouble(),
                        sc.nextDouble(),sc.nextDouble(),"1.1");
                realAdd(num,myBox,volumn);
                break;
            case "1.1.1":
                Double length = sc.nextDouble();
                Cube cube = new Cube(length);
                realAdd(num,cube,volumn);
                break;
            case "2":
                Double r1 = sc.nextDouble();
                Double r2 = sc.nextDouble();
                Double hei = sc.nextDouble();
                Tai tai = new Tai(r1,r2,hei);
                realAdd(num,tai,volumn);
                break;
            case "2.1":
                Double r3 = sc.nextDouble();
                Double hei1 = sc.nextDouble();
                Cyclinder cyc = new Cyclinder(r3,hei1);
                realAdd(num,cyc,volumn);
                break;
            case "2.2":
                Double r4 = sc.nextDouble();
                Double hei2 = sc.nextDouble();
                Cone cone = new Cone(r4,hei2);
                realAdd(num,cone,volumn);
                break;
            default:
                Double r5 = sc.nextDouble();
                Ball ball = new Ball(r5);
                realAdd(num,ball,volumn);
        }
    }

    public static void bingJi(Scanner sc,
                              ArrayList<ArrayList<CuboidBox>> column) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        ArrayList<CuboidBox> newOne = new ArrayList<CuboidBox>();
        for (int i = 0;i < column.get(n).size();i++) {
            newOne.add(column.get(n).get(i));
        }
        if (!column.get(m).isEmpty()) {
            for (int j = 0;j < column.get(m).size();j++) {
                boolean flag = true;
                for (int k = 0;k < column.get(n).size();k++) {
                    if (column.get(m).get(j).isEqual(column.get(n).get(k))) {
                        flag = false;
                    }
                }
                if (flag) {
                    newOne.add(column.get(m).get(j));
                }
            }
        }
        column.add(newOne);
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

    public static void cZ(Scanner sc,ArrayList<ArrayList<CuboidBox>> volumn) {
        int num;
        Double max;
        int opt = sc.nextInt();
        switch (opt) {
            case 1:
                num = sc.nextInt();
                if (volumn.get(num).isEmpty()) {
                    System.out.println("Sorry, the set is empty!");
                }
                else {
                    max = 0.0;
                    for (int j = 0;j < volumn.get(num).size();j++) {
                        if (volumn.get(num).get(j).printVolumn() > max) {
                            max = volumn.get(num).get(j).printVolumn();
                        }
                    }
                    System.out.println(max);
                }
                break;
            case 2:
                num = sc.nextInt();
                if (volumn.get(num).isEmpty()) {
                    System.out.println("Sorry, the set is empty!");
                }
                else {
                    max = .0;
                    CuboidBox mark = volumn.get(num).get(0);
                    for (int j = 0;j < volumn.get(num).size();j++) {
                        if (volumn.get(num).get(j).printVolumn() > max) {
                            mark = volumn.get(num).get(j);
                            max = mark.printVolumn();
                        }
                    }
                    typeOut(mark);
                }
                break;
            case 3:
                num = sc.nextInt();
                double sum = 0.0;
                if (!volumn.get(num).isEmpty()) {
                    for (int j = 0;j < volumn.get(num).size();j++) {
                        sum = sum + volumn.get(num).get(j).printVolumn();
                    }
                }
                System.out.println(sum);
                break;
            case 4:
                addIt(sc,volumn);
                break;
            default:
                bingJi(sc,volumn);
        }
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<CuboidBox>> volumn
                = new ArrayList<ArrayList<CuboidBox>>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        for (int i = 0;i <= n;i++) {
            ArrayList<CuboidBox> arr = new ArrayList<>();
            volumn.add(arr);
        }
        for (int i = 0;i < m;i++) {
            cZ(sc,volumn);
        }
    }
}

