package urnaeletronica.FrontEnd.Pages.Eleitor;

import java.awt.*;
import javax.swing.*;

public class EleitorAddPage {

    private JPanel panel;

    public EleitorAddPage(JFrame jframe) {
        panel = new JPanel();
        panel.setBackground(new java.awt.Color(0, 0, 0));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel label1 = new JLabel("Nome");
        label1.setBounds(0, 0, 600, 50);
        label1.setFont(new java.awt.Font("Martian Mono", 0, 16));
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);


        JTextField textField1 = new JTextField();
        textField1.setBounds(0, 0, 600, 50);
        textField1.setFont(new java.awt.Font("Martian Mono", 0, 16));
        textField1.setForeground(new java.awt.Color(0, 0, 0));
        textField1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        JLabel label2 = new JLabel("CPF");
        label2.setBounds(0, 0, 600, 50);
        label2.setFont(new java.awt.Font("Martian Mono", 0, 16));
        

    }

    public JPanel getPanel() {
        return panel;
    }

}
