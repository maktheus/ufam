package urnaeletronica.FrontEnd.Pages.Voting;
import javax.swing.*;

import urnaeletronica.BackEnd.Controllers.VoteController;
import urnaeletronica.FrontEnd.Components.ButtonComponent;
import urnaeletronica.FrontEnd.Components.FormInputComponent;
import urnaeletronica.FrontEnd.Pages.Page;

import java.awt.*;

public class EtituloConfirmVotePage extends Page{
    JPanel panel = new JPanel();

    public EtituloConfirmVotePage(JFrame jFrame){
        super(jFrame);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setBackground(new java.awt.Color(0, 0, 0));

        FormInputComponent formInputComponent = new FormInputComponent("Digite seu e-titulo");
        constraints.gridx = 0;

        constraints.insets = new java.awt.Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.CENTER;


        constraints.gridy = 1;
        panel.add(formInputComponent.getLabel(), constraints);

        constraints.gridy = 2;
        panel.add(formInputComponent.getTextField(), constraints);


        ButtonComponent buttonComponent = new ButtonComponent("Confirmar", "primary");
        constraints.gridy = 3;
        panel.add(buttonComponent.getButton(), constraints);

        buttonComponent.getButton().addActionListener(e -> {
            verifyIfIsAlreadyVoted(formInputComponent.getTextField().getText());
        });
    }

    private void verifyIfIsAlreadyVoted(String etitulo){
        if(VoteController.verifyIfAlredyVoted(etitulo)){
            JOptionPane.showMessageDialog(null, "Você já votou");
        }
        else{
            setChangePanel(panel, new VontingPage(frame, etitulo).getPanel());
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
