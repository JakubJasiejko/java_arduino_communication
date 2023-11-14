import com.fazecast.jSerialComm.*;
import javax.swing.SwingUtilities;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JTextArea;

public class SerialPortReader {
    private static SerialPort serialPort;
    private static FileWriter csvWriter;
    private static FileWriter htmlWriter;

    private static JTextArea textArea;

    public static void initialize(JTextArea area) {
        textArea = area;

        serialPort = SerialPort.getCommPort("COM3");
        serialPort.setBaudRate(9600);
        serialPort.openPort();
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        try {
            csvWriter = new FileWriter("data.csv");
            htmlWriter = new FileWriter("data.html");
            initializeHtmlFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> readData()).start();
    }

    private static void initializeHtmlFile() throws IOException {
        htmlWriter.write("<html><head><title>Arduino Data</title></head><body><pre>\n");
        htmlWriter.flush();
    }

    private static void readData() {
        Scanner scanner = new Scanner(serialPort.getInputStream());

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);

            SwingUtilities.invokeLater(() -> {
                textArea.append(line + "\n");
            });

            try {
                csvWriter.append(line).append("\n");
                csvWriter.flush();

                htmlWriter.append(line).append("<br>\n");
                htmlWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void endDataReading() {
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.closePort();
        }
        try {
            if (csvWriter != null) {
                csvWriter.close();
            }
            if (htmlWriter != null) {
                htmlWriter.write("</pre></body></html>");
                htmlWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static boolean isInitialized() {
        return serialPort != null && csvWriter != null && htmlWriter != null;
    }

    public static boolean isOpen() {
        return serialPort != null && serialPort.isOpen();
    }
}
