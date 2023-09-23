package urnaeletronica.FrontEnd.Pages.Voting;
import urnaeletronica.BackEnd.Controllers.VoteController;
import urnaeletronica.BackEnd.Models.Voto;
import urnaeletronica.FrontEnd.Components.ButtonComponent;
import urnaeletronica.FrontEnd.Components.VotingButtonsComponent;
import urnaeletronica.FrontEnd.Pages.Page;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class VontingPage extends Page {
    private JPanel panel;
    

    public VontingPage(JFrame frame, String etitulo) {
        super(frame);
        JLabel number = new JLabel("Digite o número do candidato");
        JLabel candidateNumber = new JLabel("");

        // Create a panel to put the buttons
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        

        panel.setBackground(new java.awt.Color(0, 0, 0));

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new java.awt.Insets(10, 10, 10, 10);

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
        

        constraints.gridx = 0;
        constraints.gridy = 3;
        VotingButtonsComponent votingButtonsComponent = new VotingButtonsComponent();
        panel.add(votingButtonsComponent.getPanel(), constraints);

        JPanel panelbutton = new JPanel();
        panelbutton.setLayout(new GridBagLayout());
        panelbutton.setOpaque(false);
        //Add confirm button

        ButtonComponent confirmButton = new ButtonComponent("Confirmar", "success");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panelbutton.add(confirmButton.getButton(), constraints);

        //add corrige button
        ButtonComponent corrigeButton = new ButtonComponent("Corrige", "danger");
        constraints.gridy = 0;
        constraints.gridx = 1;
        panelbutton.add(corrigeButton.getButton(), constraints);

        //add branco button
        ButtonComponent brancoButton = new ButtonComponent("Branco", "warning");
        constraints.gridx = 2;
        panelbutton.add(brancoButton.getButton(), constraints);

        constraints.gridy = 4;
        constraints.gridx = 0;
        panel.add(panelbutton, constraints, 0);
        


        votingButtonsComponent.setButton0ActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    candidateNumber.setText(candidateNumber.getText() + "0");
                }
            }
        );
        
        votingButtonsComponent.setButton1ActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    candidateNumber.setText(candidateNumber.getText() + "1");
                }
            }
        );

        votingButtonsComponent.setButton2ActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    candidateNumber.setText(candidateNumber.getText() + "2");
                }
            }
        );

        votingButtonsComponent.setButton3ActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    candidateNumber.setText(candidateNumber.getText() + "3");
                }
            }
        );

        votingButtonsComponent.setButton4ActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    candidateNumber.setText(candidateNumber.getText() + "4");
                }
            }
        );

        votingButtonsComponent.setButton5ActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    candidateNumber.setText(candidateNumber.getText() + "5");
                }
            }
        );

        votingButtonsComponent.setButton6ActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    candidateNumber.setText(candidateNumber.getText() + "6");
                }
            }
        );

        votingButtonsComponent.setButton7ActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    candidateNumber.setText(candidateNumber.getText() + "7");
                }
            }
        );

        votingButtonsComponent.setButton8ActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    candidateNumber.setText(candidateNumber.getText() + "8");
                }
            }
        );

        votingButtonsComponent.setButton9ActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    candidateNumber.setText(candidateNumber.getText() + "9");
                }
            }
        );

        confirmButton.getButton().addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // get the candidate number
                    String candidateNumberString = candidateNumber.getText();
                    // check if the candidate number is valid
                    if (candidateNumberString.length() == 2) {
                            Vote(etitulo,candidateNumberString);
                    } else {
                        // show error message
                        JOptionPane.showMessageDialog(null, "Número de candidato inválido", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        );

        corrigeButton.getButton().addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    candidateNumber.setText("");
                }
            }
        );

        brancoButton.getButton().addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    candidateNumber.setText("branco");
                }
            }
        );
    }

    private void Vote(String etitulo,String idCandidato) {
        if(VoteController.vote(new Voto( etitulo,idCandidato))){
            JOptionPane.showMessageDialog(null, "Voto realizado com sucesso");
            setChangePanel(panel, new EtituloConfirmVotePage(frame).getPanel());

        }else{
            JOptionPane.showMessageDialog(null, "Erro ao votar");
        }
    }


    public JPanel getPanel() {
        return panel;
    }
}
