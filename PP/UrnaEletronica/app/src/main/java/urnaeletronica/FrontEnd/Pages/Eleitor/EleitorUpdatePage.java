package urnaeletronica.FrontEnd.Pages.Eleitor;

import java.awt.*;
import javax.swing.*;

import urnaeletronica.BackEnd.Controllers.VoterController;
import urnaeletronica.BackEnd.Models.Voter;
import urnaeletronica.FrontEnd.Pages.Page;

public class EleitorUpdatePage extends Page{

    private JPanel panel;

    public EleitorUpdatePage(JFrame frame) {
        super(frame);

        panel = new JPanel();
        panel.setBackground(new java.awt.Color(0, 0, 0));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        
        JLabel label1 = new JLabel("Nome");
        JTextField textField1 = new JTextField();
        
        
        JLabel label2 = new JLabel("CPF");
        JTextField textField2 = new JTextField();
        
        
        JLabel label3 = new JLabel("Titulo de Eleitor");
        JTextField textField3 = new JTextField();
        
        super.
        FormInputComponent(textField2, label2, "CPF", 0, 0, 600, 50);
        FormInputComponent(textField3, label3, "Titulo de Eleitor", 0, 0, 600, 50);
        FormInputComponent(textField1, label1, "Nome", 0, 0, 600, 50);

        JButton button = new JButton("Cadastrar");


        // put on center of screen
        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weighty = 0.2;
        panel.add(label1, constraints);
    
        constraints.gridy = 1;
        panel.add(textField1, constraints);

        constraints.gridy = 2;
        panel.add(label2, constraints);

        constraints.gridy = 3;
        panel.add(textField2, constraints);

        constraints.gridy = 4;
        panel.add(label3, constraints);

        constraints.gridy = 5;
        panel.add(textField3, constraints);

        constraints.gridy = 7;
        panel.add(button, constraints);

        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //get data from text Field
                String nome = textField1.getText();
                String cpf = textField2.getText();
                String titulo = textField3.getText();
                Voter voter = new Voter(nome, cpf, titulo);
                updateEleitor(voter);
            }
        });
    }



    private void updateEleitor(Voter voter){
        if(!verifyForm(voter)){
            JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente");
            return;
        }
        
        
        if(VoterController.updateVoter(voter)){
            JOptionPane.showMessageDialog(null, "Eleitor atualizado com sucesso");
            setChangePanel(panel, new EleitorPage(frame).getPanel());
        }else{
            JOptionPane.showMessageDialog(null, "Eleitor não encontrado");
        }

    }
    public JPanel getPanel() {
        return panel;
    }
}
