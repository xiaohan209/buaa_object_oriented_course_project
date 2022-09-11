public class Consumer extends Thread {
    private Tray tray;

    public Consumer(Tray t) {
        this.tray = t;
    }

    public void run() {
        int value = 0;
        for (int i = 1; i <= 100; i++) {
            value = tray.get();
            System.out.println("Consumer get:" + value);
        }
    }
}

