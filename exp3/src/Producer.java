public class Producer extends Thread {
    private Tray tray;

    public Producer(Tray t) {
        this.tray = t;
    }

    public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println("Producer put:" + i);
            tray.put(i);
            try {
                sleep((int) (Math.random() * 100));
            }
            catch (InterruptedException e) {
                ;
            }
        }
    }
}