package GUI;

import Model.Manager;
import Model.Order;
import Model.OrderManager;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UndeliveredOrdersUI extends JFrame {

  private JPanel contentPane;
  private List<Order> undeliveredList;

  /** Create the frame. */
  public UndeliveredOrdersUI(Manager manager) {
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    setBounds(500, 0, 800, 1000);
    contentPane = new JPanel();
    contentPane.setBackground(ColorUtils.backgroundColor);
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    undeliveredList = OrderManager.getOrderManager().getReadyForDelivery();

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

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
                                    .addPreferredGap(
                                        ComponentPlacement.RELATED, 592, Short.MAX_VALUE)
                                    .addContainerGap()))));
    gl_contentPane.setVerticalGroup(
        gl_contentPane
            .createParallelGroup(Alignment.TRAILING)
            .addGroup(
                gl_contentPane
                    .createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE))));

    JTextArea undeliveredOrders = new JTextArea();
    undeliveredOrders.setFont(new Font("monospaced", Font.PLAIN, 16));
    undeliveredOrders.append(String.format("%50s", "Everything Here Restaurant\r\n"));
    undeliveredOrders.append(String.format("%44s", "1 Yonge Street\r\n"));
    undeliveredOrders.append(String.format("%47s", "Toronto, ON, M5E 2A3\r\n"));
    undeliveredOrders.append(String.format("%45s", "(416)-123-4567\r\n\r\n"));
    String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
    undeliveredOrders.append(
        String.format(
            "%" + String.valueOf(Math.round((78 + timeStamp.length()) / 2)) + "s",
            timeStamp + "\r\n\r\n"));
    undeliveredOrders.append("Manager: " + manager.getName());
    undeliveredOrders.append("\r\n");
    undeliveredOrders.append("List of Orders that are ready for delivery: ");
    undeliveredOrders.append("\r\n");

    for (Order order : undeliveredList) {
      undeliveredOrders.append(order.toString());
      undeliveredOrders.append("\r\n");
    }

    scrollPane.setViewportView(undeliveredOrders);
    contentPane.setLayout(gl_contentPane);
    undeliveredOrders.setEditable(false);
  }
}
