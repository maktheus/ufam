package urnaeletronica.FrontEnd.Pages.Eleitor;
import urnaeletronica.FrontEnd.Pages.Page;
import urnaeletronica.BackEnd.Controllers.VoterController;
import urnaeletronica.FrontEnd.Components.*;



import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;




public class EleitorPage extends Page{

    private JPanel panel;

    public EleitorPage(JFrame frame) {
        super(frame);
        getEleitores();
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
   
        panel.setPreferredSize(new java.awt.Dimension(0, 400));
        panel.setBackground(new java.awt.Color(0, 0, 0));

        ButtonComponent button = new ButtonComponent("Cadastrar", "primary");
        ButtonComponent button2 = new ButtonComponent("Editar", "primary");
        ButtonComponent button3 = new ButtonComponent("Excluir", "primary");
        ButtonComponent button4 = new ButtonComponent("Listar", "primary");

        constraints.anchor = GridBagConstraints.CENTER;

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

        setChangePanel(button.getButton(), panel, new EleitorAddPage(frame).getPanel());
        setChangePanel(button2.getButton(), panel, new EleitorUpdatePage(frame).getPanel());
        setChangePanel(button3.getButton(), panel, new EleitorDeletePage(frame).getPanel());
        setChangePanel(button4.getButton(), panel, new EleitorListPage(frame).getPanel());
    }

    //get all eleitores to verify what buttons will be shown
    private void getEleitores(){
        if(VoterController.getAllVoters() == null){
            //hide buttons

        }

    }

    public JPanel getPanel() {
        return panel;
    }
}