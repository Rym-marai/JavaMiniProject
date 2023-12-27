package JavaMiniProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Class representing the user input form
public class UserForm {

    // Fields for user information
    private JTextField name;
    private JTextField fname;
    private JTextField age;
    private JComboBox<String> cbox;
    private JComboBox<String> sbox;

    // Constructor for initializing the user input form
    public UserForm() {
        // Create a new JFrame for the user form
        JFrame f3 = new JFrame("User Form");
        f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        f3.setSize(400, 300); // Set size of the JFrame

        // Create main panel with BoxLayout for vertical and horizontal centering
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        mainPanel.setBackground(new Color(226, 241, 255)); // Set background color

        // Add margin to the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create form panel with GridLayout for organizing components
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 20)); // Grid layout with 6 rows, 2 columns, and spacing
        formPanel.setBackground(new Color(226, 241, 255)); // Set background color

        // Styling for labels
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        // Create labels for form fields
        JLabel n = new JLabel("Name");
        n.setFont(labelFont);
        JLabel fn = new JLabel("Family Name");
        fn.setFont(labelFont);
        JLabel a = new JLabel("Age");
        a.setFont(labelFont);
        JLabel s = new JLabel("Field");
        s.setFont(labelFont);
        JLabel c = new JLabel("Club");
        c.setFont(labelFont);

        // Create text fields for user input
        name = new JTextField(30);
        fname = new JTextField(30);
        age = new JTextField(2);

        // Styling for text fields
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);
        name.setFont(textFieldFont);
        fname.setFont(textFieldFont);
        age.setFont(textFieldFont);

        // Define options for club and field selection
        String[] clubs = {"--", "OrendaJE", "IMC", "RadioClub", "UGET", "RobotiqueISAMM"};
        cbox = new JComboBox<>(clubs);

        String[] spes = {"--", "IM", "BD", "MIME", "CM", "AV"};
        sbox = new JComboBox<>(spes);

        // Create Submit and Exit buttons
        JButton ok = new JButton("Submit");
        JButton ann = new JButton("Exit");

        // Styling for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        ok.setFont(buttonFont);
        ann.setFont(buttonFont);

        // Set colors for buttons
        ok.setBackground(new Color(160, 185, 211));
        ok.setForeground(Color.WHITE);
        ann.setBackground(new Color(160, 185, 211));
        ann.setForeground(Color.WHITE);

        // Set size for buttons
        Dimension buttonSize = new Dimension(50,20);
        ok.setPreferredSize(buttonSize);
        ann.setPreferredSize(buttonSize);

        // Add components to the form panel
        formPanel.add(n);
        formPanel.add(name);
        formPanel.add(fn);
        formPanel.add(fname);
        formPanel.add(a);
        formPanel.add(age);
        formPanel.add(s);
        formPanel.add(sbox);
        formPanel.add(c);
        formPanel.add(cbox);
        formPanel.add(ok);
        formPanel.add(ann);

        // ActionListener for the Submit button
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Validate the form and insert data into the database if valid
                if (validateForm()) {
                    if (insertDataToDatabase()) {
                        JOptionPane.showMessageDialog(f3, "User details submitted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(f3, "Failed to submit user details.");
                    }
                }
            }
        });

        // ActionListener for the Exit button
        ann.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f3.dispose(); // Close the current frame

                // Create an instance of NewMain and make it visible
                SwingUtilities.invokeLater(() -> {
                    new NewMain();
                });
            }
        });

        // Add form panel to the main panel
        mainPanel.add(formPanel);

        // Add main panel to the frame
        f3.add(mainPanel);

        // Center the frame on the screen
        f3.setLocationRelativeTo(null);

        // Make the frame visible
        f3.setVisible(true);
    }

    // Validate the user input form
    private boolean validateForm() {
        if (name.getText().isEmpty() || fname.getText().isEmpty() || age.getText().isEmpty()
                || cbox.getSelectedIndex() == 0 || sbox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields and select a valid club and field.");
            return false;
        }

        // Additional validation logic can be added if needed

        return true;
    }

    // Insert user data into the database
    private boolean insertDataToDatabase() {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3308/dbuser";
        String user = "root";
        String password = "";

        // SQL query to insert data into the database
        String sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            preparedStatement.setString(1, name.getText());
            preparedStatement.setString(2, fname.getText());
            preparedStatement.setInt(3, Integer.parseInt(age.getText()));
            preparedStatement.setString(4, (String) sbox.getSelectedItem());
            preparedStatement.setString(5, (String) cbox.getSelectedItem());

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // Returns true if at least one row is affected (data inserted)

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
            return false;
        }
    }

    // Main method to start the user form
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UserForm();
        });
    }
}
