import com.fazecast.jSerialComm.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class SerialPortReader {
    private SerialPort serialPort;
    private String portName;
    private String outputFile;
    private FileOutputStream outputFileStream;
    private int dataCount;

    public SerialPortReader(String portName, String outputFile) {
        this.portName = portName;
        this.outputFile = outputFile;
        this.dataCount = 0;
    }

    public boolean initialize() {
        serialPort = SerialPort.getCommPort(portName);
        serialPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);

        if (serialPort.openPort()) {
            try {
                File file = new File(outputFile);
                outputFileStream = new FileOutputStream(file);
            } catch (IOException e) {
                System.err.println("Nie można otworzyć pliku CSV.");
                serialPort.closePort();
                return false;
            }
            return true;
        } else {
            System.err.println("Nie można otworzyć portu szeregowego.");
            return false;
        }
    }

    public void readAndWriteData() {
        System.out.println("Odczytane dane z sensora: ");

        Scanner scanner = new Scanner(serialPort.getInputStream(), "UTF-8");

        while (dataCount < 50) {
            if (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                try {
                    int value = Integer.parseInt(data.trim()); // Konwersja na liczbę całkowitą
                    System.out.println("Odebrano: " + value);

                    // Zapisz dane jako liczby całkowite do pliku
                    try {
                        outputFileStream.write(Integer.toString(value).getBytes("UTF-8"));
                        outputFileStream.write("\n".getBytes("UTF-8")); // Dodaj znak nowej linii
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dataCount++;
                } catch (NumberFormatException e) {
                    // Obsłuż błędy w przypadku niepoprawnych danych
                    System.err.println("Błąd odczytu danych: " + e.getMessage());
                }
            }
        }

        System.out.println("Koniec odczytu");
        scanner.close();
    }

    public void close() {
        serialPort.closePort();
        try {
            outputFileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SerialPortReader reader = new SerialPortReader("COM10", "dane.csv");

        if (reader.initialize()) {
            reader.readAndWriteData();
            reader.close();
        }
    }
}
