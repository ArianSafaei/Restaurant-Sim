package GUI;

import Controller.ActionListenerForBack;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

// Template for displaying data as a list.

public class InformationPrintout extends JFrame {

  private JPanel contentPane; // The JPanel of this window.

  /**
   * Create the frame.
   *
   * @param title Title of this frame which is also bolded and underlined at the top of this text pane.
   * @param information Information to display below the title.
   */
  public InformationPrintout(String title, String information) {
    setTitle(title);
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    setBounds(500, 0, 800, 1000);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    JButton btnBack = new JButton("Back");
    btnBack.addActionListener(new ActionListenerForBack(this));
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
                            .addComponent(btnBack))
                    .addContainerGap()));
    gl_contentPane.setVerticalGroup(
        gl_contentPane
            .createParallelGroup(Alignment.LEADING)
            .addGroup(
                gl_contentPane
                    .createSequentialGroup()
                    .addContainerGap()
                    .addComponent(
                        scrollPane, GroupLayout.PREFERRED_SIZE, 876, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                    .addComponent(btnBack)
                    .addContainerGap()));

    JTextPane textPane = new JTextPane();
    textPane.setEditable(false);

    // Set up the formatting of the text.
    StyledDocument doc = textPane.getStyledDocument();

    SimpleAttributeSet attrTitle = new SimpleAttributeSet();
    StyleConstants.setFontFamily(attrTitle, "SansSerif");
    StyleConstants.setBold(attrTitle, true);
    StyleConstants.setUnderline(attrTitle, true);
    StyleConstants.setFontSize(attrTitle, 40);
    StyleConstants.setAlignment(attrTitle, StyleConstants.ALIGN_CENTER);

    SimpleAttributeSet attrNormal = new SimpleAttributeSet();
    StyleConstants.setFontFamily(attrNormal, "SansSerif");
    StyleConstants.setFontSize(attrNormal, 20);
    StyleConstants.setBold(attrNormal, false);
    StyleConstants.setUnderline(attrNormal, false);
    StyleConstants.setAlignment(attrNormal, StyleConstants.ALIGN_LEFT);

    // Print the text to the text pane.
    try {
      textPane.setParagraphAttributes(attrTitle, true);
      doc.insertString(doc.getLength(), title, attrTitle);
      textPane.setParagraphAttributes(attrTitle, true);
      doc.insertString(doc.getLength(), "\n\n", attrNormal);
      textPane.setParagraphAttributes(attrNormal, true);
      doc.insertString(doc.getLength(), information, attrNormal);
      textPane.setParagraphAttributes(attrNormal, true);
    } catch (BadLocationException e) {
      e.printStackTrace();
    }

    scrollPane.setViewportView(textPane);
    textPane.setCaretPosition(0);
    contentPane.setLayout(gl_contentPane);
  }
}
