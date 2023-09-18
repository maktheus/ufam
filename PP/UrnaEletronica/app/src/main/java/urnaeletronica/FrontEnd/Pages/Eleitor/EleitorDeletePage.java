package urnaeletronica.FrontEnd.Pages.Eleitor;

import urnaeletronica.FrontEnd.Pages.Page;

import java.awt.*;
import java.sql.Connection;
import javax.swing.*;




public class EleitorDeletePage extends Page {
    public JPanel panel;
    public JLabel label;
    public JTextField textField;
    public JButton button;
    
    public EleitorDeletePage(JFrame frame, Connection database) {
        super(frame, database);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setBackground(new java.awt.Color(0, 0, 0));
        label = new JLabel("Digite o número do candidato");
        label.setForeground(new java.awt.Color(255, 255, 255));
        textField = new JTextField();
        button = new JButton("Confirmar");
        button.setBackground(new java.awt.Color(0, 0, 0));
        button.setForeground(new java.awt.Color(255, 255, 255));
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(label, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(textField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(button, constraints);
    }

    public JPanel getPanel() {
        return panel;
    }
}
