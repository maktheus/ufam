package urnaeletronica.FrontEnd.Pages;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;


public abstract class Page {
    JFrame frame;
    public Page(JFrame  frame){}

    public void setChangePanel(JButton button, JPanel panel, JPanel newPanel, double weighty){
        button.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GridBagConstraints constraints = new GridBagConstraints();

                frame.getContentPane().remove(panel);
                constraints.gridx = 0;
                constraints.gridy = 1;
                constraints.gridwidth = 1;
                constraints.gridheight = 1;
                constraints.weighty = 0.7; // 70%
                constraints.fill = GridBagConstraints.BOTH;
                frame.getContentPane().add(newPanel, constraints);
                frame.revalidate();
                frame.repaint();
            }
        });
    }
}
