package urnaeletronica.FrontEnd.Components;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FormInputComponent {
    private JLabel label = new JLabel();
    private JTextField textField = new JTextField();

    
    public FormInputComponent( String text) {
        textField.setBounds( 0, 0, 1200, 50);
        textField.setFont(new java.awt.Font("Martian Mono", 0, 16));
        textField.setForeground(new java.awt.Color(0, 0, 0));
        textField.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textField.setPreferredSize(new Dimension(200, 24));
        
        label.setBounds( 0, 0, 1200, 50);
        label.setFont(new java.awt.Font("Martian Mono", 0, 16));
        label.setForeground(new java.awt.Color(255, 255, 255));
        
        
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText(text);
    }

    public JLabel getLabel() {
        return this.label;
    }

    public JTextField getTextField() {
        return this.textField;
    }
}
