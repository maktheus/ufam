package urnaeletronica.FrontEnd.Pages;

import urnaeletronica.FrontEnd.Components.*;
import urnaeletronica.FrontEnd.Pages.Voting.VontingPage;
//import App.java;


import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;



public class LandingPage {
    private JPanel panel1;
    private JLabel icon;
    // private String iconPath = "/home/temp/code/ufam/PP/UrnaEletronica/app/src/main/java/urnaeletronica/FrontEnd/Imgs/Urna.png";
    private Button button1 = new Button("Votar", "primary");
    private Button button2 = new Button("Candidatos", "primary");
    private Button button3 = new Button("Eleitores", "primary");
    private Button button4 = new Button("Resultados", "primary");

    
    public LandingPage() {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        panel1.setPreferredSize(new java.awt.Dimension(0, 400));
        panel1.setBackground(new java.awt.Color(0, 0, 0));


        // ImageIcon UrnaIcon = new ImageIcon(iconPath);
        // icon = new JLabel(UrnaIcon);
        // constraints.gridx = 0;
        // constraints.gridy = 0;
        // constraints.gridwidth = 1;
        // constraints.gridheight = 4;
        // panel1.add(icon, constraints);
        // constraints.gridheight = 1;  // Reset grid height for buttons
        
        // constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.8;
        constraints.weighty = 1.0;

        
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel1.add(button1.getButton(), constraints);

        //button on click listener
        button1.getButton().addActionListener(e -> {
            // Set this Jframe not visible
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
            frame.setVisible(false);
            
            // Create new VotingPage
            VontingPage votingPage = new VontingPage();
            frame.setContentPane(votingPage.getPanel());
            frame.setVisible(true);

        });

        constraints.gridy = 1;
        panel1.add(button2.getButton(), constraints);

        constraints.gridy = 2;
        panel1.add(button3.getButton(), constraints);

        constraints.gridy = 3;
        panel1.add(button4.getButton(), constraints);
    }

    public JPanel getPanel() {
        return panel1;
    }
}
