package urnaeletronica.FrontEnd.Pages.Eleitor;

import java.awt.*;

import javax.swing.*;

import urnaeletronica.BackEnd.Controllers.VoterController;
import urnaeletronica.BackEnd.Models.Voter;
import urnaeletronica.FrontEnd.Components.ButtonComponent;
import urnaeletronica.FrontEnd.Components.FormInputComponent;
import urnaeletronica.FrontEnd.Pages.Page;

public class EleitorAddPage extends Page{

    private JPanel panel;
    VoterController voterController = new VoterController();

    public EleitorAddPage(JFrame frame) {
        super(frame);

        panel = new JPanel();
        panel.setBackground(new java.awt.Color(0, 0, 0));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        
        constraints.fill = GridBagConstraints.CENTER;


        
        
        FormInputComponent cpfInput = new FormInputComponent("CPF");
        FormInputComponent etituloInput = new FormInputComponent("Titulo de Eleitor");
        FormInputComponent NomeInput = new FormInputComponent("Nome");

        ButtonComponent button = new ButtonComponent("Cadastrar", "primary");


        // put on center of screen
        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weighty = 0.2;
        panel.add( NomeInput.getLabel(), constraints);
    
        constraints.gridy = 1;
        panel.add(NomeInput.getTextField(), constraints);

        constraints.gridy = 2;
        panel.add(cpfInput.getLabel(), constraints);

        constraints.gridy = 3;
        panel.add( cpfInput.getTextField(), constraints);

        constraints.gridy = 4;
        panel.add( etituloInput.getLabel(), constraints);

        constraints.gridy = 5;
        panel.add( etituloInput.getTextField(), constraints);

        constraints.gridy = 7;
        panel.add(button.getButton(), constraints);

        button.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //get data from text Field
                String nome = NomeInput.getTextField().getText();
                String cpf =  cpfInput.getTextField().getText();
                String titulo = etituloInput.getTextField().getText();
                Voter voter = new Voter(nome, cpf, titulo);
                cadastrarEleitor(voter);
            }
        });
    }

    //cadastrar eleitor

    public void cadastrarEleitor(Voter voter){
        if(VoterController.createVoter(voter) == true){
            JOptionPane.showMessageDialog(null, "Eleitor cadastrado com sucesso");
            setChangePanel(panel, new EleitorPage(frame).getPanel());
        }else{
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar eleitor");
        }
    }


    public JPanel getPanel() {
        return panel;
    }

}
