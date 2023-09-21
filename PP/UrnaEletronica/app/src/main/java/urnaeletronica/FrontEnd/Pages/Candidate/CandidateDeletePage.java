package urnaeletronica.FrontEnd.Pages.Candidate;

import java.awt.*;
import javax.swing.*;

import urnaeletronica.BackEnd.Controllers.CandidateController;

public class CandidateDeletePage {
    private JPanel panel;

    public CandidateDeletePage(JFrame frame){
        panel = new JPanel();
        panel.setBackground(new java.awt.Color(0, 0, 0));

        JLabel label = new JLabel("Digite o Etitulo do candidato");
        label.setForeground(new java.awt.Color(255, 255, 255));
        JTextField textField = new JTextField();
        JButton button = new JButton("Confirmar");
        button.setBackground(new java.awt.Color(0, 0, 0));

        GridBagConstraints constraints = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());
        
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(label, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(textField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(button, constraints);

        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCandidate(textField.getText());
            }
        });
    }

    private void deleteCandidate(String etitulo){
        CandidateController.deleteCandidate(etitulo);
    }

    public JPanel getPanel(){
        return panel;
    }
}
