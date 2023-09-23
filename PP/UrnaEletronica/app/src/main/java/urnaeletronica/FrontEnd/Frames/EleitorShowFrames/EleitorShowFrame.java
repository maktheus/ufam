package urnaeletronica.FrontEnd.Frames.EleitorShowFrames;

import java.awt.*;
import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import urnaeletronica.BackEnd.Models.Voter;
import urnaeletronica.FrontEnd.Frames.Frame;


public class EleitorShowFrame extends Frame {

    private static JFrame frame;
    private JLabel label1 = new JLabel("Nome");
    private JLabel label2 = new JLabel("CPF");
    private JLabel label3 = new JLabel("Titulo de Eleitor");

    
    private JButton close = new JButton("Fechar");
    

    
    public EleitorShowFrame(Voter eleitor) {
       
       FlatLightLaf.setup();
        label1.setText(eleitor.getName());
        label2.setText(eleitor.getCpf());
        label3.setText(eleitor.getEtitulo());


        // Landing Page screen
        JFrame frame = new JFrame("Dados Eleitor");
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

        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frame.dispose();
            }
        });
    }

    public void setNome(String nome){
        label1.setText(nome);
    }

    public void setCPF(String cpf){
        label2.setText(cpf);
    }

    public void setTitulo(String titulo){
        label3.setText(titulo);
    }


    public JFrame getFrame(){
        return frame;
    }
}
