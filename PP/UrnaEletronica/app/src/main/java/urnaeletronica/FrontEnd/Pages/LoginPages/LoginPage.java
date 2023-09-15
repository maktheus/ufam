package urnaeletronica.FrontEnd.Pages.LoginPages;
import java.awt.*;
import javax.swing.*;

public class LoginPage {
    public JPanel panel;
    public JLabel label;
    public JTextField textField;
    public JButton button;
    
    public LoginPage(JFrame jframe){
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
}
