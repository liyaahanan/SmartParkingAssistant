import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import com.fazecast.jSerialComm.*;

public class Main {

    private static JLabel distanceLabel;
    private static JLabel statusLabel;
    private static JPanel indicator;
    private static JComboBox<String> portSelector;
    private static JButton connectButton;
    private static JButton disconnectButton;

    private static SerialPort arduinoPort;
    private static StringBuilder receivedData = new StringBuilder();

    public static void main(String[] args) {

        SwingUtilities.invokeLater(Main::createGUI);

    }

    // CREATE APPLICATION WINDOW
    private static void createGUI() {

        JFrame frame = new JFrame("Smart Parking Assistant");

        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(7, 1, 10, 10));
        panel.setBackground(Color.WHITE);

        // TITLE
        JLabel title = new JLabel(
                "SMART PARKING ASSISTANT",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));

        // DISTANCE
        distanceLabel = new JLabel(
                "Distance: -- cm",
                SwingConstants.CENTER
        );

        distanceLabel.setFont(new Font("Arial", Font.BOLD, 22));

        // STATUS
        statusLabel = new JLabel(
                "Status: DISCONNECTED",
                SwingConstants.CENTER
        );

        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // COLOUR INDICATOR
        indicator = new JPanel();

        indicator.setBackground(Color.GRAY);

        // COM PORT SELECTION
        portSelector = new JComboBox<>();

        refreshPorts();

        // CONNECT BUTTON
        connectButton = new JButton("Connect to Arduino");

        connectButton.addActionListener(e -> connectArduino());

        // DISCONNECT BUTTON
        disconnectButton = new JButton("Disconnect");

        disconnectButton.setEnabled(false);

        disconnectButton.addActionListener(e -> disconnectArduino());

        // REFRESH PORTS BUTTON
        JButton refreshButton = new JButton("Refresh Ports");

        refreshButton.addActionListener(e -> refreshPorts());

        // ADD COMPONENTS
        panel.add(title);
        panel.add(distanceLabel);
        panel.add(statusLabel);
        panel.add(indicator);
        panel.add(portSelector);
        panel.add(connectButton);
        panel.add(disconnectButton);
        panel.add(refreshButton);

        frame.add(panel);

        frame.setVisible(true);

    }

    // FIND AVAILABLE COM PORTS
    private static void refreshPorts() {

        portSelector.removeAllItems();

        SerialPort[] ports = SerialPort.getCommPorts();

        for (SerialPort port : ports) {

            portSelector.addItem(
                    port.getSystemPortName() + " - "
                            + port.getDescriptivePortName()
            );

        }

        if (ports.length == 0) {

            statusLabelSafe("No Arduino port found");

        }

    }

    // CONNECT TO ARDUINO
    private static void connectArduino() {

        int selectedIndex = portSelector.getSelectedIndex();

        if (selectedIndex < 0) {

            JOptionPane.showMessageDialog(
                    null,
                    "Please select an Arduino COM port."
            );

            return;

        }

        SerialPort[] ports = SerialPort.getCommPorts();

        if (selectedIndex >= ports.length) {
            return;
        }

        arduinoPort = ports[selectedIndex];

        arduinoPort.setComPortParameters(
                9600,
                8,
                SerialPort.ONE_STOP_BIT,
                SerialPort.NO_PARITY
        );

        arduinoPort.setComPortTimeouts(
                SerialPort.TIMEOUT_NONBLOCKING,
                0,
                0
        );

        if (arduinoPort.openPort()) {

            statusLabel.setText("Status: CONNECTED");
            indicator.setBackground(Color.GRAY);

            connectButton.setEnabled(false);
            disconnectButton.setEnabled(true);
            portSelector.setEnabled(false);

            receivedData.setLength(0);

            arduinoPort.addDataListener(
                    new SerialPortDataListener() {

                        @Override
                        public int getListeningEvents() {

                            return SerialPort.LISTENING_EVENT_DATA_RECEIVED;

                        }

                        @Override
                        public void serialEvent(SerialPortEvent event) {

                            byte[] data = event.getReceivedData();

                            String text = new String(
                                    data,
                                    StandardCharsets.UTF_8
                            );

                            processIncomingData(text);

                        }

                    }
            );

        } else {

            JOptionPane.showMessageDialog(
                    null,
                    "Could not connect. Check the COM port."
            );

        }

    }

    // RECEIVE DATA FROM ARDUINO
    private static void processIncomingData(String data) {

        synchronized (receivedData) {

            receivedData.append(data);

            int newlineIndex;

            while ((newlineIndex = receivedData.indexOf("\n")) >= 0) {

                String line = receivedData
                        .substring(0, newlineIndex)
                        .trim();

                receivedData.delete(0, newlineIndex + 1);

                processLine(line);

            }

        }

    }

    // PROCESS EACH SERIAL MESSAGE
    private static void processLine(String line) {

        SwingUtilities.invokeLater(() -> {

            if (line.startsWith("DIST:")) {

                String distance = line.substring(5).trim();

                distanceLabel.setText(
                        "Distance: " + distance + " cm"
                );

            }

            else if (line.startsWith("STATUS:")) {

                String status = line.substring(7).trim();

                updateStatus(status);

            }

        });

    }

    // UPDATE STATUS AND COLOUR
    private static void updateStatus(String status) {

        statusLabel.setText("Status: " + status);

        switch (status) {

            case "SAFE":

                indicator.setBackground(Color.GREEN);

                break;

            case "WARNING":

                indicator.setBackground(Color.YELLOW);

                break;

            case "STOP":

                indicator.setBackground(Color.RED);

                break;

            case "NO_ECHO":

                indicator.setBackground(Color.GRAY);

                distanceLabel.setText("Distance: No reading");

                break;

            default:

                indicator.setBackground(Color.GRAY);

                break;

        }

    }

    // DISCONNECT ARDUINO
    private static void disconnectArduino() {

        if (arduinoPort != null && arduinoPort.isOpen()) {

            arduinoPort.removeDataListener();

            arduinoPort.closePort();

        }

        statusLabel.setText("Status: DISCONNECTED");

        distanceLabel.setText("Distance: -- cm");

        indicator.setBackground(Color.GRAY);

        connectButton.setEnabled(true);
        disconnectButton.setEnabled(false);
        portSelector.setEnabled(true);

    }

    private static void statusLabelSafe(String message) {

        if (statusLabel != null) {
            statusLabel.setText("Status: " + message);
        }

    }

}
