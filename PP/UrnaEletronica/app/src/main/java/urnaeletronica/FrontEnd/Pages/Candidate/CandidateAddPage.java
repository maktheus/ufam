package urnaeletronica.FrontEnd.Pages.Candidate;

import java.awt.*;
import javax.swing.*;

// import urnaeletronica.Backend.Models.Candidate;
// import urnaeletronica.Backend.Controllers.CandidateController;



public class CandidateAddPage {
    private JPanel panel;

    public CandidateAddPage(JFrame frame) {
        panel = new JPanel();
        panel.setBackground(new java.awt.Color(0, 0, 0));
        JLabel title = new JLabel("Adicionar candidato");

        JLabel etitulo = new JLabel("Digite o Etitulo do candidato");
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
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(title, constraints);

        // Add Etitulo label
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(etitulo, constraints);

         // Add candidate Etitulo input
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(candidateEtitulo, constraints);

        // Add number label
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(number, constraints);

        // Add candidate number label
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(candidateNumber, constraints);

        // Add button
        constraints.gridx = 1;
        constraints.gridy = 5;
        panel.add(button, constraints);

       


        // // Add action listener to button
        // button.addActionListener(e -> {
        //     CandidateController candidateController = new CandidateController();
        //     addCandidate(frame, candidateController, candidateEtitulo.getText(), candidateNumber.getText());
        // });


    }


    // public void addCandidate(JFrame frame, CandidateController candidateController, String candidateEtitulo, String candidateNumber) {
        
    //     // Candidate candidate = candidateController.createCandidate(candidateEtitulo, candidateNumber);
    //     // if (candidate != null) {
    //     //     JOptionPane.showMessageDialog(frame, "Candidato criado com sucesso!");
    //     // } else {
    //     //     JOptionPane.showMessageDialog(frame, "Erro ao criar candidato!");
    //     // }
    // }

    public JPanel getPanel() {
        return panel;
    }

}
