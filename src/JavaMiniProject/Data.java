package JavaMiniProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Class representing the data display interface
public class Data {
    private Connection connection;
    private DefaultListModel<String> listModel;
    private JComboBox<String> filterComboBox;
    private JRadioButton c;
    private JRadioButton field;
    private JList<String> dataList;

    // Constructor for initializing the data display interface
    public Data() {
        // Initialize the database connection
        initializeDatabase();

        // Create a new JFrame for the data display
        JFrame f4 = new JFrame("Lists");
        f4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        f4.setSize(350, 400); // Set size of the JFrame
        f4.setLocationRelativeTo(null); // Center the frame

        // Set a background color
        f4.getContentPane().setBackground(new Color(226, 241, 255));

        // Create a main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add margins
        mainPanel.setBackground(new Color(226, 241, 255)); // Set background color

        // Create radio buttons for selecting data by Clubs or Fields
        c = new JRadioButton("Clubs");
        field = new JRadioButton("Fields");
        ButtonGroup grp = new ButtonGroup();
        c.setBackground(new Color(226, 241, 255));
        field.setBackground(new Color(226, 241, 255));
        grp.add(c);
        grp.add(field);

        // Initialize DefaultListModel for JList
        listModel = new DefaultListModel<>();
        dataList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(dataList);

        // Create filter combo box
        filterComboBox = new JComboBox<>();

        // Add necessary components to f4
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(createFilterPanel(), BorderLayout.NORTH);

        // Add ActionListeners for radio buttons and filter combo box
        c.addActionListener(e -> {
            updateFilterComboBoxWithClubs();
        });

        field.addActionListener(e -> {
            updateFilterComboBoxWithFields();
        });

        filterComboBox.addActionListener(e -> {
            fetchAndDisplayData();
        });

        // Set font size for components
        Font font = new Font("Arial", Font.PLAIN, 14);
        c.setFont(font);
        field.setFont(font);
        filterComboBox.setFont(font);
        dataList.setFont(font);

        f4.add(mainPanel);

        // Make the frame visible
        f4.setVisible(true);
    }

    // Create the filter panel containing radio buttons and the filter combo box
    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        filterPanel.setBackground(new Color(226, 241, 255)); // Set background color

        filterPanel.add(c);
        filterPanel.add(field);
        filterPanel.add(filterComboBox);

        // Initially, set the visibility of the combo box to false
        filterComboBox.setVisible(false);

        return filterPanel;
    }

    // Update the combo box with club options
    private void updateFilterComboBoxWithClubs() {
        filterComboBox.removeAllItems();
        filterComboBox.addItem("--");
        filterComboBox.addItem("OrendaJE");
        filterComboBox.addItem("IMC");
        filterComboBox.addItem("RadioClub");
        filterComboBox.addItem("UGET");
        filterComboBox.addItem("RobotiqueISAMM");
        filterComboBox.setVisible(true);
    }

    // Update the combo box with field options
    private void updateFilterComboBoxWithFields() {
        filterComboBox.removeAllItems();
        filterComboBox.addItem("--");
        filterComboBox.addItem("IM");
        filterComboBox.addItem("BD");
        filterComboBox.addItem("MIME");
        filterComboBox.addItem("CM");
        filterComboBox.addItem("AV");
        filterComboBox.setVisible(true);
    }

    // Fetch and display data based on the selected filter
    private void fetchAndDisplayData() {
        SwingUtilities.invokeLater(() -> {
            String selectedItem = (String) filterComboBox.getSelectedItem();
            if (selectedItem != null) {
                if (c.isSelected()) {
                    displayDataByClub(selectedItem);
                } else if (field.isSelected()) {
                    displayDataByField(selectedItem);
                }
            }
        });
    }

    // Display data based on the selected club
    private void displayDataByClub(String selectedClub) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM user WHERE club = '" + selectedClub + "'";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                // Fetch all data from ResultSet into a List
                List<String> userData = fetchDataFromResultSet(resultSet);

                // Update the list model with the fetched data
                updateListModel(userData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Display data based on the selected field
    private void displayDataByField(String selectedField) {
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM user WHERE field = '" + selectedField + "'";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                // Fetch all data from ResultSet into a List
                List<String> userData = fetchDataFromResultSet(resultSet);

                // Update the list model with the fetched data
                updateListModel(userData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Fetch data from ResultSet and return as a List of strings
    private List<String> fetchDataFromResultSet(ResultSet resultSet) throws SQLException {
        List<String> userData = new ArrayList<>();

        while (resultSet.next()) {
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            userData.add(firstName + " " + lastName);
        }

        return userData;
    }

    // Update the list model with new data
    private void updateListModel(List<String> userData) {
        SwingUtilities.invokeLater(() -> {
            listModel.clear();
            listModel.addAll(userData);
        });
    }

    // Initialize the database connection
    private void initializeDatabase() {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3308/dbuser";
        String user = "root";
        String password = "";

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection established successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
        }
    }

    // Main method to start the data display interface
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Data::new);
    }
}
