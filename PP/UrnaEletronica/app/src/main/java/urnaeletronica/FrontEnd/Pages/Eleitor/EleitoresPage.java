package urnaeletronica.FrontEnd.Pages.Eleitor;
import urnaeletronica.FrontEnd.Components.*;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;



public class EleitoresPage {

    private JPanel panel;

    public EleitoresPage(){
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
   
        panel.setPreferredSize(new java.awt.Dimension(0, 400));
        panel.setBackground(new java.awt.Color(0, 0, 0));

        Button button1 = new Button("Cadastrar", "primary");
        Button button2 = new Button("Editar", "primary");
        Button button3 = new Button("Excluir", "primary");
        Button button4 = new Button("Listar", "primary");

        constraints.weightx = 0.8;
        constraints.weighty = 1.0;

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(button1.getButton(), constraints);

        constraints.gridy = 1;
        panel.add(button2.getButton(), constraints);

        constraints.gridy = 2;
        panel.add(button3.getButton(), constraints);

        constraints.gridy = 3;
        panel.add(button4.getButton(), constraints);


    }

    public JPanel getPanel() {
        return panel;
    }
    
}
