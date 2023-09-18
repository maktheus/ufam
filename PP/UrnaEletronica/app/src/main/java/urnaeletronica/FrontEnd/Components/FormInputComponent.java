package urnaeletronica.FrontEnd.Components;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class FormInputComponent {
   

    public FormInputComponent(JTextField textField, JLabel label, String text, int x, int y, int width, int height) {
        textField.setBounds(x, y, width, height);
        textField.setFont(new java.awt.Font("Martian Mono", 0, 16));
        textField.setForeground(new java.awt.Color(0, 0, 0));
        textField.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setBounds(x, y, width, height);
        label.setFont(new java.awt.Font("Martian Mono", 0, 16));
        label.setForeground(new java.awt.Color(255, 255, 255));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText(text);
    }


}
