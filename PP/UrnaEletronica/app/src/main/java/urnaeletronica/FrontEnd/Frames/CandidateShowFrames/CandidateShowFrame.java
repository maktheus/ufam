package urnaeletronica.FrontEnd.Frames.CandidateShowFrames;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.*;

import com.formdev.flatlaf.FlatLightLaf;

import urnaeletronica.BackEnd.Models.Candidate;
import urnaeletronica.BackEnd.Models.Voter;

public class CandidateShowFrame {
    
    private static JFrame frame;
    private JLabel label1 = new JLabel("Nome");
    private JLabel label2 = new JLabel("CPF");
    private JLabel label3 = new JLabel("Titulo de candidato");
    private JLabel label4 = new JLabel("Numero do candidato");
    
    private JButton close = new JButton("Fechar");
    

    
    public CandidateShowFrame(Candidate candidato, Voter eleitor) {
    
        FlatLightLaf.setup();
        
        label1.setText(eleitor.getName());
        label2.setText(eleitor.getCpf());
        label3.setText(candidato.getEtitulo());
        label4.setText(candidato.getCandidateNumber());


        // Landing Page screen
        JFrame frame = new JFrame("Dados Candidato");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 946);
        frame.getContentPane().setBackground(new java.awt.Color(248, 255, 229));
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());
        frame.setVisible(true);

        GridBagConstraints constraints = new GridBagConstraints();


        // put on center of screen
        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weighty = 0.2;
        frame.add(label1, constraints);

        constraints.gridy = 1;
        frame.add(label2, constraints);

        constraints.gridy = 2;
        frame.add(label3, constraints);

        constraints.gridy = 3;
        frame.add(close, constraints);

        constraints.gridy = 4;
        frame.add(label4, constraints);

        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frame.dispose();
            }
        });
    }

    public JFrame getFrame(){
        return frame;
    }

}
