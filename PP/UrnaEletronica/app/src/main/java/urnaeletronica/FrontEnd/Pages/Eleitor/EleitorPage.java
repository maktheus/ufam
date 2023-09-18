package urnaeletronica.FrontEnd.Pages.Eleitor;

import urnaeletronica.FrontEnd.Components.*;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;




public class EleitorPage {

    private JPanel panel;

    public EleitorPage(JFrame frame){
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
   
        panel.setPreferredSize(new java.awt.Dimension(0, 400));
        panel.setBackground(new java.awt.Color(0, 0, 0));

        Button button = new Button("Cadastrar", "primary");
        Button button2 = new Button("Editar", "primary");
        Button button3 = new Button("Excluir", "primary");
        Button button4 = new Button("Listar", "primary");

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

        button.getButton().addActionListener(e -> {
            frame.getContentPane().remove(panel);
            frame.getContentPane().add(new EleitorAddPage(frame).getPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
        });

        button2.getButton().addActionListener(e -> {
            frame.getContentPane().remove(panel);
            frame.getContentPane().add(new EleitorUpdatePage(frame).getPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
        });

        button3.getButton().addActionListener(e -> {
            frame.getContentPane().remove(panel);
            frame.getContentPane().add(new EleitorDeletePage(frame).getPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
        });

        button4.getButton().addActionListener(e -> {
            frame.getContentPane().remove(panel);
            frame.getContentPane().add(new EleitorList(frame).getPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
        });

    }

    public JPanel getPanel() {
        return panel;
    }
}