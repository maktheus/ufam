package urnaeletronica.FrontEnd.Pages.Eleitor;

import urnaeletronica.FrontEnd.Pages.Page;
import urnaeletronica.BackEnd.Controllers.VoterController;
import urnaeletronica.FrontEnd.Components.*;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class EleitorPage extends Page {

    private JPanel panel;
    ButtonComponent button = new ButtonComponent("Cadastrar", "primary");
    ButtonComponent button2 = new ButtonComponent("Editar", "primary");
    ButtonComponent button3 = new ButtonComponent("Excluir", "primary");
    ButtonComponent button4 = new ButtonComponent("Listar", "primary");

    public EleitorPage(JFrame frame) {
        super(frame);
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        panel.setPreferredSize(new java.awt.Dimension(0, 400));
        panel.setBackground(new java.awt.Color(0, 0, 0));

        constraints.anchor = GridBagConstraints.CENTER;

        constraints.insets = new java.awt.Insets(10, 10, 10, 10);

        constraints.weighty = 1.0;

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(button.getButton(), constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(button2.getButton(), constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(button3.getButton(), constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(button4.getButton(), constraints);

        setChangePanel(button.getButton(), panel, new EleitorAddPage(frame).getPanel());
        setChangePanel(button2.getButton(), panel, new EleitorUpdatePage(frame).getPanel());
        setChangePanel(button3.getButton(), panel, new EleitorDeletePage(frame).getPanel());
        setChangePanel(button4.getButton(), panel, new EleitorListPage(frame).getPanel());

        verifyIfThereIsAnyVoters();
    }



    // get all eleitores to verify what buttons will be shown
    private void verifyIfThereIsAnyVoters(){
        if (VoterController.getAllVoters().size() == 0) {
            // Não há eleitores, desative os botões
            button2.getButton().setEnabled(false); // Desativa o botão "Editar"
            button3.getButton().setEnabled(false); // Desativa o botão "Excluir"
            button4.getButton().setEnabled(false); // Desativa o botão "Listar"
        } else {
            // Há eleitores, ative os botões
            button2.getButton().setEnabled(true); // Ativa o botão "Editar"
            button3.getButton().setEnabled(true); // Ativa o botão "Excluir"
            button4.getButton().setEnabled(true); // Ativa o botão "Listar"
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}