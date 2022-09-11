public class Tester {
    public String testVehiclePrice(double price) {
        return String.valueOf(price);
    }

    public void testVehicleId(String Id) {
        System.out.println(Id);
    }

    public int testVehicleRun(Vehicle vec) {
        vec.Run();
        return 0;
    }

    public void runTester() {
        testVehicleId();
        testVehiclePrice();
        testVehicleRun();
    }
}
