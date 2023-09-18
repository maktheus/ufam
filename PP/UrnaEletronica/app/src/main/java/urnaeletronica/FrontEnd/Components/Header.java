package urnaeletronica.FrontEnd.Components;
import javax.swing.*;

import urnaeletronica.FrontEnd.Pages.LadingPages.LandingPage;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class Header  {
    private JPanel headerPanel;
    private JLabel label1 = new JLabel("Urna Eletronica");
    private JButton button1 = new JButton("Sair");
    private JButton button2 = new JButton("Home");


    public Header(JFrame frame){

        // changePanel

        headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());

        // constraints create
        GridBagConstraints constraints = new GridBagConstraints();

        headerPanel.setBackground(new java.awt.Color(248, 255, 229));

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
        

        // set elements to the left in the panel
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.6;
        headerPanel.add(label1, constraints);  

        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 0.2;
        headerPanel.add(button1, constraints);

        constraints.gridx = 2;
        constraints.weightx = 0.2;
        headerPanel.add(button2,constraints);

        // button one on click change Jframe to LandingPage
        button1.addActionListener(new java.awt.event.ActionListener(){
            // close the aplicattion
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
            }
        });

        // button two on click change Jframe to LandingPage
        button2.addActionListener(new java.awt.event.ActionListener(){
            // close the aplicattion
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePageToHome(frame);
            }
        });

    }

    private void changePageToHome(JFrame frame){
        GridBagConstraints constraints = new GridBagConstraints();
        frame.getContentPane().removeAll();

       Header header = new Header(frame);
        LandingPage body = new LandingPage(frame);
        Footer footer = new Footer();

       

        constraints.weightx = 1.0; // Peso total

        // Header (10%)
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.1; // 10%
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.BOTH;
        frame.add(header.getPanel(), constraints);

        // Landing Page (70%)
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.7; // 70%
        frame.add(body.getPanel(), constraints);

        // Footer (20%)
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weighty = 0.2; // 20%
        constraints.anchor = GridBagConstraints.SOUTH;
        frame.add(footer.getPanel(), constraints);

        frame.revalidate();
        frame.repaint();

    }

    public JPanel getPanel() {
        return headerPanel;
    }
} 
