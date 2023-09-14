package urnaeletronica.FrontEnd.Components;
import javax.swing.*;


public class Header {
    private JPanel panel1;
    private JLabel label1 = new JLabel("Urna Eletronica");
    private JButton button1 = new JButton("Sair");
    private JButton button2 = new JButton("Home");


    public Header(){
        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBackground(new java.awt.Color(248, 255, 229));

        label1.setBounds(0, 0, 600, 50);
        label1.setFont(new java.awt.Font("Martian Mono", 0, 16));
        label1.setForeground(new java.awt.Color(0, 0, 0));
        label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        button1.setBounds(500, 0, 100, 50);
        button1.setBackground(new java.awt.Color(255, 255, 255));
        button1.setBorderPainted(false);
        button1.setFocusPainted(false);
        button1.setFont(new java.awt.Font("Arial", 0, 20));
        button1.setForeground(new java.awt.Color(0, 0, 0));

        button2.setBounds(0, 0, 100, 50);
        button2.setBackground(new java.awt.Color(255, 255, 255));
        button2.setBorderPainted(false);
        button2.setFocusPainted(false);
        button2.setFont(new java.awt.Font("Arial", 0, 20));
        button2.setForeground(new java.awt.Color(0, 0, 0));
        


        panel1.add(label1);
        panel1.add(button1);
        panel1.add(button2);

    }

    public JPanel getPanel() {
        return panel1;
    }
} 
