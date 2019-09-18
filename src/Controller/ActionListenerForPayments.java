package Controller;

import GUI.InformationPrintout;
import GUI.ManagerUI;
import Model.Restaurant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * Create action listener for Today's payments button in manager
 */
public class ActionListenerForPayments implements ActionListener {

    private ManagerUI managerUI;

    /**
     * Constructor for this class
     * @param managerUI The managerUI window that this button is on
     */
    public ActionListenerForPayments(ManagerUI managerUI) {
        this.managerUI = managerUI;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        DecimalFormat df = new DecimalFormat("0.00");
        String payments = "";
        for (Map.Entry<Integer, Double> entry : Restaurant.getPaymentRecord().entrySet()) {
            payments +=
                    "Bill ID: " + entry.getKey()
                            + "  -->  Amount: $"
                            + df.format(entry.getValue()) + "\n";
        }
        InformationPrintout frame = new InformationPrintout("Recorded Payments", payments);
        frame.setVisible(true);
    }
}
