package urnaeletronica.FrontEnd.Pages.Candidate;

import javax.swing.*;
import java.awt.*;

public class CandidatePage {
    public JPanel panel;

    public CandidatePage() {

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setBackground(new java.awt.Color(0, 0, 0));

        JButton button = new JButton("Adicionar Candidato");
        JButton button2 = new JButton("Deletar Candidato");
        JButton button3 = new JButton("Alterar Candidato");
        JButton button4 = new JButton("Listar Candidatos");
    }

    public JPanel getPanel() {
        return panel;
    }

}
