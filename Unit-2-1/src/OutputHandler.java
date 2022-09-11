import com.oocourse.TimableOutput;

public class OutputHandler {
    public void printWithTime() {
        // please MUST initialize start timestamp at the beginning
        TimableOutput.initStartTimestamp();

        // print something
        TimableOutput.println(1.0 / 7);

        // sleep for a while, then print something again
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TimableOutput.println(
                String.format("result of 2 / 7 is %.10f", 2.0 / 7));

        // sleep for a while, then print something again
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TimableOutput.println(
                String.format("result of 3 / 7 is %.10f", 3.0 / 7));
    }
}
