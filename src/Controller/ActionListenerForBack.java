package Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Create action listener for Back button
 */
public class ActionListenerForBack implements ActionListener {

    private Frame window;

    /**
     * Constructor for button
     * @param window takes in Frame as parameter so it can automatically close when button clicked
     */
    public ActionListenerForBack(Frame window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        window.dispose();
    }
}
