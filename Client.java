package ChattingApplication;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class Client implements ActionListener
{
    JPanel top = new JPanel();
    JPanel bottom = new JPanel();
    static JPanel content = new JPanel();
    JLabel name = new JLabel("User 2");
    JLabel status = new JLabel("Active Now");
    JTextField text = new JTextField();
    JButton send = new JButton("Send");
    static Box verticalBox = Box.createVerticalBox();
    static JFrame Jf = new JFrame();
    static DataOutputStream out;
    static DataInputStream in;

    Client()
    {
        // Setting up the top panel
        top.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        top.setBackground(new Color(7, 90, 84));

        name.setFont(new Font("SANS SERIF", Font.PLAIN, 18));
        name.setForeground(Color.WHITE);
        status.setFont(new Font("SANS SERIF", Font.PLAIN, 10));
        status.setForeground(Color.WHITE);

        top.add(name);
        top.add(status);

        // Setting up the bottom panel
        send.setBackground(new Color(7, 90, 84));
        send.setFont(new Font("SANS SERIF", Font.PLAIN, 15));
        send.setForeground(Color.WHITE);

        text.setFont(new Font("Arial", Font.PLAIN, 15));

        bottom.setLayout(new BorderLayout());
        bottom.setPreferredSize(new Dimension(400, 40));
        bottom.add(text, BorderLayout.CENTER);
        bottom.add(send, BorderLayout.EAST);

        // Setting up the content panel
        content.setLayout(new BorderLayout());
        content.setPreferredSize(new Dimension(400, 430));

        // Add components to the JFrame
        Jf.add(top, BorderLayout.NORTH);
        Jf.add(content, BorderLayout.CENTER);
        Jf.add(bottom, BorderLayout.SOUTH);

        send.addActionListener(this);

        // Add KeyAdapter to send message when Enter key is pressed
        text.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    send.doClick();  // Simulate the Send button click
                }
            }
        });

        // JFrame setup
        Jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Jf.setSize(400, 600);
        Jf.setVisible(true);
        Jf.getContentPane().setBackground(Color.WHITE);
        Jf.setLocation(600,100);
        startClient();
    }

    public void startClient()
    {
        try
        {
            Socket s = new Socket("127.0.0.1", 8888);
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());

            while (true)
            {
                // Check for incoming messages and handle UI events
                SwingUtilities.invokeLater(() ->
                {
                    try {
                        if (in.available() > 0) { // Check if data is available
                            String message = in.readUTF();
                            JPanel panel = formatLabel(message);
                            JPanel left = new JPanel(new BorderLayout());
                            left.add(panel, BorderLayout.LINE_START);

                            verticalBox.add(left);
                            verticalBox.add(Box.createVerticalStrut(10));
                            content.add(verticalBox, BorderLayout.PAGE_START);
                            content.revalidate();
                            content.repaint();
                        }
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                });

                // Sleep for a short amount of time to prevent CPU hogging
                Thread.sleep(100);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            String outputText = text.getText().isEmpty() ? " " : text.getText();
            JPanel print = formatLabel(outputText);

            JPanel rightAlign = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            rightAlign.setBackground(null);
            rightAlign.setOpaque(false);
            rightAlign.add(print);

            verticalBox.add(rightAlign);
            verticalBox.add(Box.createVerticalStrut(10)); // Space between messages

            content.add(verticalBox, BorderLayout.PAGE_START);

            content.revalidate();
            content.repaint();

            text.setText(""); // Clear the text field after sending the message

            out.writeUTF(outputText);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style='width: 150px'>" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102)); // Green background for the message
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel(sdf.format(cal.getTime()));
        time.setFont(new Font("Tahoma", Font.PLAIN, 12));
        time.setForeground(Color.GRAY);

        panel.add(time);

        return panel;
    }

    public static void main(String[] args)
    {
        new Client();
    }
}