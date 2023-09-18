package urnaeletronica.FrontEnd.Pages.Eleitor;

import java.awt.*;
import javax.swing.*;

import urnaeletronica.FrontEnd.Pages.Page;

public class EleitorUpdatePage extends Page{

    private JPanel panel;

    public EleitorUpdatePage(JFrame frame) {
        super(frame);
        panel = new JPanel();
        panel.setBackground(new java.awt.Color(0, 0, 0));

        JLabel label = new JLabel("Digite o número do candidato");
        label.setForeground(new java.awt.Color(255, 255, 255));
        JTextField textField = new JTextField();
        JButton button = new JButton("Confirmar");

        GridBagConstraints constraints = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());
        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(label, constraints, 0);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(textField, constraints, 0);
        
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(button, constraints, 0);

    }

    public JPanel getPanel() {
        return panel;
    }
}
