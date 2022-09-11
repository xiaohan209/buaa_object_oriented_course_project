import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        ArrayList<Vector> v1 = new ArrayList<Vector>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Double max = .0;
        Double min = Double.MAX_VALUE;
        for (int i = 0;i < n;i++) {
            Vector ini = new Vector(sc.nextDouble(),
                    sc.nextDouble(),sc.nextDouble());
            max = Double.max(max,ini.moChang());
            min = Double.min(min,ini.moChang());
            v1.add(ini);
        }
        int q = sc.nextInt();
        for (int i = 0;i < q;i++) {
            int opt = sc.nextInt();
            int vi;
            int vj;
            switch (opt) {
                case 1:
                    vi = sc.nextInt() - 1;
                    vj = sc.nextInt() - 1;
                    Vector plResult = v1.get(vi).plus(v1.get(vj));
                    v1.add(plResult);
                    max = Double.max(max,plResult.moChang());
                    min = Double.min(min,plResult.moChang());
                    break;
                case 2:
                    vi = sc.nextInt() - 1;
                    vj = sc.nextInt() - 1;
                    Vector miResult = v1.get(vi).minus(v1.get(vj));
                    v1.add(miResult);
                    max = Double.max(max,miResult.moChang());
                    min = Double.min(min,miResult.moChang());
                    break;
                case 3:
                    vi = sc.nextInt() - 1;
                    vj = sc.nextInt() - 1;
                    Double x1 = v1.get(vi).getX() * v1.get(vj).getX();
                    Double y1 = v1.get(vi).getY() * v1.get(vj).getY();
                    Double z1 = v1.get(vi).getZ() * v1.get(vj).getZ();
                    System.out.println(x1 + y1 + z1);
                    break;
                case 4:
                    vi = sc.nextInt() - 1;
                    vj = sc.nextInt() - 1;
                    Vector chResult = v1.get(vi).chaCheng(v1.get(vj));
                    v1.add(chResult);
                    max = Double.max(max,chResult.moChang());
                    min = Double.min(min,chResult.moChang());
                    break;
                case 5:
                    System.out.println(max);
                    break;
                default:
                    System.out.println(min);
            }
        }
    }
}
