package urnaeletronica.FrontEnd.Pages.Candidate;

import javax.swing.*;

import urnaeletronica.FrontEnd.Components.ButtonComponent;
import urnaeletronica.FrontEnd.Pages.Page;

import java.awt.*;

public class CandidatePage extends Page {
    public JPanel panel;
    ButtonComponent button2 = new ButtonComponent("Deletar Candidato", "primary");
    ButtonComponent button3 = new ButtonComponent("Alterar Candidato", "primary");
    ButtonComponent button4 = new ButtonComponent("Listar Candidatos", "primary");

    public CandidatePage(JFrame frame) {
        super(frame);
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setBackground(new java.awt.Color(0, 0, 0));

        ButtonComponent button = new ButtonComponent("Adicionar Candidato", "primary");
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(button.getButton(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(button2.getButton(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(button3.getButton(), constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(button4.getButton(), constraints);

        setChangePanel(button.getButton(), panel, new CandidateAddPage(frame).getPanel());
        setChangePanel(button2.getButton(), panel, new CandidateDeletePage(frame).getPanel());
        setChangePanel(button3.getButton(), panel, new CandidateUpdatePage(frame).getPanel());
        setChangePanel(button4.getButton(), panel, new CandidateListPage(frame).getPanel());

        verifyIfThereIsAnyCandidate();
    }

    private void verifyIfThereIsAnyCandidate() {
        if (urnaeletronica.BackEnd.Controllers.CandidateController.getAllCandidates().size() == 0) {
            // Não há candidatos, desative os botões
            button2.getButton().setEnabled(false); // Desativa o botão "Deletar Candidato"
            button3.getButton().setEnabled(false); // Desativa o botão "Alterar Candidato"
            button4.getButton().setEnabled(false); // Desativa o botão "Listar Candidatos"
        } else {
            // Há candidatos, ative os botões
            button2.getButton().setEnabled(true); // Ativa o botão "Deletar Candidato"
            button3.getButton().setEnabled(true); // Ativa o botão "Alterar Candidato"
            button4.getButton().setEnabled(true); // Ativa o botão "Listar Candidatos"
        }
    }

    public JPanel getPanel() {
        return panel;
    }

}
