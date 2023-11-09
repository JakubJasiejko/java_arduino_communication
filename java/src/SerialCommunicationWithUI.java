import javax.swing.*;
import java.awt.*;

public class SerialCommunicationWithUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowUI());
    }

    private static void createAndShowUI() {
        JFrame frame = new JFrame("Arduino Data Reader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JPanel buttonPanel = new JPanel();

        SerialPortReader.initialize(textArea);

        if (SerialPortReader.isInitialized() && SerialPortReader.isOpen()) {
            JButton endButton = new JButton("End");
            endButton.addActionListener(e -> SwingUtilities.invokeLater(() -> SerialPortReader.endDataReading()));
            buttonPanel.add(endButton);
        } else {
            JLabel errorLabel = new JLabel("init error SerialReader");
            buttonPanel.add(errorLabel);
        }

        frame.getContentPane().add(BorderLayout.CENTER, textArea);
        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);

        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
