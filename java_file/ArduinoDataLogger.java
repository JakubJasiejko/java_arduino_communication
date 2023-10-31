import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ArduinoDataLogger implements SerialPortEventListener {
    private SerialPort serialPort;
    private static final String PORT_NAME = "COM3"; // Zmień na odpowiedni port szeregowy
    private static final int BAUD_RATE = 9600;
    private static PrintWriter csvWriter;

    public ArduinoDataLogger() {
        try {
            serialPort = (SerialPort) CommPortIdentifier.getPortIdentifier(PORT_NAME).open("ArduinoDataLogger", 2000);
            serialPort.setSerialPortParams(BAUD_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            csvWriter = new PrintWriter(new FileWriter("dane.csv"));
            csvWriter.println("Wartosc");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String line = serialPort.getInputStream().toString();
                System.out.println(line); // Wyświetl odbierane dane na konsoli
                csvWriter.println(line); // Zapisz dane do pliku CSV
                csvWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ArduinoDataLogger();
    }
}