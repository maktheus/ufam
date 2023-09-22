package urnaeletronica.FrontEnd.Pages.Candidate;

import java.awt.*;
import javax.swing.*;

import urnaeletronica.BackEnd.Controllers.CandidateController;
import urnaeletronica.FrontEnd.Pages.Page;

public class CandidateDeletePage extends Page {
    private JPanel panel;

    public CandidateDeletePage(JFrame frame){
        super(frame);

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
        try {
            CandidateController.deleteCandidate(etitulo);

            JOptionPane.showMessageDialog(null, "Candidato deletado com sucesso!");
            // go to candidatePage
            setChangePanel(panel, new CandidatePage(frame).getPanel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public JPanel getPanel(){
        return panel;
    }
}
