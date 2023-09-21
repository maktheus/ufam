package urnaeletronica.FrontEnd.Pages.Voting;
import urnaeletronica.FrontEnd.Components.VotingButtonsComponent;


import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class VontingPage {
    private JPanel panel;
    

    public VontingPage(JFrame frame) {
        JLabel number = new JLabel("Digite o número do candidato");
        JLabel candidateNumber = new JLabel("00");

        // Create a panel to put the buttons
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        

        panel.setBackground(new java.awt.Color(0, 0, 0));

        GridBagConstraints constraints = new GridBagConstraints();

        //set label color to white
        number.setForeground(new java.awt.Color(255, 255, 255));
        candidateNumber.setForeground(new java.awt.Color(255, 255, 255));

        
        //Add number label
        constraints.gridx = 0;
        constraints.gridy = 1;
        
        panel.add(number, constraints);


        //Add candidate number label
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(candidateNumber, constraints);
        

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(new VotingButtonsComponent().getPanel(), constraints);
    }


    public JPanel getPanel() {
        return panel;
    }
}
