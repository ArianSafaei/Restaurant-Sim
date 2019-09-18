package GUI;

import Model.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Window which displays the receipt for a seat or table.
 */
public class Receipt extends JFrame {

    private List<Order> orders; // The orders contained in this receipt.
    private JPanel contentPane; // The JPanel of this receipt.

    /**
     * Create the frame.
     *
     * @param bill The bill.
     * @param tableOrSeat The table or seat for this bill.
     * @param server The server which is handling this receipt.
     */
    public Receipt(Bill bill, Object tableOrSeat, Server server) {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(500, 0, 800, 1000);
        contentPane = new JPanel();
        contentPane.setBackground(ColorUtils.backgroundColor);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // Back button which closes the frame.
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow(tableOrSeat);
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Button to confirm payment
        JButton btnRegisterPayment = new JButton("Register Payment");
        btnRegisterPayment.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        closeWindow(tableOrSeat);
                        bill.confirmPayment();
                        JOptionPane.showMessageDialog(null, "Payment has been confirmed.");
                    }
                });

        // Setup the layout
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane
                        .createParallelGroup(Alignment.LEADING)
                        .addGroup(
                                gl_contentPane
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                gl_contentPane
                                                        .createParallelGroup(Alignment.LEADING)
                                                        .addComponent(
                                                                scrollPane, GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                                                        .addGroup(
                                                                gl_contentPane
                                                                        .createSequentialGroup()
                                                                        .addComponent(btnBack)
                                                                        .addPreferredGap(
                                                                                ComponentPlacement.RELATED, 592, Short.MAX_VALUE)
                                                                        .addComponent(btnRegisterPayment)))
                                        .addContainerGap()));
        gl_contentPane.setVerticalGroup(
                gl_contentPane
                        .createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                                gl_contentPane
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
                                        .addGap(18)
                                        .addGroup(
                                                gl_contentPane
                                                        .createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(btnBack)
                                                        .addComponent(btnRegisterPayment))
                                        .addContainerGap()));

        // Change the receipt open boolean of the table/seat
        if (tableOrSeat instanceof Table){
            ((Table) tableOrSeat).setReceiptWindowOpen(true);
        } else if (tableOrSeat instanceof Seat) {
            ((Seat) tableOrSeat).setReceiptWindowOpen(true);
        }

        // Format the text area as a receipt.
        JTextArea txtReceipt = new JTextArea();
        txtReceipt.setFont(new Font("monospaced", Font.PLAIN, 16));
        txtReceipt.append(String.format("%50s", "Everything Here Restaurant\r\n"));
        txtReceipt.append(String.format("%44s", "1 Yonge Street\r\n"));
        txtReceipt.append(String.format("%47s", "Toronto, ON, M5E 2A3\r\n"));
        txtReceipt.append(String.format("%45s", "(416)-123-4567\r\n\r\n"));
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
        txtReceipt.append(
                String.format(
                        "%" + String.valueOf(Math.round((78 + timeStamp.length()) / 2)) + "s",
                        timeStamp + "\r\n\r\n"));
        txtReceipt.append("Server: " + server.getName());
        txtReceipt.append("\r\n");
        if (tableOrSeat instanceof Table) {
            txtReceipt.append("Table: " + ((Table) tableOrSeat).getTableID());
        } else if (tableOrSeat instanceof Seat) {
            txtReceipt.append("Seat: " + ((Seat) tableOrSeat).getSeatID());
        }
        txtReceipt.append("\r\n");
        txtReceipt.append(
                "--------------------------------------------------------------------------\r\n");
        txtReceipt.append(String.format("%-5s %-55s %s", "QTY", "DESCRIPTION", "AMT\r\n"));
        txtReceipt.append(
                "--------------------------------------------------------------------------\r\n");

        DecimalFormat df = new DecimalFormat("0.00");
        orders = bill.getOrders();

        for (Order order : orders) {
            txtReceipt.append(
                    String.format(
                            "%-5s %-55s %s",
                            " 1",
                            order.getMenuItem().getName(),
                            "$" + String.valueOf(df.format((order.getMenuItem().getPrice()) / 100))));
            txtReceipt.append("\r\n");
            List<String> itemModifications = order.getMenuItem().getItemModifications();
            Set<String> unique = new HashSet<>(itemModifications);
            for (String key : unique) {
                if (key.substring(0,5).equals("extra")) {
                    txtReceipt.append(String.format("%-7s %s", "",
                            "+" + Collections.frequency(itemModifications, key) + " "
                                    + key.substring(6)));
                } else if (key.substring(0,4).equals("less")) {
                    txtReceipt.append(String.format("%-7s %s", "",
                            "-" + Collections.frequency(itemModifications, key) + " "
                                    + key.substring(5)));
                }
                txtReceipt.append("\r\n");
            }
        }

        txtReceipt.append(
                "--------------------------------------------------------------------------\r\n\r\n");
        txtReceipt.append(String.format("%-61s %s", "Subtotal", "$" + df.format(bill.getSubTotal())));
        txtReceipt.append("\r\n");
        txtReceipt.append(String.format("%-61s %s", "Tax", "$" + df.format(bill.getTaxAmount())));
        txtReceipt.append("\r\n");
        txtReceipt.append(String.format("%-61s %s", "Gratuity", "$" + df.format(bill.calculateTip())));
        txtReceipt.append("\r\n\r\n");
        txtReceipt.append(String.format("%-61s %s", "Total", "$" + df.format(bill.getGrandTotal())));
        scrollPane.setViewportView(txtReceipt);
        contentPane.setLayout(gl_contentPane);
        txtReceipt.setEditable(false);

        // Window listener for the closing of the window.
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeWindow(tableOrSeat);
                dispose();
            }
        });
    }

    /**
     * Close the window and set the receipt window boolean of the table or seat to false.
     *
     * @param tableOrSeat Table or Seat used for this receipt.
     */
    private void closeWindow(Object tableOrSeat) {
        if (tableOrSeat instanceof Table){
            ((Table) tableOrSeat).setReceiptWindowOpen(false);
        } else if (tableOrSeat instanceof Seat) {
            ((Seat) tableOrSeat).setReceiptWindowOpen(false);
        }
        dispose();
    }
}
