package urnaeletronica.FrontEnd.Components;
import javax.swing.*;
import java.awt.*;


public class FooterComponent {
    private JPanel panel;
    private JLabel label1 = new JLabel("© 2023 Matheus Serrão");
    private JLabel label2 = new JLabel("Feito em setembro de 2023");

    public FooterComponent(){
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
    
        GridBagConstraints constraints = new GridBagConstraints();
        
        panel.setBackground(new java.awt.Color(248, 255, 229));

        label1.setBounds(0, 0, 600, 50);
        label1.setFont(new java.awt.Font("Martian Mono", 0, 16));
        label1.setForeground(new java.awt.Color(0, 0, 0));
        label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

    
        label2.setBounds(0, 50, 600, 50);
        label2.setFont(new java.awt.Font("Martian Regular", 0, 16));
        label2.setForeground(new java.awt.Color(0, 0, 0));
        label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 1;

        panel.add(label1,constraints);

        constraints.gridy = 2;
        panel.add(label2,constraints);
    }

    public JPanel getPanel() {
        return panel;
    }

}
