package urnaeletronica.FrontEnd.Pages.Candidate;

import java.awt.*;
import javax.swing.*;

public class CandidateAddPage {
    private JPanel panel;

    public CandidateAddPage() {
        panel = new JPanel();
        panel.setBackground(new java.awt.Color(0, 0, 0));
        JLabel title = new JLabel("Adicionar candidato");

        JLabel etitulo = new JLabel("Digite o número do candidato");
        JTextField candidateEtitulo = new JTextField(20);

        // Create input to get candidate Etitulo
        JLabel number = new JLabel("Digite o número do candidato");
        JTextField candidateNumber = new JTextField(20);

        JButton button = new JButton("Criar candidato");
        button.setBackground(new java.awt.Color(0, 0, 0));

        // Create a panel to put the buttons
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        // set label color to white
        button.setForeground(new java.awt.Color(25, 0, 0));

        // Add title
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(title, constraints);

        // Add Etitulo label
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(etitulo, constraints);

        // Add number label
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(number, constraints);

        // Add candidate number label
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(candidateNumber, constraints);

        // Add button
        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(button, constraints);
    }

    public JPanel getPanel() {
        return panel;
    }

}
