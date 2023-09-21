package urnaeletronica.FrontEnd.Pages.Eleitor;
import urnaeletronica.BackEnd.Controllers.VoterController;
import urnaeletronica.FrontEnd.Pages.Page;

import java.awt.*;
import javax.swing.*;

public class EleitorDeletePage extends Page {
    public JPanel panel;
    public JLabel label;
    public JTextField textField;
    public JButton button;

    public EleitorDeletePage(JFrame frame) {
        super(frame);
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setBackground(new java.awt.Color(0, 0, 0));
        label = new JLabel("Digite o número do candidato");

        label.setForeground(new java.awt.Color(255, 255, 255));
        textField = new JTextField();
        button = new JButton("Confirmar");
        button.setBackground(new java.awt.Color(0, 0, 0));
        button.setForeground(new java.awt.Color(255, 255, 255));

        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(label, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(textField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(button, constraints);

        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteEleitor(textField.getText());
            }
        });
    }

    void deleteEleitor(String etitulo) {
        if(!VoterController.deleteVoter(etitulo)){
            JOptionPane.showMessageDialog(null, "Eleitor não encontrado");
        }else{
            JOptionPane.showMessageDialog(null, "Eleitor deletado com sucesso");
            //return to EleitorPage
            setChangePanel(panel, new EleitorPage(frame).getPanel());
        }
    }
    public JPanel getPanel() {
        return panel;
    }
}
