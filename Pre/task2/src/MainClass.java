import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int num;
        ArrayList<ArrayList<Double>> volumn = new ArrayList();
        for (int i = 0;i <= n;i++) {
            ArrayList<Double> arr = new ArrayList<Double>();
            volumn.add(arr);
        }
        for (int i = 0;i < m;i++) {
            int opt = sc.nextInt();
            switch (opt) {
                case 1:
                    num = sc.nextInt();
                    Double max = .0;
                    for (int j = 0;j < volumn.get(num).size();j++) {
                        if (volumn.get(num).get(j) >= max) {
                            max = volumn.get(num).get(j);
                        }
                    }
                    System.out.println(max);
                    break;
                case 2:
                    num = sc.nextInt();
                    Double sum = .0;
                    for (int j = 0;j < volumn.get(num).size();j++) {
                        sum = sum + volumn.get(num).get(j);
                    }
                    System.out.println(sum);
                    break;
                default:
                    num = sc.nextInt();
                    Double length = sc.nextDouble();
                    Double width = sc.nextDouble();
                    Double height = sc.nextDouble();
                    volumn.get(num).add(length * width * height);
            }
        }
    }
}
