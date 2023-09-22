package urnaeletronica.FrontEnd.Pages.Candidate;
import java.awt.*;
import javax.swing.*;

import urnaeletronica.BackEnd.Controllers.CandidateController;
import urnaeletronica.BackEnd.Models.Candidate;
import urnaeletronica.FrontEnd.Components.ButtonComponent;
import urnaeletronica.FrontEnd.Components.FormInputComponent;
import urnaeletronica.FrontEnd.Pages.Page;


public class CandidateUpdatePage extends Page {
        
        private JPanel panel;
        
        public CandidateUpdatePage(JFrame frame){
            super(frame);
            panel = new JPanel();
            panel.setBackground(new java.awt.Color(0, 0, 0));

            // JLabel label = new JLabel("Digite o Etitulo do candidato");
            // label.setForeground(new java.awt.Color(255, 255, 255));

            FormInputComponent candidateEtitulo = new FormInputComponent("Digite o Etitulo do candidato");
            // JTextField textField = new JTextField();
            // JButton button = new JButton("Confirmar");

            FormInputComponent candidateNumber = new FormInputComponent("Digite o numero do candidato");

            ButtonComponent button = new ButtonComponent("Confirmar", "primary");

            GridBagConstraints constraints = new GridBagConstraints();
            panel.setLayout(new GridBagLayout());

            constraints.anchor = GridBagConstraints.CENTER;

            constraints.gridx = 1;
            constraints.gridy = 0;
            panel.add(candidateEtitulo.getLabel(), constraints);

            constraints.gridx = 1;
            constraints.gridy = 1;
            panel.add(candidateEtitulo.getTextField(), constraints);

            constraints.gridx = 1;
            constraints.gridy = 2;
            panel.add(candidateNumber.getLabel(), constraints);

            constraints.gridx = 1;
            constraints.gridy = 3;
            panel.add(candidateNumber.getTextField(), constraints);

            constraints.gridx = 1;
            constraints.gridy = 4;
            panel.add(button.getButton(), constraints);

            button.getButton().addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    updateCandidate(candidateEtitulo.getTextField().getText(), candidateNumber.getTextField().getText());
                }
            });
        }

        private void updateCandidate(String etitulo, String candidateNumber){
            try {
                
                if(CandidateController.updateCandidate( new Candidate(etitulo, candidateNumber))){
                    JOptionPane.showMessageDialog(null, "Candidato encontrado com sucesso!");

                    setChangePanel(panel, new CandidatePage(frame).getPanel());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Candidato não encontrado!");
            }
        }

    
        public JPanel getPanel(){
            return panel;
        }
    
}
