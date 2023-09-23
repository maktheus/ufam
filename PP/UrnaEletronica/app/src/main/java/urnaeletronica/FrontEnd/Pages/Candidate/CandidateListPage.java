package urnaeletronica.FrontEnd.Pages.Candidate;
import  java.awt.*;
import javax.swing.*;

import urnaeletronica.BackEnd.Controllers.CandidateController;
import urnaeletronica.BackEnd.Models.Candidate;
import urnaeletronica.FrontEnd.Components.ButtonComponent;
import urnaeletronica.FrontEnd.Components.FormInputComponent;
import urnaeletronica.FrontEnd.Pages.Page;


public class CandidateListPage extends Page {
        
        private JPanel panel;
        
        public CandidateListPage(JFrame frame){
            super(frame);
            panel = new JPanel();
            panel.setBackground(new java.awt.Color(0, 0, 0));

            // JLabel label = new JLabel("Digite o Etitulo do candidato");
            // label.setForeground(new java.awt.Color(255, 255, 255));
            // JTextField textField = new JTextField();
            // JButton button = new JButton("Confirmar");

            FormInputComponent candidateEtitulo = new FormInputComponent("Digite o Etitulo do candidato");

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
            panel.add(button.getButton(), constraints);

            button.getButton().addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    getCandidate(candidateEtitulo.getTextField().getText());
                }
            });
        }
        private void getCandidate(String etitulo){
            try {
                Candidate candidate = CandidateController.getCandidate(etitulo);
                if(candidate != null){
                    JOptionPane.showMessageDialog(null, "Candidato encontrado com sucesso!");

                    setChangePanel(panel, new CandidatePage(frame).getPanel());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        public JPanel getPanel(){
            return panel;
        }    
}
