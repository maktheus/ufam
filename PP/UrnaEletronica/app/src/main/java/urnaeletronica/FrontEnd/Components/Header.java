package urnaeletronica.FrontEnd.Components;
import javax.swing.*;

import urnaeletronica.FrontEnd.Pages.LandingPage;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class Header {
    private JPanel panel;
    private JLabel label1 = new JLabel("Urna Eletronica");
    private JButton button1 = new JButton("Sair");
    private JButton button2 = new JButton("Home");


    public Header(JFrame frame){
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // constraints create
        GridBagConstraints constraints = new GridBagConstraints();

        panel.setBackground(new java.awt.Color(248, 255, 229));

        label1.setBounds(0, 0, 600, 50);
        label1.setFont(new java.awt.Font("Martian Mono", 0, 16));
        label1.setForeground(new java.awt.Color(0, 0, 0));
        label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        button1.setBounds(500, 0, 100, 50);
        button1.setBackground(new java.awt.Color(255, 255, 255));
        button1.setBorderPainted(false);
        button1.setFocusPainted(false);
        button1.setFont(new java.awt.Font("Martin Mono", 0, 20));
        button1.setForeground(new java.awt.Color(0, 0, 0));

        button2.setBounds(0, 0, 100, 50);
        button2.setBackground(new java.awt.Color(255, 255, 255));
        button2.setBorderPainted(false);
        button2.setFocusPainted(false);
        button2.setFont(new java.awt.Font("Martian Mono", 0, 20));
        button2.setForeground(new java.awt.Color(0, 0, 0));
        

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.6;
        panel.add(label1, constraints);  

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 0.2;
        panel.add(button1, constraints);

        constraints.gridx = 2;
        constraints.weightx = 0.2;
        panel.add(button2,constraints);

        // button one on click change Jframe to LandingPage
        button1.addActionListener(new java.awt.event.ActionListener(){
            // close the aplicattion
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
            }
        });

        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GridBagConstraints constraints = new GridBagConstraints();

                frame.getContentPane().remove(panel);
                constraints.gridx = 0;
                constraints.gridy = 1;
                constraints.gridwidth = 1;
                constraints.gridheight = 1;
                constraints.weighty = 0.7; // 70%
                constraints.fill = GridBagConstraints.BOTH;
    
                frame.getContentPane().add(new LandingPage(frame).getPanel(), constraints);
                frame.revalidate();
                frame.repaint();
            }
        });

    }

    public JPanel getPanel() {
        return panel;
    }
} 
