package client.main.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChattingRoomUI extends JFrame {
    private Client client;

    private JTextField nameTextField;
    private JTextField ipTextField;
    private JTextField messageTextField;
    private JLabel nameLabel;
    private JLabel serverIpLabel;
    private JTextArea chattingTextArea;
    private JButton connectButton;
    private JButton sendButton;
    private JScrollPane jScrollPane;

    public ChattingRoomUI() {
        setTitle("Chatting Room");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 200, 500, 350);
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);


        JPanel settingPanel = new JPanel();
        settingPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        nameLabel = new JLabel("name: ");
        nameTextField = new JTextField();
        nameTextField.setColumns(10);
        serverIpLabel = new JLabel("server ip: ");
        ipTextField = new JTextField();
        ipTextField.setColumns(10);
        connectButton = new JButton("connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String serverip = ipTextField.getText();
                client = new Client(name, serverip, chattingTextArea);
                client.start();
            }
        });
        Dimension preferredSize=new Dimension(80, 20);
        connectButton.setPreferredSize(preferredSize);
        settingPanel.add(nameLabel);
        settingPanel.add(nameTextField);
        settingPanel.add(serverIpLabel);
        settingPanel.add(ipTextField);
        settingPanel.add(connectButton);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        chattingTextArea = new JTextArea();
        Dimension preferredSize2=new Dimension(420, 200);
        chattingTextArea.setPreferredSize(preferredSize2);
        chattingTextArea.setEditable(false);
        messagePanel.add(chattingTextArea);

        JPanel sendMessagePanel = new JPanel();
        sendMessagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        messageTextField = new JTextField();
        messageTextField.setColumns(33);
        sendButton = new JButton("send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chattingTextArea.append("me: " + messageTextField.getText() + "\n");
                client.send(messageTextField.getText());
                messageTextField.setText("");
            }
        });
        sendButton.setPreferredSize(preferredSize);
        sendMessagePanel.add(messageTextField);
        sendMessagePanel.add(sendButton);

        contentPanel.add(settingPanel);
        contentPanel.add(messagePanel);
        contentPanel.add(sendMessagePanel);
    }

    public static void main(String[] args) {
        ChattingRoomUI chattingRoomUI = new ChattingRoomUI();
        chattingRoomUI.setVisible(true);
    }
}
