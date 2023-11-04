package urnaeletronica.FrontEnd.Pages.Candidate;

import java.awt.*;
import javax.swing.*;

import urnaeletronica.BackEnd.Controllers.CandidateController;
import urnaeletronica.BackEnd.Controllers.VoterController;
import urnaeletronica.BackEnd.Models.Candidate;
import urnaeletronica.FrontEnd.Components.ButtonComponent;
import urnaeletronica.FrontEnd.Components.FormInputComponent;
import urnaeletronica.FrontEnd.Pages.Page;



public class CandidateAddPage extends Page {
    private JPanel panel;

    public CandidateAddPage(JFrame frame) {
        super(frame);
        panel = new JPanel();
        panel.setBackground(new java.awt.Color(0, 0, 0));
        JLabel title = new JLabel("Adicionar candidato");

        // JLabel etitulo = new JLabel("Digite o Etitulo do candidato");
        // JTextField candidateEtitulo = new JTextField(20);

        FormInputComponent candidateEtitulo = new FormInputComponent("Digite o Etitulo do candidato");

        // Create input to get candidate Etitulo
        // JLabel number = new JLabel("Digite o número do candidato");
        // JTextField candidateNumber = new JTextField(20);

        FormInputComponent candidateNumber = new FormInputComponent("Digite o número do candidato");

        ButtonComponent button = new ButtonComponent("Adicionar", "primary");
        
        // Set layout
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;


        // Add title
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(title, constraints);

        // Add Etitulo label
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(candidateEtitulo.getLabel(), constraints);

         // Add candidate Etitulo input
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(candidateEtitulo.getTextField(), constraints);

        // Add number label
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(candidateNumber.getLabel(), constraints);

        // Add candidate number label
        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(candidateNumber.getTextField(), constraints);

        // Add button
        constraints.gridx = 1;
        constraints.gridy = 5;
        panel.add(button.getButton(), constraints);

        // // Add action listener to button
        button.getButton().addActionListener(e -> {

            // Get candidate Etitulo
            String candidateEtituloText = candidateEtitulo.getTextField().getText();

            // Get candidate number
            String candidateNumberText = candidateNumber.getTextField().getText();

            // Add candidate
            Candidate candidate = new Candidate(candidateEtituloText, candidateNumberText);
            addCandidate(candidate);
        });


    }

    private boolean checkIfVoterExists(String candidateEtituloText) {  
        if(VoterController.getVoter(candidateEtituloText) == null){
            JOptionPane.showMessageDialog(null, "Eleitor não existe");
            return false;
        }
        return true;
    } 

    private void addCandidate(Candidate candidate) {
       try{
            // Check if voter exists
            if(!checkIfVoterExists(candidate.getEtitulo())){
                JOptionPane.showMessageDialog(null, "Eleitor não existe");
                return;
            }
            
            // Add candidate

            CandidateController.createCandidate(candidate);
            JOptionPane.showMessageDialog(null, "Candidato adicionado com sucesso!");
            // go to candidatePage
            setChangePanel(panel, new CandidatePage(frame).getPanel());
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao adicionar candidato");
       }
    }

    public JPanel getPanel() {
        return panel;
    }

}
