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

        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new java.awt.Font("Martian Mono", 0, 20));
        button.setForeground(new java.awt.Color(0, 0, 0));

        button2.setBorderPainted(false);
        button2.setFocusPainted(false);
        button2.setFont(new java.awt.Font("Martian Mono", 0, 20));
        button2.setForeground(new java.awt.Color(0, 0, 0));

        button3.setBorderPainted(false);
        button3.setFocusPainted(false); 
        button3.setFont(new java.awt.Font("Martian Mono", 0, 20));
        button3.setForeground(new java.awt.Color(0, 0, 0));

        button4.setBorderPainted(false);
        button4.setFocusPainted(false);
        button4.setFont(new java.awt.Font("Martian Mono", 0, 20));
        button4.setForeground(new java.awt.Color(0, 0, 0));



        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(button, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(button2, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(button3, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(button4, constraints);

        button.addActionListener(e -> {
            frame.getContentPane().remove(panel);
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.weighty = 0.7; // 70%
            constraints.fill = GridBagConstraints.BOTH;

            frame.getContentPane().add(new CandidateAddPage(frame).getPanel(), constraints);
            frame.revalidate();
            frame.repaint();
        });


        button2.addActionListener(e -> {
            frame.getContentPane().remove(panel);
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.weighty = 0.7; // 70%
            constraints.fill = GridBagConstraints.BOTH;

            frame.getContentPane().add(new CandidateDeletePage(frame).getPanel(), constraints);
            frame.revalidate();
            frame.repaint();

        });

        button3.addActionListener(e -> {
            frame.getContentPane().remove(panel);
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            constraints.weighty = 0.7; // 70%
            constraints.fill = GridBagConstraints.BOTH;

            frame.getContentPane().add(new CandidateUpdatePage(frame).getPanel(), constraints);
            frame.revalidate();
            frame.repaint();

        });

        

    }

    public JPanel getPanel() {
        return panel;
    }

}
