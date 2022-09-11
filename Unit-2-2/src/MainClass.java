import com.oocourse.TimableOutput;

import java.io.IOException;

public class MainClass {

    public static void main(String[] args) throws IOException {
        Buffer buffer = new Buffer();
        InputHandler input = new InputHandler(buffer);
        TimableOutput.initStartTimestamp();
        Thread inputRequest = new Thread(input);
        inputRequest.start();
    }
}
