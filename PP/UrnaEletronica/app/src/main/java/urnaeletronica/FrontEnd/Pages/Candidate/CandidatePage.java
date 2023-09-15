package urnaeletronica.FrontEnd.Pages.Candidate;

import javax.swing.*;
import java.awt.*;

public class CandidatePage {
    public JPanel panel;

    public CandidatePage(JFrame frame) {

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setBackground(new java.awt.Color(0, 0, 0));

        JButton button = new JButton("Adicionar Candidato");
        JButton button2 = new JButton("Deletar Candidato");
        JButton button3 = new JButton("Alterar Candidato");
        JButton button4 = new JButton("Listar Candidatos");

        button.setBackground(new java.awt.Color(255, 255, 255));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new java.awt.Font("Martian Mono", 0, 20));
        button.setForeground(new java.awt.Color(0, 0, 0));

        button2.setBackground(new java.awt.Color(255, 255, 255));
        button2.setBorderPainted(false);
        button2.setFocusPainted(false);
        button2.setFont(new java.awt.Font("Martian Mono", 0, 20));
        button2.setForeground(new java.awt.Color(0, 0, 0));


        
    }

    public JPanel getPanel() {
        return panel;
    }

}
