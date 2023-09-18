package urnaeletronica.FrontEnd.Components;

import urnaeletronica.FrontEnd.Pages.LandingPage;
import java.awt.*;
import javax.swing.*;
// addActionListener
import java.awt.event.ActionListener;


public class ChangePageComponent {
    
    public void setChangePanel(JButton button,JFrame frame, JPanel panel, JPanel newPanel, double weighty){
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
