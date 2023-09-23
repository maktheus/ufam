package urnaeletronica.FrontEnd.Frames.ResultsFrames;

import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;


import urnaeletronica.BackEnd.Controllers.VoteController;
import urnaeletronica.FrontEnd.Frames.Frame;

public class ResultsFrame extends Frame {
    private JPanel panel;
    private String winner = VoteController.getWinner();
    private Map<String, Integer> results = VoteController.getAllVotes();
    private JFrame frame = new JFrame("Resultado da Eleição");

    public ResultsFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.getContentPane().setBackground(new java.awt.Color(27, 154, 170));
        frame.setResizable(false);
        frame.setLayout(new java.awt.GridBagLayout());
        

        
        panel = new JPanel();
        panel.setBackground(new java.awt.Color(0, 0, 0));

        JLabel label = new JLabel("Resultado da Eleição");

        GridBagConstraints constraints = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());
        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(label, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(new JLabel("Vencedor: " + winner), constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(new JLabel("Candidatos: "), constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(new JLabel("Votos: "), constraints);

        int i = 4;
        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            constraints.gridx = 1;
            constraints.gridy = i;
            panel.add(new JLabel(entry.getKey()), constraints);

            constraints.gridx = 2;
            constraints.gridy = i;
            panel.add(new JLabel(entry.getValue().toString()), constraints);
            i++;
        }

        constraints.gridx = 1;
        constraints.gridy = i;
        panel.add(new JLabel("Total de votos: " + VoteController.getTotalVotes()), constraints);

        constraints.gridx = 1;
        constraints.gridy = i + 1;
        panel.add(new JLabel("Total de votos nulos: " + VoteController.getNullVotes()), constraints);

        constraints.gridx = 1;
        constraints.gridy = i + 2;
        panel.add(new JLabel("Total de votos brancos: " + VoteController.getWhiteVotes()), constraints);

        constraints.gridx = 1;
        constraints.gridy = i + 3;
        panel.add(new JLabel("Total de votos válidos: " + VoteController.getValidVotes()), constraints);

        frame.add(panel);
    }

    public String getResults() {
        String winner = VoteController.getWinner();
        return winner;
    }

    public JFrame getFrame() {
        return frame;
    }

}
