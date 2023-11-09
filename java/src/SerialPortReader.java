import com.fazecast.jSerialComm.*;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class SerialPortReader {
    public static void main(String[] args) {
        SerialPort serialPort = SerialPort.getCommPort("COM3");
        serialPort.setBaudRate(9600);
        serialPort.openPort();
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try {
            FileWriter csvWriter = new FileWriter("dane.csv");

            Scanner scanner = new Scanner(serialPort.getInputStream());

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
                csvWriter.append(line).append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        serialPort.closePort();
    }
}