package JavaMiniProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Class representing the admin login interface
public class AdminLogin {

    // Method to open the Data class and close the current frame
    private void openData(JFrame currentFrame) {
        SwingUtilities.invokeLater(() -> {
            new Data(); // Open the Data class
            currentFrame.dispose(); // Close the current frame
        });
    }

    // Constructor for initializing the admin login interface
    public AdminLogin() {
        // Create a new JFrame for the login interface
        JFrame f2 = new JFrame("Login");
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        f2.setSize(400, 200); // Set size of the JFrame

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add margins
        mainPanel.setBackground(new Color(226, 241, 255));

        // Create center panel with FlowLayout for password input
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10)); // FlowLayout with center alignment, spacing 0, 10
        centerPanel.setBackground(new Color(226, 241, 255));
        JLabel l = new JLabel("Enter Password : ");
        JPasswordField pass = new JPasswordField(20);
        pass.setHorizontalAlignment(JTextField.CENTER);

        String mdp = "bd0000"; // Admin password

        // Create Login and Exit buttons
        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");

        // ActionListener for the Login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                char[] password = pass.getPassword();
                String enteredPassword = new String(password);

                // Check if entered password is correct
                if (!enteredPassword.equals(mdp)) {
                    JOptionPane.showMessageDialog(f2, "Incorrect Password.");
                    pass.setText(""); // Clear the password field
                } else {
                    openData(f2); // Open the Data class and close the current frame
                }
            }
        });

        // ActionListener for the Exit button
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f2.dispose(); // Close the current frame
                SwingUtilities.invokeLater(() -> {
                    new NewMain(); // Open the NewMain class
                });
            }
        });

        centerPanel.add(l);
        centerPanel.add(pass);

        // Create button panel with FlowLayout for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // FlowLayout for buttons with center alignment
        buttonPanel.add(loginButton);
        buttonPanel.setBackground(new Color(226, 241, 255));

        // Add space between buttons
        buttonPanel.add(Box.createHorizontalStrut(10));

        buttonPanel.add(exitButton);

        // Add space above buttons
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Add center and button panels to the main panel
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set colors for buttons
        loginButton.setBackground(new Color(160, 185, 211));
        loginButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(160, 185, 211));
        exitButton.setForeground(Color.WHITE);

        // Center the frame on the screen
        f2.setLocationRelativeTo(null);

        // Add main panel to the frame
        f2.add(mainPanel);

        // Make the frame visible
        f2.setVisible(true);
    }

    // Main method to start the admin login interface
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminLogin();
        });
    }
}
