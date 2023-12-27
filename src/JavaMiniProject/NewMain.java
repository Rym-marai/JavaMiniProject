package JavaMiniProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// The main class representing the application's entry point
public class NewMain extends JFrame {
    
    // Constructor for initializing the main JFrame
    public NewMain() {
        setTitle("Home"); // Set the title of the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Specify default close operation
        setSize(400, 250); // Set the size of the JFrame
        setLocationRelativeTo(null); // Center the JFrame on the screen
        
        // Set the image icon
        ImageIcon icon = new ImageIcon("isamm.jpg");  
        setIconImage(icon.getImage());
        
        // Create a main panel with BoxLayout for vertical centering
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(226, 241, 255)); // Set background color

        // Create a subpanel with FlowLayout for horizontal centering
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(226, 241, 255)); // Set background color

        // Create Admin and User buttons
        JButton ad = new JButton("Admin");
        JButton us = new JButton("User");

        // Customize button colors
        ad.setBackground(new Color(160, 185, 211));
        ad.setForeground(Color.WHITE);

        us.setBackground(new Color(160, 185, 211));
        us.setForeground(Color.WHITE);

        // Customize font for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        ad.setFont(buttonFont);
        us.setFont(buttonFont);

        // Set preferred size for buttons
        ad.setPreferredSize(new Dimension(150, 50));
        us.setPreferredSize(new Dimension(150, 50));

        // ActionListener for User button
        us.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                SwingUtilities.invokeLater(() -> {
                    new UserForm(); // Open the UserForm on the Event Dispatch Thread
                });
            }
        });

        // ActionListener for Admin button
        ad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                SwingUtilities.invokeLater(() -> {
                    new AdminLogin(); // Open the Data class on the Event Dispatch Thread
                });
            }
        });

        // Add buttons to the buttonPanel
        buttonPanel.add(ad);
        buttonPanel.add(us);

        // Add vertical glue for centering in the mainPanel
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());

        // Add the mainPanel to the JFrame
        add(mainPanel);

        // Make the frame visible
        setVisible(true);
    }

    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(NewMain::new); // Invoke the constructor on the Event Dispatch Thread
    }
}
