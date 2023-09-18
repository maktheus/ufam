package urnaeletronica.FrontEnd.Pages.Eleitor;

import urnaeletronica.FrontEnd.Components.*;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;




public class EleitorPage {

    private JPanel panel;

    public EleitorPage(JFrame frame){
        ChangePageComponent changePageComponent = new ChangePageComponent();

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
   
        panel.setPreferredSize(new java.awt.Dimension(0, 400));
        panel.setBackground(new java.awt.Color(0, 0, 0));

        Button button = new Button("Cadastrar", "primary");
        Button button2 = new Button("Editar", "primary");
        Button button3 = new Button("Excluir", "primary");
        Button button4 = new Button("Listar", "primary");

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weighty = 0.2;
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

        changePageComponent.setChangePanel(button.getButton(), frame, panel, new EleitorAddPage(frame).getPanel(), 0.7);
        changePageComponent.setChangePanel(button2.getButton(), frame, panel, new EleitorUpdatePage(frame).getPanel(), 0.7);
        changePageComponent.setChangePanel(button3.getButton(), frame, panel, new EleitorDeletePage(frame).getPanel(), 0.7);
        changePageComponent.setChangePanel(button4.getButton(), frame, panel, new EleitorListPage(frame).getPanel(), 0.7);

    }

    public JPanel getPanel() {
        return panel;
    }
}