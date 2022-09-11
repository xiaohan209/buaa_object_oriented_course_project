public class Adapter {
    public Vehicle vehicle;

    public Adapter(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getVehiclePrice() {
        return vehicle.getPrice();
    }

    public String getVehicleId() {
        return String.valueOf(vehicle.getId());
    }
}
