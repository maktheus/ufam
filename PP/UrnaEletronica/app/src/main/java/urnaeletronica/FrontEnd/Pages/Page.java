package urnaeletronica.FrontEnd.Pages;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.sql.Connection;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;


public abstract class Page {
    public JFrame frame;
    public Connection database;
    public Page(JFrame  frame,  Connection database){
        this.frame = frame;
        this.database = database;
    }

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
