package GUI;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/*
The Login window. Login takes in an employee ID and password, and displays the appropriate employee UI
according to the ID entered, if the password is valid and the employee is not already signed in.
 */

public class Login extends JFrame {
  private JPanel mainPanel;
  private JButton loginButton;
  private JTextField iDTextField; // To enter employee ID.
  private JTextField passwordTextField; // To enter password.
  private int numLogins = 0;
  private int horizontal_scalar = 0; // Used for positioning the UI.
  private int vertical_scalar = 0; // Used for positioning the UI.
  private List<Employee> validEmployees; // List of employees that may sign in.
  private List<Table> validTables;

  /**
   * Initialize a login screen with (x, y) positions.
   *
   * @param x x-position of screen.
   * @param y y-position of screen.
   */
  public Login(int x, int y) {
    JFrame frame = new JFrame("Login");

    setup();
    frame.getContentPane().add(mainPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    mainPanel.setVisible(true);

    // pass both coordinates as -1 if want center (for first window)
    if (x == -1 && y == -1) {
      // to center window on screen
      frame.setLocationRelativeTo(null);
    } else {
      frame.setLocation(x, y);
    }
    frame.getContentPane().setBackground(Color.getHSBColor(0, 201, 28));
    frame.setVisible(true);

    // to disable resizing
    frame.setResizable(false);

    loginButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String user_ID = iDTextField.getText();

            try {
              Employee employee = getEmployeeByID(user_ID);
              String user_Password = passwordTextField.getText();
              JFrame newWindow = new JFrame();

              // Check if already logged in, and reset the fields if so
              if (employee.isLoggedIn()) {
                JOptionPane.showMessageDialog(null, employee.toString() + " is already signed in.");
                return;
              }

              // Check if password entered is correct
              if (employee.hasCorrectPassword(user_Password)) {

                // Open the corresponding screen depending on job, and set logged in status
                if (user_ID.startsWith("s")) {
                  newWindow = new ServerUI(validTables, (Server) getEmployeeByID(user_ID));
                  employee.setLoggedIn(true);
                  windowInit(newWindow);
                } else if (user_ID.startsWith("c")) {
                  newWindow = new CookUI((Cook) getEmployeeByID(user_ID));
                  employee.setLoggedIn(true);
                  windowInit(newWindow);
                } else if (user_ID.startsWith("m")) {
                  newWindow = new ManagerUI((Manager) getEmployeeByID(user_ID));
                  employee.setLoggedIn(true);
                  windowInit(newWindow);
                }

                // handle incorrect passwords
              } else {
                JOptionPane.showMessageDialog(null, "Incorrect Password.");
              }
              Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
              double width = screenSize.getWidth();
              double height = screenSize.getHeight();

              if ((numLogins % 2 == 0) && (numLogins != 0)) {
                horizontal_scalar = 0;
                vertical_scalar += 1;
              }
              if (vertical_scalar * 450 > height) {
                vertical_scalar = 0;
              }
              if (horizontal_scalar * 750 > width) {
                horizontal_scalar = 0;
              }

              // clear data in text field so new user can sign in
              iDTextField.setText("");
              passwordTextField.setText("");
              horizontal_scalar++;
              numLogins++;

              // handle invalid ID
            } catch (InvalidIDException e1) {
              JOptionPane.showMessageDialog(null, "Please enter a valid ID.");
            }
          }
        });
  }

  public void setValidEmployees(List<Employee> validEmployees) {
    this.validEmployees = validEmployees;
  }

  public void setValidTables(List<Table> validTables) {
    this.validTables = validTables;
  }

  /**
   * Return the appropriate employee object with this staffID.
   *
   * @param staffID The ID of the employee to be returned.
   * @return The corresponding employee object.
   * @throws InvalidIDException When there is no employee in the system with staffID.
   */
  private Employee getEmployeeByID(String staffID) throws InvalidIDException {
    for (Employee employee : validEmployees) {
      if (staffID.equals(employee.getID())) {
        return employee;
      }
    }
    throw new InvalidIDException("There is no employee with ID " + staffID + ".");
  }

  /**
   * Return the appropriate table object with this TableID.
   *
   * @param tableID The ID of the table to be returned.
   * @return The Table object with this ID.
   * @throws InvalidIDException When there is no table with TableID in the system.
   */
  private Table getTableByID(int tableID) throws InvalidIDException {
    for (Table table : validTables) {
      if (tableID == table.getTableID()) {
        return table;
      }
    }
    throw new InvalidIDException("There is no table with ID " + tableID + ".");
  }

  /**
   * Initialize this employeeWindow and make it visible.
   *
   * @param employeeWindow The window to be opened.
   */
  public void windowInit(JFrame employeeWindow) {
    employeeWindow.setLocation(horizontal_scalar * 750, vertical_scalar * 450);
    employeeWindow.setVisible(true);
  }

  /** Set up this UI. */
  private void setup() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout(0, 0));
    mainPanel.setMinimumSize(new Dimension(327, 200));
    mainPanel.setOpaque(true);
    mainPanel.setPreferredSize(new Dimension(327, 200));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridBagLayout());
    panel2.setBackground(ColorUtils.backgroundColor);
    mainPanel.add(panel2, BorderLayout.WEST);
    final JLabel label1 = new JLabel();
    label1.setText("ID");
    GridBagConstraints gbc;
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.WEST;
    panel2.add(label1, gbc);
    final JLabel label2 = new JLabel();
    label2.setText("Password");
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.WEST;
    panel2.add(label2, gbc);
    final JPanel panel3 = new JPanel();
    panel3.setLayout(new GridLayout(1, 1, -1, -1));
    Font panel3Font = new Font("Bauhaus 93", Font.PLAIN, 16);
    panel3.setFont(panel3Font);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    panel2.add(panel3, gbc);
    final JLabel label3 = new JLabel();
    label3.setBackground(ColorUtils.backgroundColor);
    label3.setIcon(new ImageIcon(getClass().getResource("/GUI/Pictures/Fruits and Vegetables small.png")));
    label3.setOpaque(true);
    label3.setText("");
    panel3.add(label3);
    final JPanel panel4 = new JPanel();
    panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    panel4.setBackground(ColorUtils.backgroundColor);
    mainPanel.add(panel4, BorderLayout.SOUTH);
    loginButton = new JButton();
    loginButton.setBackground(ColorUtils.fieldColor);
    loginButton.setText("Sign In");
    panel4.add(loginButton);
    final JPanel panel5 = new JPanel();
    panel5.setLayout(new GridBagLayout());
    panel5.setBackground(ColorUtils.backgroundColor);
    mainPanel.add(panel5, BorderLayout.CENTER);
    iDTextField = new JTextField();
    iDTextField.setBackground(ColorUtils.fieldColor);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    panel5.add(iDTextField, gbc);
    passwordTextField = new JTextField();
    passwordTextField.setBackground(ColorUtils.fieldColor);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    panel5.add(passwordTextField, gbc);
    final JPanel panel6 = new JPanel();
    panel6.setLayout(new GridLayout(1, 1, -1, -1));
    panel6.setBackground(ColorUtils.backgroundColor);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    panel5.add(panel6, gbc);
    final JPanel spacer2 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.fill = GridBagConstraints.VERTICAL;
    gbc.ipady = 70;
    spacer2.setBackground(ColorUtils.backgroundColor);
    panel5.add(spacer2, gbc);
    final JLabel label4 = new JLabel();
    label4.setBackground(ColorUtils.backgroundColor);
    Font label4Font = new Font("Bauhaus 93", Font.PLAIN, 18);
    label4.setFont(label4Font);
    label4.setOpaque(true);
    label4.setText("Everything Here Restaurant");
    // label4.setForeground(Color.WHITE);
    label4.setVisible(true);
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel5.add(label4, gbc);
    final JPanel panel7 = new JPanel();
    panel7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    panel7.setBackground(ColorUtils.backgroundColor);
    mainPanel.add(panel7, BorderLayout.EAST);
  }
}
