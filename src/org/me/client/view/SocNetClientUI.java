package org.me.client.view;

import org.me.server.SocNetProtocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class SocNetClientUI extends JFrame {
    //TODO : THIS IS NOT COMPLETE!!!!!
    private Socket socNetSocket;
    private PrintWriter outToServer;
    private BufferedReader inFromServer;


    private JFrame jFrame;
    private JPanel mainPanel;

    private JPanel upperPanel;
    private JPanel lowerPanel;

    private ArrayList<JComponent> phoneComponentsList;



    /**
     * The combo box containing the list of customers' names.
     */
//    private JComboBox<String> customerListComboBox;
    private JButton shareBtn;
    private JButton removeBtn;

    private JTextField serverMsg;


    public SocNetClientUI() {

        phoneComponentsList = new ArrayList<JComponent>();

        jFrame = new JFrame("My Social Network!");
        jFrame.setBounds(300, 200, 600, 600);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        lowerPanel = new JPanel();
        upperPanel = new JPanel();











        String hostName = SocNetProtocol.address;
        int portNumber = SocNetProtocol.portNo;

//
//        try {
//            socNetSocket = new Socket(hostName, portNumber);
//        } catch (IOException e) {
//            System.err.println("Unable to create the client socket!");
//        }
//        try {
//            outToServer = new PrintWriter(socNetSocket.getOutputStream(), true);
//        } catch (IOException e) {
//            System.err.println("Unable to create the output stream!");
//        }
//        try {
//            inFromServer = new BufferedReader(new InputStreamReader(socNetSocket.getInputStream()));
//        } catch (IOException e) {
//            System.err.println("Unable to create the input stream!");
//        }

    }

    public static void main(String[] args) {
        SocNetClientUI client = new SocNetClientUI();

        client.init();
        client.run();

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromUser;
        String fromServer;

//        try {
//            while ((fromServer = client.inFromServer.readLine())!= null) {
//                String[] res = fromServer.split("\t");
//                System.out.println("******************** server says : ");
//                for (String re : res) {
//                    System.out.println(re);
//                }
//                System.out.println("********************");
//
//                System.out.print(">>> ");
//                fromUser = stdIn.readLine();
//                if (fromUser != null) {
//                    System.out.println("----- Client To server : \"" + fromUser + "\" -----");
//                    client.outToServer.println(fromUser);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            if (client.inFromServer != null)
//                client.inFromServer.close();
//            if (client.outToServer != null)
//                client.outToServer.close();
//            if (client.socNetSocket != null)
//                client.socNetSocket.close();
//        } catch (Exception e) {
//            System.out.println("Unable to close the streams, very bad exception!");
//            e.printStackTrace();
//        }
    }


    public void init() {
        jFrame.add(mainPanel);

        mainPanel.setLayout(new GridBagLayout());
        upperPanel.setPreferredSize(new Dimension(300, 200));
        lowerPanel.setPreferredSize(new Dimension(500, 200));

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        // build the upper panel (containing ComboBox)
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        mainPanel.add(upperPanel, new GridBagConstraints(0, 0, 1, 1, 1, 0.1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));


        // build the lower panel (Containing Buttons)
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
//        lowerPanel.setBorder(new BorderUIResource.LineBorderUIResource(Color.GREEN));
        mainPanel.add(lowerPanel, gridBagConstraints);








        mainPanel.repaint();
        mainPanel.revalidate();
    }

    public void run() {
//        addAddressLabel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                JPanel p = newAddressGroup("", "", "", "", "");
//                addressPanel.add(p);
//                addressComponentsList.add(p);
//                addressPanel.revalidate();
//            }
//        });





//        refreshView("", null, null, null, "");
    }


    private JPanel newPhoneGroup(String type, String value) {
        final JPanel p = new JPanel(new GridLayout(0, 2));
        final JTextField typeField = new JTextField(type);
        final JTextField valueField = new JTextField(value);
        JLabel typeLabel = new JLabel("Type:   ");
        JLabel valueLabel = new JLabel("Value:  ");

        JLabel delPhoneLabel = new JLabel("- Delete Phone");

        typeLabel.setPreferredSize(new Dimension(70, 20));
        typeField.setPreferredSize(new Dimension(130, 20));
        valueLabel.setPreferredSize(new Dimension(70, 20));
        valueField.setPreferredSize(new Dimension(130, 20));
        p.add(typeLabel);
        p.add(typeField);
        p.add(valueLabel);
        p.add(valueField);
        p.add(new JLabel());
        p.add(delPhoneLabel);

        delPhoneLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idx = phoneComponentsList.indexOf(p);

//                if (phoneComponentsList.size() > 1) {
                phoneComponentsList.remove(idx);
//                phonePanel.remove(idx);
//                } else {
//                    typeField.setText("");
//                    valueField.setText("");
//                }
//                phonePanel.repaint();
//                phonePanel.revalidate();

            }
        });
        return p;
    }
}
